<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Board</title>

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

        .create-board-form {
            text-align: center;
            background-color: #fff;
            padding: 20px;
            border: 1px solid #ccc;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 50%;
        }

        .create-board-form h1 {
            font-size: 32px;
            margin-bottom: 20px;
        }

        .create-board-form h2 {
            font-size: 24px;
            margin-bottom: 10px;
            color: #333;
        }

        .create-board-form label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }

        .create-board-form select,
        .create-board-form input[type="text"],
        .create-board-form textarea {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .create-board-form textarea {
            resize: vertical;
            height: 300px; /* 더 큰 comment box 높이 */
        }

        .create-board-form button[type="submit"] {
            padding: 10px 20px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .create-board-form button[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>

</head>
<body>
<div class="create-board-form">
    <div style="display: flex; align-items: center;">
        <div id="logo" style="margin-right: 20px;">
            <a href="/mainHome">
                <img src="https://boardbuket.s3.ap-northeast-2.amazonaws.com/logo/logo.png" alt="웹 로고" style="max-width: 100px; max-height: 100px;">        </a>
        </div>
        <h1>HealthBoard</h1>
    </div>

    <div>
        <h2> ${sessionScope.nickName}님</h2>
    </div>

    <form action="/board/${updateBoard.boardId}/edit" method="POST" enctype="multipart/form-data">
        <div>
            <label for="category">Category:</label>
            <select id="category" name="category" required>
                <option value="FOOTBALL">Football</option>
                <option value="BASKETBALL">Basketball</option>
                <option value="VOLLEYBALL">Volleyball</option>
                <option value="TENNIS">Tennis</option>
            </select>
        </div>

        <div>
            <label for="title">Title:</label>
            <input type="text" id="title" name="title" value="${updateBoard.title}" required>
        </div>

        <div>
            <label for="contents">Contents:</label>
            <textarea id="contents" name="contents" required>${updateBoard.contents}</textarea>
        </div>

        <div>
            <label for="image">이미지 업로드:</label>
            <input type="file" id="image" name="BoardFile" accept="image/*">
        </div>

        <button type="submit">수정하기</button>
    </form>
</div>
</body>
</html>
