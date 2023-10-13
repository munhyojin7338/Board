<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Board Detail</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <style>

        .board-container {
            display: flex;
            flex-direction: column;
            align-items: center; /* 가운데 정렬 */
            text-align: center;
            padding: 20px;
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

        .board-container table {
            border-collapse: collapse;
            width: 100%;
        }

        .board-container th, .board-container td {
            border: 1px solid #ccc;
            padding: 10px;
            text-align: left;
        }

        .board-container th {
            background-color: #f2f2f2;
        }

        .board-container form {
            margin-top: 20px;
        }
    </style>

    <script>
        $(document).ready(function () {
            $("#commentForm").submit(function (event) {
                event.preventDefault(); // 폼 제출 이벤트 중지

                const boardId = $("#boardId").val();
                console.log("boardId:", boardId);

                const memberId = $("#memberId").val();
                const content = $("#commentContent").val();


                // AJAX 요청 보내기
                $.ajax({
                    url: `/comments/${boardId}/`,
                    type: "POST",
                    contentType: "application/json",
                    data: JSON.stringify({content: content}),
                    success: function (response) {

                        loadCommentList(boardId);
                        alert("성공적으로 적성했습니다. ^^")
                    },
                    error: function (xhr, status, error) {
                        // 에러 발생 시 처리할 로직
                        console.log("AJAX 요청 에러: " + error);
                    }
                });
            });


            // 댓글 리스트 로드 함수
            function loadCommentList(boardId) {
                $.ajax({
                    url: "/comment/list",
                    type: "GET",
                    data: {bid: boardId},
                    success: function (data) {
                        // 성공적으로 댓글 리스트를 받아온 후 처리할 로직
                        const comments = data;
                        console.log("Ajax Response Data:", data); // 응답 데이터 확인

                        // 댓글 리스트를 표시하는 로직 추가
                        let html = "";
                        for (let i = 0; i < comments.length; i++) {
                            const comment = comments[i];
                            const createdAt = new Date(comment.createdAt).toLocaleDateString("ko-KR");
                            html += "<tr>";
                            html += "<td>" + (i + 1) + "</td>"; // 순서를 i + 1로 표시합니다.
                            html += "<td>" + comment.memberId + "</td>";
                            html += "<td>" + comment.content + "</td>";
                            html += "<td>" + createdAt + "</td>"; // 변환된 createdAt 표시
                            html += "</tr>";

                            console.log("HTML:", html); // 생성된 HTML 확인

                        }
                        $("#commentTableBody").html(html);
                    },
                    error: function (xhr, status, error) {
                        // 에러 발생 시 처리할 로직
                        console.log("AJAX 요청 에러: " + error);
                    }
                });
            }
            // 페이지 로드 시 초기 댓글 리스트 가져오기
            const boardId = $("#boardId").val();
            loadCommentList(boardId);

        });

        function deleteComment(boardId, commentId) {
            const memberId = $("#memberId").val();

            // AJAX 요청 보내기
            $.ajax({
                url: `/comments/delete/${commentId}`,
                type: "DELETE",
                data: JSON.stringify({boardId: boardId, memberId: memberId}),
                contentType: "application/json",
                success: function (response) {
                    // 성공적으로 댓글 삭제 후 처리할 로직
                    loadCommentList(boardId);
                },
                error: function (xhr, status, error) {
                    // 에러 발생 시 처리할 로직
                    console.log("AJAX 요청 에러: " + error);
                }
            });
        }
    </script>


</head>

<body style="display: flex; flex-direction: column; justify-content: center; align-items: center; height: 100vh; margin: 0; background-color: #f0f0f0; font-family: Arial, sans-serif;">
<h1>Board Detail</h1>
<h2>작성자: ${board.member.nickName}</h2>
<h3 style="margin-left: 20px;">${board.title}</h3>
<p style="margin-left: 20px;">${board.contents}</p>

<c:if test="${board.member != null && board.member.nickName != null && board.member.nickName eq nickName}">
    <a href="/board/${board.id}/edit">수정하기</a>
    <button onclick="showConfirmation()">삭제하기</button>
</c:if>

<form id="deleteForm" action="/board/${boardId}" method="POST" style="display: none;">
    <input type="hidden" name="_method" value="DELETE">
</form>

<form id="commentForm">
    <input type="hidden" name="boardId" id="boardId" value="${boardId}">
    <input type="hidden" name="memberId" id="memberId" value="${sessionScope.memberId}">
    <textarea name="content" id="commentContent" placeholder="댓글 내용" required
              style="width: 790px; height: 290px;"></textarea>
    <button type="submit">댓글 작성</button>
</form>

<p>조회수: ${board.views}</p>

<div id="commentList">
    <h4>댓글 리스트</h4>
    <table>
        <thead>
        <tr>
            <th>순서</th>
            <th>작성자</th>
            <th>댓글 내용</th>
            <th>작성 시간</th>
        </tr>
        </thead>

        <tbody id="commentTableBody">

        <c:forEach items="${commentList}" var="comment" varStatus="status">
            <tr>
                <td>${status.index + 1}</td>
                <td>${comment.member.nickName}</td>
                <td>${comment.content}</td>
                <td>${createdAt}</td>
                <td>
                    <c:if test="${sessionScope.member.nickName == comment.member.nickName}">
                        <button onclick="deleteComment('${boardId}', '${comment.id}')">삭제하기</button>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>

</html>
