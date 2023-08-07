<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta charset="UTF-8">

<html>
<head>
    <meta charset="UTF-8">
    <title>home 화면</title>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-color: #f0f0f0;
        }

        .main-box {
            text-align: center;
            padding: 20px;
            border: 1px solid #ccc;
            background-color: #fff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .main-box h1 {
            font-size: 28px;
            margin-bottom: 20px;
        }

        .main-box p {
            font-size: 16px;
            margin-bottom: 30px;
        }

        .main-box a {
            display: block;
            margin-bottom: 10px;
            padding: 10px 20px;
            text-decoration: none;
            background-color: #007bff;
            color: #fff;
            border-radius: 4px;
        }

        .main-box a:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<div class="main-box">
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
</div>
</body>
</html>
