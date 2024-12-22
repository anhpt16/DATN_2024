
import lessonService from "../service/LessonService.js";
import textbookService from "../service/TextbookService.js";
import lessonUI from "../ui/LessonUI.js";
import textbookUI from "../ui/TextbookUI.js";
import { showNotification } from "../ui/notification.js";
import { showConfirmation } from "../ui/notification.js";


$(document).ready(function() {
    const lessonTitleContainer = $(".lesson-title-container");
    const lessonDetail = $(".lesson-detail");
    const lessonDetailItem = $(".lesson-detail-item a");
    const menuChoose = $(".menu-choose");
    const menuChooseSection = $("#menu-choose-section");
    const menuChooseExercise = $("#menu-choose-exercise");
    const lessonCb = $(".lesson-title-container input");
    const listLessonContainer = $(".list-lesson-container");

    tinymce.init({
        selector: '#textbook-description', // ID của textarea
        license_key: 'gpl',
        height: 880, // Chiều cao trình soạn thảo
        menubar: false, // Tắt thanh menu (nếu muốn)
        plugins: [
            'advlist', 'autolink', 'link', 'image', 'lists', 'charmap', 'preview', 'anchor', 'pagebreak',
            'searchreplace', 'wordcount', 'visualblocks', 'visualchars', 'code', 'fullscreen', 'insertdatetime',
            'media', 'table', 'emoticons', 'help', 'code'
          ],
          toolbar: 'undo redo | styles | bold italic | alignleft aligncenter alignright alignjustify | ' +
          'bullist numlist outdent indent | link image | print preview media fullscreen | ' +
          'forecolor backcolor emoticons | help' + 'code',
        menubar: 'file edit view insert format tools table help', // Hiển thị đầy đủ menu
        content_style: 'body { font-family:Arial,sans-serif; font-size:14px }'
    });

    $(".list-lesson-container").on('click', ".lesson-title-container input", function(event) {
        event.stopPropagation();
        if (this.checked) {
            $(".lesson-title-container input").not(this).prop('checked', false);
        }
        checkLessonCb();
    })

    $(".list-lesson-container").on('click', ".lesson-item .lesson-title-container", function() {
        console.log("This done")
        const thisItemDetail = $(this).closest("li").find('ul');
        const thisItemIcon = $(this).find('i');
        if (thisItemDetail.hasClass('d-none')) {
            thisItemDetail.removeClass('d-none');
            thisItemIcon.removeClass('fa-angle-down').addClass('fa-angle-right');
        } else {
            thisItemDetail.addClass('d-none');
            thisItemIcon.removeClass('fa-angle-right').addClass('fa-angle-down');
        }
    })

    let lessonIdCurrent = null;
    let detailIdCurrent = null;
    function setNull() {
        lessonIdCurrent = null;
        detailIdCurrent = null;
    }
    $(".list-lesson-container").on('click', ".lesson-detail-item a", async function() {
        $(".lesson-detail-item a").removeClass('active');
        $(this).addClass('active')
        checkLessonCb();
        checkLessonDetail();
        tinymce.get('textbook-description').setContent('');
        setNull();
        let detailId = $(this).closest('li').attr('data-id');
        let lessonId = $(this).closest('li').closest('ul').closest('li').attr('data-id');
        if (detailId !== null && lessonId !== null) {
            lessonIdCurrent = lessonId;
            detailIdCurrent = detailId;
        }
        console.log(lessonId + " " + detailId);
        console.log(menuSelected());
        const editor = tinymce.get("textbook-description")
        if (menuSelected() === 0) {
            try {
                const response = await lessonService.getLessonSectionDetail(lessonId, detailId);
                console.log(response);
                if (editor) {
                    editor.setContent(unescapeHTML(response.content));
                }
            } catch (error) {
                console.log("Error: " + error);
            }
        }
        else if (menuSelected() === 1) {
            try {
                const response  = await lessonService.getLessonExerciseDetail(detailId);
                console.log(response);
                if (editor) {
                    editor.setContent(response.content);
                }
            } catch (error) {
                console.log("Error: " + error);
            }
        }
        else {
            console.log("Menu Choosen Invalid");
        }
    })

    let lessonIdCb = null;
    // Xử lý sự kiện checked
    $(".list-lesson-container").on('change', '.checkbox-lesson', function() {
        if ($(this).is(':checked')) {
            let id = $(this).closest('a').closest('li').attr('data-id');
            console.log(id)
            if (id !== null && id !== undefined && id !== "") {
                lessonIdCb = id;
            }    
        } else {
            lessonIdCb = null;
            console.log(lessonIdCb)
        }
    })

    $("#menu-choose-exercise").on('click', async function(event) {
        if ($(this).data('selected') === true) {
            event.preventDefault(); // Ngăn hành động click lại
        } else {
            console.log('ex done')
            $(".menu-choose").data('selected', false);
            $(this).data('selected', true); // Đánh dấu đã chọn
            $(".menu-choose").removeClass('active');
            $(this).addClass('active');
            $(".menu-section-btn").addClass('d-none');
            $(".menu-exercise-btn").removeClass('d-none');
            // Lấy danh sách các bài tập của các bài học
            try {
                let id = $("#textbookId").attr('data-id');
                if (id !== null) {
                    const response = await textbookService.getLessonsExercisesByTextbookId(id);
                    console.log(response)
                    textbookUI.renderTextbookLessonDetail(response);
                }
            } catch (error) {
                console.log("Error: " + error);
            } finally {
                checkLessonCb();
                checkLessonDetail();
                setNull();
                lessonIdCb = null;
                tinymce.get('textbook-description').setContent('');
            }
        }
    })

    $("#menu-choose-section").on('click', async function(event) {
        if ($(this).data('selected') === true) {
            event.preventDefault(); // Ngăn hành động click lại
        } else {
            console.log('se done')
            $(".menu-choose").data('selected', false);
            $(this).data('selected', true); // Đánh dấu đã chọn
            $(".menu-choose").removeClass('active');
            $(this).addClass('active');
            $(".menu-exercise-btn").addClass('d-none');
            $(".menu-section-btn").removeClass('d-none');

            // Lấy danh sách các đề mục của các bài học
            // Lấy danh sách các bài tập của các bài học
            try {
                let id = $("#textbookId").attr('data-id');
                if (id !== null) {
                    const response = await textbookService.getLessonsSectionsByTextbookId(id);
                    console.log(response)
                    textbookUI.renderTextbookLessonDetail(response);
                }
            } catch (error) {
                console.log("Error: " + error);
            } finally {
                checkLessonCb();
                checkLessonDetail();
                setNull();
                lessonIdCb = null;
                tinymce.get('textbook-description').setContent('');
            }
        }
    })

    // Xem thông tin bài học
    $("#lesson-btn-view").on('click', async function() {
        if (lessonIdCb == null || lessonIdCb == undefined || lessonIdCb === "") {
            return;
        }
        let id = lessonIdCb;
        try {
            const response = await lessonService.getLessonById(id);
            lessonUI.renderInfoLesson(response);
            $("#info-modal").find('.modal').modal('show')
        } catch (error) {
            console.log("Error: " + error);
        }
    })
    // Sửa thông tin bài học
    $("#lesson-btn-edit").on('click', async function() {
        if (lessonIdCb == null || lessonIdCb == undefined || lessonIdCb === "") {
            return;
        }
        let id = lessonIdCb;
        try {
            const response = await lessonService.getLessonById(id);
            const statuses = await lessonService.getLessonStatuses();
            if (response !== null && response !== undefined) {
                if (response.title !== null && response.title !== "") {
                    EditTitleOrigin = response.title;
                }
                if (response.status !== null && response.status.name !== null && response.status.name !== "") {
                    EditStatusOrigin = response.status.name;
                }
                if (response.note !== null && response.note !== "") {
                    EditNoteOrigin = response.note;
                }
                if (response.description !== null && response.description !== "") {
                    EditDescriptionOrigin = response.description;
                }
            }
            lessonUI.renderEditLesson(response, statuses);
            $("#edit-modal").find('.modal').modal('show');
            $("#edit-modal").attr('data-id', 1);
        } catch (error) {
            console.log("Error: " + error);
        }
    })
    // Thêm bài học mới cho giáo trình
    $("#lesson-btn-add").on('click', function() {
        let id = $("#textbookId").attr('data-id');
        if (id == null || id == undefined || id === "") {
            return;
        }
        lessonUI.renderAddLesson();
        $("#add-modal").find('.modal').modal('show');
        $("#add-modal").attr('data-id', 1);
    })
    // Thêm đề mục mới cho bài học
    $("#section-btn-add").on('click', function() {
        if (lessonIdCb == null || lessonIdCb == undefined) {
            return;
        }
        lessonUI.renderAddSection();
        $("#add-modal").find('.modal').modal('show');
        $("#add-modal").attr('data-id', 2);
    })
    // Thêm bài tập mới cho bài học
    $("#exercise-btn-add").on('click', function() {
        if (lessonIdCb == null || lessonIdCb == undefined) {
            return;
        }
        lessonUI.renderAddExercise();
        $("#add-modal").find('.modal').modal('show');
        $("#add-modal").attr('data-id', 3);
    })

    // Xóa đề mục
    $("#section-btn-delete").on('click', async function() {
        if (menuSelected() === 1) {
            return;
        }
        let lessonId = lessonIdCurrent;
        let lessonTitle = $(`.lesson-item[data-id='${lessonId}']`).find('.lesson-title').text();
        let sectionId = detailIdCurrent;
        let sectionTitle = $(`.lesson-detail-item[data-id='${sectionId}']`).find('.lesson-detail-title').text();
        if (lessonId == null || lessonId == undefined || sectionId == null || sectionId == undefined) {
            return;
        }
        if (lessonTitle == null || lessonTitle == undefined || sectionTitle == null || sectionTitle == undefined) {
            return;
        }
        try {
            const result = await showConfirmation('delete', 'Xác nhận xóa' ,`Đề mục: ${sectionTitle}<br>Bài học: ${lessonTitle}`);
            if (result) {
                try {
                    const response = await lessonService.deleteSection(lessonId, sectionId);
                    showNotification('success', '', 'Xóa thành công');
                    await renderListLesson();
                } catch (error) {
                    console.log("Error: " + error);
                    showNotification('error', '', 'Xóa thất bại')
                }
            }
        } catch (error) {
            console.log("Error: " + error);
        }
    })
    // Xóa bài tập ra khỏi bài học
    $("#exercise-btn-delete").on('click', async function() {
        if (menuSelected() === 0) {
            return;
        }
        let lessonId = lessonIdCurrent;
        let lessonTitle = $(`.lesson-item[data-id='${lessonId}']`).find('.lesson-title').text();
        let exerciseId = detailIdCurrent;
        let exerciseTitle = $(`.lesson-detail-item[data-id='${exerciseId}']`).find('.lesson-detail-title').text();
        if (lessonId == null || lessonId == undefined || exerciseId == null || exerciseId == undefined) {
            return;
        }
        if (lessonTitle == null || lessonTitle == undefined || exerciseTitle == null || exerciseTitle == undefined) {
            return;
        }
        try {
            const result = await showConfirmation('delete', 'Xác nhận xóa' ,`Bài tập: ${exerciseTitle}<br>Bài học: ${lessonTitle}`);
            if (result) {
                try {
                    const response = await lessonService.deleteExercise(lessonId, exerciseId);
                    showNotification('success', '', 'Xóa thành công');
                    await renderListLesson();
                } catch (error) {
                    console.log("Error: " + error);
                    showNotification('error', '', 'Xóa thất bại')
                }
            }
        } catch (error) {
            console.log("Error: " + error);
        }
    })
    // Xóa bài học ra khỏi giáo trình
    $("#lesson-btn-delete").on('click', async function() {
        if (lessonIdCb == null || lessonIdCb == undefined) {
            return;
        }
        let textbookId = $("#textbookId").attr('data-id');
        let lessonId = lessonIdCb;
        let lessonTitle = $(`.lesson-item[data-id='${lessonId}']`).find('.lesson-title').text();
        if (textbookId == null || textbookId == undefined || lessonId == null || lessonId == undefined) {
            return;
        }
        if (lessonTitle == null || lessonTitle == undefined) {
            return;
        }
        try {
            const result = await showConfirmation('delete', 'Xác nhận xóa' ,`Bài học: ${lessonTitle}`);
            if (result) {
                try {
                    const response = await textbookService.deleteLessonFromTextbook(textbookId, lessonId);
                    showNotification('success', '', 'Xóa thành công');
                    await renderListLesson();
                } catch (error) {
                    console.log("Error: " + error);
                    showNotification('error', '', 'Xóa thất bại')
                }
            }
        } catch (error) {
            console.log("Error: " + error);
        }
    })

    // Xem thông tin đề mục
    $("#section-btn-view").on('click', async function() {
        if (menuSelected() === 1) {
            return;
        }
        try {
            const response = await lessonService.getLessonSectionDetail(lessonIdCurrent, detailIdCurrent);
            console.log(response);
            lessonUI.renderInfoSection(response);
            $("#info-modal").find('.modal').modal('show');
        } catch (error) {
            console.log("Error: " + error);
        }
    });
    // Sửa thông tin đề mục
    $("#section-btn-edit").on('click', async function() {
        if (menuSelected() === 1) {
            return;
        }
        try {
            const response = await lessonService.getLessonSectionDetail(lessonIdCurrent, detailIdCurrent);
            const statuses = await lessonService.getSectionStatuses();
            console.log(response);
            if (response.title !== null && response.title !== "") {
                EditTitleOrigin = response.title;
            }
            if (response.status !== null && response.status.name !== null && response.status.name !== "") {
                EditStatusOrigin = response.status.name
            }
            if (response.priority !== null && response.priority !== "") {
                EditPriorityOrigin = response.priority;
            }
            lessonUI.renderEditSection(response, statuses);
            $("#edit-modal").find('.modal').modal('show');
            $("#edit-modal").attr('data-id', 2);
        } catch (error) {
            console.log("Error: " + error);
        }
    })
    // Xem thông tin bài tập
    $("#exercise-btn-view").on('click', async function() {
        if (menuSelected() === 0) {
            return;
        }
        try {
            const response = await lessonService.getLessonExerciseDetail(detailIdCurrent);
            console.log(response);
            lessonUI.renderInfoExercise(response);
            $("#info-modal").find('.modal').modal('show');
        } catch (error) {
            console.log("Error: " + error);
        }
    })
    let EditTitleOrigin = null;
    let EditStatusOrigin = null;
    let EditPriorityOrigin = null;
    let EditNoteOrigin = null;
    let EditDescriptionOrigin = null;
    function setNullEditModal() {
        EditTitleOrigin = null;
        EditStatusOrigin = null;
        EditPriorityOrigin = null;
        EditNoteOrigin = null;
        EditDescriptionOrigin = null;
    }
    // Sửa thông tin bài tập
    $("#exercise-btn-edit").on('click', async function() {
        if (menuSelected() === 0) {
            return;
        }
        try {
            const response = await lessonService.getLessonExerciseDetail(detailIdCurrent);
            const statuses = await lessonService.getExerciseStatuses();
            if (response.title !== null && response.title !== "") {
                EditTitleOrigin = response.title;
            }
            if (response.status !== null && response.status.name !== null && response.status.name !== "") {
                EditStatusOrigin = response.status.name
            }
            lessonUI.renderEditExercise(response, statuses);
            $("#edit-modal").find('.modal').modal('show');
            $("#edit-modal").attr('data-id', 3);
        } catch (error) {
            console.log("Error: " + error);
        }
    })
    // Khi người dùng nhấn nút thêm mới
    $("#add-modal").on('click', "#add-new-btn", async function() {
        let textbookId = $("#textbookId").attr('data-id');
        if (textbookId == null || textbookId == undefined || textbookId === "") {
            return;
        }
        let dataId = $("#add-modal").attr('data-id');
        console.log(dataId)
        if (dataId !== null && dataId !== "") {
            if (dataId == 1) {
                let formData = {};
                let title = $("#addTitle").val();
                let priority = $("#addPriority").val();
                let note = $("#addNote").val();
                let description = $("#addDescription").val();
                if (title !== null && title !== undefined && title !== "") {
                    formData.title = title;
                }
                if (priority !== null && priority !== undefined) {
                    formData.priority = priority;
                }
                if (note !== null && note !== undefined && note !== '') {
                    formData.note = note;
                }
                if (description !== null && description !== undefined && description !== "") {
                    formData.description = description;
                }
                try {
                    const response = await textbookService.addNewLesson(textbookId, formData);
                    showNotification('success', '', 'Thêm mới thành công');
                    await renderListLesson();
                } catch (error) {
                    console.log("Error: " + error);
                } finally {
                    $("#add-modal").find('.modal').modal('hide');
                }
            } else if (dataId == 2) {
                if (lessonIdCb == null || lessonIdCb == undefined) {
                    return;
                }
                let formData = {};
                let title = $("#addTitle").val();
                let priority = $("#addPriority").val();
                if (title !== null && title !== undefined && title !== "") {
                    formData.title = title;
                }
                if (priority !== null && priority !== undefined) {
                    formData.priority = priority;
                }
                try {
                    const response = await lessonService.addSection(lessonIdCb, formData);
                    showNotification('success', '', 'Thêm mới thành công');
                    await renderListLesson();
                } catch (error) {
                    console.log("Error: " + error);
                } finally {
                    $("#add-modal").find('.modal').modal('hide');
                }
            } else if (dataId == 3) {
                if (lessonIdCb == null || lessonIdCb == undefined) {
                    return;
                }
                let formData = {};
                let title = $("#addTitle").val();
                let priority = $("#addPriority").val();
                if (title !== null && title !== undefined && title !== "") {
                    formData.title = title;
                }
                if (priority !== null && priority !== undefined) {
                    formData.priority = priority;
                }
                try {
                    const response = await lessonService.addExercise(lessonIdCb, formData);
                    showNotification('success', '', 'Thêm mới thành công');
                    await renderListLesson();
                } catch (error) {
                    console.log("Error: " + error);
                } finally {
                    $("#add-modal").find('.modal').modal('hide');
                }
            } else {
                console.log("Menu Invalid");
            }
        }
    })
    // Khi người dùng nhấn nút lưu trong edit modal
    $("#edit-modal").on('click', '#edit-modal-save', async function() {
        let dataId = $("#edit-modal").attr('data-id');
        console.log(dataId)
        if (dataId !== null && dataId !== "") {
            if (dataId == 3) {
                let formData = {};
                console.log($("#editTitle"));
                let editTitle = $("#editTitle").val();
                let editStatus = $("#editStatus").val();
                if (EditTitleOrigin == null) {
                    if (editTitle !== null && editTitle !== undefined && editTitle !== "") {
                        formData.title = editTitle;
                    }
                } else {
                    if (editTitle !== null && editTitle !== undefined && editTitle !== EditTitleOrigin) {
                        formData.title = editTitle;
                    }
                }
                if (EditTitleOrigin == null) {
                    if (editStatus !== null && editStatus !== undefined && editStatus !== "") {
                        formData.status = editStatus;
                    }
                } else {
                    if (editStatus !== null && editStatus !== undefined && EditStatusOrigin !== editStatus) {
                        formData.status = editStatus;
                    }
                }
                console.log(formData.title + " " + formData.status);
                try {
                    console.log(lessonIdCurrent + " " + detailIdCurrent + " " + formData);
                    const response = await lessonService.updateExerciseFromLesson(lessonIdCurrent, detailIdCurrent, formData);
                    showNotification('success', '', 'Cập nhật thành công');
                    await renderListLesson();
                } catch (error) {
                    console.log("Error: " + error);
                } finally {
                    $("#edit-modal").find('.modal').modal('hide');
                }
            } else if (dataId == 2) {
                console.log("dm vao trong roi")
                let formData = {};
                let editTitle = $("#editTitle").val();
                let editStatus = $("#editStatus").val();
                let editPriority = $("#editPriority").val();
                if (EditTitleOrigin == null) {
                    if (editTitle !== null && editTitle !== undefined && editTitle !== "") {
                        formData.title = editTitle;
                    }
                } else {
                    if (editTitle !== null && editTitle !== undefined && editTitle !== EditTitleOrigin) {
                        formData.title = editTitle;
                    }
                }
                if (EditTitleOrigin == null) {
                    if (editStatus !== null && editStatus !== undefined && editStatus !== "") {
                        formData.status = editStatus;
                    }
                } else {
                    if (editStatus !== null && editStatus !== undefined && EditStatusOrigin !== editStatus) {
                        formData.status = editStatus;
                    }
                }
                if (EditPriorityOrigin == null) {
                    if (editPriority !== null && editPriority !== undefined && editPriority !== "") {
                        formData.priority = editPriority;
                    }
                } else {
                    if (editPriority !== null && editPriority !== undefined && EditPriorityOrigin !== editPriority) {
                        formData.priority = editPriority;
                    }
                }
                console.log(lessonIdCurrent + " " + detailIdCurrent + " " + formData);
                try {
                    const response = await lessonService.updateSectionFromLesson(lessonIdCurrent, detailIdCurrent, formData);
                    showNotification('success', '', 'Cập nhật thành công');
                    await renderListLesson();
                } catch (error) {
                    console.log("Error: " + error);
                } finally {
                    $("#edit-modal").find('.modal').modal('hide');
                }
            } else if (dataId == 1) {
                if (lessonIdCb == null || lessonCb == undefined || lessonCb === "") {
                    return;
                }
                let id = lessonIdCb;
                let formData = {};
                let editTitle = $("#editTitle").val();
                let editStatus = $("#editStatus").val();
                let editNote = $("#editNote").val();
                let editDescription = $("#editDescription").val();
                if (EditTitleOrigin == null) {
                    if (editTitle !== null && editTitle !== undefined && editTitle !== "") {
                        formData.title = editTitle;
                    }
                } else {
                    if (editTitle !== null && editTitle !== undefined && editTitle !== EditTitleOrigin) {
                        formData.title = editTitle;
                    }
                }
                if (EditTitleOrigin == null) {
                    if (editStatus !== null && editStatus !== undefined && editStatus !== "") {
                        formData.status = editStatus;
                    }
                } else {
                    if (editStatus !== null && editStatus !== undefined && EditStatusOrigin !== editStatus) {
                        formData.status = editStatus;
                    }
                }
                if (EditNoteOrigin == null) {
                    if (editNote !== null && editNote !== undefined && editNote !== "") {
                        formData.note = editNote;
                    }
                } else {
                    if (editNote !== null && editNote !== undefined && editNote !== EditNoteOrigin) {
                        formData.note = editNote;
                    }
                }
                if (EditDescriptionOrigin == null) {
                    if (editDescription !== null && editDescription !== undefined && editDescription !== "") {
                        formData.description = editDescription;
                    }
                } else {
                    if (editDescription !== null && editDescription !== undefined && editDescription !== EditDescriptionOrigin) {
                        formData.description = editDescription;
                    }
                }
                try {
                    const response = await lessonService.updateLessonById(id, formData);
                    showNotification('success', '', 'Cập nhật thành công');
                    await renderListLesson();
                } catch (error) {
                    console.log("Error: " + error);
                } finally {
                    $("#edit-modal").find('.modal').modal('hide');
                }
            } else {
                console.log("Menu Invalid");
            }
        }
    })
    // Lưu nội dung
    $("#detail-content-save").on('click', async function() {
        let formData = {};
        let content = tinymce.get('textbook-description').getContent();
        if (menuSelected() === 0) {
            if (content !== null && content !== undefined && content !== '') {
                formData.content = content;
            }
            try {
                const response = await lessonService.updateSectionFromLesson(lessonIdCurrent, detailIdCurrent, formData);
                showNotification('success', '', 'Thay đổi thành công');
            } catch (error) {
                console.log("Error: " + error);
            }
        } else if (menuSelected() === 1) {
            if (content !== null && content !== undefined && content !== '') {
                formData.content = escapeHTML(content);
            }
            try {
                const response = await lessonService.updateExerciseFromLesson(lessonIdCurrent, detailIdCurrent, formData);
                showNotification('success', '', 'Thay đổi thành công');
            } catch (error) {
                console.log("Error: " + error);
            }
        } else {
            console.log("Menu Invalid")
        }
    })
    // Đóng modal info
    $("#info-modal .modal").on('hidden.bs.modal', function() {
        lessonUI.closeInfoModal();
    })
    // Đóng modal edit
    $("#edit-modal .modal").on('hidden.bs.modal', function() {
        lessonUI.closeEditModal();
        setNullEditModal();
        $("#edit-modal").removeAttr('data-id');
    })
    // ĐÓng modal add
    $("#add-modal .modal").on('hidden.bs.modal', function() {
        lessonUI.closeAddModal();
        $("#edit-modal").removeAttr('data-id');
    })
    function menuSelected() {
        if ($("#menu-choose-section").data('selected') === true) {
            return 0;
        }
        if ($("#menu-choose-exercise").data('selected') === true) {
            return 1;
        }
    }


    function checkLessonCb() {
        const isChecked = $(".lesson-title-container input").is(':checked');
        if (isChecked) {
            $("#lesson-btn-view").prop('disabled', false);
            $("#lesson-btn-edit").prop('disabled', false);
            $("#lesson-btn-delete").prop('disabled', false);
            if ($("#menu-choose-section").hasClass('active')) {
                $("#section-btn-add").prop('disabled', false)
            }
            if ($("#menu-choose-exercise").hasClass('active')) {
                $("#exercise-btn-add").prop('disabled', false);
                $("#exercise-btn-add-exist").prop('disabled', false);
            }
        } else {
            $("#lesson-btn-view").prop('disabled', true);
            $("#lesson-btn-edit").prop('disabled', true);
            $("#lesson-btn-delete").prop('disabled', true);
            if ($("#menu-choose-section").hasClass('active')) {
                $("#section-btn-add").prop('disabled', true)
            }
            if ($("#menu-choose-exercise").hasClass('active')) {
                $("#exercise-btn-add").prop('disabled', true);
                $("#exercise-btn-add-exist").prop('disabled', true);
            }
        }
    }

    function checkLessonDetail() {
        console.log("Go")
        const isChoosen = $(".lesson-detail-item a").hasClass('active');
        console.log(isChoosen)
        if (isChoosen) {
            $("#detail-content-save").prop('disabled', false);
            if ($("#menu-choose-section").hasClass('active')) {
                $("#section-btn-view").prop('disabled', false);
                $("#section-btn-edit").prop('disabled', false);
                $("#section-btn-delete").prop('disabled', false);
            }
            if ($("#menu-choose-exercise").hasClass('active')) {
                $("#exercise-btn-view").prop('disabled', false);
                $("#exercise-btn-edit").prop('disabled', false);
                $("#exercise-btn-delete").prop('disabled', false);
            }
        } else {
            $("#detail-content-save").prop('disabled', true);
                $("#section-btn-view").prop('disabled', true);
                $("#section-btn-edit").prop('disabled', true);
                $("#section-btn-delete").prop('disabled', true);
                $("#exercise-btn-view").prop('disabled', true);
                $("#exercise-btn-edit").prop('disabled', true);
                $("#exercise-btn-delete").prop('disabled', true);
        }
    }

    function escapeHTML(str) {
        return str.replace(/&/g, "&amp;")
                  .replace(/</g, "&lt;")
                  .replace(/>/g, "&gt;")
                  .replace(/"/g, "&quot;")
                  .replace(/'/g, "&#039;");
    }

    function unescapeHTML(str) {
        return str.replace(/&lt;/g, "<")
                  .replace(/&gt;/g, ">")
                  .replace(/&quot;/g, "\"")
                  .replace(/&#039;/g, "'")
                  .replace(/&amp;/g, "&");
    }

    async function renderListLesson() {
        let id = $("#textbookId").attr('data-id');
        let menuCurrent = menuSelected();
        if (id == null || id === "" || menuCurrent == null || menuCurrent === "") {
            return;
        }
        if (menuCurrent === 0) {
            try {
                const response = await textbookService.getLessonsSectionsByTextbookId(id);
                lessonUI.renderListLessonSection(response);
            } catch (error) {
                console.log("Error: " + error);
            }
        } else if (menuCurrent === 1) {
            try {
                const response = await textbookService.getLessonsExercisesByTextbookId(id);
                lessonUI.renderListLessonExercise(response);
            } catch (error) {
                console.log("Error: " + error);
            }
        } else {
            console.log("Menu Invalid");
        }
        checkLessonCb();
        checkLessonDetail();
        setNull();
        setNullEditModal();
        lessonIdCb = null;
        tinymce.get('textbook-description').setContent('');
    }

    $("#btn-back").on('click', function() {
        history.back();
    })
})