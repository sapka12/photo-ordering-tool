<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Galleries</title>
        <%@include file='./common/head.jsp'%>
    </head>
    <body>
        <%@include file='./common/navbar.jsp'%>
    <div class="panel panel-default">
        <div class="panel-body">
            <c:forEach var="gallery" varStatus="status" items="${list}">
                <c:if test="${status.count % 4 == 1}" >
                    <div class="row">
                    </c:if>
                    <div class="col-md-3 col-sm-6">
                        <ul class="list-group">
                            <li class="list-group-item">
                                <a href="${pageContext.request.contextPath}/gallery/<c:out value="${gallery.id}"/>" class="thumbnail" >
                                    <img style="min-height: 128px; max-height: 128px;" src="${pageContext.request.contextPath}/gallery/icon/<c:out value="${gallery.id}"/>" alt="<c:out value="${gallery.title}"/>" >
                                </a>
                            </li>
                            <li class="list-group-item text-center">
                                <h4><c:out value="${gallery.title}"/></h4>
                            </li>
                        </ul>
                    </div>
                    <c:if test="${status.count % 4 == 0}" >
                    </div>
                </c:if>
            </c:forEach>
        </div>
    </div>
</body>
</html>
