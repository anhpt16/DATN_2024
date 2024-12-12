import { showNotification } from "../ui/notification.js";
import { ImageModal } from "./image_modal.js";
import accountService from "../service/AccountService.js";

$(document).ready(function() {
    const changeAvatar = $("#change-avatar");
    const imageModal = $("#image-modal");
    const avatarSelected = $("#image-modal #image-value-selected");
    //user info
    const accountAvatar = $("#account-avatar");
    const accountUsername = $("#account-username");
    const accountDisplayName = $("#account-display-name");
    const accountEmail = $("#account-email");
    const accountPhone = $("#account-phone");
    // change
    const changeDisplayName = $("#change-display-name");
    const changeEmail = $("#change-email");
    const changePhone = $("#change-phone");
    //message
    const errorMessage = $("#error-message");
    const message = $("#error-message .error-content");

    const galleryConfig = {
        galleryContainer: '#image-modal .list-media-container',
        fileUpload: '#file-upload',
        imageSearchCb: '#image-search-checkbox',
        imageSearchInput: '#image-search-input',
        imageSearchBtn: '#image-search-btn',
        imageSortOrder: '#image-sort-order',
        prevPage: '#image-modal-prev-page',
        nextPage: '#image-modal-next-page',
        refreshBtn: '#image-modal-refresh-btn',
        imageModal: '#image-modal', // Modal container
    };
    const modalImage = new ImageModal(galleryConfig);


    // Đổi ảnh đại diện
    changeAvatar.on('click', function() {
        imageModal.find('.modal').modal('show');
        modalImage.onModalShow();
        modalImage.init();
    })
    changeDisplayName.on('click', async function() {
        let queryString = '';
        let displayName = accountDisplayName.val().trim();
        if (!displayName && displayName == '') {
            return;
        }
        queryString += "&displayName=" + displayName;
        try {
            const response = await accountService.updateAccountDisplayName(queryString);
            showNotification('success', '', 'Cập nhật thành công');
            await renderDisplayName();
        } catch (error) {
            console.log("Error: " + error);
            showNotification('error', '', 'Cập nhật thất bại');
            message.text(error.message);
            errorMessage.removeClass('d-none');
        }
    })
    changeEmail.on('click', async function() {
        let queryString = '';
        let email = accountEmail.val().trim();
        if (!email && email == '') {
            return;
        }
        queryString += "&email=" + email;
        try {
            const response = await accountService.updateAccountEmail(queryString);
            showNotification('success', '', 'Cập nhật thành công');
            await renderEmail();
        } catch (error) {
            console.log("Error: " + error);
            showNotification('error', '', 'Cập nhật thất bại');
            message.text(error.message);
            errorMessage.removeClass('d-none');
        }
    })
    changePhone.on('click', async function() {
        let queryString = '';
        let phone = accountPhone.val().trim();
        if (!phone && phone == '') {
            return;
        }
        queryString += "&phone=" + phone;
        try {
            const response = await accountService.updateAccountPhone(queryString);
            showNotification('success', '', 'Cập nhật thành công');
            await renderPhone();
        } catch (error) {
            console.log("Error: " + error);
            showNotification('error', '', 'Cập nhật thất bại');
            message.text(error.message);
            errorMessage.removeClass('d-none');
        }
    })
    errorMessage.on('click', '.close-icon i', function() {
        message.empty();
        errorMessage.addClass('d-none');
    })

    // Người dùng chọn ảnh
    avatarSelected.on('click', async function() {
        const selectedValue = imageModal.find('input[name="selectedImage"]:checked').val();
        if (selectedValue == null || selectedValue === '') {
            showNotification('warning', 'Cảnh báo', 'Chưa chọn ảnh');
        }
        console.log(selectedValue);
        try {
            const response = await accountService.updateAccountAvatar(selectedValue);
            showNotification('success', '', 'Cập nhật thành công');
            imageModal.find('.modal').modal('hide');
            modalImage.onModalHide();
            await renderAvatar();
        } catch (error) {
            console.log("Error: " + error);
            showNotification('error', '', 'Cập nhật thất bại');
        }
    })

    // Đóng modal
    imageModal.on('hidden.bs.modal', function () {
        modalImage.onModalHide();
    })

    // Render
    async function renderAvatar() {
        try {
            const response = await accountService.getAccountInfo();
            if (response.avatarUrl) {
                if (response.avatarUrl === '') {
                    accountAvatar.attr('src', '/images/user_image_default.jpg');
                } else {
                    accountAvatar.attr('src', response.avatarUrl);
                }
            }
        } catch (error) {
            console.log("Error: " + error);
        }
    }
    async function renderDisplayName() {
        try {
            const response = await accountService.getAccountInfo();
            if (response.displayName) {
                accountDisplayName.text(response.displayName);
            }
        } catch (error) {
            console.log("Error: " + error);
        }
    }
    async function renderEmail() {
        try {
            const response = await accountService.getAccountInfo();
            if (response.email) {
                accountEmail.text(response.email);
            }
        } catch (error) {
            console.log("Error: " + error);
        }
    }
    async function renderPhone() {
        try {
            const response = await accountService.getAccountInfo();
            if (response.phone) {
                accountPhone.text(response.phone);
            }
        } catch (error) {
            console.log("Error: " + error);
        }
    }

})