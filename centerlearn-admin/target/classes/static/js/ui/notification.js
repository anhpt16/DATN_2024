export function showNotification(type, title, message, duration = 3000) {
    // Tìm hoặc tạo container
    let container = $('#notification-container');
    if (container.length === 0) {
        container = $('<div id="notification-container"></div>').appendTo('body');
    }
    // Tạo notification
    let notification;
    if (type === 'success') {
        notification = $(`
            <div class="notification success">
                <div class="d-flex">
                    <i class="fa-regular fa-circle-check"></i>
                    <div class="d-flex">
                        <p class="notification-title">${title}</p>
                        <p class="notification-detail">${message}</p>
                    </div>
                </div>
            </div>
        `);
    } else if (type === 'error') {
        notification = $(`
            <div class="notification error">
                <div class="d-flex">
                    <i class="fa-solid fa-triangle-exclamation"></i>
                    <div class="d-flex">
                        <p class="notification-title">${title}</p>
                        <p class="notification-detail">${message}</p>
                    </div>
                </div>
            </div>
        `);
    } else {
        console.error('Invalid notification type');
        return;
    }
    // Thêm notification vào container
    container.append(notification);
    // Tự động xóa sau duration
    setTimeout(() => {
        notification.fadeOut(300, function () {
            $(this).remove();
        });
    }, duration);
}

export function showConfirmation(type, title, content = '') {
    return new Promise((resolve, reject) => {
        //Tìm hoặc tạo container
        let container = $('#confirmation-modal');
        if (container.length === 0) {
            container = $('<div id="confirmation-modal"></div>').appendTo('body');
        }
        // Tạo confirmation
        let confirmation;
        if (type === 'delete') {
            confirmation = $(`
                <div class="modal fade" tabindex="-1">
                    <div class="modal-dialog">
                        <div class="modal-content">
                        <div class="modal-header">
                            <i class="fa-solid fa-ban text-danger"></i>
                            <h5 class="modal-title text-danger ms-3">${title}</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <p>${content}</p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                            <button type="button" class="btn btn-primary confirm-action-btn">Xác nhận</button>
                        </div>
                        </div>
                    </div>
                </div>
            `)
        }
        else if (type === 'add') {
            confirmation = $(`
                <div class="modal fade" tabindex="-1">
                    <div class="modal-dialog">
                        <div class="modal-content">
                        <div class="modal-header">
                            <i class="fa-solid fa-user-pen text-warning"></i>
                            <h5 class="modal-title text-warning ms-3">${title}</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <p>${content}</p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                            <button type="button" class="btn btn-primary confirm-action-btn">Xác nhận</button>
                        </div>
                        </div>
                    </div>
                </div>
            `)
        }
        else if (type === 'update') {
            confirmation = $(`
                <div class="modal fade" tabindex="-1">
                    <div class="modal-dialog">
                        <div class="modal-content">
                        <div class="modal-header">
                            <i class="fa-solid fa-user-pen text-warning"></i>
                            <h5 class="modal-title text-warning ms-3">${title}</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <p>${content}</p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                            <button type="button" class="btn btn-primary confirm-action-btn">Xác nhận</button>
                        </div>
                        </div>
                    </div>
                </div>
            `)
        }
        else {
            console.error('Invalid Confirmation Type');
            return;
        }
        // Thêm nội dung confirmation vào thẻ chứa
        container.append(confirmation);
        // Hiển thị confirmation
        const modalInstance = new bootstrap.Modal(confirmation[0]);
        modalInstance.show();
        // Lắng nghe sự kiện khi đóng confirmation
        confirmation.on('hidden.bs.modal', function () {
            container.empty(); // Xóa tất cả nội dung trong container
            resolve(false);
        });
        confirmation.find('.confirm-action-btn').on('click', () => {
            container.empty();
            modalInstance.hide();
            resolve(true);
        })
    })
}
