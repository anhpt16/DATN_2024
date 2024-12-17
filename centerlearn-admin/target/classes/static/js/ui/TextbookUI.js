import util from "../utils.js";

const textbookUI = {
    el: {
        tbodyEl: $("#table-textbook"),
        textbookInfoId: $("#textbookInfoId"),
        textbookInfoName: $("#textbookInfoName"),
        textbookInfoStatus: $("#textbookInfoStatus"),
        textbookInfoAuthor: $("#textbookInfoAuthor"),
        textbookInfoUrl: $("#textbookInfoUrl"),
        textbookInfoCreatedAt: $("#textbookInfoCreatedAt"),
        textbookInfoUpdatedAt: $("#textbookInfoUpdatedAt"),
        textbookInfoSlug: $("#textbookInfoSlug"),
        textbookInfoDescription: $("#textbookInfoDescription"),
        // Edit
        textbookEditId: $("#textbookEditId"),
        textbookEditStatus: $("#textbookEditStatus"),
        textbookEditName: $("#textbookEditName"),
        textbookEditAuthor: $("#textbookEditAuthor"),
        textbookEditUrl: $("#textbookEditUrl"),
        textbookEditDescription: $("#textbookEditDescription"),
    },
    renderTable: (textbooks) => {
        textbookUI.el.tbodyEl.empty();
        if (textbooks.length === 0) {
            textbookUI.el.tbodyEl.append('<tr><td colspan="8">No Textbook available</td></tr>');
            return;
        }
        textbooks.forEach(function(textbook) {
            let row = '<tr class="fade-in" data-id="' + textbook.id + '">' +
                '<td class="item-textbook-id">' + textbook.id + '</td>' +
                '<td class="item-textbook-name">' + textbook.name + '</td>' +
                '<td class="item-textbook-author">' + textbook.author + '</td>' +
                '<td class="item-account-status" '
                + (textbook.status.colorCode ? 'style="color:' + textbook.status.colorCode + '"' : '') + '>' +
                textbook.status.displayName + '</td>' +
                '<td>' +
                    '<i class="fa-regular fa-eye view-btn"></i>' +
                    '<i class="fa-regular fa-pen-to-square edit-btn"></i>' +
                '</td>' +
            '</tr>';
            textbookUI.el.tbodyEl.append(row);
        })
        setTimeout(function() {
            textbookUI.el.tbodyEl.find('.fade-in').addClass('show');
        }, 100);
    },
    renderInfoDetail: (textbook) => {
        if (textbook == null || textbook == undefined) {
            return;
        }
        if (textbook.id !== null && textbook.id !== undefined && textbook.id !== '') {
            textbookUI.el.textbookInfoId.text('#' + textbook.id);
        }
        if (textbook.name !== null && textbook.name !== undefined && textbook.name !== '') {
            textbookUI.el.textbookInfoName.text(textbook.name);
        }
        if (textbook.description !== null && textbook.description !== undefined && textbook.description !== '') {
            textbookUI.el.textbookInfoDescription.text(textbook.description);
        }
        if (textbook.author !== null && textbook.author !== undefined && textbook.author !== '') {
            textbookUI.el.textbookInfoAuthor.text(textbook.author);
        }
        if (textbook.url !== null && textbook.url !== undefined && textbook.url !== '') {
            textbookUI.el.textbookInfoUrl.text(textbook.url);
            textbookUI.el.textbookInfoUrl.attr('src', textbook.url);
        }
        if (textbook.createdAt !== null && textbook.createdAt !== undefined && textbook.createdAt !== '') {
            textbookUI.el.textbookInfoCreatedAt.text(util.formatDateTime(textbook.createdAt));
        }
        if (textbook.updatedAt !== null && textbook.updatedAt !== undefined && textbook.updatedAt !== '') {
            textbookUI.el.textbookInfoUpdatedAt.text(util.formatDateTime(textbook.updatedAt));
        }
        if (textbook.slug !== null && textbook.slug !== undefined && textbook.slug !== '') {
            textbookUI.el.textbookInfoSlug.text(textbook.slug);
        }
        if (textbook.status !== null && textbook.status !== undefined) {
            if (textbook.status.displayName !== null && textbook.status.displayName !== undefined && textbook.status.displayName !== '') {
                textbookUI.el.textbookInfoStatus.text(textbook.status.displayName);
            }
            if (textbook.status.colorCode !== null && textbook.status.colorCode !== undefined && textbook.status.colorCode !== '') {
                textbookUI.el.textbookInfoStatus.css('color', textbook.status.colorCode);
            }
        }
    },
    closeInfoDetail: () => {
        textbookUI.el.textbookInfoId.text('');
        textbookUI.el.textbookInfoName.text('');
        textbookUI.el.textbookInfoAuthor.text('');
        textbookUI.el.textbookInfoDescription.text('');
        textbookUI.el.textbookInfoSlug.text('');
        textbookUI.el.textbookInfoStatus.text('');
        textbookUI.el.textbookInfoCreatedAt.text('');
        textbookUI.el.textbookInfoUpdatedAt.text('');
        textbookUI.el.textbookInfoUrl.text('');
        textbookUI.el.textbookInfoUrl.attr('src', '');
    },
    renderEditModal: (textbook, statuses) => {
        if (textbook == null || textbook == undefined) {
            return;
        }
        if (textbook.id !== null && textbook.id !== undefined && textbook.id !== '') {
            textbookUI.el.textbookEditId.text("#" + textbook.id);
            textbookUI.el.textbookEditId.attr('data-id', textbook.id);
        }
        if (textbook.name !== null && textbook.name !== undefined && textbook.name !== '') {
            textbookUI.el.textbookEditName.val(textbook.name);
        }
        if (textbook.author !== null && textbook.author !== undefined && textbook.author !== '') {
            textbookUI.el.textbookEditAuthor.val(textbook.author);
        }
        if (textbook.url !== null && textbook.url !== undefined && textbook.url !== '') {
            textbookUI.el.textbookEditUrl.text(textbook.url);
        }
        if (textbook.description !== null && textbook.description !== undefined && textbook.description !== '') {
            textbookUI.el.textbookEditDescription.val(textbook.description);
        }
        if (textbook.status !== null && textbook.status !== undefined && textbook.status.name !== null
            && textbook.status.name !== undefined && textbook.status.name !== '' && statuses !== null && statuses !== undefined && statuses.length > 0) {
            statuses.forEach(status => {
                let option = $(`<option value="${status.name}" ${status.name === textbook.status.name ? 'selected' : ''}>${status.displayName}</option>`)
                textbookUI.el.textbookEditStatus.append(option);
            })
        }
    },
    closeEditModal: () => {
        textbookUI.el.textbookEditId.text('');
        textbookUI.el.textbookEditId.removeAttr('data-id');
        textbookUI.el.textbookEditStatus.empty();
        textbookUI.el.textbookEditName.val('');
        textbookUI.el.textbookEditAuthor.val('');
        textbookUI.el.textbookEditUrl.text('');
        textbookUI.el.textbookEditDescription.val('');
    }
}

export default textbookUI;