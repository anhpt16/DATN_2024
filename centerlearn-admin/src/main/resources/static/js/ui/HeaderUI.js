
const headerUI = {
    el: {

    },
    renderAvatarUser: (user) => {
        const userInfoContainer = $("#user-info");
        userInfoContainer.empty();
        //Ảnh
        const avatarUrl = user.userImageUrl || "https://via.placeholder.com/40";
        userInfoContainer.append(`<img src="${avatarUrl}" alt="avatar" class="rounded-circle me-2">`);
        //Tên
        if (user.displayName) {
            userInfoContainer.append(`<span class="user-name">${user.displayName}</span>`);
        }
    }
}

export default headerUI;