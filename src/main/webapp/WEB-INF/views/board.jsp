<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>HealthBoard</title>

    <style>
        body {
            display: flex;
            flex-direction: column; /* 수직 방향으로 요소들을 정렬합니다. */
            justify-content: center; /* 수직 중앙 정렬 */
            align-items: center; /* 수평 중앙 정렬 */
            height: 100vh;
            margin: 0;
            background-color: #f0f0f0;
        }

        .board-container {
            text-align: center;
            padding: 20px;
            border: 1px solid #ccc;
            background-color: #fff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .board-container h1 {
            font-size: 28px;
            margin-bottom: 10px;
            color: black; /* 검정색으로 변경 */

        }

        .board-container h2 {
            font-size: 24px;
            margin-bottom: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            border: 1px solid #ccc;
            padding: 10px;
            text-align: center;
        }

        th {
            background-color: #f0f0f0;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        a {
            text-decoration: none;
            color: #007bff;
        }

        a:hover {
            text-decoration: underline;
        }

        button {
            margin-top: 20px;
            padding: 10px 20px;
            text-decoration: none;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        button:hover {
            background-color: #0056b3;
        }

        /* Style for the image container */
        #imageContainer {
            width: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
            background-color: #eee;
        }

    </style>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <script>
        function goToCreateBoardPage() {
            window.location.href = "/createBoardDto"; // 게시글 작성 페이지로 이동하는 URL로 수정해야 합니다.
        }
    </script>

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
<h1>HealthBoard</h1>
<br><br>
<div id="imageContainer"
     style="width: 100%; height: 300px; display: flex; justify-content: center; align-items: center; background-color: #eee;">
    <img id="proFile" src="https://boardbuket.s3.ap-northeast-2.amazonaws.com/proFile/${member.imageUrl}" alt="프로필"
         style="max-width: 100%; max-height: 100%;">
</div>
<p1>${sessionScope.nickName}님!! 자유롭게 소통해보세요!</p1>
<br>

<c:set var="board" value="${boardService.getBoardsOrderByViewsDesc()}"/>

<table>
    <thead>
    <tr>
        <th>번호</th>
        <th>작성자</th>
        <th>카테고리</th>
        <th>제목</th>
        <th>내용</th>
        <th>조회수</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${boardList}" var="board" varStatus="loop">
        <tr>
            <td>${loop.index + 1}</td>
            <td>${board.member.nickName}</td>
            <td>${board.category}</td>
            <td><a href="/board/${board.id}">${board.title}</a></td>
            <td>${board.contents}</td>
            <td>${board.views}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<button onclick="goToCreateBoardPage()">게시글 작성</button>
</body>
</html>
