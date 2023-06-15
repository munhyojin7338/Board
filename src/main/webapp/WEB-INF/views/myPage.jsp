
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>MyPage</title>
</head>
<body>
<h1>HealthBoard</h1>
<h2>반갑습니다, ${sessionScope.nickName}님</h2>
<br>
<h3>알림 내역(나중에 구현)</h3>
<br>
<a href="updateNickName"> 닉네임 수정 </a>
<a href="/updatePassword"> password 수정</a>
<br>
<a href="/mainHome">메인 홈으로</a>
<a href="/logout">로그아웃</a>

</body>
</html>
