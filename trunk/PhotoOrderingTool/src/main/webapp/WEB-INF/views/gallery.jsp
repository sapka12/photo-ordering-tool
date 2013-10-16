<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${galleryName}</title>
        <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
        <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-theme.min.css">
        <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
    </head>
    <body>
        <nav class="navbar navbar-default" role="navigation">
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="#">${galleryName}</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="../gallerylist/<c:out value="${i.id}"/>" type="button" class="btn">
                            <span class="glyphicon glyphicon-chevron-left"></span>
                        </a></li>
                </ul>
            </div>
        </nav>

        <div class="panel panel-default">
            <div class="panel-body">
                <table class="table table-striped table-bordered table-hover">
                    <c:forEach var="i" items="${photos}">
                        <tr>
                            <!--<td><c:out value="${i.id}"/></td>-->
                            <td><c:out value="${i.title}"/></td>
                            <td>
                                <img src="../photo/<c:out value="${i.id}"/>" alt="<c:out value="${i.title}"/>" >
                            </td>
                        </tr>
                    </c:forEach>   
                </table>
            </div>
        </div>
    </body>
</html>
