<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>닉네임 변경</title>
    <style>
        .main-container {
            text-align: center;
            padding: 20px;
            background-color: #fff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            max-width: 800px;
            width: 100%;
            margin-top: 50px;
            border-radius: 8px;
        }
    </style>

</head>

<body>
<div class="main-container">
    <div class="logo">
        <a href="/mainHome">
            <img src="https://boardbuket.s3.ap-northeast-2.amazonaws.com/logo/logo.png" alt="웹 로고"
                 style="max-width: 100px; max-height: 100px;">
        </a>
        <h1>HealthBoard</h1>
    </div>
</div>
<h2>반갑습니다, ${sessionScope.nickName}님</h2>
<h2>닉네임 변경</h2>
<input type="text" id="newNickName" required>
<button onclick="updateNickname()">수정</button>


<script>
    function updateNickname() {
        const newNickName = document.getElementById("newNickName").value;
        const memberId = "${member.id}";

        fetch(`/editNickName/${memberId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ updateNickName: newNickName })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok.');
                }
                return response.text();
            })
            .then(data => {
                console.log(data); // 서버 응답 확인
                window.location.href = "/myPage"; // 성공적으로 업데이트되었음을 나타내는 메시지를 반환
            })
            .catch(error => {
                console.error('There has been a problem with your fetch operation:', error);
                window.location.href = "/mainHome"; // 업데이트에 실패한 경우 에러 메시지를 반환
            });
    }
</script>
</body>
</html>
