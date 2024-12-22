document.getElementById('generateQR').addEventListener('click', () => {
    const accountNumber = document.getElementById('accountNumber').value.trim();
    const amount = document.getElementById('amount').value.trim();
    const receiverName = document.getElementById('receiverName').value.trim();

    // Kiểm tra đầu vào
    if (!accountNumber.match(/^\d+$/)) {
        alert("Số tài khoản không hợp lệ.");
        return;
    }
    if (isNaN(amount) || amount <= 0) {
        alert("Số tiền phải là số nguyên dương.");
        return;
    }
    if (receiverName.trim() === '') {
        alert("Tên người nhận không được để trống.");
        return;
    }

    // Chuỗi QR
    const qrData = `
        000201
        010212
        0215MB BANK
        0306MBI000
        52040000
        5303704
        5408${amount}
        5802VN
        5908${receiverName}
        6008HANOI
        6304
    `.replace(/\s+/g, '');

    // Tạo mã QR
    const qrCodeContainer = document.getElementById('qrcode');
    qrCodeContainer.innerHTML = ''; // Xóa mã QR cũ
    QRCode.toCanvas(qrCodeContainer, qrData, {
        width: 256,
        height: 256,
        colorDark: "#000000",
        colorLight: "#ffffff",
    }).catch(err => {
        console.error('Lỗi khi tạo mã QR:', err);
    });
});
