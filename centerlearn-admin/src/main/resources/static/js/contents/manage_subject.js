import { showNotification } from "../ui/notification.js";
import { showConfirmation } from "../ui/notification.js";
import { ImageModal } from "./image_modal.js";
import subjectUI from "../ui/SubjectUI.js";
import subjectService from "../service/SubjectService.js";

$(document).ready(function() {
    let currentContext = null;

    const tbodyEl = $("#table-subject");
    //add subject
    const formAddSubject = $("#form-add-subject");
    const addSubjectName = $("#add-subject-name");
    const addSubjectDisplayName = $("#add-subject-display-name");
    const addSubjectDescription = $("#add-subject-description");
    //image modal
    const imageModal = $("#image-modal");
    const imageSelected = $("#image-modal #image-value-selected");
    //subject info modal
    const infoModal = $("#info-modal")
    const imageInfoModal = $("#subjectInfoImage");
    const statusInfoModal = $("#subjectInfoStatus");
    const idInfoModal = $("#subjectInfoId");
    const nameInfoModal = $("#subjectInfoName");
    const displayNameInfoModal = $("#subjectInfoDisplayName");
    const createdAtInfoModal = $("#subjectInfoCreatedAt");
    const updatedAtInfoModal = $("#subjectInfoUpdatedAt");
    const slugInfoModal = $("#subjectInfoSlug");
    const descriptionInfoModal = $("#subjectInfoDescription");
    // subject edit modal
    const editModal = $("#edit-modal");
    const idEditModal = $("#subjectEditId");
    const subjectEditImage = $("#subjectEditImage");
    const saveEditModal = $("#save-subject-btn");
    const statusEditModal = $("#subjectEditStatus");
    const nameEditModal = $("#subjectEditName");
    const displayNameEditModal = $("#subjectEditDisplayName");
    const descriptionEditModal = $("#subjectEditDescription");
    //message
    const errorMessage = $("#error-message");
    const message = $("#error-message .error-content");
    //image
    const deleteImageOnCre = $("#delete-image-create");
    const deleteImageOnUp = $("#delete-image-update");
    const imageSubjectContainer = $("#form-add-subject .image-subject-container");
    //search
    const searchSubjectCheckbox = $("#search-subject-checkbox");
    const searchSubjectType = $("#search-subject-type");
    const searchSubjectContent = $("#search-subject-content");
    const searchSubjectButton = $("#search-subject-btn");
    const searchSubjectStatus = $("#select-subject-status");
    const searchSubjectSortOrder = $("#subject-sort-order");
    //refresh
    const prevPage = $("#prev-page");
    const nextPage = $("#next-page");
    const refreshBtn = $('#refresh-btn');

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

    // Kích hoạt sự kiện mở Image Modal
    $('[data-bs-toggle="image-modal"]').on('click', function () {
        currentContext = $(this).data('context');
        console.log(currentContext)
        $("#image-modal").find('.modal').modal('show');
        modalImage.onModalShow();
        modalImage.init();
    });


    // Khi chọn một ảnh
    imageSelected.on('click', function() {
        // Lấy dữ liệu mà người dùng chọn
        const imageId = imageModal.find('input[name="selectedImage"]:checked').val();
        const imageUrl = imageModal.find('input[name="selectedImage"]:checked').closest('.media-item').children('a').find('img').attr('src');
        // Kiểm tra trước khi điền giá trị
        if (imageId == null || imageId === '' || imageUrl == null || imageUrl === '') {
            showNotification('warning', 'Cảnh báo', 'Chưa chọn ảnh');
            return;
        }
        console.log(imageId);
        console.log(imageUrl);
        // Nếu ảnh được chọn tại phần tạo môn học
        if (currentContext === "create") {
            subjectUI.renderImageOnCreate(imageId, imageUrl);
            imageModal.find('.modal').modal('hide');
            modalImage.onModalHide();
        }
        // Nếu ảnh được chọn tại phần cập nhật môn học
        else if (currentContext === "update") {
            subjectUI.renderImageOnUpdate(imageId, imageUrl);
            imageModal.find('.modal').modal('hide');
            modalImage.onModalHide();
        }
    })

    // Xóa ảnh phần tạo môn học
    deleteImageOnCre.on('click', function() {
        imageSubjectContainer.empty();
        imageSubjectContainer.removeAttr('data-id');
    })
    // Xóa ảnh phần cập nhật môn học
    deleteImageOnUp.on('click', function() {
        subjectEditImage.empty();
        subjectEditImage.removeAttr('data-id');
    })

    errorMessage.on('click', '.close-icon i', function() {
        message.empty();
        errorMessage.addClass('d-none');
    })

    // Khi nhấn tạo một môn học
    formAddSubject.on('submit', async function(event) {
        event.preventDefault();
        let formData = {};
        // Lấy dữ liệu
        let name = addSubjectName.val().trim();
        let displayName = addSubjectDisplayName.val().trim();
        let description = addSubjectDescription.val().trim();
        let imageId = imageSubjectContainer.data('id');
        // Validate
        if (name && name !== '') {
            formData.name = name;
        }
        if (displayName && displayName !== '') {
            formData.displayName = displayName;
        }
        if (description && description !== '') {
            formData.description = description;
        }
        if (imageId && typeof imageId === 'number' && imageId > 0) {
            formData.imageId = imageId;
        }
        console.log(formData)
        // Gửi api
        try {
            const response = await subjectService.addSubject(formData);
            showNotification('success', '', 'Thêm thành công');
            addSubjectName.val('');
            addSubjectDisplayName.val('');
            addSubjectDescription.val('');
            imageSubjectContainer.empty();
            imageSubjectContainer.removeAttr('data-id');
        } catch (error) {
            showNotification('error', '', 'Thất bại');
            message.text(error.message);
            console.log(error)
            errorMessage.removeClass('d-none');
        }
    });

    // Xem chi tiết một môn học
    tbodyEl.on('click', '.view-btn', async function() {
        const getRow = $(this).closest('tr');
        const subjectId = getRow.attr('data-id');
        try {
            const response = await subjectService.getSubjectDetailById(subjectId);
            subjectUI.renderInfoDetail(response);
            infoModal.find('.modal').modal('show');
        } catch (error) {
            console.log("Error: " + error);
        }
    })
    // Đóng modal chi tiết môn học
    $(document).on('hidden.bs.modal', '#infoModal', function() {
        subjectUI.closeInfoDetail();
    })

    // Cập nhật một môn học
    tbodyEl.on('click', '.edit-btn', async function() {
        const getRow = $(this).closest('tr');
        const subjectId = getRow.attr('data-id');
        try {
            const response = await subjectService.getSubjectDetailById(subjectId);
            // Lấy danh sách trạng thái của môn học
            const statuses = await subjectService.getSubjectStatuses();
            // Kiểm tra dữ liệu nhận được và gán với giá trị đầu
            setOriginValue(response);
            // Hiển thị dữ liệu trong edit modal
            subjectUI.renderEditModal(response, statuses);
            editModal.find('.modal').modal('show');
        } catch (error) {
            console.log("Error: " + error);
        }
    })
    // Đóng modal cập nhật môn học
    $(document).on('hidden.bs.modal', '#editModal', function() {
        subjectUI.closeEditModal();
    })

    // Khi nhấn nút lưu trong modal cập nhật môn học
    saveEditModal.on('click', async function() {
        console.log("done")
        const subjectId = idEditModal.attr('data-id');
        let formData = {};
        // Kiểm tra ảnh
        const currentImageState = subjectEditImage.find('img').attr('data-id');
        const currentImageUrl = subjectEditImage.find('img').attr('src');
        if (currentImageState !== undefined && currentImageState !== null) {
            if (originSubjectImageUrl == null) {
                formData.imageId = currentImageState;
            } else {
                if (originSubjectImageUrl !== currentImageUrl) {
                    formData.imageId = currentImageState;
                }
            }
        }
        // Kiểm tra trạng thái
        const currentStatusState = statusEditModal.val();
        if (originSubjectStatus == undefined || originSubjectStatus == null) {
            if (currentStatusState !== null && currentStatusState !== undefined && currentStatusState !== '') {
                formData.status = currentStatusState;
            }
        } else {
            if (currentStatusState !== originSubjectStatus) {
                formData.status = currentStatusState;
            }
        }
        // Kiểm tra tên
        const currentNameState = nameEditModal.val().trim();
        if (currentNameState == null || currentNameState == undefined || currentNameState === '') {
            showNotification('warning', '', 'Tên không được để trống')
            return;
        }
        if (originSubjectName == undefined || originSubjectName == null) {
            if (currentNameState !== null && currentNameState !== undefined && currentNameState !== '') {
                formData.name = currentNameState;
            }
        } else {
            if (currentNameState !== originSubjectName) {
                formData.name = currentNameState;
            }
        }
        // Kiểm tra tên hiển thị
        const currentDisplayNameState = displayNameEditModal.val().trim();
        if (currentDisplayNameState == null || currentDisplayNameState == undefined || currentDisplayNameState === '') {
            showNotification('warning', '', 'Tên hiển thị không được để trống')
            return;
        }
        if (originSubjectDisplayName == undefined || originSubjectDisplayName == null) {
            if (currentDisplayNameState !== null && currentDisplayNameState !== undefined && currentDisplayNameState !== '') {
                formData.displayName = currentDisplayNameState;
            }
        } else {
            if (currentDisplayNameState !== originSubjectDisplayName) {
                formData.displayName = currentDisplayNameState;
            }
        }
        // Kiểm tra mô tả
        const currentDescriptionState = descriptionEditModal.val().trim();
        if (originSubjectDescription == undefined || originSubjectDescription == null) {
            if (currentDescriptionState !== null && currentDescriptionState !== undefined && currentDescriptionState !== '') {
                formData.description = currentDescriptionState;
            }
        } else {
            if (currentDescriptionState !== originSubjectDescription) {
                formData.description = currentDescriptionState;
            }
        }
        console.log(formData);
        console.log(subjectId);
        try {
            const response = await subjectService.updateSubject(subjectId, formData);
            showNotification('success', '', 'Cập nhật thành công');
            subjectUI.closeEditModal();
            editModal.find('.modal').modal('hide');
        } catch (error) {
            console.log("Error: " + error);
            showNotification('error', '', 'Cập nhật thất bại');
        } finally {
            getSubjectByFilter(currentPage);
        }
        
    })

    // Đóng mở tìm kiếm
    searchSubjectCheckbox.change(function() {
        const isChecked = $(this).prop('checked');
        if (!$(this).prop('checked')) {
            searchSubjectContent.prop('disabled', true);
            searchSubjectType.prop('disabled', true);
            searchSubjectButton.prop('disabled', true);
        } else {
            searchSubjectContent.prop('disabled', false);
            searchSubjectType.prop('disabled', false);
            searchSubjectButton.prop('disabled', false);
        }
    })

    // Thay đổi điều kiện lọc
    searchSubjectCheckbox.on('click', function() {
        if (!$(this).prop('checked')) {
            getSubjectByFilter();
        }
    });
    searchSubjectStatus.on('change', function() {
        getSubjectByFilter();
    });
    searchSubjectButton.on('click', function() {
        getSubjectByFilter();
    });
    searchSubjectSortOrder.on('change', function() {
        getSubjectByFilter();
    });
    refreshBtn.on('click', function() {
        getSubjectByFilter(currentPage);
    })

    // Lấy danh sách môn học
    async function getSubjectByFilter(page = 0, size = 10) {
        let queryString = '';
        queryString += "&page=" + page;
        queryString += "&size=" + size;
        let keyword = searchSubjectContent.val();
        let status = searchSubjectStatus.val();
        let type = searchSubjectType.val();
        let sortOrder = searchSubjectSortOrder.val();
        if (searchSubjectCheckbox.prop('checked')) {
            if (type !== null & type !== undefined && keyword !== '' && type === '1') {
                queryString += "&name=" + keyword;
            }
            else if (type !== null & type !== undefined && keyword !== '' && type === '2') {
                queryString += "&displayName=" + keyword;
            }
            else {
                console.log("Search Type Subject Invalid");
            }
        }
        if (status !== null && status !== undefined && status !== '') {
            queryString += "&status=" + status;
        }
        if (sortOrder !== null & sortOrder !== undefined && sortOrder !== '') {
            queryString += "&sortOrder=" + sortOrder;
        }
        console.log(queryString);
        try {
            const response = await subjectService.getSubjectFilter(queryString);
            subjectUI.renderTable(response.items);
            currentPage = response.currentPage;
            totalPage = response.totalPage;
            validatePageNumber(currentPage, totalPage);
        } catch (error) {
            console.log("Error: " + error);
        }
    }

    // Gán giá trị đầu
    let originSubjectImageUrl = null;
    let originSubjectStatus = null;
    let originSubjectName = null;
    let originSubjectDisplayName = null;
    let originSubjectDescription = null;
    function setOriginValue(subject) {
        if (subject !== null && subject !== undefined) {
            if (subject.imageUrl !== null && subject.imageUrl !== undefined && subject.imageUrl !== '') {
                originSubjectImageUrl = subject.imageUrl;
            }
            if (subject.status !== null && subject.status !== undefined && subject.status.name !== null && subject.status.name !== undefined) {
                originSubjectStatus = subject.status.name;
            }
            if (subject.name !== null && subject.name !== undefined && subject.name !== '') {
                originSubjectName = subject.name;
            }
            if (subject.displayName !== null && subject.displayName !== undefined && subject.displayName !== '') {
                originSubjectDisplayName = subject.displayName;
            }
            if (subject.description !== null && subject.description !== undefined && subject.description !== '') {
                originSubjectDescription = subject.description;
            }
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
        getSubjectByFilter(currentPage);
    })
    prevPage.on('click', function() {
        if (currentPage > 0) {
            currentPage -= 1;
        }            
        getSubjectByFilter(currentPage);
    })
})