
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>updatePassword</title>
</head>

<script>
    const newPassword = document.getElementsByName("newPassword")[0].value;
    const confirmPassword = document.getElementsByName("confirmPassword")[0].value;

    if (newPassword !== confirmPassword) {
        const errorDiv = document.getElementById("error-message");
        errorDiv.innerHTML = "새로운 비밀번호가 일치하지 않습니다.";
    }
</script>

<body>

<h2>비밀번호 수정</h2>
<form method="post" action="/myPage/password/{id}">
    <input type="hidden" name="id" value="{현재 사용자의 ID}">
    <input type="password" name="newPassword" placeholder="새로운 비밀번호 입력" required>
    <input type="password" name="confirmPassword" placeholder="새로운 비밀번호 확인" required>
    <button type="submit">수정</button>
</form>


</body>
</html>
