<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Board</title>
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
            padding: 40px;
            border: 1px solid #ccc;
            background-color: #fff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 500px;
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
            height: 200px;
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
<h1>Create Board</h1>

<form action="/createBoardDto" method="post">
    <input type="hidden" name="memberId" value="${memberId}">
    <div>
        <h2> ${sessionScope.nickName}님</h2>
    </div>
    <div>
        <label for="category">Category:</label>
        <select id="category" name="categoryEnum" required>
            <option value="FOOTBALL">Football</option>
            <option value="BASKETBALL">Basketball</option>
            <option value="VOLLEYBALL">Volleyball</option>
            <option value="TENNIS">Tennis</option>
        </select>
    </div>
    <div>
        <label for="title">제목:</label>
        <input type="text" id="title" name="title" required  style="width: 790px; ">
    </div>
    <div>
        <label for="contents">내용:</label>
        <textarea id="contents" name="contents" required style="width: 790px; height: 290px;" ></textarea>
    </div>
    <button type="submit">Submit</button>
</form>
</body>
</html>
