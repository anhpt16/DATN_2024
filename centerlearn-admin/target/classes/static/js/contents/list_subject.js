import subjectUI from "../ui/SubjectUI.js";
import subjectService from "../service/SubjectService.js";

$(document).ready(function() {
    const listCard = $("#list-card-subject");
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
    // info modal
    const infoModal = $("#info-modal");




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
    async function getSubjectByFilter(page = 0, size = 12) {
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
            subjectUI.renderCard(response.items);
            currentPage = response.currentPage;
            totalPage = response.totalPage;
            validatePageNumber(currentPage, totalPage);
        } catch (error) {
            console.log("Error: " + error);
        }
    }

    // Xem chi tiết môn học
    listCard.on('click', '.card-container a', async function() {
        const getRow = $(this).closest('.card-container');
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