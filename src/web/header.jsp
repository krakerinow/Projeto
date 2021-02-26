<%@ page import="org.apache.log4j.Logger" %>
<%@ page import="teste.web.LogoutServlet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld"  prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-nested.tld"  prefix="nested" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld"  prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld"  prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld"  prefix="tiles" %>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular.min.js"></script>

<!DOCTYPE html>
<html>
<head>
    <style>
        ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
            overflow: hidden;
            background-color: #333;
        }

        li {
            float: left;
        }

        li a {
            display: block;
            color: white;
            text-align: center;
            padding: 20px 20px;
            text-decoration: none;
        }

        li a:hover {
            background-color: gray;
        }
    </style>
</head>
<body>
<%
    boolean logado = request.getAttribute("userLoggedIn")!=null;
    Logger logger = Logger.getLogger(LogoutServlet.class);
    logger.info(logado);

%>
<div>
    <ul>
        <!--<li><a href="home.do">Home</a></li>
        <li><a href="login.do">Login</a></li>
        <li><a href="user.do">Users</a></li>-->

        <li>  <a id="home" class="navbar-item" href="<%=request.getContextPath()%>/home.do">Home</a></li>
        <li>  <a id="pages" class="navbar-item" href="<%=request.getContextPath()%>/page.do">Pages</a></li>
        <li>  <a id="users" class="navbar-item" href="<%=request.getContextPath()%>/user.do">Users</a></li>
        <li> <a id="login" class="navbar-item"  href="<%=request.getContextPath()%>/login.do">Login</a></li>
        <li> <a id="logout" class="navbar-item" style="display: none;" onclick="document.getElementById('logoutForm').submit();">Logout</a></li>
    </ul>
    <form id="logoutForm" style="display: none;" method="post" action="<%=request.getContextPath()%>/logout">
        <input type="text" id="username" name="username">
    </form>


</div>




<script>
    $(function () {
        if(<%=logado == true%>) {
            $("#login").css("display", "none");
            $("#logout").css("display", "");
            $("#home").css("display", "");
            $("#pages").css("display", "");
            $("#users").css("display", "");
        } else {
            $("#logout").css("display", "none");
            $("#home").css("display", "none");
            $("#login").css("display", "");
            $("#pages").css("display", "none");
            $("#users").css("display", "none");
        }
    })
</script>

</body>
</html>


