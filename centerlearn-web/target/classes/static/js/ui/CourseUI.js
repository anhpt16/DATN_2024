
const courseUI = {
    el: {
        orderCode: $("#orderCode"),
        orderName: $("#orderName"),
        orderType: $("#orderType"),
        orderDuration: $("#orderDuration"),
        orderPrice: $("#orderPrice")
    },

    renderOrderModal: (response) => {
        if (response == null || response == undefined) {
            return;
        }
        if (response.code !== null && response.code !== undefined && response.code !== "") {
            courseUI.el.orderCode.text(response.code);
            courseUI.el.orderCode.attr('data-id', response.id);
        }
        if (response.displayName !== null && response.displayName !== undefined && response.displayName !== "") {
            courseUI.el.orderName.text(response.displayName);
        }
        if (response.courseType !== null && response.courseType !== undefined && response.courseType !== "") {
            courseUI.el.orderType.text(response.courseType);
        }
        if (response.duration !== null && response.duration !== undefined && response.duration !== "") {
            courseUI.el.orderDuration.text(response.duration);
        }
        if (response.price !== null && response.price !== undefined && response.price !== "") {
            courseUI.el.orderPrice.text(response.price);
        }
    },
    closeOrderModal: () => {
        courseUI.el.orderCode.text('');
        courseUI.el.orderCode.removeAttr('data-id');
        courseUI.el.orderName.text('');
        courseUI.el.orderType.text('');
        courseUI.el.orderDuration.text('');
        courseUI.el.orderPrice.text('');
    },
    
}

export default courseUI;