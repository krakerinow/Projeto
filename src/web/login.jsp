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
<style>
    .login_form{
        display: table;
        text-align: center;
        margin-left: auto;
        margin-right: auto;
        height: 25%;
        width: 25%;
        padding: 100px 0px;
        border: 5px solid white;
    }
    .editform{
        padding: 150px 0px;
    }


    .login{
        text-align: center;
        font-size: 35px;
        font-weight: bold;
    }


    body{
        background-color: silver;
    }

</style>
<html>
    <head>
        <title>Login</title>
    </head>


    <body>
            <div class="editform">
                <form class="login_form form-vertical" method="post" action="<%=request.getContextPath()%>/login">
                    <p class="login">Login</p>
                    <div class="form-group">
                        <span class="glyphicon glyphicon-user"/>
                        <input type="text" class="form-control" id="username" placeholder="Enter username"  name="username">
                    </div>
                    <div class="form-group">
                        <span class="glyphicon glyphicon-lock"/>
                        <input type="password" class="form-control" id="pwd" placeholder="Enter password" name="pwd">
                    </div>
                    <input class="btn btn-success" type="submit">
                </form>
            </div>
    </body>
</html>
