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
        listCard: $("#list-card-textbook"),
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
    },
    renderCard: (textbooks) => {
        textbookUI.el.listCard.empty();
        if (textbooks.length === 0) {
            textbookUI.el.listCard.append('<div>No Textbook available</div>');
            return;
        }
        textbooks.forEach(textbook => {
            let card = $(`
                <div data-id="${textbook.id}" class="col-lg-2 card-container hideCard">
                    <a href="${textbook != null && textbook.slug != null ? textbook.slug + '?lang=vi' : '#'}" class="text-decoration-none">
                        <div class="card-subject">
                        <div class="card-image">
                            <img src="/images/image_default.webp" alt="Image">
                        </div>
                        <div class="card-content d-flex justify-content-center align-items-center">
                            <span >${textbook.name}</span>
                        </div>
                        </div>
                        <div class="content-detail">
                        <p class="fw-bold" >${'#' + textbook.id}</p>
                        <p >${textbook.name}</p>
                        </div>
                    </a>
                </div>
            `)
            textbookUI.el.listCard.append(card);
        })
        setTimeout(function() {
            textbookUI.el.listCard.find('.card-container').addClass('showCard');
        }, 100);
    },
    renderTextbookLessonDetail: (lessons) => {
        $(".list-lesson-container").empty();
        if (lessons.length === 0) {
            return;
        }
        lessons.forEach(lesson => {
            let sectionsOrExercises = '';

            // Kiểm tra nếu mảng sections tồn tại và không rỗng
            if (Array.isArray(lesson.sections) && lesson.sections.length > 0) {
                sectionsOrExercises = lesson.sections.map(section => {
                    return `
                        <li class="lesson-detail-item" data-id="${section.id}">
                            <a href="javascript:void(0)" class="nav-link d-flex align-items-center text-decoration-none">
                                <strong>${section.priority}</strong>.
                                <span class="lesson-detail-title">${section.title}</span>
                            </a>
                        </li>
                    `;
                }).join('');
            }
            // Nếu không có sections, kiểm tra mảng exercises
            else if (Array.isArray(lesson.exercises) && lesson.exercises.length > 0) {
                sectionsOrExercises = lesson.exercises.map(exercise => {
                    // Đảm bảo sử dụng đúng thuộc tính từ đối tượng exercise
                    return `
                        <li class="lesson-detail-item" data-id="${exercise.id}">
                            <a href="javascript:void(0)" class="nav-link d-flex align-items-center text-decoration-none">
                                <strong>${exercise.priority}</strong>.
                                <span class="lesson-detail-title">${exercise.title}</span>
                            </a>
                        </li>
                    `;
                }).join('');
            } else {
                sectionsOrExercises = `<li class="lesson-detail-item">No data available</li>`;
            }

            let li = $(`
                <li class="lesson-item" data-id=${lesson.id}>
                    <a href="javascript:void(0)" class="lesson-title-container d-flex justify-content-between align-items-center border-bottom">
                        <input type="checkbox" class="checkbox-lesson">
                        <span class="lesson-title">${lesson.title}</span>
                        <i class="fa-solid fa-angle-down"></i>
                    </a>
                    <ul class="lesson-detail d-none">
                        ${sectionsOrExercises}
                    </ul>
                </li>
            `);
            $(".list-lesson-container").append(li);
        })
        setTimeout(function() {
            $(".list-lesson-container").addClass('showCard');
        }, 100);
    }
}

export default textbookUI;