import textbookService from "../service/TextbookService.js";
import textbookUI from "../ui/TextbookUI.js";


$(document).ready(function(){
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

    // Khi nhấn vào một giáo trình

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
            textbookUI.renderCard(response.items);
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