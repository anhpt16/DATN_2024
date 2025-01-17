import util from "../utils.js";

const courseUI = {
    el: {
        tbodyEl: $("#table-course"),
        // Info Modal
        courseInfoImage: $("#courseInfoImage"),
        courseInfoId: $("#courseInfoId"),
        courseInfoCode: $("#courseInfoCode"),
        courseInfoDisplayName: $("#courseInfoDisplayName"),
        courseInfoType: $("#courseInfoType"),
        courseInfoDuration: $("#courseInfoDuration"),
        courseInfoStatus: $("#courseInfoStatus"),
        courseInfoCreator: $("#courseInfoCreator"),
        courseInfoPrice: $("#courseInfoPrice"),
        courseInfoCreatedAt: $("#courseInfoCreatedAt"),
        courseInfoUpdatedAt: $("#courseInfoUpdatedAt"),
        courseInfoSlug: $("#courseInfoSlug"),
        courseInfoDescription: $("#courseInfoDescription"),
        // Edit modal
        courseEditImage: $("#courseEditImage"),//
        courseEditId: $("#courseEditId"),
        courseEditStatus: $("#courseEditStatus"),//
        courseEditCode: $("#courseEditCode"),//
        courseEditDisplayName: $("#courseEditDisplayName"),//
        courseEditCourseType: $("#courseEditCourseType"),
        courseEditPrice: $("#courseEditPrice"),
        courseEditDescription: $("#courseEditDescription"),//
        // Add model
        courseAddId: $("#courseAddId"),
        courseAddCode: $("#courseAddCode"),
        courseAddDisplayName: $("#courseAddDisplayName"),
        selectSubject: $("#select-subject"),
        selectTextbook: $("#select-textbook"),
        tableSubject: $("#table-subject"),
        selectManager: $("#select-manager"),

    },

    renderTable: (courses) => {
        courseUI.el.tbodyEl.empty();
        if (courses.length === 0) {
            courseUI.el.tbodyEl.append('<tr><td colspan="8">No course available</td></tr>');
            return;
        }
        courses.forEach(course => {
            let row = '<tr class="fade-in" data-id="' + course.id + '">' +
                '<td class="item-course-id">' + course.id + '</td>' +
                '<td class="item-course-code">' + course.code + '</td>' +
                '<td class="item-course-display-name">' + course.displayName + '</td>' +
                '<td class="item-course-type">' + course.courseType + '</td>' +
                '<td class="item-account-status" '
                + (course.status.colorCode ? 'style="color:' + course.status.colorCode + '"' : '') + '>' +
                course.status.displayName + '</td>' +
                '<td>' +
                    '<i class="fa-regular fa-eye view-btn"></i>' +
                    '<i class="fa-regular fa-pen-to-square edit-btn"></i>' +
                    '<i class="fa-solid fa-bookmark add-btn"></i>' +
                '</td>' +
            '</tr>';
            courseUI.el.tbodyEl.append(row);
        })
        setTimeout(function() {
            courseUI.el.tbodyEl.find('.fade-in').addClass('show');
        }, 100);
    },
    
    renderImageOnCreate: (imageId, imageUrl) => {
        $("#form-add-course .image-subject-container").empty();
        $("#form-add-course .image-subject-container").attr("data-id", imageId);
        let image = $(`
            <img src="${imageUrl}" alt="Image" >    
        `)
        $("#form-add-course .image-subject-container").append(image);
    },

    renderImageOnUpdate: (imageId, imageUrl) => {
        courseUI.el.courseEditImage.empty();
        let image = $(`<img data-id="${imageId}" src="${imageUrl}" alt="Image">`);
        courseUI.el.courseEditImage.append(image);
    },

    renderInfoDetail: (course) => {
        if (course == null || course == undefined) {
            return;
        }
        if (course.apiUrl !== null && course.apiUrl !== undefined) {
            courseUI.el.courseInfoImage.empty();
            if (course.apiUrl === '') {
                let img = $(`<img src="/images/image_default.webp" alt="Image">`)
                courseUI.el.courseInfoImage.append(img);
            } else {
                let img = $(`<img src="${course.apiUrl}" alt="Image">`)
                courseUI.el.courseInfoImage.append(img);
            }
        }
        if (course.id !== null && course.id !== undefined && course.id !== '') {
            courseUI.el.courseInfoId.text(course.id);
        }
        if (course.code !== null && course.code !== undefined && course.code !== '') {
            courseUI.el.courseInfoCode.text(course.code);
        }
        if (course.displayName !== null && course.displayName !== undefined && course.displayName !== '') {
            courseUI.el.courseInfoDisplayName.text(course.displayName);
        }
        if (course.courseType !== null && course.courseType !== undefined && course.courseType !== '') {
            courseUI.el.courseInfoType.text(course.courseType);
        }
        if (course.courseType !== null && course.courseType !== undefined && course.courseType !== '') {
            courseUI.el.courseInfoType.text(course.courseType);
        }
        if (course.duration !== null && course.duration !== undefined && course.duration !== '') {
            courseUI.el.courseInfoDuration.text(course.duration);
        }
        if (course.duration !== null && course.duration !== undefined && course.duration !== '') {
            courseUI.el.courseInfoDuration.text(course.duration);
        }
        if (course.status !== null && course.status !== undefined) {
            if (course.status.displayName !== null && course.status.displayName !== undefined && course.status.displayName !== '') {
                courseUI.el.courseInfoStatus.text(course.status.displayName);
            }
            if (course.status.colorCode !== null && course.status.colorCode !== undefined && course.status.colorCode !== '') {
                courseUI.el.courseInfoStatus.css('color', course.status.colorCode);
            }
        }
        if (course.creatorId !== null && course.creatorId !== undefined && course.creatorId !== '') {
            courseUI.el.courseInfoCreator.text(course.creatorId + ": " + course.creatorName);
        }
        if (course.price !== null && course.price !== undefined && course.price !== '') {
            courseUI.el.courseInfoPrice.text(course.price);
        }
        if (course.createdAt !== null && course.createdAt !== undefined && course.createdAt !== '') {
            courseUI.el.courseInfoCreatedAt.text(util.formatDateTime(course.createdAt));
        }
        if (course.updatedAt !== null && course.updatedAt !== undefined && course.updatedAt !== '') {
            courseUI.el.courseInfoUpdatedAt.text(util.formatDateTime(course.updatedAt));
        }
        if (course.slug !== null && course.slug !== undefined && course.slug !== '') {
            courseUI.el.courseInfoSlug.text(course.slug);
        }
        if (course.description !== null && course.description !== undefined && course.description !== '') {
            courseUI.el.courseInfoDescription.text(course.description);
        }
    },
    closeInfoDetail: () => {
        courseUI.el.courseInfoImage.empty();
        courseUI.el.courseInfoId.text('');
        courseUI.el.courseInfoStatus.text('');
        courseUI.el.courseInfoCode.text('');
        courseUI.el.courseInfoDisplayName.text('');
        courseUI.el.courseInfoCreatedAt.text('');
        courseUI.el.courseInfoUpdatedAt.text('');
        courseUI.el.courseInfoSlug.text('');
        courseUI.el.courseInfoDescription.text('');
        courseUI.el.courseInfoType.text('');
        courseUI.el.courseInfoDuration.text('');
        courseUI.el.courseInfoPrice.text('');
        courseUI.el.courseInfoCreator.text('');
    },

    renderEditModal: (course, statuses, types) => {
        if (course == null || course == undefined) {
            return;
        }
        // render hình ảnh
        if (course.apiUrl !== null && course.apiUrl !== undefined) {
            courseUI.el.courseEditImage.empty();
            if (course.apiUrl === '') {
                let img = $(`<img src="/images/image_default.webp" alt="Image">`)
                courseUI.el.courseEditImage.append(img);
            } else {
                let img = $(`<img src="${course.apiUrl}" alt="Image">`)
                courseUI.el.courseEditImage.append(img);
            }
        }
        // render mã
        if (course.id !== null && course.id !== undefined && course.id !== '') {
            courseUI.el.courseEditId.text("#" + course.id);
            courseUI.el.courseEditId.attr('data-id', course.id);
        }
        // render trạng thái
        if (course.status !== null && course.status !== undefined && course.status.name !== null
            && course.status.name !== undefined && course.status.name !== '' && statuses !== null && statuses !== undefined && statuses.length > 0) {
            statuses.forEach(status => {
                let option = $(`<option value="${status.name}" ${status.name === course.status.name ? 'selected' : ''}>${status.displayName}</option>`)
                courseUI.el.courseEditStatus.append(option);
            })
        }
        console.log(course);
        console.log(types)
        // render loại khóa học
        if (course.courseType !== null && course.courseType !== undefined && types !== null && types !== undefined && types.length > 0) {
                console.log("GoIn")
                types.forEach(type => {
                    
                let option = $(`<option value="${type.name}" ${type.displayName === course.courseType ? 'selected' : ''}>${type.displayName}</option>`)
                courseUI.el.courseEditCourseType.append(option);
            })
        }
        // render mã khóa học
        if (course.code !== null && course.code !== undefined && course.code !== '') {
            courseUI.el.courseEditCode.val(course.code);
        }
        // render tên hiển thị
        if (course.displayName !== null && course.displayName !== undefined && course.displayName !== '') {
            courseUI.el.courseEditDisplayName.val(course.displayName);
        }
        // render mô tả
        if (course.description !== null && course.description !== undefined && course.description !== '') {
            courseUI.el.courseEditDescription.val(course.description);
        }
        // render giá khóa học
        if (course.price !== null && course.price !== undefined && course.price !== '') {
            courseUI.el.courseEditPrice.val(course.price);
        }
    },
    closeEditModal: () => {
        courseUI.el.courseEditImage.empty();
        courseUI.el.courseEditId.text('');
        courseUI.el.courseEditId.removeAttr('data-id');
        courseUI.el.courseEditStatus.empty();
        courseUI.el.courseEditCourseType.empty();
        courseUI.el.courseEditCode.val('');
        courseUI.el.courseEditDisplayName.val('');
        courseUI.el.courseEditDescription.val('');
        courseUI.el.courseEditPrice.val('');
    },
    renderAddModal: (course, subjects, managers, subjectTextbooks) => {
        if (course == null || course == undefined) {
            return;
        }
        if (course.id !== null && course.id !== undefined && course.id !== '') {
            courseUI.el.courseAddId.text(course.id);
            courseUI.el.courseAddId.attr('data-id', course.id);
        }
        if (course.code !== null && course.code !== undefined && course.code !== '') {
            courseUI.el.courseAddCode.text(course.code);
        }
        if (course.displayName !== null && course.displayName !== undefined && course.displayName !== '') {
            courseUI.el.courseAddDisplayName.text(course.displayName);
        }
        // render select
        courseUI.renderOptionSubject(subjects, subjectTextbooks);
        // render table
        courseUI.renderTableSubject(subjectTextbooks);
        // render manager select
        courseUI.renderManagerSelect(managers, course.managerId);
    },
    closeAddModal: () => {
        courseUI.el.courseAddId.text('');
        courseUI.el.courseAddId.removeAttr('data-id');
        courseUI.el.courseAddCode.text('');
        courseUI.el.courseAddDisplayName.text('');
        courseUI.el.selectSubject.empty();
        courseUI.el.selectTextbook.empty();
        courseUI.el.tableSubject.empty();
        courseUI.el.selectManager.empty();
    },
    renderTableSubject: (subjectTextbooks) => {
        if (subjectTextbooks.length === 0) {
            return;
        }
        courseUI.el.tableSubject.empty();
        subjectTextbooks.forEach(subject => {
            let tr = $(`
                <tr data-id="${subject.subjectId}">
                    <td>${subject.subjectId}</td>
                    <td>${subject.subjectName}</td>
                    <td>${subject.textbookName}</td>
                    <td>
                        <i class="fa-solid fa-trash-can delete-btn"></i>
                    </td>
                </tr>    
            `)
            courseUI.el.tableSubject.append(tr);
        })
    },
    renderOptionSubject: (allSubjects, selectedSubjects) => {
        courseUI.el.selectSubject.empty();
        let optionEmpty = $(`<option value="">Trống</option>`);
        courseUI.el.selectSubject.append(optionEmpty);
        const filteredSubjects = (allSubjects?.length && selectedSubjects?.length) 
            ? allSubjects.filter(subject => 
                !selectedSubjects.some(selected => selected.subjectId === subject.id)
            )
            : allSubjects;
        if (filteredSubjects.length === 0) {
            return;
        }
        filteredSubjects.forEach(subject => {
            let option = $(`
                <option value="${subject.id}">${subject.displayName} ( ${subject.name} )</option>    
            `)
            courseUI.el.selectSubject.append(option);
        })
    },
    renderManagerSelect: (managers, managerId) => {
        if (managers.length === 0) {
            return;
        }
        courseUI.el.selectManager.empty();
        let optionEmpty = $(`<option value="0">Trống</option>`);
        courseUI.el.selectManager.append(optionEmpty)
        managers.forEach(manager => {
            let option = $(`
                <option value="${manager.id}" ${manager.id == managerId ? 'selected' : ''}>${manager.displayName} (${manager.id})</option>    
            `)
            courseUI.el.selectManager.append(option);
        })
    },
    renderTextbookSelect: (textbooks) => {
        courseUI.el.selectTextbook.empty();
        if (textbooks.length === 0) {
            return;
        }
        textbooks.forEach(textbook => {
            let option = $(`
                <option value="${textbook.id}">${textbook.name} (${textbook.author})</option>    
            `)
            courseUI.el.selectTextbook.append(option);
        })
    }
}

export default courseUI;