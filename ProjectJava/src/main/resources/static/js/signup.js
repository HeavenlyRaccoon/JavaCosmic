function signup() {
    const login = document.querySelector('#login').value;
    const password = document.querySelector('#password').value;
    const confirmPassword = document.querySelector('#confirmPassword').value;
    const resultMessage = document.querySelector('#resultMessage');
    if(confirmPassword!==password){
        resultMessage.innerHTML = 'Пароли не совпадают';
        return;
    }
    fetch("/register", {
        method: "POST",
        headers: {'Content-Type': 'application/json', 'Accept': 'application/json'},
        body: JSON.stringify({
            login,
            password
        })
    }).then(async (response) => {
        if(response.status === 201) {
            resultMessage.className = "successMessage";
            resultMessage.innerHTML = 'Аккаунт создан!';
        } else {
            const data = await response.json();
            resultMessage.className = "errorMessage";
            resultMessage.innerHTML = data.errors[0].message;
        }
    })
}