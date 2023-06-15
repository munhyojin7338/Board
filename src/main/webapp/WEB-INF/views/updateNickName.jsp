<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>닉네임 수정</title>
</head>
<body>
<h1>HealthBoard</h1>
<h2>반갑습니다, ${sessionScope.nickName}님</h2>
<h2>닉네임 수정</h2>

<c:if test="${not empty errorMessage}">
    <p>${errorMessage}</p>
</c:if>

<form id="updateForm" method="post" action="/updateNickName/${sessionScope.updateNickName}">
    <input type="hidden" name="_method" value="PUT">
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="text" id="nickNameInput" name="updateNickName" placeholder="새로운 닉네임 입력" required>
    <button type="button" onclick="updateMemberInfo()">수정</button>
</form>

<script>
    function updateMemberInfo() {
        const nickNameInput = document.getElementById("nickNameInput");
        const updateNickName = nickNameInput.value.trim();

        // 유효성 검사: 닉네임 필드가 비어있는지 확인
        if (updateNickName === "") {
            alert("닉네임을 입력해주세요.");
            return;
        }

        const previousNickName = "${sessionScope.nickName}"; // 기존 닉네임 가져오기
        if (updateNickName === previousNickName) {
            alert("기존 닉네임과 동일한 닉네임입니다.");
            return;
        }

        const form = document.getElementById("updateForm");
        const actionUrl = form.getAttribute("action"); // 폼의 action URL 가져오기

        const xhr = new XMLHttpRequest();
        xhr.open("POST", actionUrl);
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    // 성공 처리
                    alert("회원 정보가 성공적으로 수정되었습니다.");
                    location.href = "/myPage"; // 마이페이지로 이동
                } else if (xhr.status === 400){
                    // 실패 처리
                    const responseText = xhr.responseText;
                    alert(responseText);
                } else {
                    // 오류 처리
                    alert("오류가 발생했습니다.");
                }
            }
        };
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.send(JSON.stringify({ updateNickName: updateNickName }));
    }
</script>

</body>
</html>
