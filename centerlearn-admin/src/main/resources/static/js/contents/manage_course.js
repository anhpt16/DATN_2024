
import courseService from "../service/CourseService.js";
import courseUI from "../ui/CourseUI.js";
import subjectService from "../service/SubjectService.js";
import accountService from "../service/AccountService.js";
import { showNotification } from "../ui/notification.js";
import { showConfirmation } from "../ui/notification.js";
import { ImageModal } from "./image_modal.js";

$(document).ready(function() {
    let currentContext = null;
    const tbodyEl = $("#table-course");
    const infoModal = $("#info-modal")
    //image modal
    const imageModal = $("#image-modal");
    const imageSelected = $("#image-modal #image-value-selected");
    //image
    const deleteImageOnCre = $("#delete-image-create");
    const deleteImageOnUp = $("#delete-image-update");
    const imageSubjectContainer = $("#form-add-course .image-subject-container");
    //message
    const errorMessage = $("#error-message");
    const message = $("#error-message .error-content");
    //search
    const searchCourseCheckbox = $("#search-course-checkbox");
    const searchCourseContent = $("#search-course-content");
    const searchCourseButton = $("#search-course-btn");
    const searchCourseType = $("#select-course-type");
    const searchCourseStatus = $("#select-course-status");
    const searchCourseSortOrder = $("#course-sort-order");
    //refresh
    const prevPage = $("#prev-page");
    const nextPage = $("#next-page");
    const refreshBtn = $('#refresh-btn');
    // edit modal
    const editModal = $("#edit-modal");
    const addModal = $("#add-modal");
    const saveEditModal = $("#save-course-btn");
    const courseEditImage = $("#courseEditImage");
    const courseEditId = $("#courseEditId");
    const courseEditStatus = $("#courseEditStatus");
    const courseEditCode = $("#courseEditCode");
    const courseEditDisplayName= $("#courseEditDisplayName");
    const courseEditCourseType= $("#courseEditCourseType");
    const courseEditPrice= $("#courseEditPrice");
    const courseEditDescription= $("#courseEditDescription");

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
            courseUI.renderImageOnCreate(imageId, imageUrl);
            imageModal.find('.modal').modal('hide');
            modalImage.onModalHide();
        }
        // Nếu ảnh được chọn tại phần cập nhật môn học
        else if (currentContext === "update") {
            courseUI.renderImageOnUpdate(imageId, imageUrl);
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

    $("#form-add-course").on('submit', async function(event) {
        event.preventDefault();
        let code = $("#add-course-code").val().trim();
        let displayName = $("#add-course-display-name").val().trim();
        let courseType = $("#add-course-type").val().trim();
        let price = $("#add-course-price").val().trim();
        let imageId = imageSubjectContainer.attr('data-id');
        let description = $("#add-course-description").val().trim();
        let formData = {};
        if (code && code !== "") {
            formData.code = code;
        }
        if (displayName && displayName !== "") {
            formData.displayName = displayName;
        }
        if (description && description !== "") {
            formData.description = description;
        }
        if (imageId && imageId > 0) {
            formData.imageId = imageId;
        }
        if (price && price > 0) {
            formData.price = price;
        }
        if (courseType && courseType !== "") {
            formData.courseType = courseType;
        }
        console.log(formData)
        try {
            const response = await courseService.addCourse(formData);
            showNotification('success', '', 'Tạo thành công');
            $("#add-course-code").val('');
            $("#add-course-display-name").val('');
            $("#add-course-price").val('');
            imageSubjectContainer.empty();
            imageSubjectContainer.removeAttr('data-id');
            $("#add-course-description").val('');
        } catch (error) {
            console.log("Error: " + error);
            console.log('error', '', 'Tạo thất bại');            
        }
    })

    // Đóng mở tìm kiếm
    searchCourseCheckbox.change(function() {
        const isChecked = $(this).prop('checked');
        if (!$(this).prop('checked')) {
            searchCourseContent.prop('disabled', true);
            searchCourseButton.prop('disabled', true);
        } else {
            searchCourseContent.prop('disabled', false);
            searchCourseButton.prop('disabled', false);
        }
    })

    // Thay đổi điều kiện lọc
    searchCourseCheckbox.on('click', function() {
        if (!$(this).prop('checked')) {
            getCourseByFilter();
        }
    });
    searchCourseType.on('change', function() {
        getCourseByFilter();
    });
    searchCourseStatus.on('change', function() {
        getCourseByFilter();
    });
    searchCourseButton.on('click', function() {
        getCourseByFilter();
    });
    searchCourseSortOrder.on('change', function() {
        getCourseByFilter();
    });
    refreshBtn.on('click', function() {
        getCourseByFilter(currentPage);
    })

    // Lấy danh sách khóa học
    async function getCourseByFilter(page = 0, size = 10) {
        let queryString = '';
        queryString += "&page=" + page;
        queryString += "&size=" + size;
        let keyword = searchCourseContent.val();
        let status = searchCourseStatus.val();
        let type = searchCourseType.val();
        let sortOrder = searchCourseSortOrder.val();
        if (searchCourseCheckbox.prop('checked')) {
            if (keyword !== null & keyword !== undefined && keyword !== '') {
                queryString += "&keyword=" + keyword;
            }
        }
        if (type !== null && type !== undefined && type !== '') {
            queryString += "&courseType=" + type;
        }
        if (status !== null && status !== undefined && status !== '') {
            queryString += "&status=" + status;
        }
        if (sortOrder !== null & sortOrder !== undefined && sortOrder !== '') {
            queryString += "&sortOrder=" + sortOrder;
        }
        console.log(queryString);
        try {
            const response = await courseService.getCourseFilter(queryString);
            console.log(response.items)
            courseUI.renderTable(response.items);
            currentPage = response.currentPage;
            totalPage = response.totalPage;
            validatePageNumber(currentPage, totalPage);
        } catch (error) {
            console.log("Error: " + error);
        }
    }

    // Xem chi tiết một môn học
    tbodyEl.on('click', '.view-btn', async function() {
        const getRow = $(this).closest('tr');
        const courseId = getRow.attr('data-id');
        try {
            const response = await courseService.getCourseById(courseId);
            courseUI.renderInfoDetail(response);
            infoModal.find('.modal').modal('show');
        } catch (error) {
            console.log("Error: " + error);
        }
    })
    // Đóng modal chi tiết môn học
    $(document).on('hidden.bs.modal', '#infoModal', function() {
        courseUI.closeInfoDetail();
    })

    // Cập nhật một môn học
    tbodyEl.on('click', '.edit-btn', async function() {
        const getRow = $(this).closest('tr');
        const courseId = getRow.attr('data-id');
        try {
            const response = await courseService.getCourseById(courseId);
            // Lấy danh sách trạng thái của môn học
            const statuses = await courseService.getCourseStatus();
            const types = await courseService.getCourseTypes();
            // Kiểm tra dữ liệu nhận được và gán với giá trị đầu
            console.log(types);
            setOriginValue(response);
            // Hiển thị dữ liệu trong edit modal
            courseUI.renderEditModal(response, statuses, types);
            editModal.find('.modal').modal('show');
        } catch (error) {
            console.log("Error: " + error);
        }
    })
    // Đóng modal cập nhật môn học
    $(document).on('hidden.bs.modal', '#editModal', function() {
        courseUI.closeEditModal();
    })

    // Khi nhấn nút lưu trong modal cập nhật khóa học
    saveEditModal.on('click', async function() {
        console.log("done")
        const courseId = courseEditId.attr('data-id');
        let formData = {};
        // Kiểm tra ảnh
        const currentImageState = courseEditImage.find('img').attr('data-id');
        const currentImageUrl = courseEditImage.find('img').attr('src');
        if (currentImageState !== undefined && currentImageState !== null) {
            if (imageUrlOrigin == null) {
                formData.imageId = currentImageState;
            } else {
                if (imageUrlOrigin !== currentImageUrl) {
                    formData.imageId = currentImageState;
                }
            }
        }
        // Kiểm tra trạng thái
        const currentStatusState = courseEditStatus.val();
        if (statusOrigin == undefined || statusOrigin == null) {
            if (currentStatusState !== null && currentStatusState !== undefined && currentStatusState !== '') {
                formData.status = currentStatusState;
            }
        } else {
            if (currentStatusState !== statusOrigin) {
                formData.status = currentStatusState;
            }
        }
        // Kiểm tra loại khóa học
        const currentTypeState = $("#courseEditCourseType option:selected").text();
        if (courseTypeOrigin == undefined || courseTypeOrigin == null) {
            if (currentTypeState !== null && currentTypeState !== undefined && currentTypeState !== '') {
                formData.courseType = currentTypeState;
            }
        } else {
            if (currentTypeState !== courseTypeOrigin) {
                formData.courseType = currentTypeState;
            }
        }
        // kiểm tra giá
        const currentPrice = courseEditPrice.val();
        if (currentPrice == null || currentPrice == undefined || currentPrice ===  "") {
            showNotification('warning', '', "Giá không được để trống");
            return;
        }
        if (priceOrigin == undefined || priceOrigin == null) {
            formData.price = currentPrice;
        } else {
            if (currentPrice !== priceOrigin) {
                formData.price = currentPrice;
            }
        }
        // Kiểm tra code
        const currentCodeState = courseEditCode.val().trim();
        if (currentCodeState == null || currentCodeState == undefined || currentCodeState === '') {
            showNotification('warning', '', 'Mã khóa học không được để trống')
            return;
        }
        if (codeOrigin == undefined || codeOrigin == null) {
            if (currentCodeState !== null && currentCodeState !== undefined && currentCodeState !== '') {
                formData.code = currentCodeState;
            }
        } else {
            if (currentCodeState !== codeOrigin) {
                formData.code = currentCodeState;
            }
        }
        // Kiểm tra tên hiển thị
        const currentDisplayNameState = courseEditDisplayName.val().trim();
        if (currentDisplayNameState == null || currentDisplayNameState == undefined || currentDisplayNameState === '') {
            showNotification('warning', '', 'Tên hiển thị không được để trống')
            return;
        }
        if (displayNameOrigin == undefined || displayNameOrigin == null) {
            if (currentDisplayNameState !== null && currentDisplayNameState !== undefined && currentDisplayNameState !== '') {
                formData.displayName = currentDisplayNameState;
            }
        } else {
            if (currentDisplayNameState !== displayNameOrigin) {
                formData.displayName = currentDisplayNameState;
            }
        }
        // Kiểm tra mô tả
        const currentDescriptionState = courseEditDescription.val().trim();
        if (descriptionOrigin == undefined || descriptionOrigin == null) {
            if (currentDescriptionState !== null && currentDescriptionState !== undefined && currentDescriptionState !== '') {
                formData.description = currentDescriptionState;
            }
        } else {
            if (currentDescriptionState !== descriptionOrigin) {
                formData.description = currentDescriptionState;
            }
        }
        console.log(formData);
        console.log(courseId);
        try {
            const response = await courseService.updateCourse(courseId, formData);
            showNotification('success', '', 'Cập nhật thành công');
            courseUI.closeEditModal();
            editModal.find('.modal').modal('hide');
        } catch (error) {
            console.log("Error: " + error);
            showNotification('error', '', 'Cập nhật thất bại');
        } finally {
            getCourseByFilter(currentPage);
            setNull();
        }
  
    });

    // Thêm nội dung cho khóa học
    tbodyEl.on('click', '.add-btn', async function() {
        const getRow = $(this).closest('tr');
        const courseId = getRow.attr('data-id');
        if (courseId == null && courseId == undefined) {
            return;
        }
        try {
            // Lấy chi tiết khóa học
            // Lấy danh sách môn học
            // lấy danh sách quản lý
            // Lấy danh sách các môn học - giáo trình của khóa học
            // lấy danh sách các quản lý của khóa học
            const course = await courseService.getCourseById(courseId);
            const subjects = await subjectService.getAllSubjectShort();
            const managers = await accountService.getAccountManager();
            const subjectTextbooks = await courseService.getCourseSubjectByCourseId(courseId);
            courseUI.renderAddModal(course, subjects, managers, subjectTextbooks);
            addModal.find('.modal').modal('show');
        } catch (error) {
            console.log("Error: " + error);
        }
    });
    // Đóng modal cập nhật môn học
    $(document).on('hidden.bs.modal', '#addModal', function() {
        courseUI.closeAddModal();
    })

    // Khi chọn một môn học
    $("#select-subject").on('change', async function() {
        let subjectId = $("#select-subject").val();
        if (subjectId == null || subjectId == undefined || subjectId === "") {
            return;
        }
        try {
            const response = await subjectService.getTextbooksBySubjectId(subjectId);
            console.log(response)
            courseUI.renderTextbookSelect(response);
        } catch (error) {
            console.log("Error: " + error);
        }
    })

    // Khi nhấn thêm giáo trình
    $("#add-subject-btn").on('click', async function() {
        let courseId = $("#courseAddId").attr('data-id');
        let subjectId = $("#select-subject").val();
        let textbookId = $("#select-textbook").val();
        if (courseId == null || courseId == undefined || courseId === ""
            || subjectId == null || subjectId == undefined || subjectId === ""
            || textbookId == null || textbookId == undefined || textbookId === ""
        ) {
            return;
        }
        try {
            const response = await courseService.addCourseSubject(courseId, subjectId, textbookId);
            showNotification('success', '', 'Thêm thành công');
        } catch (error) {
            console.log("Error: " + error);
            showNotification('error', '', 'Thêm không thành công')
            return;
        }
        try {
            const subjects = await subjectService.getAllSubjectShort();
            const subjectTextbooks = await courseService.getCourseSubjectByCourseId(courseId); 
            courseUI.renderTableSubject(subjectTextbooks);
            courseUI.renderOptionSubject(subjects, subjectTextbooks);
        } catch (error) {
            console.log("Error: " + error);
        }
    });

    // Khi nhấn xóa một môn học
    $("#table-subject").on('click', '.delete-btn', async function() {
        const getRow = $(this).closest('tr');
        const subjectId = getRow.attr('data-id');
        const courseId = $("#courseAddId").attr('data-id');
        if (courseId == null || courseId == undefined || courseId === ""
            || subjectId == null || subjectId == undefined || subjectId === ""
        ) {
            return;
        }
        try {
            const response = await courseService.deleteCourseSubject(courseId, subjectId);
            showNotification('success', '', 'Xóa thành công');
        } catch (error) {
            console.log("Error: " + error);
            showNotification('error', '', 'Xóa thất bại');
            return;
        }
        try {
            const subjects = await subjectService.getAllSubjectShort();
            const subjectTextbooks = await courseService.getCourseSubjectByCourseId(courseId); 
            courseUI.renderTableSubject(subjectTextbooks);
            courseUI.renderOptionSubject(subjects, subjectTextbooks);
            getRow.remove();
        } catch (error) {
            console.log("Error: " + error);
        }
    })

    // Khi nhấn nút đổi người quản lý
    $("#add-manager-btn").on('click', async function() {
        let managerId = $("#select-manager").val();
        const courseId = $("#courseAddId").attr('data-id');
        if (managerId == null || managerId == undefined || managerId === ""
            || courseId == null || courseId == undefined || courseId === ""
        ) 
        {
            return;
        }
        try {
            const response = await courseService.updateManager(courseId, managerId);
            showNotification('success', '', 'Đổi thành công');
        } catch (error) {
            console.log("Error: " + error);
            showNotification('error', '', 'Xóa thành công');
        }
        try {
            const managers = await accountService.getAccountManager();
            courseUI.renderManagerSelect(managers, managerId);
        } catch (error) {
            console.log("Error: " + error);
        }
    })

    // Chuyển trang
    function validatePageNumber(currentPage, totalPage) {
        nextPage.toggleClass('disabled-link', currentPage >= (totalPage - 1));
        prevPage.toggleClass('disabled-link', currentPage <= 0);
    }
    nextPage.on('click', function() {
        if (currentPage < totalPage - 1) {
            currentPage += 1;
        }            
        getCourseByFilter(currentPage);
    })
    prevPage.on('click', function() {
        if (currentPage > 0) {
            currentPage -= 1;
        }            
        getCourseByFilter(currentPage);
    })

    let codeOrigin = null;//
    let displayNameOrigin = null;//
    let courseTypeOrigin = null;
    let statusOrigin = null;//
    let descriptionOrigin = null;
    let imageUrlOrigin = null;//
    let priceOrigin = null;
    function setNull() {
        codeOrigin = null;
        displayNameOrigin = null;
        courseTypeOrigin = null;
        statusOrigin = null;
        descriptionOrigin = null;
        imageUrlOrigin = null;
        priceOrigin = null;
    }
    function setOriginValue(response) {
        if (response == null) {
            return;
        }
        if (response.code !== null || response.code !== undefined || response.code !== "") {
            codeOrigin = response.code;
        }
        if (response.displayName !== null || response.displayName !== undefined || response.displayName !== "") {
            displayNameOrigin = response.displayName;
        }
        if (response.courseType !== null || response.courseType !== undefined || response.courseType !== "") {
            courseTypeOrigin = response.courseType;
        }
        if (response.status !== null || response.status !== undefined || response.status.name !== "") {
            statusOrigin = response.status.name;
        }
        if (response.description !== null || response.description !== undefined || response.description !== "") {
            descriptionOrigin = response.description;
        }
        if (response.apiUrl !== null || response.apiUrl !== undefined || response.apiUrl !== "") {
            imageUrlOrigin = response.apiUrl;
        }
        if (response.price !== null || response.price !== undefined || response.price !== "") {
            priceOrigin = response.price;
        }
    }
})