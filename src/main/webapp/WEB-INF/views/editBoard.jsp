<!-- editBoard.html -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Board</title>
</head>
<body>
<h1>Edit Board</h1>

<form action="/board/${updateBoard.boardId}/edit" method="POST">
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
    <button type="submit">수정하기</button>
</form>
</body>
</html>
