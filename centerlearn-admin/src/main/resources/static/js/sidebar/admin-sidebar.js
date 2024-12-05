import controllerService from "../service/ControllerService.js";


$(document).ready(function () {
    const sidebarMenu = $(".sidebar-menu");
    const sidebarSubmenu = $(".sidebar-submenu");
    const iconMenuCollapse = $(".icon-collapse");
    const currentPath = window.location.pathname;
    const requestUri = $("a.request");

    sidebarSubmenu.find('a').each(function() {
        const menuLink = $(this);
        const menuLinkHref = menuLink.attr('href');
        const parentMenu = menuLink.closest('li').closest('.sidebar-submenu').closest('.nav-item');
        const iconCollapse = parentMenu.find('.icon-collapse');
        console.log(menuLinkHref);
        if (currentPath.startsWith(menuLinkHref) || menuLinkHref.startsWith(currentPath)) {
            if (menuLinkHref && menuLinkHref.trim() !== '#') {
                menuLink.addClass('active');
                menuLink.closest('.sidebar-submenu').show();
                iconCollapse.removeClass('fa-caret-down').addClass('fa-caret-right');
            }
        }
    })

    requestUri.on('click', async function(event) {
        event.preventDefault();
        const requestController = $(this).attr('href');
        console.log(requestController);
        try {
            const response = await controllerService(`${requestController}`, "GET");
            window.location.href = requestController;
        } catch (error) {
            console.log(error);
        }
    })

    // Toggle submenu khi click
    sidebarMenu.click(function () {
        $(this).next(sidebarSubmenu).slideToggle();
        $(this).find(iconMenuCollapse).toggleClass("fa-caret-down fa-caret-right");
    });


});