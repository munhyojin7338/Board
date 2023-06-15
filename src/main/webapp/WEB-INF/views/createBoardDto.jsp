<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Board</title>
</head>
<body>
<h1>Create Board</h1>

<form action="/createBoard" method="post" modelAttribute="createBoardDto">
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
        <input type="text" id="title" name="title" required>
    </div>
    <div>
        <label for="contents">내용:</label>
        <textarea id="contents" name="contents" required></textarea>
    </div>
    <button type="submit">Submit</button>
</form>
</body>
</html>
