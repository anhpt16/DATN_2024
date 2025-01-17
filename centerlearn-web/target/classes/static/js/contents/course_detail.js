import courseService from "../service/CourseService.js";
import courseUI from "../ui/CourseUI.js";
import orderService from "../service/OrderService.js";
import { showNotification } from "../ui/notification.js";

$(document).ready(function() {

    $("#order-btn").on('click', async function() {
        let code = $("#code").attr('data-id');
        if (code == null || code == undefined || code === "") {
            return;
        }
        try {
            const response = await courseService.getCourseByCode(code);
            console.log(response)
            courseUI.renderOrderModal(response);
            $("#order-modal").find('.modal').modal('show');
        } catch (error) {
            console.log("Error: " + error);
        }
    });
    // Đóng modal chi tiết môn học
    $(document).on('hidden.bs.modal', '#orderModal', function() {
        courseUI.closeOrderModal();
    });

    $("#btn-accept").on('click', async function() {
        let courseId = $("#orderCode").attr('data-id');
        console.log(courseId);
        if (courseId == null || courseId == undefined || courseId == "") {
            return;
        }
        try {
            const response = await courseService.addOrder(courseId);
            window.location.href = response;
        } catch (error) {
            console.log("Error: " + error);
        }
    })
    // Khi nhấn xác nhận


})