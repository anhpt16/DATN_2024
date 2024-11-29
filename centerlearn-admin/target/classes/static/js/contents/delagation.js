
import accountService from "../service/AccountService.js";
import delegationUI from "../ui/DelegationUI.js";

$(document).ready(function() {
    const tbodyEl = $("#delegation-body");
    const searchAccountContent = $("#search-account-content");
    const searchAccountType = $("#search-account-type");
    const searchAccountButton = $("#search-account-btn");

    searchAccountButton.on('click', getAccount);

    async function getAccount() {
        let queryString = '';
        let accountContent = searchAccountContent.val();
        
        if (accountContent !== null && accountContent !== undefined && accountContent !== '' && searchAccountType.val() === '1') {
            queryString += "&id=" + accountContent;
        }
        else if (accountContent !== null && accountContent !== undefined && accountContent !== '' && searchAccountType.val() === '2') {
            queryString += "&email=" + accountContent;
        }
        else if (accountContent !== null && accountContent !== undefined && accountContent !== '' && searchAccountType.val() === '3') {
            queryString += "&phone=" + accountContent;
        }
        else {
            console.log("Account Type Invalid");
        }
         try {
            const response = await accountService.getAccountFilter(queryString);
            delegationUI.renderTable(response.items);
         } catch (error) {
            console.log("Error: " + error);
         }
    } 
})