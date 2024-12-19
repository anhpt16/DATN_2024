

$(document).ready(function() {

    tinymce.init({
        selector: '#textbook-description', // ID của textarea
        license_key: 'gpl',
        height: 1000, // Chiều cao trình soạn thảo
        menubar: false, // Tắt thanh menu (nếu muốn)
        plugins: [
            'advlist', 'autolink', 'link', 'image', 'lists', 'charmap', 'preview', 'anchor', 'pagebreak',
            'searchreplace', 'wordcount', 'visualblocks', 'visualchars', 'code', 'fullscreen', 'insertdatetime',
            'media', 'table', 'emoticons', 'help'
          ],
          toolbar: 'undo redo | styles | bold italic | alignleft aligncenter alignright alignjustify | ' +
          'bullist numlist outdent indent | link image | print preview media fullscreen | ' +
          'forecolor backcolor emoticons | help',
        menubar: 'file edit view insert format tools table help', // Hiển thị đầy đủ menu
        content_style: 'body { font-family:Arial,sans-serif; font-size:14px }'
    });


})