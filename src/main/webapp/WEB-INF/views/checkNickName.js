function checkNickName(nickName) {
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "/check/nickName/" + nickName, true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const response = JSON.parse(xhr.responseText);
            if (response === true) {
                // 닉네임이 중복되지 않음
                // 원하는 동작을 수행
            } else {
                // 닉네임이 중복됨
                // 원하는 동작을 수행
            }
        }
    };
    xhr.send();
}
