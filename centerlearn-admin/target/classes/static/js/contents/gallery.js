// Client-side code: GalleryService.js

import galleryService from "../service/GalleryService.js";
import galleryUI from "../ui/GalleryUI.js";
import { showNotification } from "../ui/notification.js";

$(document).ready(function() {
    const fileInput = $("#file-input"); // Lấy phần tử input file
    const uploadButton = $("#btn-upload"); // Lấy phần tử nút tải lên

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
});
