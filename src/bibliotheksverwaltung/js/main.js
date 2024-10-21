document.querySelector('form')
    .addEventListener('submit', function (event) {
        const password = document.getElementById('new-password').value;
        const confirmPassword = document.getElementById('confirm-password').value;

        if (password !== confirmPassword) {
            alert('Die Passwörter stimmen nicht überein!');
            event.preventDefault(); // Verhindert das Absenden des Formulars
        }
    });
