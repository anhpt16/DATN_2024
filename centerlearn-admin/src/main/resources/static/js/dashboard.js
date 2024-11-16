

$(document).ready(function() {
    const btn_zoom = $("#btn-zoom");
    const admin_menu_col_container = $(".admin-menu-container");
    const admin_menu_col_el = $(".admin-menu-col");
    const admin_menu_item_a_el = $(".admin-menu-item > a");
    const admin_menu_item_i_el = $(".admin-menu-item > a > i");
    const admin_menu_item_p_el = $(".admin-menu-item > a > p");
    const admin_menu_scroll_container = $(".admin-menu-scroll-container");
    const admin_menu_title_p = $(".admin-menu-title > .title-name > p");
    const admin_content_col = $(".admin-content-col");

    let ps = new PerfectScrollbar('.admin-menu-scroll-container');
    // let navbarOffSet = admin_menu_col_container.offset().top;

    // $(window).scroll(function() {
    //     let scrollPos = $(window).scrollTop();
    //     scrollPos >= navbarOffSet 
    //         ? admin_menu_col_container.addClass("position-fixed") 
    //         : admin_menu_col_container.removeClass("position-fixed");
        
    // })

    btn_zoom.on("click", function(){
        admin_menu_item_p_el.toggleClass("width-0 ");
        admin_menu_item_i_el.toggleClass("width-auto");
        admin_menu_title_p.toggleClass("d-none");
        admin_menu_col_container.toggleClass("width-collapse width-expand");
    });

    admin_menu_scroll_container.on("wheel", function(e) {
        e.preventDefault();
    })

});