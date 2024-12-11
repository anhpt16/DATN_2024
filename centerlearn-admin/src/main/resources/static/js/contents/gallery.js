// Client-side code: GalleryService.js

import galleryService from "../service/GalleryService.js";
import galleryUI from "../ui/GalleryUI.js";
import { showConfirmation, showNotification } from "../ui/notification.js";

$(document).ready(function() {
    const fileInput = $("#file-input"); // Lấy phần tử input file
    const uploadButton = $("#btn-upload"); // Lấy phần tử nút tải lên
    const imageSearchCb = $("#image-search-checkbox");
    const imageSearchInput = $("#image-search-input");
    const imageSearchBtn = $("#image-search-btn");
    const imageSortOrder = $("#image-sort-order");
    // Chuyển trang
    const prevPage = $("#prev-page");
    const nextPage = $("#next-page");
    const refreshBtn = $('#refresh-btn');

    // Khi nhấn vào nút tải ảnh lên
    uploadButton.on('click', function() {
        const file = fileInput[0].files[0]; // Lấy file đã chọn
        if (!file) {
            alert("Vui lòng chọn ảnh để tải lên.");
            return;
        }

        // Lấy tên gốc của tệp
        const originalFileName = file.name; // Lấy tên file gốc
        const fileExtension = originalFileName.split('.').pop(); // Lấy đuôi tệp gốc (ví dụ .jpg, .png)
        const baseName = originalFileName.replace(`.${fileExtension}`, ''); // Lấy tên gốc mà không có đuôi

        // Đọc ảnh và chuyển đổi thành WebP
        const reader = new FileReader();
        reader.onload = function(event) {
            const img = new Image();
            img.onload = function() {
                // Tạo một canvas mới để vẽ ảnh lên
                const canvas = document.createElement('canvas');
                const ctx = canvas.getContext('2d');

                // Đặt kích thước canvas bằng với kích thước của ảnh
                canvas.width = img.width;
                canvas.height = img.height;

                // Vẽ ảnh lên canvas
                ctx.drawImage(img, 0, 0);

                // Chuyển canvas thành WebP Blob
                canvas.toBlob(async function(blob) {
                    if (blob) {
                        // Gửi ảnh WebP lên server với tên tệp gốc + đuôi .webp
                        const webpFileName = `${baseName}.webp`; // Tạo tên tệp mới với đuôi .webp
                        await uploadImage(blob, webpFileName);
                    } else {
                        alert("Không thể chuyển đổi ảnh thành WebP.");
                    }
                }, 'image/webp', 0.8); // Chuyển thành WebP với chất lượng 80%
            };
            img.src = event.target.result;
        };
        reader.readAsDataURL(file);
    });

    // Hàm tải ảnh lên
    async function uploadImage(blob, webpFileName) {
        const formData = new FormData();
        // Gửi blob với tên file là tên gốc + đuôi .webp
        formData.append("file", blob, webpFileName); // Sử dụng tên gốc với đuôi .webp

        // Kiểm tra trước khi gửi
        if (formData.has("file")) {
            console.log("File đã được thêm vào FormData.");
        } else {
            console.log("Không có file trong FormData.");
        }

        console.log("FormData contents:");
        for (let pair of formData.entries()) {
            console.log(pair[0] + ', ' + pair[1]); 
        }

        // Gửi ảnh WebP lên server
        try {
            const response = await galleryService.uploadImage(formData);
            showNotification('success', '', 'Tải thành công');
        } catch (error) {
            showNotification('error', '', 'Tải thất bại');
            console.error("Lỗi khi tải ảnh lên:", error);
        }
    }

    // Hiển thị chi tiết ảnh
    $(".list-media-container").on('click', '.media-item a', async function(event) {
        event.preventDefault();
        const mediaId = $(this).data('id');
        // Lấy chi tiết ảnh theo id
        try {
            const response = await galleryService.getImageDetailById(mediaId);
            // Hiển thị
            galleryUI.renderEditModal(response);
        } catch (error) {
            console.log("Error: " + error);
        }
    })
    $("#edit-modal").on('hidden.bs.modal', function () {
        galleryUI.closeEditModal();
    })

    // Chỉnh sửa ảnh
    $("#edit-modal").on('click', "#btn-save", async function() {
        const id = $("#edit-modal #image-detail").data('id');
        const name = $("#edit-image-name").val().trim();
        if ( !name || name === '') {
            return;
        }
        let formData = {
            name: name,
        }
        try {
            const response = await galleryService.updateImageById(id, formData);
            showNotification('success', "", "Sửa thành công");
            $("#edit-modal .modal").modal('hide');
            galleryUI.closeEditModal();
        } catch (error) {
            showNotification("error", "", "Sửa thất bại");
            console.log("Error: " + error);
        }
    })

    // Xóa ảnh
    $(".list-media-container").on('click', '.media-item .delete-btn', async function() {
        const id = $(this).closest('.media-item').find('a').data('id');
        const name = $(this).closest('.media-item').find('a').find('span').text();
        const result = await showConfirmation('delete', 'Xác nhận xóa !', `${name}`);
        if (result) {
            try {
                const response = await galleryService.deleteImageById(id);
                showNotification('success', '', 'Xóa thành công !');
                getGalleryByFilter(currentPage);
            } catch (error) {
                console.log("Error: " + error);
                showNotification('error', '', 'Xóa thất bại');
            }
        }
    })

    // Nút checkbox tìm kiếm
    imageSearchCb.change(function() {
        const isChecked = $(this).prop('checked');
        if (!$(this).prop('checked')) {
            imageSearchInput.prop('disabled', true);
            imageSearchBtn.prop('disabled', true);
        }
        else {
            imageSearchInput.prop('disabled', false);
            imageSearchBtn.prop('disabled', false);
        }
    })

    // Thay đổi điều kiện lọc
    imageSearchCb.on('click', function() {
        if (!$(this).prop('checked')) {
            getGalleryByFilter();
        }
    });
    imageSortOrder.on('change', function() {
        getGalleryByFilter();
    });
    imageSearchBtn.on('click', function() {
        getGalleryByFilter();
    });
    refreshBtn.on('click', function() {
        getGalleryByFilter(currentPage);
    })

    // Lấy danh sách theo bộ lọc
    async function getGalleryByFilter(page = 0, size = 12) {
        let queryString = '';
        queryString += "&page=" + page;
        queryString += "&size=" + size;
        let keyword = imageSearchInput.val().trim();
        let sortOrder = imageSortOrder.val();
        if (imageSearchCb.prop('checked')) {
            if (keyword && keyword !== '') {
                queryString += "&keyword=" + keyword;
            }
        }
        if (sortOrder) {
            queryString += "&sortOrder=" + sortOrder;
        }
        try {
            const response = await galleryService.getGalleryFilter(queryString);
            galleryUI.renderGallery(response.items);
            currentPage = response.currentPage;
            totalPage = response.totalPage;
            validatePageNumber(currentPage, totalPage);
        } catch (error) {
            console.log("Error: " + error);
        }
    }

    // Chuyển trang
    function validatePageNumber(currentPage, totalPage) {
        nextPage.toggleClass('disabled-link', currentPage >= (totalPage - 1));
        prevPage.toggleClass('disabled-link', currentPage <= 0);
    }
    nextPage.on('click', function() {
        if (currentPage < totalPage - 1) {
            currentPage += 1;
        }            
        getGalleryByFilter(currentPage);
    })
    prevPage.on('click', function() {
        if (currentPage > 0) {
            currentPage -= 1;
        }            
        getGalleryByFilter(currentPage);
    })
});
