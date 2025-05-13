document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.modify-cart-btn').forEach(button => {
        button.addEventListener('click', function () {
            const pizzaId = this.getAttribute('data-pizza-id');
            const size = this.getAttribute('data-size');
            const action = this.getAttribute('data-action');
            modifyCart(pizzaId, size, action);
        });
    });
});

function modifyCart(pizzaId, size, action) {
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    fetch(`/api/cart/${action}/${pizzaId}/${size}`, {
        method: 'POST',
        headers: {
            [csrfHeader]: csrfToken
        }
    }).then(response => {
        if (response.ok) {
            location.reload();
        } else {
            alert("Ошибка при добавлении в корзину");
        }
    });
}
