function myMusic(id) {
    const accountId = window.localStorage.getItem("accountId");
    console.log(id);
    fetch("/addmymusic",{
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            songId: id,
            userId: accountId
        })
    }).then((response) => {
            return response;
        })
}

function removeUserSong(id) {
    const accountId = window.localStorage.getItem("accountId");
    console.log(id);
    fetch("/removeusersong",{
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            songId: id,
            userId: accountId
        })
    }).then((response) => {
        return response;
    })
}