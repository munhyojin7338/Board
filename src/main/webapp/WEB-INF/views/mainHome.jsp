<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>mainHome</title>
</head>
<body>
    <h1>HealthBoard</h1>
    <h2>반갑습니다, ${sessionScope.nickName}님</h2>
    <br>

    <a href="logout">logout</a>
    <a href="board">Board</a>
    <a href="myPage">myPage</a>
</body>
</html>
