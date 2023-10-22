<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Board</title>

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


        .create-board-form {
            text-align: center;
            background-color: #fff;
            padding: 20px;
            border: 1px solid #ccc;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 50%;
        }


        .create-board-form h1 {
            font-size: 32px;
            margin-bottom: 20px;
        }

        .create-board-form h2 {
            font-size: 24px;
            margin-bottom: 10px;
            color: #333;
        }

        .create-board-form label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }

        .create-board-form select,
        .create-board-form input[type="text"],
        .create-board-form textarea {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .create-board-form textarea {
            resize: vertical;
            height: 300px; /* 더 큰 comment box 높이 */
        }

        .create-board-form button[type="submit"] {
            padding: 10px 20px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .create-board-form button[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
    <script>
        function previewImage(input) {
            var imagePreview = document.getElementById('image-preview');
            if (input.files && input.files[0]) {
                var reader = new FileReader();
                reader.onload = function(e) {
                    imagePreview.src = e.target.result;
                    imagePreview.style.display = 'block';
                };
                reader.readAsDataURL(input.files[0]);
            } else {
                imagePreview.style.display = 'none';
            }
        }
    </script>

</head>
<body>
<div class="create-board-form">
    <h1>Create Board</h1>
    <form action="/createBoardDto" method="post" enctype="multipart/form-data">

        <input type="hidden" name="memberId" value="${memberId}">

        <div>
            <h2> ${sessionScope.nickName}님</h2>
        </div>

        <div>
            <label for="category">Category:</label>
            <select id="category" name="categoryEnum" required>
                <option value="FOOTBALL">Football</option>
                <option value="BASKETBALL">Basketball</option>
                <option value="VOLLEYBALL">Volleyball</option>
                <option value="TENNIS">Tennis</option>
            </select>
        </div>

        <div>
            <label for="title">제목:</label>
            <input type="text" id="title" name="title" class="form-input" required style="width: 790px;">
        </div>

        <div>
            <label for="contents">내용:</label>
            <textarea id="contents" name="contents" class="form-textarea" required style="width: 790px; height: 290px;"></textarea>
        </div>

        <div>
            <label for="image">이미지 업로드:</label>
            <input type="file" id="image" name="BoardFile" accept="image/*">
        </div>

        <button type="submit" class="form-button">Submit</button>
    </form>
</div>

</body>
</html>
