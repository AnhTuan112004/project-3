/* src/main/resources/static/js/main.js */

// Hàm định dạng tiền tệ (VND)
function formatCurrency(amount) {
    return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(amount);
}

// Logic tính tổng tiền khi đặt phòng
document.addEventListener('DOMContentLoaded', function() {
    const basePriceElement = document.getElementById('basePrice');
    const totalPriceElement = document.getElementById('totalEstimate');
    const checkboxes = document.querySelectorAll('.equipment-checkbox');
    const durationInput = document.getElementById('duration'); // Giả sử có input nhập số giờ

    if (basePriceElement && totalPriceElement) {
        function calculateTotal() {
            let basePrice = parseFloat(basePriceElement.getAttribute('data-price'));
            let hours = durationInput ? parseInt(durationInput.value) : 1;
            let equipmentTotal = 0;

            checkboxes.forEach(cb => {
                if (cb.checked) {
                    equipmentTotal += parseFloat(cb.getAttribute('data-price'));
                }
            });

            let total = (basePrice * hours) + equipmentTotal;
            totalPriceElement.innerText = formatCurrency(total);
        }

        // Lắng nghe sự kiện thay đổi
        checkboxes.forEach(cb => cb.addEventListener('change', calculateTotal));
        if (durationInput) durationInput.addEventListener('change', calculateTotal);
    }
});