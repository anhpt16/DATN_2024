
import loginService from "../service/LoginService.js";
import { showNotification } from "../ui/notification.js";


$(document).ready(function() {
    const usernameInput = $("#username");
    const passwordInput = $("#password");
    const loginBtn = $("#login-btn");

    loginBtn.on('click', async function(event) {
        event.preventDefault();
        const formData = {
            username: usernameInput.val().trim(),
            password: passwordInput.val().trim(),
        }
        try {
            const response = await loginService.login(formData);
            console.log(response);
            if (response.status === 200) {
                window.location.href = "/account/user?lang=vi";
            }
        } catch (error) {
            showNotification("error", "Đăng nhập thất bại", error.message);
        }
    })
})