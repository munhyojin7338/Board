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

        .main-container {
            display: flex;
            align-items: center;
            justify-content: center;
            flex-direction: column;
            margin-top: 50px;
            padding: 20px;
            max-width: 800px;
            background-color: #fff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
        }

        .logo {
            margin-bottom: 20px;
            text-align: center;
        }

        .logo img {
            max-width: 100px;
            max-height: 100px;
        }

        .mypage-box {
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
            margin: 10px 0;
        }

        .image-upload label {
            display: block;
            font-weight: bold;
        }

        .image-upload input[type="file"] {
            display: none;
        }

        .image-upload img {
            max-width: 150px;
            max-height: 150px;
        }

        .user-info {
            position: relative;
            text-align: center;
        }

        .user-nickname {
            position: absolute;
            top: 0;
            left: 50%;
            transform: translateX(-50%);
            font-size: 24px;
            margin: 0;
            margin-top: 10px;
        }
    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <script>
        // 페이지 점검 중인 알림 창 표시
        function underMaintenanceAlert() {
            alert("현재 페이지는 점검 중입니다. 이용에 불편을 드려 죄송합니다.");
        }

        document.addEventListener('DOMContentLoaded', function() {
            const editNickNameLink = document.querySelector('.mypage-box .action-links a:nth-child(1)');
            if (editNickNameLink) {
                editNickNameLink.addEventListener('click', function (event) {
                    event.preventDefault();
                    underMaintenanceAlert();
                });
            }

            const updatePasswordLink = document.querySelector('.mypage-box .action-links a:nth-child(2)');
            if (updatePasswordLink) {
                updatePasswordLink.addEventListener('click', function (event) {
                    event.preventDefault();
                    underMaintenanceAlert();
                });
            }
        });
    </script>

    <script>
        $(document).ready(function () {
            // 이미지 URL을 가져와서 로그에 출력
            console.log("프로필 이미지 URL: " + "${member.getImageUrl()}");

            //프로필 이미지 URL 확인
            const imageUrl = "${member.getImageUrl()}";
            if (imageUrl === "" || imageUrl === "null") {
                // 이미지가 없는 경우 대체 이미지로 교체
                document.getElementById("imageContainer").style.backgroundColor = "#eee"; // 배경색 변경
                document.getElementById("proFile").src = "https://boardbuket.s3.ap-northeast-2.amazonaws.com/proFile/profile.png";
                document.getElementById("proFile").alt = "대체 이미지";
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

    <div class="mypage-box">
        <div class="user-info">
            <div id="imageContainer"
                 style="width: 100%; height: 300px; display: flex; justify-content: center; align-items: center; background-color: #eee;">
                <img id="proFile" src="https://boardbuket.s3.ap-northeast-2.amazonaws.com/${member.getImageUrl()}" alt="프로필"
                     style="max-width: 100%; max-height: 100%;">
            </div>
            <h4 class="user-nickname">${sessionScope.nickName}님</h4>
        </div>
        <br><br>
        <div class="action-links">
            <a href="#">닉네임 수정</a>
            <a href="#">비밀번호 수정</a>
            <a href="/mainHome">메인 홈으로</a>
            <a href="/logout">로그아웃</a>
            <a href="/withdraw">회원탈퇴</a>
        </div>


        <form action="/upload" method="POST" enctype="multipart/form-data" class="image-upload">
            <label for="image">이미지 업로드:</label>
            <input type="file" id="image" name="proFile" accept="image/*">
            <input type="submit" value="Upload">
        </form>
    </div>
</div>

</body>
</html>
