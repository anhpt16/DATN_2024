
const delegationUI = {
    el: {
        tbodyEl: $("#delegation-body"),
    },
    renderTable: (account) => {
        delegationUI.el.tbodyEl.empty();
        if (account === null) {
            delegationUI.el.tbodyEl.append('<tr><td colspan="8">No account available</td></tr>');
            return;
        }
        let row = '<tr class="fade-in" data-id="' + account.id + '">' +
            '<td class="item-account-id">' + account.id + '</td>' +
            '<td class="item-account-username">' + account.username + '</td>' +
            '<td class="item-account-display-name">' + account.displayName + '</td>' +
            '<td class="item-account-email">' + account.email + '</td>' +
            '<td class="item-account-phone">' + account.phone + '</td>' +
            '<td class="item-account-status" '
            + (account.status.colorCode ? 'style="color:' + account.status.colorCode + '"' : '') + '>' +
            account.status.displayName + '</td>' +
            '<td>' +
                '<i class="fa-regular fa-pen-to-square edit-btn"></i>' +
            '</td>' +
        '</tr>';
        delegationUI.el.tbodyEl.append(row);
        setTimeout(function() {
            delegationUI.el.tbodyEl.find('.fade-in').addClass('show');
          }, 100);
    }
}

export default delegationUI;