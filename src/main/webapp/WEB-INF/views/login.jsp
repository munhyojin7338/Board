<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<style>
    body {
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        margin: 0;
    }

    .login-box {
        width: 300px;
        border: 1px solid #ccc;
        padding: 20px;
        text-align: center;
    }

    .form-group {
        margin-bottom: 10px;
    }

    .form-group label {
        display: block;
    }

    .form-group input {
        width: 100%;
        padding: 5px;
    }

    .form-group button {
        width: 100%;
        padding: 10px;
        background-color: #007bff;
        color: #fff;
        border: none;
        cursor: pointer;
    }

    .form-group a {
        display: block;
        text-align: center;
        margin-top: 10px;
    }
</style>
<body>
<%-- 로그인 실패 시에만 메시지를 표시 --%>
<% if (request.getAttribute("errorMessage") != null) { %>
<p><%= request.getAttribute("errorMessage") %></p>
<% } %>

<div class="login-box">
    <form method="post" action="/login">
        <%-- token 값을 넘길 때는 <input type="hidden">을 사용해야 합니다 --%>
        <% if (request.getHeader("Authorization") != null) { %>
        <% String jwtToken = request.getHeader("Authorization").replace("Bearer ", ""); %>
        <p>JWT Token: <%= jwtToken %></p>
        <% } %>

        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>
        </div>

        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
        </div>

        <button type="submit">로그인</button>
    </form>
    <a href="signup">회원가입</a>
</div>
</body>
</html>
