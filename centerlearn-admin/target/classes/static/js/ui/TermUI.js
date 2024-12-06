import util from "../utils.js";

const termUI = {
    el: {
        termSuggestionEl: $("#term-suggestion"),
        parentTypeDropdown: $("#parent-type-dropdown"),
        tbodyEl: $('#term-tbody'),
    },
    renderTermSuggestion: (terms) => {
        termUI.el.termSuggestionEl.empty();
        if (terms.length <= 0) {
            termUI.el.termSuggestionEl.hide();
        }
        else {
            let termNames = terms.name;
            termNames.forEach(term => {
                termUI.el.termSuggestionEl.append('<div class="suggestion-item">' + term + '</div>');
            });
            termUI.el.termSuggestionEl.show();
        }
        
    },
    renderEditSuggestion: (terms) => {
        const sg = $("#edit-term-form .form-group #edit-term-suggestion");
        sg.empty();
        console.log(terms);
        console.log(sg);
        if (terms.length <= 0) {
            sg.hide();
        }
        else {
            let termNames = terms.name;
            termNames.forEach(term => {
                sg.append('<div class="edit-suggestion-item">' + term + '</div>');
            });
            sg.show();
        }
    },
    renderParentType: (types) => {
        termUI.el.parentTypeDropdown.empty(); // Xóa các tùy chọn cũ
        if (types && types.length > 0) {
            types.forEach(function(type) {
                termUI.el.parentTypeDropdown.append('<option>' + type + '</option>'); // Thêm type vào dropdown
            });
            // Hiển thị dropdown
            termUI.el.parentTypeDropdown.show();
        } else {
            // Ẩn dropdown nếu không có type
            termUI.el.parentTypeDropdown.hide();
            termUI.el.parentTypeDropdown.empty();
        }
    },
    renderEditParentType: (types) => {
        const tp = $("#edit-term-form .form-group #edit-parent-type-dropdown");
        tp.empty();
        if (types && types.length > 0) {
            types.forEach(function(type) {
                tp.append('<option>' + type + '</option>'); // Thêm type vào dropdown
            });
            // Hiển thị dropdown
            tp.show();
        } else {
            // Ẩn dropdown nếu không có type
            tp.hide();
            tp.empty();
        }
    },
    renderTerm: (terms) => {
        termUI.el.tbodyEl.empty();
        if (terms.length === 0) {
            termUI.el.tbodyEl.append('<tr><td colspan="8">No terms available</td></tr>');
            return;
        }
        terms.forEach(function(term) {
            let row = '<tr class="fade-in" data-id="' + term.id + '">' +
                '<td><input type="checkbox"></td>' +
                '<td class="item-term-id">' + term.id + '</td>' +
                '<td class="item-term-name">' + term.name + '</td>' +
                '<td class="item-term-slug">' + term.slug + '</td>' +
                '<td class="item-term-type">' + term.termType + '</td>' +
                '<td class="item-term-parent">' + (term.parentName ? term.parentName : '') + '</td>' +
                '<td class="item-term-description">' + (term.description ? term.description : '') + '</td>' +
                '<td>' +
                    '<i class="fa-regular fa-eye view-btn"></i>' +
                    '<i class="fa-regular fa-pen-to-square edit-btn"></i>' +
                    '<i class="fa-solid fa-trash-can delete-btn"></i>' +
                '</td>' +
            '</tr>';
            termUI.el.tbodyEl.append(row);
        });
        setTimeout(function() {
            termUI.el.tbodyEl.find('.fade-in').addClass('show');
        }, 100); 
    },
    renderViewModal: (term) => {
        let container = $("#info-modal");
        if (container.length === 0) {
            container = $('<div id="info-modal"></div>').appendTo('body');
        }
        let modal = $(`
            <div class="modal fade" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header text-center d-flex align-items-center justify-content-between">
                            <h5 class="modal-title">Thông tin chi tiết</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <p><label class="view-term-lable fw-bold">Mã:</label> <span class="view-term-id" id="">${term.id}</span></p>
                            <p><label class="view-term-lable fw-bold">Tên:</label> <span class="view-term-name" id="">${term.name}</span></p>
                            <p><label class="view-term-lable fw-bold">Slug:</label> <span class="view-term-slug" id="">${term.slug}</span></p>
                            <p><label class="view-term-lable fw-bold">Loại:</label> <span class="view-term-type" id="">${term.termType}</span></p>
                            <p><label class="view-term-lable fw-bold">Thuộc:</label> <span class="view-term-parent" id="">${term.parentName}</span></p>
                            <p class="row">
                                <label class="col-lg-3 p-0 view-term-lable fw-bold">Mô tả:</label> 
                                <span class="col-lg-9 p-0 view-term-description" id="">${term.description}</span></p>
                            </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                        </div>
                    </div>
                </div>
            </div>
        `)
        container.html(modal);
        const modalInstance = new bootstrap.Modal(modal[0]);
        modalInstance.show();
        // Lắng nghe sự kiện khi đóng confirmation
        modal.on('hidden.bs.modal', function () {
            container.empty(); // Xóa tất cả nội dung trong container
        });
    },
    renderEditModal: () => {
        let container = $("#edit-modal");
        if (container.length === 0) {
            container = $('<div id="edit-modal"></div>').appendTo('body');
        }
        let modal = $(`
               <div class="modal fade" tabindex="-1">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header text-center d-flex align-items-center justify-content-between">
                            <h5 class="modal-title">Chỉnh sửa thuật ngữ</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form id="edit-term-form" method="" action="">
                                <div class="form-group">
                                    <label class="fw-bold" for="edit-term-id">Mã: </label>
                                    <input type="text" id="edit-term-id" name="id" readonly>
                                </div>
                                <div class="form-group">
                                    <label for="edit-term-name">Tên: </label>
                                    <input type="text" id="edit-term-name" name="name">
                                </div>
                                <div class="form-group">
                                    <label for="edit-term-slug">Slug: </label>
                                    <input type="text" id="edit-term-slug" name="slug" readonly>
                                </div>
                                <div class="form-group">
                                    <label for="edit-term-type">Loại: </label>
                                    <input type="text" id="edit-term-type" name="termType">
                                </div>
                                <div class="form-group position-relative">
                                    <label for="edit-term-parent-name">Thuộc: </label>
                                    <input type="text" id="edit-term-parent-name" name="parentName">
                                    <div id="edit-term-suggestion" class="suggestions-list" style="display: none;"></div>
                                    <select id="edit-parent-type-dropdown" class="" style="display: none;">
                                        <!-- Các tùy chọn sẽ được thêm vào đây bởi JavaScript -->
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="edit-term-description">Mô tả: </label>
                                    <textarea id="edit-term-description" name="description"></textarea>
                                </div>
                                <div class="d-flex align-items-center justify-content-center">
                                    <button type="submit" class="btn btn-primary" data-bs-dismiss="modal">Lưu</button>
                                </div>    
                            </form>
                        <div class="modal-footer">
                            
                        </div>
                    </div>
                </div>
            </div> 
        `)
        container.html(modal);
        const modalInstance = new bootstrap.Modal(modal[0]);
        modalInstance.show();
        // Lắng nghe sự kiện khi đóng confirmation
        modal.on('hidden.bs.modal', function () {
            container.empty(); // Xóa tất cả nội dung trong container
        });
    },
}

export default termUI;