window.addEventListener('signin', async () => {
    const token = window.localStorage.getItem('token');

    if (token) {
        const response = await fetch('/getInfo', {
            headers: { Authorization: `Bearer ${token}` },
        });
        if (response.status === 200) {
            const data = await response.json();

            //window.localStorage.setItem('name', data.name);
            //window.localStorage.setItem('image', data.image);

            document.querySelector('#auth').style.display = 'none';
            document.querySelector('#myProfile').style.display = 'block';
            //document.querySelector('#logout').style.display = 'block';
        } else {
            window.localStorage.clear();
        }
    }
});

window.dispatchEvent(new Event('signin'));