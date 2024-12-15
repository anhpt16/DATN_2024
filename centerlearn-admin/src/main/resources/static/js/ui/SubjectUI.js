import util from "../utils.js";

const subjectUI = {
    el: {
        imageOnCreContainer: $("#form-add-subject .image-subject-container"),
        imageOnUpContainer: $("#subjectEditImage"),
        tbodyEl: $("#table-subject"),
        listCard: $("#list-card-subject"),
        // Info Modal
         imageInfoModal: $("#subjectInfoImage"),
         statusInfoModal: $("#subjectInfoStatus"),
         idInfoModal: $("#subjectInfoId"),
         nameInfoModal: $("#subjectInfoName"),
         displayNameInfoModal: $("#subjectInfoDisplayName"),
         createdAtInfoModal: $("#subjectInfoCreatedAt"),
         updatedAtInfoModal: $("#subjectInfoUpdatedAt"),
         slugInfoModal: $("#subjectInfoSlug"),
         descriptionInfoModal: $("#subjectInfoDescription"),
        // Edit Modal
         imageEditModal: $("#subjectEditImage"),
         statusEditModal: $("#subjectEditStatus"),
         idEditModal: $("#subjectEditId"),
         nameEditModal: $("#subjectEditName"),
         displayNameEditModal: $("#subjectEditDisplayName"),
         descriptionEditModal: $("#subjectEditDescription"),
    },

    renderTable: (subjects) => {
        subjectUI.el.tbodyEl.empty();
        if (subjects.length === 0) {
            subjectUI.el.tbodyEl.append('<tr><td colspan="8">No subject available</td></tr>');
            return;
        }
        subjects.forEach(subject => {
            let row = '<tr class="fade-in" data-id="' + subject.id + '">' +
                '<td class="item-subject-username">' + subject.name + '</td>' +
                '<td class="item-subject-display-name">' + subject.displayName + '</td>' +
                '<td class="item-account-status" '
                + (subject.status.colorCode ? 'style="color:' + subject.status.colorCode + '"' : '') + '>' +
                subject.status.displayName + '</td>' +
                '<td>' +
                    '<i class="fa-regular fa-eye view-btn"></i>' +
                    '<i class="fa-regular fa-pen-to-square edit-btn"></i>' +
                    '<i class="fa-solid fa-book add-btn"></i>' +
                '</td>' +
            '</tr>';
            subjectUI.el.tbodyEl.append(row);
        })
        setTimeout(function() {
            subjectUI.el.tbodyEl.find('.fade-in').addClass('show');
        }, 100);
    },

    renderCard: (subjects) => {
        subjectUI.el.listCard.empty();
        if (subjects.length === 0) {
            subjectUI.el.listCard.append('<div>No subject available</div>');
            return;
        }
        subjects.forEach(subject => {
            let card = $(`
                <div data-id="${subject.id}" class="col-lg-2 card-container hideCard">
                    <a href="#" class="text-decoration-none">
                        <div class="card-subject">
                        <div class="card-image">
                            <img src="${subject.imageUrl != null && subject.imageUrl != '' ? subject.imageUrl : '/images/image_default.webp'}" alt="Image">
                        </div>
                        <div class="card-content d-flex justify-content-center align-items-center">
                            <span >${subject.displayName}</span>
                        </div>
                        </div>
                        <div class="content-detail">
                        <p class="fw-bold" >${subject.name}</p>
                        <p >${subject.displayName}</p>
                        </div>
                    </a>
                </div>
            `)
            subjectUI.el.listCard.append(card);
        })
        setTimeout(function() {
            subjectUI.el.listCard.find('.card-container').addClass('showCard');
        }, 100);
    },

    renderInfoDetail: (subject) => {
        if (subject == null || subject == undefined) {
            return;
        }
        if (subject.imageUrl !== null && subject.imageUrl !== undefined) {
            subjectUI.el.imageInfoModal.empty();
            if (subject.imageUrl === '') {
                let img = $(`<img src="/images/image_default.webp" alt="Image">`)
                subjectUI.el.imageInfoModal.append(img);
            } else {
                let img = $(`<img src="${subject.imageUrl}" alt="Image">`)
                subjectUI.el.imageInfoModal.append(img);
            }
        }
        if (subject.id !== null && subject.id !== undefined && subject.id !== '') {
            subjectUI.el.idInfoModal.text("#" + subject.id);
        }
        if (subject.status !== null && subject.status !== undefined) {
            if (subject.status.displayName !== null && subject.status.displayName !== undefined && subject.status.displayName !== '') {
                subjectUI.el.statusInfoModal.text(subject.status.displayName);
            }
            if (subject.status.colorCode !== null && subject.status.colorCode !== undefined && subject.status.colorCode !== '') {
                subjectUI.el.statusInfoModal.css('color', subject.status.colorCode);
            }
        }
        if (subject.name !== null && subject.name !== undefined && subject.name !== '') {
            subjectUI.el.nameInfoModal.text(subject.name);
        }
        if (subject.displayName !== null && subject.displayName !== undefined && subject.displayName !== '') {
            subjectUI.el.displayNameInfoModal.text(subject.displayName);
        }
        if (subject.createdAt !== null && subject.createdAt !== undefined && subject.createdAt !== '') {
            subjectUI.el.createdAtInfoModal.text(util.formatDateTime(subject.createdAt));
        }
        if (subject.updatedAt !== null && subject.updatedAt !== undefined && subject.updatedAt !== '') {
            subjectUI.el.updatedAtInfoModal.text(util.formatDateTime(subject.updatedAt));
        }
        if (subject.slug !== null && subject.slug !== undefined && subject.slug !== '') {
            subjectUI.el.slugInfoModal.text(subject.slug);
        }
        if (subject.description !== null && subject.description !== undefined && subject.description !== '') {
            subjectUI.el.descriptionInfoModal.text(subject.description);
        }
    },
    closeInfoDetail: () => {
        subjectUI.el.imageInfoModal.empty();
        subjectUI.el.idInfoModal.text('');
        subjectUI.el.statusInfoModal.text('');
        subjectUI.el.nameInfoModal.text('');
        subjectUI.el.displayNameInfoModal.text('');
        subjectUI.el.createdAtInfoModal.text('');
        subjectUI.el.updatedAtInfoModal.text('');
        subjectUI.el.slugInfoModal.text('');
        subjectUI.el.descriptionInfoModal.text('');
    },

    renderImageOnCreate: (imageId, imageUrl) => {
        subjectUI.el.imageOnCreContainer.empty();
        subjectUI.el.imageOnCreContainer.attr("data-id", imageId);
        let image = $(`
            <img src="${imageUrl}" alt="Image" >    
        `)
        subjectUI.el.imageOnCreContainer.append(image);
    },

    renderImageOnUpdate: (imageId, imageUrl) => {
        subjectUI.el.imageOnUpContainer.empty();
        let image = $(`<img data-id="${imageId}" src="${imageUrl}" alt="Image">`);
        subjectUI.el.imageOnUpContainer.append(image);
    },

    renderEditModal: (subject, statuses) => {
        if (subject == null || subject == undefined) {
            return;
        }
        // render hình ảnh
        if (subject.imageUrl !== null && subject.imageUrl !== undefined) {
            subjectUI.el.imageEditModal.empty();
            if (subject.imageUrl === '') {
                let img = $(`<img src="/images/image_default.webp" alt="Image">`)
                subjectUI.el.imageEditModal.append(img);
            } else {
                let img = $(`<img src="${subject.imageUrl}" alt="Image">`)
                subjectUI.el.imageEditModal.append(img);
            }
        }
        // render mã
        if (subject.id !== null && subject.id !== undefined && subject.id !== '') {
            subjectUI.el.idEditModal.text("#" + subject.id);
            subjectUI.el.idEditModal.attr('data-id', subject.id);
        }
        // render trạng thái
        if (subject.status !== null && subject.status !== undefined && subject.status.name !== null
            && subject.status.name !== undefined && subject.status.name !== '' && statuses !== null && statuses !== undefined && statuses.length > 0) {
            statuses.forEach(status => {
                let option = $(`<option value="${status.name}" ${status.name === subject.status.name ? 'selected' : ''}>${status.displayName}</option>`)
                subjectUI.el.statusEditModal.append(option);
            })
        }
        // render tên
        if (subject.name !== null && subject.name !== undefined && subject.name !== '') {
            subjectUI.el.nameEditModal.val(subject.name);
        }
        // render tên hiển thị
        if (subject.displayName !== null && subject.displayName !== undefined && subject.displayName !== '') {
            subjectUI.el.displayNameEditModal.val(subject.displayName);
        }
        // render mô tả
        if (subject.description !== null && subject.description !== undefined && subject.description !== '') {
            subjectUI.el.descriptionEditModal.val(subject.description);
        }
    },
    closeEditModal: () => {
        subjectUI.el.imageEditModal.empty();
        subjectUI.el.idEditModal.text('');
        subjectUI.el.statusEditModal.empty();
        subjectUI.el.nameEditModal.val('');
        subjectUI.el.displayNameEditModal.val('');
        subjectUI.el.descriptionEditModal.val('');
    }
}

export default subjectUI;