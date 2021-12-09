function logout() {
    window.localStorage.clear();
    document.querySelector('#auth').style.display = 'block';
    document.querySelector('#myProfile').style.display = 'none';
}