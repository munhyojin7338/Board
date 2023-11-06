<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Board Detail</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <style>
        body {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-color: #f0f0f0;
            font-family: Arial, sans-serif;
        }

        .board-container {
            width: 60%;
            margin: 20px auto;
            border: 1px solid #ccc;
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.1);
        }

        .title {
            font-size: 28px;
            margin-bottom: 20px;
            color: #333;
            text-align: center;
        }


        .author {
            font-size: 20px;
            color: #555;
        }

        .content {
            margin-bottom: 20px;
            color: #666;
            padding: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        .comment-list table {
            border-collapse: collapse;
            width: calc(100% - 40px);
            margin-top: 20px;
            margin-bottom: 20px;
            box-sizing: border-box;
        }

        .comment-list th, .comment-list td {
            border: 1px solid #ccc;
            padding: 10px;
            text-align: left;
        }

        .comment-list th {
            background-color: #f2f2f2;
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

        function deleteComment(boardId) {
            const memberId = $("#memberId").val();

            // AJAX 요청 보내기
            $.ajax({
                url: `/board/${boardId}`,
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

    <script>
        $(document).ready(function () {
            // 이미지 URL을 가져와서 로그에 출력
            console.log("이미지 URL: " + "${board.getBoardImageUrl()}");
        });
    </script>

    <script>
        $(document).ready(function () {
            // 이미지 URL을 가져와서 로그에 출력
            console.log("이미지 URL: " + "${board.getBoardImageUrl()}");

            // 게시글 이미지 URL 확인
            const imageUrl = "${board.getBoardImageUrl()}";
            if (imageUrl === "" || imageUrl === "null") {
                // 이미지가 없는 경우 대체 이미지로 교체
                document.getElementById("imageContainer").style.backgroundColor = "#eee"; // 배경색 변경
                document.getElementById("boardImage").src = "https://boardbuket.s3.ap-northeast-2.amazonaws.com/23%EB%85%84%EB%8F%84+%EB%B4%84.PNG";
                document.getElementById("boardImage").alt = "대체 이미지";
            }
        });
    </script>

</head>
<body>
<div class="board-container">
    <h1 class="title">${board.title}
        <div id="logo" style="margin-right: 20px;">
            <a href="/mainHome">
                <img src="https://boardbuket.s3.ap-northeast-2.amazonaws.com/logo/logo.png" alt="웹 로고" style="max-width: 100px; max-height: 100px;">        </a>
        </div>
    </h1>
    <div class="author">작성자: ${board.member.nickName}</div>
    <p class="content">${board.contents}</p>

    <c:if test="${board.member != null && board.member.nickName != null && board.member.nickName eq nickName}">
        <a href="/board/${board.id}/edit">수정하기</a>
    </c:if>

    <div class="comment-container">
        <form class="comment-form" id="commentForm">
            <input type="hidden" name="boardId" id="boardId" value="${boardId}">
            <input type="hidden" name="memberId" id="memberId" value="${sessionScope.memberId}">
            <textarea class="comment-textarea" name="content" id="commentContent" placeholder="댓글 내용을 작성해주세요." required="" style="width: 1280px; height: 58px;"></textarea>
            <button class="comment-button" type="submit">댓글 작성</button>
        </form>

        <div id="imageContainer" style="width: 100%; height: 300px; display: flex; justify-content: center; align-items: center; background-color: #eee;">
            <img id="boardImage" src="https://boardbuket.s3.ap-northeast-2.amazonaws.com/${board.getBoardImageUrl()}" alt="게시글 이미지" style="max-width: 100%; max-height: 100%;">
        </div>
    </div>

    <p>조회수: ${board.views}</p>

    <div id="commentList">
        <h4>댓글 리스트</h4>
        <table class="comment-list">
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

                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
