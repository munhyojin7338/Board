function checkEmail(email) {
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "/check/email/" + email, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const response = JSON.parse(xhr.responseText);
            if (response === true) {

            } else {

            }
        }
    };
    xhr.send();
}
