<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
                <h1><spring:message code="loginpage.login" /></h1>
                
                <c:if test="${param.failure != null}" >
                    <!--LOGIN ERROR MESSAGE-->
                    <div class="alert alert-danger"><spring:message code="loginpage.loginerror" /></div>
                </c:if>
                    
                <form role="form" method="post" action="j_spring_security_check">
                    <div class="form-group">
                        <label for="loginname"><spring:message code="loginpage.loginname" /></label>
                        <input class="form-control"  id="j_username" name="j_username" type="email" >
                    </div>
                    <div class="form-group">
                        <label for="passwd"><spring:message code="loginpage.password" /></label>
                        <input class="form-control" id="j_password" name="j_password" type="password" >
                    </div>
                    <button type="submit" class="btn btn-default"><spring:message code="loginpage.submit" /></button>
                </form>
            </div>
        </div>
    </body>
</html>
