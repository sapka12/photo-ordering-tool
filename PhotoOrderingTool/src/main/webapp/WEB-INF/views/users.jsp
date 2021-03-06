<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>        
        <title>${galleryName}</title>
        <%@include file='./common/head.jsp'%>
    </head>
    <body>
        <%@include file='./common/navbar.jsp'%>

        <div class="panel panel-default">
            <div class="panel-body">
                <div class="panel panel-default">
                    <div class="panel-heading"><spring:message code="userspage.users" /></div>
                    <div class="panel-body">
                        <table class="table table-striped ">
                            <c:forEach var="user" varStatus="status" items="${users}">
                                <tr>
                                    <td>${user.email}</td>
                                    <td>
                                        <div class="btn-group pull-right">
                                            <button class="btn changeadminrole" user-id="${user.id}" type="button" >
                                                <span class="glyphicon 
                                                      <c:if test="${user.admin}">glyphicon-plus</c:if>
                                                      <c:if test="${!user.admin}">glyphicon-minus</c:if>
                                                          "></span>
                                                </button>
                                                <button class="btn resendpassword" user-id="${user.id}" type="button" >
                                                <span class="glyphicon glyphicon-send"></span>
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading"><spring:message code="userspage.addnewuser" /></div>
                    <div class="panel-body">
                        <form action="${pageContext.request.contextPath}/user/addnew/" method="GET">
                            <table class="table">
                                <tr>
                                    <td>
                                        <input type="email" name="email" class="form-control" id="newUserEmail" >
                                    </td>
                                    <td>
                                        <div class="pull-right">
                                            <button type="submit" class="btn">
                                                <span class="glyphicon glyphicon-plus-sign"></span>
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <script>
            $(document).ready(function() {

                var getResendPasswordPath = function(userId) {
                    return "${pageContext.request.contextPath}/user/resendpassword/" + userId;
                };

                var resendPwButtons = $(".resendpassword");
                resendPwButtons.each(function() {
                    var button = $(this);
                    var userId = button.attr("user-id");

                    button.click(function() {
                        button.prop('disabled', true);
                        $.post(getResendPasswordPath(userId), function() {
                            button.prop('disabled', false);
                        });
                    });
                });

                var getChangeAdminPath = function(userId) {
                    return "${pageContext.request.contextPath}/user/changeadminrole/" + userId;
                };

                var changeAdminRoleButtons = $(".changeadminrole");
                changeAdminRoleButtons.each(function() {
                    var changeButton = $(this);
                    var userId = changeButton.attr("user-id");

                    changeButton.click(function() {
                        $.post(getChangeAdminPath(userId), function(isAdmin) {
                            var span = $("span:first", changeButton);
                            span.removeClass("glyphicon-minus");
                            span.removeClass("glyphicon-plus");
                            span.addClass(isAdmin ? "glyphicon-plus" : "glyphicon-minus");
                        });
                    });
                });
            });
        </script>

    </body>
</html>
