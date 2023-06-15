<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Board Detail</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        $(document).ready(function() {
            // 댓글 작성 폼 제출 이벤트 핸들러
            $("#commentForm").submit(function(event) {
                event.preventDefault(); // 폼 제출 기본 동작 막기

                const boardId = $("#boardId").val();
                const content = $("#commentContent").val();

                // AJAX 요청으로 댓글 작성 API 호출
                $.ajax({
                    url: "/comments/" + boardId,
                    type: "POST",
                    contentType: "application/json",
                    data: JSON.stringify({
                        content: content
                    }),
                    success: function(response) {
                        alert(response); // 성공 메시지 출력
                        location.reload(); // 페이지 새로고침
                    },
                    error: function(error) {
                        alert("댓글 작성에 실패했습니다. 다시 시도해주세요.");
                    }
                });
            });
        });

        function showConfirmation() {
            if (confirm("정말로 삭제하시겠습니까?")) {
                $("#deleteForm").submit();
            }
        }
    </script>
</head>
<body>
<h1>Board Detail</h1>
<h2>작성자: ${board.member.nickName}</h2>
<h3>${board.title}</h3>
<p>${board.contents}</p>

<c:if test="${board.member != null && board.member.nickName != null && board.member.nickName eq nickName}">
    <a href="/board/${board.id}/edit">수정하기</a>

    <button onclick="showConfirmation()">삭제하기</button>
</c:if>

<form id="deleteForm" action="/board/${board.id}" method="POST" style="display: none;">
    <input type="hidden" name="_method" value="DELETE">
</form>

<form id="commentForm">
    <input type="hidden" name="boardId" id="boardId" value="${board.id}">
    <textarea name="content" id="commentContent" placeholder="댓글 내용" required></textarea><br>
    <button type="submit">댓글 작성</button>
</form>

<div id="comments">
    <c:forEach var="comment" items="${comments}">
        <p>${comment.member.nickName} : ${comment.content}</p>
    </c:forEach>
</div>

</body>
</html>
