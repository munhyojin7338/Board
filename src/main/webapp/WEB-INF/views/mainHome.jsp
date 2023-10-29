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

        .logo {
            display: flex;
            align-items: center;
            justify-content: center;
            margin-bottom: 20px;
        }

        .logo img {
            max-width: 100px;
            max-height: 100px;
        }

        .main-box {
            display: flex;
            flex-direction: column;
            align-items: center;
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

        /* Style for the image container */
        #imageContainer {
            width: 100%;
            height: 300px;
            display: flex;
            justify-content: center;
            align-items: center;
            background-color: #eee;
            margin-bottom: 20px;
        }

    </style>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(document).ready(function () {
            // 이미지 URL을 가져와서 로그에 출력
            console.log("프로필 이미지 URL: " + "${member.getImageUrl()}");
        });
    </script>

    <script>
        $(document).ready(function () {
            // 이미지 URL을 가져와서 로그에 출력
            console.log("프로필 이미지 URL: " + "${member.getImageUrl()}");

            // 게시글 이미지 URL 확인
            const imageUrl = "${member.getImageUrl()}";
            if (imageUrl === "" || imageUrl === null) {
                // 이미지가 없는 경우 대체 이미지로 교체
                const imageContainer = document.getElementById("imageContainer");
                const proFile = document.getElementById("proFile");
                if (imageContainer && proFile) {
                    imageContainer.style.backgroundColor = "#eee"; // 배경색 변경
                    proFile.src = "https://boardbuket.s3.ap-northeast-2.amazonaws.com/proFile/profile.png";
                    proFile.alt = "대체 이미지";
                }
            }
        });

    </script>

</head>
<body>
<div class="main-container">
    <div class="logo">
        <a href="/mainHome">
            <img src="https://boardbuket.s3.ap-northeast-2.amazonaws.com/logo/logo.png" alt="웹 로고">
        </a>
        <h1>HealthBoard</h1>
    </div>
    <div class="main-box">

        <br><br>
        <div id="imageContainer"
             style="width: 100%; height: 300px; display: flex; justify-content: center; align-items: center; background-color: #eee;">
            <img id="proFile" src="https://boardbuket.s3.ap-northeast-2.amazonaws.com/proFile/profile.png" alt="프로필"
                 style="max-width: 100%; max-height: 100%;">
        </div>
        <h2>반갑습니다, ${sessionScope.nickName}님</h2>
        <br>

        <a href="logout">logout</a>
        <a href="board">Board</a>
        <a href="myPage">myPage</a>
    </div>
</body>
</html>
