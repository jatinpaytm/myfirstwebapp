<!-- login.jsp file is not needed after we added Spring Security -->

<html>
	<head>
		<title>Login Page</title>
	</head>
	<body>
            <div class="container">
                <h1>Login</h1>
                <pre>${errorMessage}</pre>
                <form method="post">
                        Name: <input type="text" name="name">
                        Password: <input type="password" name="password">
                        <input type="submit">
                </form>
        	</div>
	</body>
</html>