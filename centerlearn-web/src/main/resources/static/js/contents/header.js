
import accountService from "../service/AccountService.js";
import headerUI from "../ui/HeaderUI.js";

$(document).ready( async function() {
    console.log("In header")
    try {
        const response = await accountService.getUserByToken();
        console.log(response);
        headerUI.renderAvatarUser(response);
    } catch (error) {
        
    }

})