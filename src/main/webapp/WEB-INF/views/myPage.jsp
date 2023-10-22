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
            padding: 80px;
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

        /* 이미지 업로드 스타일 */
        .image-upload {
            margin-top: 60px;

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

        .user-image {
            position: absolute;
            top: 0;
            left: 0;
            max-width: 150px;
            max-height: 150px;
        }

        .user-nickname {
            position: absolute;
            top: 0;
            left: 160px; /* 이미지와 닉네임 사이의 간격을 조절하려면 이 값을 조정합니다. */
            font-size: 24px;
            margin: 0;
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
                    proFile.src = "https://boardbuket.s3.ap-northeast-2.amazonaws.com/blank-profile-picture-973460_1280.png";
                    proFile.alt = "대체 이미지";
                }
            }
        });

    </script>



</head>

<body>

<div class="mypage-box">
    <h1>HealthBoard</h1>
    <div class="user-info">
        <div id="imageContainer" style="width: 100%; height: 300px; display: flex; justify-content: center; align-items: center; background-color: #eee;">
            <img id="proFile" src="https://boardbuket.s3.ap-northeast-2.amazonaws.com/proFile/${member.imageUrl}" alt="프로필" style="max-width: 100%; max-height: 100%;">
        </div>
        <h4 class="user-nickname">반갑습니다, ${sessionScope.nickName}님</h4>
    </div>
    <br><br>
    <div class="action-links">
        <a href="updateNickName">닉네임 수정</a>
        <a href="/updatePassword">비밀번호 수정</a>
        <a href="/mainHome">메인 홈으로</a>
        <a href="/logout">로그아웃</a>
        <a href="/withdraw">회원탈퇴</a>
    </div>

    <form action="/upload" method="POST" enctype="multipart/form-data">
        <label for="image">이미지 업로드:</label>
        <input type="file" id="image" name="proFile" accept="image/*">
        <input type="submit" value="Upload">
    </form>

</div>

</body>

</html>
