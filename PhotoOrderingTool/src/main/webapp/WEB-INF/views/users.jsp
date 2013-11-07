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
                    <div class="panel-heading">Users</div>
                    <div class="panel-body">
                        <table class="table table-striped ">
                            <c:forEach var="user" varStatus="status" items="${users}">
                                <tr>
                                    <td>${user.username}</td>
                                    <td>
                                        <div class="btn-group pull-right">
                                            <button class="btn changeadminrole" user-id="${user.id}" type="button" class="btn">
                                                <span class="glyphicon 
                                                      <c:if test="${user.admin}">glyphicon-plus</c:if>
                                                      <c:if test="${!user.admin}">glyphicon-minus</c:if>
                                                          "></span>
                                                </button>
                                                <button type="button" class="btn">
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
                    <div class="panel-heading">Add new user</div>
                    <div class="panel-body">
                        <table class="table">
                            <tr>
                                <td>
                                    <input type="email" class="form-control" id="newUserEmail" >
                                </td>
                                <td>
                                    <div class="pull-right">
                                        <button type="button" class="btn">
                                            <span class="glyphicon glyphicon-plus-sign"></span>
                                        </button>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <script>
            $(document).ready(function() {

                //href="${pageContext.request.contextPath}/user/resendpassword/${user.id}" 
                var getChangeAdminPath = function(userId) {
                    return "${pageContext.request.contextPath}/user/changeadminrole/" + userId;
                };

                var changeAdminRoleButtons = $(".changeadminrole");

                changeAdminRoleButtons.each(function() {
                    var changeButton = $(this);
                    var userId = changeButton.attr("user-id");

                    $(this).click(function() {
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
