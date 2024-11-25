$(document).ready(function () {
    const sidebarMenu = $(".sidebar-menu");
    const sidebarSubmenu = $(".sidebar-submenu");
    const iconMenuCollapse = $(".icon-collapse");

    // Toggle submenu khi click
    sidebarMenu.click(function () {
        $(this).next(sidebarSubmenu).slideToggle();
        $(this).find(iconMenuCollapse).toggleClass("fa-caret-down fa-caret-right");
    });
});