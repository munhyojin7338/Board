<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>회원가입</title>
  <script src="WEB-INF/views/checkNickName.js"></script>

  <!-- JavaScript 코드 작성 -->
  <script>
    // 예시: 사용자가 닉네임 입력 필드를 작성할 때마다 중복 확인을 수행
    const nickNameInput = document.getElementById("nickNameInput");
    nickNameInput.addEventListener("input", function () {
      const nickName = nickNameInput.value;
      checkNickName(nickName);
    });

    function handleNickNameCheckResponse(response) {
      if (response === true) {
        // 닉네임이 중복되지 않음
        // 원하는 동작을 수행
        console.log("닉네임 사용 가능");
      } else {
        // 닉네임이 중복됨
        // 원하는 동작을 수행
        console.log("닉네임이 이미 사용 중");
      }
    }

  </script>

  <script src="WEB-INF/views/checkEmail.js"></script>

  <script>
    // 예시: 사용자가 닉네임 입력 필드를 작성할 때마다 중복 확인을 수행
    const emailInput = document.getElementById("emailInput");
    emailInput.addEventListener("input", function () {
      const email = emailInput.value;
      checkEmail(email);
    });

    function handleEmailCheckResponse(response) {
      if (response === true) {
        // email 사용가능
        console.log("email 사용 가능");
      } else {
        // email 중복 됨
        console.log("email 이미 사용 중");
      }
    }

  </script>


</head>

<body>
<h1>회원가입</h1>
<form action="/signup" method="post">
  <label>Email:</label>
  <input type="email" name="email" id="emailInput" required><br><br>

  <label>Password:</label>
  <input type="password" name="password" required><br><br>


  <label>NickName:</label>
  <input type="text" name="nickName" id="nickNameInput" required><br><br>

  <label>Phone:</label>
  <input type="phone" name="phone" required><br><br>

  <input type="submit" value="가입하기">
</form>

<a href="/" input type="submit">메인으로</a>


</body>
</html>