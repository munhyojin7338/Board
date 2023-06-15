<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<%-- 로그인 실패 시에만 메시지를 표시 --%>
<% if (request.getAttribute("errorMessage") != null) { %>
<p><%= request.getAttribute("errorMessage") %></p>
<% } %>

<form method="post" action="/login">
    <%-- token 값을 넘길 때는 <input type="hidden">을 사용해야 합니다 --%>
    <% if (request.getHeader("Authorization") != null) { %>
    <% String jwtToken = request.getHeader("Authorization").replace("Bearer ", ""); %>
    <p>JWT Token: <%= jwtToken %></p>
    <% } %>

    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required><br>

    <button type="submit">로그인</button>
</form>
<a href="signup" input type="submit">회원가입</a>
</body>
</html>
