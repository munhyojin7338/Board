<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>mainHome</title>
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

        .main-box h2 {
            font-size: 24px;
            margin-bottom: 10px;
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
    <h1>HealthBoard</h1>
    <h2>반갑습니다, ${sessionScope.nickName}님</h2>
    <br>

    <a href="logout">logout</a>
    <a href="board">Board</a>
    <a href="myPage">myPage</a>
</div>
</body>
</html>
