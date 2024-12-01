
const teacherUI = {
    el: {
        tbodyEl: $("#table-account"),
    },

    renderTable: (accounts) => {
        teacherUI.el.tbodyEl.empty();
        if (accounts.length === 0) {
            teacherUI.el.tbodyEl.append('<tr><td colspan="8">No account available</td></tr>');
            return;
        }
        accounts.forEach(function(account) {
            let row = '<tr class="fade-in" data-id="' + account.id + '">' +
                '<td class="item-account-image"><img src="https://via.placeholder.com/50" alt="Avatar"></td>' +
                '<td class="item-account-username">' + account.username + '</td>' +
                '<td class="item-account-display-name">' + account.displayName + '</td>' +
                '<td class="item-account-email">' + account.email + '</td>' +
                '<td class="item-account-phone">' + account.phone + '</td>' +
                '<td class="item-account-status" '
                + (account.status.colorCode ? 'style="color:' + account.status.colorCode + '"' : '') + '>' +
                account.status.displayName + '</td>' +
                '<td>' +
                    '<i class="fa-regular fa-eye view-btn"></i>' +
                    '<i class="fa-regular fa-pen-to-square edit-btn"></i>' +
                '</td>' +
            '</tr>';
            teacherUI.el.tbodyEl.append(row);
        })
        setTimeout(function() {
            teacherUI.el.tbodyEl.find('.fade-in').addClass('show');
          }, 100);
    }
}

export default teacherUI;