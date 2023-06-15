<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>HealthBoard</title>

    <style>
        ul {
            list-style-type: none;
            padding: 0;
        }
        li {
            border: 1px solid #ccc;
            padding: 10px;
            margin-bottom: 10px;
        }
        h3 {
            margin: 0;
        }
        p {
            margin: 5px 0;
        }
    </style>

    <script>
        function goToCreateBoardPage() {
            window.location.href = "/createBoardDto"; // 게시글 작성 페이지로 이동하는 URL로 수정해야 합니다.
        }
    </script>
</head>
<body>
  <h1>HealthBoard</h1>
  <p1>${sessionScope.nickName}님!! 자유롭게 소통해보세요!</p1>
  <h2>작성한 게시판 페이지 리스트</h2>
  <ul>
      <c:forEach items="${boardList}" var="board">
          <li>
              <span>${board.member.nickName}</span> <!-- 작성자 nickName -->
              <span>${board.category}</span>
              <a href="/board/${board.id}"> <!-- 해당 게시물 페이지로 이동하는 링크 -->
                  <span class="title">${board.title}</span>
                  <span>${board.contents}</span>
              </a>
          </li>
      </c:forEach>
  </ul>
  <button onclick="goToCreateBoardPage()">게시글 작성</button>
</body>
</html>
