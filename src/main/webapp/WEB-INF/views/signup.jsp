<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>회원가입</title>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-color: #f0f0f0;
        }

        .signup-box {
            text-align: center;
            padding: 20px;
            border: 1px solid #ccc;
            background-color: #fff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 400px; /* 조절 가능한 너비값 */
        }

        .signup-box h1 {
            font-size: 28px;
            margin-bottom: 20px;
        }

        .signup-box label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }

        .signup-box input[type="text"],
        .signup-box input[type="email"],
        .signup-box input[type="password"],
        .signup-box input[type="phone"] {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .signup-box button {
            display: block;
            margin: 5px 0;
            padding: 10px 20px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .signup-box a {
            display: block;
            margin-top: 10px;
            text-decoration: none;
            color: #007bff;
        }
    </style>
    <script src="WEB-INF/views/checkNickName.js"></script>
    <script src="WEB-INF/views/checkEmail.js"></script>

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

        // 이메일 중복 확인
        const emailInput = document.getElementById("emailInput");
        emailInput.addEventListener("input", function () {
            const email = emailInput.value;
            checkEmail(email);
        });

        function handleEmailCheckResponse(response) {
            if (response === true) {
                // email 사용 가능
                console.log("email 사용 가능");
            } else {
                // email 중복 됨
                console.log("email 이미 사용 중");
            }
        }

        function checkErrors() {
            const password = document.getElementById("passwordInput").value;
            const confirmPassword = document.getElementById("confirmPasswordInput").value;

            if (password !== confirmPassword) {
                alert("비밀번호가 일치하지 않습니다.");
                return false;
            }

            // 추가적인 유효성 검사 로직을 여기에 추가할 수 있습니다.

            return true;
        }
    </script>

</head>

<body>
<h1>회원가입</h1>

<form action="/signup" method="post">
    <div>
        <label>Email:</label>
        <input type="email" name="email" id="emailInput" required><br>
    </div>


    <div>
        <label>Password:</label>
        <input type="password" name="password" id="passwordInput" required><br>
    </div>

    <div>
        <label>Confirm Password:</label>
        <input type="password" name="confirmPassword" id="confirmPasswordInput" required><br>
    </div>

    <div>
        <label>NickName:</label>
        <input type="text" name="nickName" id="nickNameInput" required><br>
    </div>

    <div>
        <label>phone: <input type="phone" name="phone"> ex "-"없이 숫자만 입력</label>
    </div>

    <input type="submit" value="가입하기">
</form>

<a href="/" input type="submit">메인으로</a>


</body>
</html>