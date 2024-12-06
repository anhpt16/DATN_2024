
import termService from "../service/TermService.js";
import termUI from "../ui/TermUI.js";
import { showNotification } from "../ui/notification.js";
import { showConfirmation } from "../ui/notification.js";

$(document).ready(function() {
    // Flag
    let suggestionSelected = false;
    let editSuggestionSelected = false;
    // Term
    const termTypeSelect = $("#term-type-select");
    const tbodyEl = $('#term-tbody');
    const termParentInput = $("#term-parent");
    const termSuggestionEl = $("#term-suggestion");
    const termFormEl = $("#term-form");
    const termNameInput = $("#term-name");
    const termTypeInput = $("#term-type");
    const termDescriptionInput = $("#term-description");
    const parentTypeDropdown = $("#parent-type-dropdown");
    const searchTerm = $("#search-term");
    // Pagination
    const firstPage = $("#first-page");
    const prevPage = $("#prev-page");
    const nextPage = $("#next-page");
    const lastPage = $("#last-page");
    const currentPageEl = $("#current-page");
    const totalPageEl = $("#total-page");
    const refreshBtn = $("#refresh-btn");

    refreshBtn.on('click', function() {
        renderTableByTermType(currentPage);
    })

    // Khi click ở ngoài màn hình
    $(document).on('click', function(event) {
        if (!$(event.target).closest('#term-parent, #term-suggestion').length) {
            $('#term-suggestion').hide(); // Ẩn danh sách gợi ý
        }
        if (!$(event.target).closest('#edit-term-parent, #edit-term-suggestion').length) {
            $('#edit-term-suggestion').hide(); // Ẩn danh sách gợi ý
        }
    })

    // Khi người dùng nhập vào ô thuật ngữ cha
    termParentInput.on('input', async function() {
        let queryString = '';
        const keyword = $(this).val().trim();
        if (keyword.length > 0) {
            queryString += "&keyword=" + keyword;
            try {
                const response = await termService.getTermByName(queryString);
                console.log(response)
                if (!suggestionSelected) {
                    termUI.renderTermSuggestion(response);
                } else {
                    suggestionSelected = false;
                }

                let exactMatch = response.name.some(function(term) {
                    return term.toUpperCase() === keyword.toUpperCase();
                });

                if (!exactMatch) {
                    termParentInput.css('border', '1px solid red');
                } else {
                    termParentInput.css('border', '');
                }
                if (keyword.length === 0) {
                    termParentInput.css('border', '');
                }
                termUI.renderParentType(response.type);
            } catch (error) {
                termParentInput.css('border', '2px solid red');
                console.log("Error: " + error);
            }
        } else {
            termSuggestionEl.empty().hide();
            parentTypeDropdown.hide();
            termParentInput.css('border', '');
        }
    })

    // Khi người dùng nhập vào ô thuật ngữ cha trong phần chỉnh sửa thuật ngữ
    $(document).on('input', '#edit-term-parent-name', async function() {
        let queryString ='';
        const keyword = $(this).val().trim();
        if (keyword.length > 0) {
            queryString += "&keyword=" + keyword;
            try {
                const response = await termService.getTermByName(queryString);
                console.log(response)
                if (!suggestionSelected) {
                    termUI.renderEditSuggestion(response);
                } else {
                    suggestionSelected = false;
                }

                let exactMatch = response.name.some(function(term) {
                    return term.toUpperCase() === keyword.toUpperCase();
                });

                if (!exactMatch) {
                    $('#edit-term-parent-name').css('border', '1px solid red');
                } else {
                    $('#edit-term-parent-name').css('border', '');
                }
                if (keyword.length === 0) {
                    $('#edit-term-parent-name').css('border', '');
                }
                termUI.renderEditParentType(response.type);
            } catch (error) {
                termParentInput.css('border', '2px solid red');
                console.log("Error: " + error);
            }
        } else {
            $("#edit-term-suggestion").empty().hide();
            $("#edit-parent-type-dropdown").hide();
            $('#edit-term-parent-name').css('border', '');
        }
    })

    // Khi người dùng nhấn vào nút lưu trong phần chỉnh sửa
    $(document).on('submit', '#edit-term-form', async function(event) {
        event.preventDefault();
        const idEl = $("#edit-term-id");
        const nameEl = $("#edit-term-name");
        const termTypeEl = $("#edit-term-type");
        const parentNameEl = $("#edit-term-parent-name");
        const parentTypeEl = $("#edit-parent-type-dropdown");
        const descriptionEl = $("#edit-term-description");

        const els = [idEl, nameEl, termTypeEl, parentNameEl, parentTypeEl, descriptionEl,];
        const allDefined = els.every(el => el != undefined && el.length > 0);

        if (allDefined) {
            const id = idEl.val();
            const formData = {
                name: nameEl.val(),
                termType: termTypeEl.val(),
                parentName: parentNameEl.val(),
                parentType: parentTypeEl.val(),
                description: descriptionEl.val()
            }
            try {
                const response = await termService.updateTermById(id, formData);
                showNotification('success', '', "Sửa thành công");
            } catch (error) {
                showNotification('error', '', 'Sửa thất bại');
                console.log("Error: " + error);
            }
        }
        else {
            console.log("Attribute Undefined");
            return;
        }
    })

    // Khi người dùng click vào một gợi ý
   $(document).on('click', '.suggestion-item', function() {
        suggestionSelected = true;
        termParentInput.val($(this).text()); // Điền giá trị được chọn vào ô input
        termParentInput.css('border', ''); // Xóa border đỏ khi người dùng chọn một gợi ý
        termSuggestionEl.hide(); // Ẩn danh sách gợi ý sau khi chọn
        termParentInput.trigger('input');
    })

    // Khi người dùng click vào một gợi ý trong phần chỉnh sửa
    $(document).on('click', '.edit-suggestion-item', function() {
        suggestionSelected = true;
        $('#edit-term-parent-name').val($(this).text());
        $('#edit-term-parent-name').css('border', '');
        $("#edit-term-suggestion").hide();
        $('#edit-term-parent-name').trigger('input');
    })

    // Tạo mới thuật ngữ
    termFormEl.on('submit', async function(event) {
        event.preventDefault();
        let formData = {
            name: termNameInput.val(),
            termType: termTypeInput.val(),
            parentName: termParentInput.val(),
            parentType: parentTypeDropdown.val(),
            description: termDescriptionInput.val()
        };
        try {
            const response = await termService.addTerm(formData);
            showNotification('success', '', 'Tạo thành công');
            termNameInput.val('');
            termTypeInput.val('');
            termParentInput.val('');
            termDescriptionInput.val('');
            parentTypeDropdown.empty();
            parentTypeDropdown.hide();
        } catch (error) {
            console.log("Error: " + error);
            showNotification('error', 'Tạo thất bại', error);
        }
    })

    // Lấy danh sách term
    async function renderTableByTermType(page = 0, size = 10) {
        let queryString = '';
        let selectedTermType = termTypeSelect.val();
        queryString += "&type=" + selectedTermType;
        queryString += "&page=" + page;
        queryString += "&size=" + size;
        try {
            const response = await termService.getTermByTermType(queryString);
            termUI.renderTerm(response.items);
            currentPage = response.currentPage;
            totalPage = response.totalPage;
            validatePageNumber(currentPage, totalPage);
            currentPageEl.text(currentPage + 1);
            totalPageEl.text(totalPage);
        } catch (error) {
            console.log("Error: " + error);
        }
    }

    // Load lại bảng khi chọn loại mới
    termTypeSelect.change(function() {
        renderTableByTermType();
    })

    // Lấy ra chi tiết thuật ngữ
    tbodyEl.on('click', '.view-btn', async function() {
        const getTermRow = $(this).closest("tr");
        const termId = getTermRow.find(".item-term-id").text();
        try {
            const response = await termService.getTermById(termId);
            console.log(response)
            termUI.renderViewModal(response);
        } catch (error) {
            showNotification('error', 'Lỗi');
            console.log("Error: " + error);
        }
    })

    // Xóa thuật ngữ
    tbodyEl.on('click', '.delete-btn', async function() {
        const getTermRow = $(this).closest('tr');
        const termId = getTermRow.find(".item-term-id").text();
        const termName = getTermRow.find(".item-term-name").text();
        const termType = getTermRow.find(".item-term-type").text();
        let message = `
            <strong>Mã: </strong>${termId}<br>
            <strong>Tên: </strong>${termName}<br>
            <strong>Loại: </strong>${termType}
        `
        const result = await showConfirmation('delete', 'Xác nhận xóa !', message)
        if (result) {
            try {
                const response = await termService.deleteTermById(termId);
                showNotification("success", '', 'Xóa thành công');
            } catch (error) {
                showNotification("error", '', 'Xóa thất bại');
                console.log("Error: " + error);
            }
        }
       
    })

    // Chỉnh sửa thuật ngữ
    tbodyEl.on('click', '.edit-btn', async function() {
        const getTermRow = $(this).closest('tr');
        const termId = getTermRow.find(".item-term-id").text();
        termUI.renderEditModal();
        try {
            const response = await termService.getTermById(termId);
            console.log(response)
            const editTermId = $("#edit-term-id");
            const editTermName = $("#edit-term-name");
            const editTermSlug = $("#edit-term-slug");
            const editTermType = $("#edit-term-type");
            const editTermParentName = $("#edit-term-parent-name");
            const editParentTypeDropdown = $("#edit-parent-type-dropdown");
            const editTermDescription = $("#edit-term-description");

            const inputs = [editTermId, editTermName, editTermSlug, editTermType, editTermParentName, editParentTypeDropdown, editTermDescription];
            const allDefined = inputs.every(input => input != undefined && input.length > 0);
            
            if (allDefined) {
                editTermId.val(response.id);
                editTermName.val(response.name);
                editTermSlug.val(response.slug);
                editTermType.val(response.termType);
                editTermParentName.val(response.parentName);
                editTermDescription.val(response.description)
                if (response.parentName === null || response.parentName === '') {
                    editParentTypeDropdown.empty();
                    editParentTypeDropdown.hide();
                } else {
                    editParentTypeDropdown.val(response.parentType);
                    editParentTypeDropdown.show();
                    try {
                        const types = await termService.getTypesByName(response.parentName);
                        types.forEach(type => {
                            const select = type === response.parentType ? "selected" : "";
                            editParentTypeDropdown.append(`<option value="${type}" ${select}>${type}</option>`)
                        })
                    } catch (error) {
                        console.log("Error: " + error);
                    }
                }
            }
            else {
                console.log("Attribute Undefined");
                return;
            }
        } catch (error) {
            showNotification('error', 'Lỗi');
            console.log("Error: " + error);
        }
        
    })

    //Pagination
    function validatePageNumber(currentPage, totalPage) {
        nextPage.toggleClass('disabled-link', currentPage >= (totalPage - 1));
        lastPage.toggleClass('disabled-link', currentPage >= (totalPage - 1));
        prevPage.toggleClass('disabled-link', currentPage <= 0);
        firstPage.toggleClass('disabled-link', currentPage <= 0);
    }
    nextPage.on('click', function() {
        if (currentPage < totalPage - 1) {
            currentPage += 1;
        }            
        // Render
        renderTableByTermType(currentPage);
    })
    prevPage.on('click', function() {
        if (currentPage > 0) {
            currentPage -= 1;
        }            
        // Render
        renderTableByTermType(currentPage);
    })
    firstPage.on('click', function() {
        renderTableByTermType(0);
    })
    lastPage.on('click', function() {
        renderTableByTermType(totalPage - 1);
    })
})