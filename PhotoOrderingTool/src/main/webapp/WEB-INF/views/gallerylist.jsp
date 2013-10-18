<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Galleries</title>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
        <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-theme.min.css">
        <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
    </head>
    <body>
        <nav class="navbar navbar-default" role="navigation">
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="${pageContext.request.contextPath}/gallery/">Galleries</a></li>
                    <li><a href="${pageContext.request.contextPath}/order/">Order</a></li>

                    <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                        <li><a href="${pageContext.request.contextPath}/order/allactual">Actual Orders</a></li>        
                        <!--<li><a href="${pageContext.request.contextPath}/order/all">All Orders</a></li>-->        
                    </sec:authorize>
                </ul>
                <ul class="nav navbar-nav navbar-right">                
                    <li><a>${username}</a></li>
                    <li>
                        <a href="${pageContext.request.contextPath}/logout<c:out value="${i.id}"/>" type="button" class="btn btn-danger">
                            <span class="glyphicon glyphicon-log-out"></span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <div class="panel panel-default">
        <div class="panel-body">
            <c:forEach var="gallery" varStatus="status" items="${list}">
                <c:if test="${status.count % 6 == 1}" >
                    <div class="row">
                    </c:if>
                    <div class="col-md-2 col-sm-4">
                        <ul class="list-group">
                            <li class="list-group-item">
                                <a href="${pageContext.request.contextPath}/gallery/<c:out value="${gallery.id}"/>" class="thumbnail" >
                                    <img src="${pageContext.request.contextPath}/gallery/icon/<c:out value="${gallery.id}"/>" alt="<c:out value="${gallery.title}"/>" >
                                </a>
                            </li>
                            <li class="list-group-item text-center">
                                <h4><c:out value="${gallery.title}"/></h4>
                            </li>
                        </ul>
                    </div>
                    <c:if test="${status.count % 6 == 0}" >
                    </div>
                </c:if>
            </c:forEach>
        </div>
    </div>
</body>
</html>
