
const headerUI = {
    el: {

    },
    renderAvatarUser: (user) => {
        const userInfoContainer = $("#user-info");
        userInfoContainer.empty();
        //Ảnh
        const avatarUrl = user.userImageUrl || "/images/user_image_default.jpg";
        userInfoContainer.append(`<img id="header-avatar" src="${avatarUrl}" alt="avatar" class="rounded-circle me-2">`);
        //Tên
        if (user.displayName) {
            userInfoContainer.append(`<span class="user-name">${user.displayName}</span>`);
        }
    }
}

export default headerUI;