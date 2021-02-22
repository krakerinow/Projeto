<%--
  Created by IntelliJ IDEA.
  User: jmachado
  Date: 28/10/2019
  Time: 10:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>

Login

<form method="post" action="<%=request.getContextPath()%>/login">
    Username:<input type="text" name="username"><br/>
    Pass: <input type="password" name="password"><br/>
    <input type="submit">

</form>

</body>
</html>
