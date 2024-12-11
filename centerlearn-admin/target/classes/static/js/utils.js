
const util = {
    formatDateTime: (dateString) => {
        // Thay dấu ':' bằng dấu '.' để hợp lệ ISO 8601
        const validDateString = dateString.replace(/:(?=\d{3}$)/, '.');
            
        // Tạo đối tượng Date từ chuỗi đã sửa
        const date = new Date(validDateString);

        // Kiểm tra nếu đối tượng Date hợp lệ
        if (isNaN(date)) {
            return ''; // Trả về chuỗi rỗng nếu ngày giờ không hợp lệ
        }

        // Lấy giờ, phút, giây
        const hours = date.getHours().toString().padStart(2, '0');
        const minutes = date.getMinutes().toString().padStart(2, '0');
        const seconds = date.getSeconds().toString().padStart(2, '0');

        // Lấy ngày, tháng, năm
        const day = date.getDate().toString().padStart(2, '0');
        const month = (date.getMonth() + 1).toString().padStart(2, '0');
        const year = date.getFullYear();

        // Trả về ngày giờ theo định dạng hh:mm:ss dd/mm/yyyy
        return `${hours}:${minutes}:${seconds} ${day}/${month}/${year}`;
    },
    formatDate: (dateString) => {
        // Thay dấu ':' bằng dấu '.' để hợp lệ ISO 8601
        const validDateString = dateString.replace(/:(?=\d{3}$)/, '.');
            
        // Tạo đối tượng Date từ chuỗi đã sửa
        const date = new Date(validDateString);

        // Kiểm tra nếu đối tượng Date hợp lệ
        if (isNaN(date)) {
            return ''; // Trả về chuỗi rỗng nếu ngày giờ không hợp lệ
        }

        // Lấy giờ, phút, giây
        const hours = date.getHours().toString().padStart(2, '0');
        const minutes = date.getMinutes().toString().padStart(2, '0');
        const seconds = date.getSeconds().toString().padStart(2, '0');

        // Lấy ngày, tháng, năm
        const day = date.getDate().toString().padStart(2, '0');
        const month = (date.getMonth() + 1).toString().padStart(2, '0');
        const year = date.getFullYear();

        // Trả về ngày giờ theo định dạng hh:mm:ss dd/mm/yyyy
        return `${day}/${month}/${year}`;
    }
}



export default util;