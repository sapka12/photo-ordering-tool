<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <title>Login</title>
        <%@include file='./common/head.jsp'%>
    </head>
    <body>
        <div class="container">
            <div class="well">
                <h1>Login</h1>
                <form role="form" method="post" action="j_spring_security_check">
                    <div class="form-group">
                        <label for="loginname">Loginname</label>
                        <input class="form-control"  id="j_username" name="j_username" type="text" placeholder="Enter Loginname">
                    </div>
                    <div class="form-group">
                        <label for="passwd">Password</label>
                        <input class="form-control" id="j_password" name="j_password" type="password" placeholder="Password">
                    </div>
                    <button type="submit" class="btn btn-default">Submit</button>
                </form>
            </div>
        </div>
    </body>
</html>
