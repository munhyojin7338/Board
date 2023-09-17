<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>MyPage</title>

    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-color: #f0f0f0;
            font-family: Arial, sans-serif;
        }

        .mypage-box {
            text-align: center;
            padding: 40px;
            border: 1px solid #ccc;
            background-color: #fff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 400px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .mypage-box h1 {
            font-size: 32px;
            margin-bottom: 10px;
        }

        .mypage-box h2 {
            font-size: 24px;
            margin-bottom: 20px;
        }

        .mypage-box h3 {
            font-size: 18px;
            margin-top: 20px;
        }

        .mypage-box a {
            display: block;
            margin: 10px 0;
            padding: 10px 20px;
            text-decoration: none;
            background-color: #007bff;
            color: #fff;
            border-radius: 4px;
        }

        .mypage-box a:hover {
            background-color: #0056b3;
        }

        /* 추가한 스타일 */
        .mypage-box .action-links {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-top: 20px;
        }

        .mypage-box .action-links a {
            margin: 5px 0;
        }
    </style>
</head>

<body>
<div class="mypage-box">
    <h1>HealthBoard</h1>
    <h2>반갑습니다, ${sessionScope.nickName}님</h2>
    <h3>알림 내역(나중에 구현)</h3>
    <div class="action-links">
        <a href="updateNickName">닉네임 수정</a>
        <a href="/updatePassword">password 수정</a>
        <a href="/mainHome">메인 홈으로</a>
        <a href="/logout">로그아웃</a>
        <a href="/withdraw">회원탈퇴</a>
    </div>
</div>
</body>
</html>
