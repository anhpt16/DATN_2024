import util from "../utils.js";

const lessonUI = {
    renderInfoSection: (section) => {
        $("#info-modal .modal-body").empty();
        if (section !== null) {
            let content = $(`
                <div class="row mb-2">
                    <div class="col-lg-4 fw-bold">Mã: </div>
                    <div class="col-lg-8">${section.id !== null ? section.id : ''}</div>
                </div>
                <div class="row mb-2">
                    <div class="col-lg-4 fw-bold">Tiêu đề: </div>
                    <div class="col-lg-8">${section.title !== null ? section.title : ''}</div>
                </div>
                <div class="row mb-2">
                    <div class="col-lg-4 fw-bold">Người tạo: </div>
                    <div class="col-lg-8">${section.creatorId !== null && section.creatorName !== null ? section.creatorId + ': ' + section.creatorName : ''}</div>
                </div>
                <div class="row mb-2">
                    <div class="col-lg-4 fw-bold">Trạng thái: </div>
                    <div class="col-lg-8" 
                        style="${section.status !== null && section.status.colorCode !== null ? `color: ${section.status.colorCode};` : ''}">
                        ${section.status !== null && section.status.displayName !== null ? section.status.displayName : ''}
                    </div>
                </div>
                <div class="row mb-2">
                    <div class="col-lg-4 fw-bold">Ngày tạo: </div>
                    <div class="col-lg-8">${section.createdAt !== null ? util.formatDateTime(section.createdAt) : ''}</div>
                </div>
                <div class="row mb-2">
                    <div class="col-lg-4 fw-bold">Ngày cập nhật: </div>
                    <div class="col-lg-8">${section.updatedAt !== null ? util.formatDateTime(section.updatedAt) : ''}</div>
                </div>
                <div class="row mb-2">
                    <div class="col-lg-4 fw-bold">Bài học: </div>
                    <div class="col-lg-8">${section.lessonId !== null && section.lessonName !== null ? section.lessonId + ': ' + section.lessonName : ''}</div>
                </div>
                <div class="row mb-2">
                    <div class="col-lg-4 fw-bold">Số thứ tự: </div>
                    <div class="col-lg-8">${section.priority !== null ? section.priority : ''}</div>
                </div>
            `)
            $("#info-modal .modal-body").append(content);
        }
    },
    closeInfoModal: () => {
        $("#info-modal .modal-body").empty();
    },
    closeEditModal: () => {
        $("#edit-modal .modal-body").empty();
    },
    closeAddModal: () => {
        $("#add-modal .modal-body").empty();
    },
    renderInfoExercise: (exercise) => {
        $("#info-modal .modal-body").empty();
        if (exercise == null) {
            return;
        }
        let content = $(`
            <div class="row mb-2">
                <div class="col-lg-4 fw-bold">Mã: </div>
                <div class="col-lg-8">${exercise.id !== null ? exercise.id : ''}</div>
            </div>
            <div class="row mb-2">
                <div class="col-lg-4 fw-bold">Tiêu đề: </div>
                <div class="col-lg-8">${exercise.title !== null ? exercise.title : ''}</div>
            </div>
            <div class="row mb-2">
                <div class="col-lg-4 fw-bold">Người tạo: </div>
                <div class="col-lg-8">${exercise.creatorId !== null && exercise.creatorName !== null ? exercise.creatorId + ': ' + exercise.creatorName : ''}</div>
            </div>
            <div class="row mb-2">
                <div class="col-lg-4 fw-bold">Trạng thái: </div>
                <div class="col-lg-8" 
                    style="${exercise.status !== null && exercise.status.colorCode !== null ? `color: ${exercise.status.colorCode};` : ''}">
                    ${exercise.status !== null && exercise.status.displayName !== null ? exercise.status.displayName : ''}
                </div>
            </div>
            <div class="row mb-2">
                <div class="col-lg-4 fw-bold">Ngày tạo: </div>
                <div class="col-lg-8">${exercise.createdAt !== null ? util.formatDateTime(exercise.createdAt) : ''}</div>
            </div>
            <div class="row mb-2">
                <div class="col-lg-4 fw-bold">Ngày cập nhật: </div>
                <div class="col-lg-8">${exercise.updatedAt !== null ? util.formatDateTime(exercise.updatedAt) : ''}</div>
            </div>
            <div class="row mb-2">
                <div class="col-lg-4 fw-bold">Thuật ngữ: </div>
                <div class="col-lg-8">${exercise.userTermId !== null && exercise.userTermName !== null ? exercise.userTermId + ': ' + exercise.userTermName : ''}</div>
            </div>
        `);
        $("#info-modal .modal-body").append(content);
    },
    renderInfoLesson: (lesson) => {
        $("#info-modal .modal-body").empty();
        if (lesson == null) {
            return;
        }
        let content = $(`
                <div class="row mb-2">
                    <div class="col-lg-4 fw-bold">ID:</div>
                    <div class="col-lg-8">${lesson.id !== null ? lesson.id : ''}</div>
                </div>
                <div class="row mb-2">
                    <div class="col-lg-4 fw-bold">Title:</div>
                    <div class="col-lg-8">${lesson.title !== null ? lesson.title : ''}</div>
                </div>
                <div class="row mb-2">
                    <div class="col-lg-4 fw-bold">Người tạo:</div>
                    <div class="col-lg-8">${lesson.creatorId !== null && lesson.creatorName !== null ? lesson.creatorId + ': ' + lesson.creatorName : ''}</div>
                </div>
                <div class="row mb-2">
                    <div class="col-lg-4 fw-bold">Trạng thái:</div>
                    <div class="col-lg-8" 
                    style="${lesson.status !== null && lesson.status.colorCode !== null ? `color: ${lesson.status.colorCode};` : ''}">
                    ${lesson.status !== null && lesson.status.displayName !== null ? lesson.status.displayName : ''}
                </div>
                </div>
                <div class="row mb-2">
                    <div class="col-lg-4 fw-bold">Ngày tạo:</div>
                    <div class="col-lg-8">${lesson.createdAt !== null ? util.formatDateTime(lesson.createdAt) : ''}</div>
                </div>
                <div class="row mb-2">
                    <div class="col-lg-4 fw-bold">Ngày cập nhật:</div>
                    <div class="col-lg-8">${lesson.updatedAt !== null ? util.formatDateTime(lesson.updatedAt) : ''}</div>
                </div>
                <div class="row mb-2">
                    <div class="col-lg-4 fw-bold">Thuật ngữ:</div>
                    <div class="col-lg-8">${lesson.userTermId !== null && lesson.userTermName !== null ? lesson.userTermId + ': ' + lesson.userTermName : ''}</div>
                </div>
                <div class="row mb-2">
                    <div class="col-lg-4 fw-bold">Ghi chú:</div>
                    <div class="col-lg-8">${lesson.note !== null ? lesson.note : ''}</div>
                </div>
                <div class="row mb-2">
                    <div class="col-lg-4 fw-bold">Mô tả:</div>
                    <div class="col-lg-8">${lesson.description !== null ? lesson.description : ''}</div>
                </div>
        `)
        $("#info-modal .modal-body").append(content);
    },
    renderEditSection: (section, statuses) => {
        $("#edit-modal .modal-body").empty();
        if (section == null) {
            return;
        }
        let options = statuses.map(status => {
            return `
                <option value="${status.name}" ${section.status.name === status.name ? 'selected':''}>${status.displayName}</option>
            `
        }).join('');
        let content = $(`
            <div class="row mb-2">
                <div class="col-lg-3 fw-bold">Tiêu đề: </div>
                <div class="col-lg-9">
                    <input type="text" class="form-control" id="editTitle">
                </div>
            </div>
            <div class="row mb-2">
                <div class="col-lg-3 fw-bold">Trạng thái: </div>
                <div class="col-lg-9">
                    <select class="form-select" id="editStatus">
                        ${options}
                    </select>
                </div>
            </div>
            <div class="row mb-2">
                <div class="col-lg-3 fw-bold">Bài học: </div>
                <div class="col-lg-9">
                    ${section.lessonId}: ${section.lessonName}
                </div>
            </div>
            <div class="row mb-2">
                <div class="col-lg-3 fw-bold">Số thứ tự: </div>
                <div class="col-lg-9">
                    <input type="number" step="any" min="0.01" class="form-control" id="editPriority">
                </div>
            </div>
        `)
        $("#edit-modal .modal-body").append(content);
        if (section.title !== null && section.title !== "") {
            $("#editTitle").val(section.title);
        }
        if (section.priority !== null && section.priority !== "") {
            $("#editPriority").val(section.priority);
        }
    },
    renderEditLesson: (lesson, statuses) => {
        $("#edit-modal .modal-body").empty();
        if (lesson == null) {
            return;
        }
        let options = statuses.map(status => {
            return `
                <option value="${status.name}" ${lesson.status.name === status.name ? 'selected':''}>${status.displayName}</option>
            `
        }).join('');
        let content = $(`
            <div class="row mb-2">
                <div class="col-lg-4 fw-bold">Tiêu đề:</div>
                <div class="col-lg-8">
                    <input type="text" class="form-control" id="editTitle">
                </div>
            </div>
            <div class="row mb-2">
                <div class="col-lg-4 fw-bold">Trạng thái:</div>
                <div class="col-lg-8">
                    <select name="" class="form-select" id="editStatus">
                        ${options}
                    </select>
                </div>
            </div>
            <div class="row mb-2">
                    <div class="col-lg-4 fw-bold">Số thứ tự: </div>
                    <div class="col-lg-8">
                        <input type="number" id="editPriority" step="any" min="0.01" class="form-control">
                    </div>
                </div>
            <div class="row mb-2">
                <div class="col-lg-4 fw-bold">Ghi chú:</div>
                <div class="col-lg-8">
                    <input type="text" class="form-control" id="editNote">
                </div>
            </div>
            <div class="row mb-2">
                <div class="col-lg-4 fw-bold">Mô tả:</div>
                <div class="col-lg-8">
                    <textarea class="w-100" name="" id="editDescription"></textarea>
                </div>
            </div>    
        `);
        $("#edit-modal .modal-body").append(content);
        if (lesson.title !== null && lesson.title !== undefined) {
            $("#editTitle").val(lesson.title);
        }
        if (lesson.note !== null && lesson.note !== undefined) {
            $("#editNote").val(lesson.note);
        }
        if (lesson.description !== null && lesson.description !== undefined) {
            $("#editDescription").val(lesson.description);
        }
    },
    renderEditExercise: (exercise, statues) => {
        $("#edit-modal .modal-body").empty();
        if (exercise == null) {
            return;
        }
        let options = statues.map(status => {
            return `
                <option value="${status.name}" ${exercise.status.name === status.name ? 'selected':''}>${status.displayName}</option>
            `
        }).join('');
        let content = $(`
            <div class="row mb-2">
                <div class="col-lg-3 fw-bold">Tiêu đề: </div>
                <div class="col-lg-9">
                    <input type="text" class="form-control" id="editTitle">
                </div>
            </div>
            <div class="row mb-2">
                <div class="col-lg-3 fw-bold">Trạng thái: </div>
                <div class="col-lg-9">
                    <select class="form-select" id="editStatus">
                        ${options}
                    </select>
                </div>
            </div>
            <div class="row mb-2">
                <div class="col-lg-3 fw-bold">Thuật ngữ: </div>
                <div class="col-lg-9">
                    <select class="form-select" id="editUserTerm">
                    </select>
                </div>
            </div>
        `)
        $("#edit-modal .modal-body").append(content);
        if (exercise.title !== null && exercise.title !== "") {
            $("#editTitle").val(exercise.title);
        }
    },
    renderAddSection: () => {
        $("#add-modal .modal-body").empty();
        let content = $(`
            <div class="fw-bold mb-3">Thêm mới đề mục</div>
                <div class="row mb-2">
                    <div class="col-lg-4 fw-bold">Tiêu đề:</div>
                    <div class="col-lg-8">
                        <input type="text" id="addTitle" class="form-control">
                    </div>
                </div>
                <div class="row mb-2">
                    <div class="col-lg-4 fw-bold">Số thứ tự: </div>
                    <div class="col-lg-8">
                        <input type="number" id="addPriority" step="any" min="0.01" class="form-control">
                    </div>
                </div>
        `);
        $("#add-modal .modal-body").append(content);
    },
    renderAddExercise: () => {
        $("#add-modal .modal-body").empty();
        let content = $(`
            <div class="fw-bold mb-3">Thêm mới bài tập</div>
                <div class="row mb-2">
                    <div class="col-lg-4 fw-bold">Tiêu đề:</div>
                    <div class="col-lg-8">
                        <input type="text" id="addTitle" class="form-control">
                    </div>
                </div>
                <div class="row mb-2">
                    <div class="col-lg-4 fw-bold">Số thứ tự: </div>
                    <div class="col-lg-8">
                        <input type="number" id="addPriority" step="any" min="0.01" class="form-control">
                    </div>
                </div>
        `);
        $("#add-modal .modal-body").append(content);
    },
    renderAddLesson: () => {
        $("#add-modal .modal-body").empty();
        let content = $(`
            <div class="fw-bold mb-3">Thêm mới bài học</div>
                <div class="row mb-2">
                    <div class="col-lg-4 fw-bold">Tiêu đề:</div>
                    <div class="col-lg-8">
                        <input type="text" id="addTitle" class="form-control">
                    </div>
                </div>
                <div class="row mb-2">
                    <div class="col-lg-4 fw-bold">Số thứ tự: </div>
                    <div class="col-lg-8">
                        <input type="number" id="addPriority" step="any" min="0.01" class="form-control">
                    </div>
                </div>
                <div class="row mb-2">
                    <div class="col-lg-4 fw-bold">Ghi chú: </div>
                    <div class="col-lg-8">
                        <input type="text" id="addNote" class="form-control">
                    </div>
                </div>
                <div class="row mb-2">
                    <div class="col-lg-4 fw-bold">Mô tả: </div>
                    <div class="col-lg-8">
                        <textarea class="form-control" name="" id="addDescription"></textarea>
                    </div>
                </div>
        `);
        $("#add-modal .modal-body").append(content);
    },
    renderAddExerciseExist: () => {

    },
    renderAddLessonExist: () => {

    },
    renderListLessonSection: (lessonsSections) => {
        // Lấy thẻ chứa
        const $container = $(".list-lesson-container");
        
        // Xóa nội dung cũ
        $container.empty();

        // Kiểm tra nếu danh sách lessonsSections rỗng
        if (!lessonsSections || lessonsSections.length === 0) {
            $container.append('<li class="lesson-item">No lessons available</li>');
            return;
        }

        // Lặp qua lessonsSections và tạo nội dung HTML
        lessonsSections.forEach(lessonSection => {
            let lessonDetails = '';

            // Kiểm tra nếu có sections
            if (Array.isArray(lessonSection.sections) && lessonSection.sections.length > 0) {
                lessonDetails = lessonSection.sections.map(section => {
                    return `
                        <li class="lesson-detail-item" data-id="${section.id}">
                            <a href="javascript:void(0)" class="nav-link d-flex align-items-center text-decoration-none">
                                <strong>${section.priority}</strong>.
                                <span class="lesson-detail-title">${section.title}</span>
                            </a>
                        </li>
                    `;
                }).join('');
            } else {
                // Nếu không có sections, hiển thị thông báo
                lessonDetails = `
                    <li class="lesson-detail-item">
                        No sections available
                    </li>
                `;
            }

            // Tạo nội dung HTML cho lessonSection
            const lessonHTML = `
                <li class="lesson-item" data-id="${lessonSection.id}">
                    <a href="javascript:void(0)" class="lesson-title-container d-flex justify-content-between align-items-center border-bottom">
                        <input type="checkbox" class="checkbox-lesson">
                        <span class="lesson-title">${lessonSection.title}</span>
                        <i class="fa-solid fa-angle-down"></i>
                    </a>
                    <ul class="lesson-detail d-none">
                        ${lessonDetails}
                    </ul>
                </li>
            `;

            // Thêm nội dung mới vào container
            $container.append(lessonHTML);
        });
    },
    renderListLessonExercise: (lessonsExercises) => {
        // Lấy thẻ chứa
        const $container = $(".list-lesson-container");
        
        // Xóa nội dung cũ
        $container.empty();
    
        // Kiểm tra nếu danh sách lessonsExercises rỗng
        if (!lessonsExercises || lessonsExercises.length === 0) {
            $container.append('<li class="lesson-item">No lessons available</li>');
            return;
        }
    
        // Lặp qua lessonsExercises và tạo nội dung HTML
        lessonsExercises.forEach(lessonExercise => {
            let lessonDetails = '';
    
            // Kiểm tra nếu có exercises
            if (Array.isArray(lessonExercise.exercises) && lessonExercise.exercises.length > 0) {
                lessonDetails = lessonExercise.exercises.map(exercise => {
                    return `
                        <li class="lesson-detail-item" data-id="${exercise.id}">
                            <a href="javascript:void(0)" class="nav-link d-flex align-items-center text-decoration-none">
                                <strong>${exercise.priority}</strong>:
                                <span class="lesson-detail-title">${exercise.title}</span>
                            </a>
                        </li>
                    `;
                }).join('');
            } else {
                // Nếu không có exercises, hiển thị thông báo
                lessonDetails = `
                    <li class="lesson-detail-item">
                        No exercises available
                    </li>
                `;
            }
    
            // Tạo nội dung HTML cho lessonExercise
            const lessonHTML = `
                <li class="lesson-item" data-id="${lessonExercise.id}">
                    <a href="javascript:void(0)" class="lesson-title-container d-flex justify-content-between align-items-center border-bottom">
                        <input type="checkbox" class="checkbox-lesson">
                        <span class="lesson-title">${lessonExercise.title}</span>
                        <i class="fa-solid fa-angle-down"></i>
                    </a>
                    <ul class="lesson-detail d-none">
                        ${lessonDetails}
                    </ul>
                </li>
            `;
    
            // Thêm nội dung mới vào container
            $container.append(lessonHTML);
        });
    },
}

export default lessonUI;