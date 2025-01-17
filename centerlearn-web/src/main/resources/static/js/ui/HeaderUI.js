
const headerUI = {
    el: {

    },
    renderAvatarUser: (user) => {
        const userInfoContainer = $("#user-info");
        userInfoContainer.empty();
        //Ảnh
        const avatarUrl = user.userImageUrl || "/images/user_image_default.jpg";
        userInfoContainer.append(`
            <a class"text-decoration-none" href="javascript:void(0)" data-bs-toggle="dropdown" aria-expanded="false">
                <img id="header-avatar" src="${avatarUrl}" alt="avatar" class="rounded-circle me-2">
                <span class="user-name">${user.displayName}</span>
            </a>
            <ul class="dropdown-menu" aria-labelledby="dLabel">
                <li>
                    <a class="dropdown-item" href="/personal/course?lang=vi">Quản lý</a>
                </li>
                <li>
                    <a class="dropdown-item" href="javascript:void(0)">Đăng xuất</a>
                </li>
            </ul>
        `);
        //Tên
    }
}

export default headerUI;