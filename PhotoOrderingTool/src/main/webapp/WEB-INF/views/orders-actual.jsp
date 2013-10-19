<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Actual Orders</title>
        <%@include file='./common/head.jsp'%>
    </head>
    <body>
        <%@include file='./common/navbar.jsp'%>

        <div class="well">
            <c:forEach var="order" items="${orders}">
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <a>
                            <span class="badge pull-right">${order.sum}</span>
                            ${order.user.username}
                        </a>
                    </div>
                    <div class="panel-body">
                        <c:forEach var="photo" varStatus="status" items="${order.photos}">
                            <c:if test="${status.count % 6 == 1}" >
                                <div class="row">
                                </c:if>
                                <div class="col-md-2 col-sm-4">

                                    <ul class="list-group">
                                        <li class="list-group-item">
                                            <a class="thumbnail" style="min-height: 110px;">
                                                <img src="${pageContext.request.contextPath}/photo/<c:out value="${photo.photo.id}"/>" >
                                            </a>
                                        </li>
                                        <li class="list-group-item text-center">
                                            <h4><c:out value="${photo.photo.title}"/></h4>
                                        </li>
                                        <li class="list-group-item">
                                            <input type="text" class="form-control item-counter" photo-id="${photo.photo.id}" value="${photo.counter}" disabled>        
                                        </li>
                                    </ul>
                                </div>
                                <c:if test="${status.count % 6 == 0}" >
                                </div>
                            </c:if>

                        </c:forEach>
                    </div>
                </div>
            </c:forEach>
        </div>
    </body>
</html>
