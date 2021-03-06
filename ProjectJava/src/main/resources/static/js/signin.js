async function signin() {
    const login = document.querySelector('#login').value;
    const password = document.querySelector('#password').value;
    const resultMessage = document.querySelector('#resultMessage');

    const response = await fetch("/auth",
        {
            method: 'POST',
            headers: {'Content-Type': 'application/json', 'Accept': 'application/json'},
            body: JSON.stringify({
                login,
                password
            })
        });

    const data = await response.json();

    if(response.status === 200 && data.token && data.accountId) {
        window.localStorage.setItem("token", data.token);
        window.localStorage.setItem("accountId", data.accountId);
        resultMessage.innerHTML = "";
        document.getElementById('myModal').style.display="none";
        window.dispatchEvent(new Event("signin"));
    } else {
        resultMessage.className = "errorMessage";
        resultMessage.innerHTML = "Неверный логин или пароль!";
    }
}