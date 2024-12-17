
import textbookService from "../service/TextbookService.js";
import textbookUI from "../ui/TextbookUI.js";
import { showNotification } from "../ui/notification.js";

$(document).ready(function() {
    //message
    const tbodyEl = $("#table-textbook");
    const errorMessage = $("#error-message");
    const message = $("#error-message .error-content");
    const infoModal = $("#info-modal");
    const editModal = $("#edit-modal");
    // edit
    const textbookEditId= $("#textbookEditId");
    const textbookEditStatus= $("#textbookEditStatus");
    const textbookEditName= $("#textbookEditName");
    const textbookEditAuthor= $("#textbookEditAuthor");
    const textbookEditUrl= $("#textbookEditUrl");
    const textbookEditDescription= $("#textbookEditDescription");
    const saveTextbookBtn = $("#save-textbook-btn");
    // Thêm giáo trình
    const formAddTextbook = $("#form-add-textbook");
    const addTextbookName = $("#add-textbook-name");
    const addTextbookAuthor = $("#add-textbook-author");
    const addTextbookSubject = $("#add-textbook-subject");
    const addTextbookDescription = $("#add-textbook-description");
    // Tìm kiếm
    const searchTextbookCheckbox = $("#search-textbook-checkbox");
    const searchTextbookContent = $("#search-textbook-content");
    const searchTextbookType = $("#search-textbook-type");
    const searchTextbookButton = $("#search-textbook-btn");
    const searchTextbookSubject = $("#select-textbook-subject");
    const searchTextbookStatus = $("#select-textbook-status");
    const searchTextbookSortOrder = $("#textbook-sort-order");
    // Chuyển trang
    const prevPage = $("#prev-page");
    const nextPage = $("#next-page");
    const refreshBtn = $('#refresh-btn');

    formAddTextbook.on('submit', async function(event){
        event.preventDefault();
        let formData = {};
        let name = addTextbookName.val();
        let author = addTextbookAuthor.val();
        let subjectId = addTextbookSubject.val();
        let description = addTextbookDescription.val();

        if (name !== undefined && name !== null && name !== '') {
            formData.name = name;
        }
        if (author !== undefined && author !== null && author !== '') {
            formData.author = author;
        }
        if (subjectId !== undefined && subjectId !== null && subjectId !== '') {
            formData.subjectId = subjectId;
        }
        if (description !== undefined && description !== null && description !== '') {
            formData.description = description;
        }
        console.log(formData);
        try {
            const response = await textbookService.addTextbook(formData);
            showNotification('success', '', "Tạo thành công");
            addTextbookName.val('');
            addTextbookAuthor.val('');
            addTextbookDescription.val('');
            addTextbookSubject.val('');
        } catch (error) {
            showNotification('error', '', 'Tạo thất bại');
            message.text(error.message);
            console.log(error)
            errorMessage.removeClass('d-none');
        }
    });

    // Xem thông tin chi tiết của giáo trình
    tbodyEl.on('click', '.view-btn', async function() {
        const getRow = $(this).closest('tr');
        const textbookId = getRow.attr('data-id');
        try {
            const response = await textbookService.getTextbookDetailById(textbookId);
            textbookUI.renderInfoDetail(response);
            infoModal.find('.modal').modal('show');
        } catch (error) {
            console.log("Error: " + error);
        }
    })
    // Đóng modal chi tiết môn học
    $(document).on('hidden.bs.modal', '#infoModal', function() {
        textbookUI.closeInfoDetail();
    })

    // Chỉnh sửa thông tin giáo trình
    tbodyEl.on('click', '.edit-btn', async function() {
        const getRow = $(this).closest('tr');
        const textbookId = getRow.attr('data-id');
        try {
            const response = await textbookService.getTextbookDetailById(textbookId);
            const statuses = await textbookService.getTextbookStatus();
            setOriginValue(response);
            textbookUI.renderEditModal(response, statuses);
            editModal.find('.modal').modal('show');
        } catch (error) {
            console.log("Error: " + error);
        }
    })
    // Đóng modal cập nhật giáo trình
    $(document).on('hidden.bs.modal', '#editModal', function() {
        textbookUI.closeEditModal();
    })

    // Khi người dùng nhấn nút lưu
    saveTextbookBtn.on('click', async function() {
        let formData = {};
        const currentId = textbookEditId.attr('data-id');
        const currentStatus = textbookEditStatus.val();
        const currentName = textbookEditName.val();
        const currentAuthor = textbookEditAuthor.val();
        const currentDescription = textbookEditDescription.val();
        if (originTextbookStatus == null) {
            if (currentStatus !== null && currentStatus !== undefined && currentStatus !== '') {
                formData.status = currentStatus;
            }
        } else {
            if (currentStatus !== originTextbookStatus) {
                formData.status = currentStatus;
            }
        }
        if (originTextbookName == null) {
            if (currentName !== null && currentName !== undefined && currentName !== '') {
                formData.name = currentName;
            }
        } else {
            if (currentName !== originTextbookName) {
                formData.name = currentName;
            }
        }
        if (originTextbookAuthor == null) {
            if (currentAuthor !== null && currentAuthor !== undefined && currentAuthor !== '') {
                formData.author = currentAuthor;
            }
        } else {
            if (currentAuthor !== originTextbookAuthor) {
                formData.author = currentAuthor
            }
        }
        if (originTextbookDescription == null) {
            if (currentDescription !== null && currentDescription !== undefined && currentDescription !== '') {
                formData.description = currentDescription;
            }
        } else {
            if (currentDescription !== originTextbookDescription) {
                formData.description = currentDescription;
            }
        }
        console.log(formData);
        console.log(currentId)
        try {
            const response = await textbookService.updateTextbookById(currentId, formData);
            showNotification('success', '', 'Cập nhật thành công');
            textbookUI.closeEditModal();
            editModal.find('.modal').modal('hide');
        } catch (error) {
            console.log("Error: " + error);
            showNotification('error', '', 'Cập nhật thất bại');
        } finally {
            getTextbookByFilter(currentPage);
            setNull();
        }
    })

    let originTextbookStatus = null;
    let originTextbookName = null;
    let originTextbookAuthor = null;
    let originTextbookDescription = null;
    function setOriginValue(textbook) {
        if (textbook !== null && textbook !== undefined) {
            if (textbook.status !== null && textbook.status !== undefined && textbook.status.name !== null && textbook.status.name !== undefined) {
                originTextbookStatus = textbook.status.name;
            }
            if (textbook.name !== null && textbook.name !== undefined && textbook.name !== '') {
                originTextbookName = textbook.name;
            }
            if (textbook.author !== null && textbook.author !== undefined && textbook.author !== '') {
                originTextbookAuthor = textbook.author;
            }
            if (textbook.description !== null || textbook.description !== undefined && textbook.description !== '') {
                originTextbookDescription = textbook.description;
            }
        }
    }
    function setNull() {
        originTextbookStatus = null;
        originTextbookName = null;
        originTextbookAuthor = null;
        originTextbookDescription = null;
    }

    // Xử lý sự kiện
    // Đóng mở tìm kiếm
    searchTextbookCheckbox.change(function() {
        const isChecked = $(this).prop('checked');
        if (!$(this).prop('checked')) {
            searchTextbookContent.prop('disabled', true);
            searchTextbookType.prop('disabled', true);
            searchTextbookButton.prop('disabled', true);
        } else {
            searchTextbookContent.prop('disabled', false);
            searchTextbookType.prop('disabled', false);
            searchTextbookButton.prop('disabled', false);
        }
    })
    // Thay đổi điều kiện lọc
    searchTextbookCheckbox.on('click', function() {
        if (!$(this).prop('checked')) {
            getTextbookByFilter();
        } else {
            if (searchTextbookType.val() === '1') {
                searchTextbookContent.attr('type', 'number');
                searchTextbookContent.attr('min', '1');
            } else {
                searchTextbookContent.attr('type', 'text');
            searchTextbookContent.removeAttr('min');
            }
        }
    });
    searchTextbookStatus.on('change', function() {
        getTextbookByFilter();
    });
    searchTextbookButton.on('click', function() {
        getTextbookByFilter();
    });
    searchTextbookSortOrder.on('change', function() {
        getTextbookByFilter();
    });
    searchTextbookSubject.on('change', function() {
        getTextbookByFilter();
    });
    searchTextbookType.on('change', function() {
        const selectedType = $(this).val();
        if (selectedType === '1') {
            searchTextbookContent.attr('type', 'number');
            searchTextbookContent.attr('min', '1');
        }
        else {
            searchTextbookContent.attr('type', 'text');
            searchTextbookContent.removeAttr('min');
        }
    });
    refreshBtn.on('click', function() {
        getTextbookByFilter(currentPage);
    })


    // Lấy danh sách giáo trình
    async function getTextbookByFilter(page = 0, size = 10) {
        let queryString = '';
        queryString += "&page=" + page;
        queryString += "&size=" + size;
        let keyword = searchTextbookContent.val();
        let type = searchTextbookType.val();
        let status = searchTextbookStatus.val();
        let sortOrder = searchTextbookSortOrder.val();
        let subjectId = searchTextbookSubject.val();
        if (searchTextbookCheckbox.prop('checked')) {
            if (type !== null & type !== undefined && keyword !== '' && type === '1') {
                queryString += "&id=" + keyword;
            }
            else if (type !== null & type !== undefined && keyword !== '' && type === '2') {
                queryString += "&name=" + keyword;
            }
            else if (type !== null & type !== undefined && keyword !== '' && type === '3') {
                queryString += "&author=" + keyword;
            }
            else {
                console.log("Search Type Textbook Invalid");
            }
        }
        if (subjectId !== null && subjectId !== undefined && subjectId !== '') {
            queryString += "&subjectId=" + subjectId;
        }
        if (status !== null && status !== undefined && status !== '') {
            queryString += "&status=" + status;
        }
        if (sortOrder !== null & sortOrder !== undefined && sortOrder !== '') {
            queryString += "&sortOrder=" + sortOrder;
        }
        console.log(queryString);
        try {
            const response = await textbookService.getTextbookFilter(queryString);
            console.log(response.items);
            textbookUI.renderTable(response.items);
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
        getTextbookByFilter(currentPage);
    })
    prevPage.on('click', function() {
        if (currentPage > 0) {
            currentPage -= 1;
        }            
        getTextbookByFilter(currentPage);
    })
})