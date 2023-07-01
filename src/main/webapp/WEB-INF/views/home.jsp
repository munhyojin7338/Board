
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta charset="UTF-8">

<html>
<head>
    <meta charset="UTF-8">
    <title>home 화면</title>


</head>
<body>
<h1>Welcome to the Main Page!</h1>

<c:if test="${not empty errorMessage}">
    <p>Error: ${errorMessage}</p>
</c:if>

<p>로그인 후에 더 많은 콘텐츠를 확인하실 수 있습니다.</p>
    <a href="signup">회원가입</a>
    <a href="login">로그인</a>

    <a href="https://kauth.kakao.com/oauth/authorize?client_id=2b463a34237272c508fea8ab9e99a0ab&redirect_uri=http://localhost:8080/kakaoPage&response_type=code">
        카카오로그인
    </a>

</body>
</html>
