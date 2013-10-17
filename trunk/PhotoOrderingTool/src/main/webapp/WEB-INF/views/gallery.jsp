<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${galleryName}</title>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
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
                    <li><a href="../gallery/<c:out value="${i.id}"/>" type="button" class="btn">
                            <span class="glyphicon glyphicon-chevron-left"></span>
                        </a></li>
                </ul>
            </div>
        </nav>

        <div class="panel panel-default">
            <div class="panel-body">
                <c:forEach var="photo" varStatus="status" items="${photos}">
                    <c:if test="${status.count % 6 == 1}" >
                        <div class="row">
                        </c:if>
                        <div class="col-md-2 col-sm-4">

                            <ul class="list-group">
                                <li class="list-group-item">
                                    <a class="thumbnail" >
                                        <img src="../photo/<c:out value="${photo.photo.id}"/>" alt="<c:out value="${photo.photo.title}"/>" >
                                    </a>
                                </li>
                                <li class="list-group-item text-center">
                                    <h4><c:out value="${photo.photo.title}"/></h4>
                                </li>
                                <li class="list-group-item">
                                    <input type="text" class="form-control item-counter" photo-id="${photo.photo.id}" value="0">        
                                </li>
                                <li class="list-group-item text-center">
                                    <div class="btn-group">
                                        <button type="button" class="btn btn-default"><span class="glyphicon glyphicon-minus"></span></button>
                                        <button type="button" class="btn btn-default"><span class="glyphicon glyphicon-plus"></span></button>
                                    </div>
                                </li>
                            </ul>
                        </div>
                        <c:if test="${status.count % 6 == 0}" >
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>
        <script>
            $(document).ready(function() {
                var itemCounters = $("li.list-group-item .item-counter");
                itemCounters.each(function () {
                   console.log($(this).attr("photo-id")) ;
                });
            });
        </script>
    </body>
</html>
