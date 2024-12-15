

import galleryService from "../service/GalleryService.js";
import { showNotification } from "../ui/notification.js";

export class ImageModal {
    constructor(config) {
        this.galleryContainer = $(config.galleryContainer);
        this.fileUpload = $(config.fileUpload);
        this.imageSearchCb = $(config.imageSearchCb);
        this.imageSearchInput = $(config.imageSearchInput);
        this.imageSearchBtn = $(config.imageSearchBtn);
        this.imageSortOrder = $(config.imageSortOrder);
        this.prevPage = $(config.prevPage);
        this.nextPage = $(config.nextPage);
        this.refreshBtn = $(config.refreshBtn);
        this.imageModal = $(config.imageModal);
        
        this.currentPage = 0;
        this.totalPage = 0;
        this.isUploading = false;
    }

    // Khi modal mở
    onModalShow() {
        // Khởi tạo lại gallery khi modal mở
        this.initGallery();
    }

    // Khi modal đóng
    onModalHide() {
        // Xóa tất cả dữ liệu khi đóng modal
        this.galleryContainer.empty();
        this.imageSearchInput.val('');
        $('input[type="radio"]').prop('checked', false).removeClass('active-select');
    }

    // Khởi tạo gallery
    async initGallery() {
        this.getGalleryByFilter();
        this.handleEvents();
    }

    // Tải ảnh lên
    async uploadImage() {
        if (this.isUploading) return;
        this.isUploading = true;
        const file = this.fileUpload[0].files[0];
        if (!file) {
            alert("Vui lòng chọn ảnh để tải lên.");
            return;
        }
        if (file.type !== 'image/jpeg' && file.type !== 'image/png') {
            alert("Chọn file định dạng jpeg/png !");
            return;
        }

        let formData = new FormData();
        formData.append('file', file);
        try {
            await galleryService.uploadImage(formData);
            showNotification('success', '', 'Tải thành công');
        } catch (error) {
            console.log("Error: " + error);
            showNotification('error', '', 'Tải thất bại');
        } finally {
            this.isUploading = false;
        }
    }

    // Lọc và tìm kiếm ảnh
    async getGalleryByFilter(page = 0, size = 12) {
        let queryString = `&page=${page}&size=${size}`;

        const keyword = this.imageSearchInput.val().trim();
        const sortOrder = this.imageSortOrder.val();

        if (this.imageSearchCb.prop('checked') && keyword !== '') {
            queryString += `&keyword=${keyword}`;
        }
        if (sortOrder) {
            queryString += `&sortOrder=${sortOrder}`;
        }

        try {
            const response = await galleryService.getGalleryFilter(queryString);
            this.renderGallery(response.items);
            this.currentPage = response.currentPage;
            this.totalPage = response.totalPage;
            this.validatePageNumber();
        } catch (error) {
            console.log("Error: " + error);
        }
    }

    // Render ảnh vào gallery
    renderGallery(galleries) {
        this.galleryContainer.empty();

        if (galleries.length === 0) {
            this.galleryContainer.append(`<div>Trống<div>`);
            return;
        }

        galleries.forEach(gallery => {
            let item = `
                <div class="col-lg-2 fade-in media-item">
                    <a href="#" data-id="${gallery.id || ''}" class="text-decoration-none">
                        <div class="image modal-image">
                            <img src="${gallery.url}" alt="Media Image">
                        </div>
                        <div class="content">
                            <span>${gallery.name}</span>
                        </div>
                    </a>
                    <input class="image-select" type="radio" name="selectedImage" value="${gallery.id || ''}">
                </div>
            `;
            this.galleryContainer.append(item);
        });

        setTimeout(() => {
            this.galleryContainer.find('.fade-in').addClass('show');
        }, 100);
    }

    // Xử lý sự kiện chuyển trang
    validatePageNumber() {
        this.nextPage.toggleClass('disabled-link', this.currentPage >= (this.totalPage - 1));
        this.prevPage.toggleClass('disabled-link', this.currentPage <= 0);
    }

    // Cập nhật trang tiếp theo
    nextPageHandler() {
        if (this.currentPage < this.totalPage - 1) {
            this.currentPage += 1;
        }
        this.getGalleryByFilter(this.currentPage);
    }

    // Cập nhật trang trước
    prevPageHandler() {
        if (this.currentPage > 0) {
            this.currentPage -= 1;
        }
        this.getGalleryByFilter(this.currentPage);
    }

    // Các sự kiện cần xử lý
    handleEvents() {
        // Xóa các sự kiện cũ trước khi gắn lại
        this.galleryContainer.off('click', '.media-item a');
        
        this.fileUpload.change(() => this.uploadImage());
        this.imageSearchBtn.click(() => this.getGalleryByFilter());
        this.refreshBtn.click(() => this.getGalleryByFilter(this.currentPage));
        this.prevPage.click(() => this.prevPageHandler());
        this.nextPage.click(() => this.nextPageHandler());
        this.imageSortOrder.change(() => this.getGalleryByFilter());

        this.imageSearchCb.change(() => {
            const isChecked = this.imageSearchCb.prop('checked');
            if (!isChecked) {
                this.imageSearchBtn.prop('disabled', true);
                this.imageSearchInput.prop('disabled', true);
            } else {
                this.imageSearchBtn.prop('disabled', false);
                this.imageSearchInput.prop('disabled', false);
            }
        });

        this.imageSearchCb.click(() => {
            if (!this.imageSearchCb.prop('checked')) {
                this.getGalleryByFilter();
            }
        });

        // Gắn sự kiện click cho các item trong gallery
        this.galleryContainer.on('click', '.media-item a', (event) => {
            event.preventDefault();
            const allRadioBtn = $('.media-item input').removeClass('active-select')
            const radioBtn = $(event.target).closest('.media-item').find('input');
            console.log(radioBtn);

            // Đảm bảo chỉ thay đổi radio khi có sự thay đổi thực sự
            if (radioBtn.prop('checked')) {
                radioBtn.prop('checked', false).removeClass('active-select');
            } else {
                radioBtn.prop('checked', true).addClass('active-select');
            }
        });
    }

    // Khởi tạo
    init() {
        this.handleEvents();
        this.initGallery();
        // Lắng nghe sự kiện modal show và hide
        this.imageModal.on('show.bs.modal', () => this.onModalShow());
        this.imageModal.on('hide.bs.modal', () => this.onModalHide());
    }
}
