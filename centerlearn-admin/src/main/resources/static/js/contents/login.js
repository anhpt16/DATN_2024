
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
            if (response && response.headers.get('token')) {
                localStorage.setItem("token", response.headers.get('token'));
                window.location.href = response.headers.get('location');
            } else {
                console.log("No Token From Server !")
                showNotification("error", "Đăng nhập thất bại", "No Token");
            }
        } catch (error) {
            showNotification("error", "Đăng nhập thất bại", error.message);
        }
    })
})