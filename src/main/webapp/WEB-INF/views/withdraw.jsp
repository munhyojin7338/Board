<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Withdraw</title>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-color: #f0f0f0;
            font-family: Arial, sans-serif;
        }

        .withdraw-box {
            text-align: center;
            padding: 40px;
            border: 1px solid #ccc;
            background-color: #fff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 400px;
        }

        .withdraw-box h1 {
            font-size: 32px;
            margin-bottom: 10px;
        }

        .withdraw-box label {
            display: block;
            margin-bottom: 10px;
        }

        .withdraw-box input[type="text"] {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .withdraw-box button {
            padding: 10px 20px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .withdraw-box button:hover {
            background-color: #0056b3;
        }
    </style>

    <script>
        const memberId = ${sessionScope.memberId};

        function confirmWithdraw() {
            const confirmMessage = "정말로 회원을 탈퇴하시겠습니까?";

            if (confirm(confirmMessage)) {
                const url = `/delete/user/${memberId}`;

                fetch(url, {
                    method: 'DELETE'
                })
                    .then(response => response.json())
                    .then(data => {
                        if (data.success) {
                            alert("회원이 탈퇴되었습니다.");
                            window.location.href = "home";
                        } else {
                            alert("탈퇴에 실패했습니다. 관리자에게 문의하세요.");
                        }
                    })
                    .catch(error => {
                        console.error('Error:', error);
                    });
            }
        }
    </script>
</head>
<body>
<div class="withdraw-box">
    <h1>Withdraw</h1>
    <form>
        <button type="button" onclick="confirmWithdraw()">탈퇴하기</button>
    </form>
</div>
</body>
</html>
