<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
                    <li><a href="${pageContext.request.contextPath}/gallery/">Galleries</a></li>
                    <li<c:if test="${galleryName == null}"> class="active"</c:if>><a href="${pageContext.request.contextPath}/order/">Order</a></li>
                        <c:if test="${galleryName != null}">
                        <li class="active"><a href="#">${galleryName}</a></li>
                        </c:if>

                    <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                        <li><a href="${pageContext.request.contextPath}/order/allactual">Actual Orders</a></li>        
                        <li><a href="${pageContext.request.contextPath}/order/all">All Orders</a></li>        
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
                                    <a class="thumbnail" style="min-height: 110px;">
                                        <img src="${pageContext.request.contextPath}/photo/<c:out value="${photo.photo.id}"/>" alt="<c:out value="${photo.photo.title}"/>" >
                                    </a>
                                </li>
                                <li class="list-group-item text-center">
                                    <h4><c:out value="${photo.photo.title}"/></h4>
                                </li>
                                <li class="list-group-item">
                                    <input type="text" class="form-control item-counter" photo-id="${photo.photo.id}" value="${photo.counter}" disabled>        
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
                var incUrlBase = "${pageContext.request.contextPath}/photo/inc/";
                var decUrlBase = "${pageContext.request.contextPath}/photo/dec/";
                var listGroups = $(".list-group");

                var setEnableButtons = function(enable) {
                    if (enable) {
                        $(".glyphicon-minus").parent().removeAttr('disabled');
                        $(".glyphicon-plus").parent().removeAttr('disabled');
                    } else {
                        $(".glyphicon-minus").parent().attr('disabled', 'disabled');
                        $(".glyphicon-plus").parent().attr('disabled', 'disabled');
                    }
                };

                listGroups.each(function() {

                    var itemCounter = $(".item-counter:first", this);
                    var showChangedCounter = function(changedCounter) {
                        itemCounter.val(changedCounter);
                        setEnableButtons(true);
                    };

                    var count = itemCounter.val();
                    var photoId = itemCounter.attr("photo-id");
                    console.log("photo[" + photoId + "]: " + count);

                    var minusButton = $(".glyphicon-minus:first", this).parent();
                    var plusButton = $(".glyphicon-plus:first", this).parent();

                    plusButton.click(function() {
                        $.post(incUrlBase + photoId, showChangedCounter);
                    });

                    minusButton.click(function() {
                        setEnableButtons(false);
                        $.post(decUrlBase + photoId, showChangedCounter);
                    });

                });
            });
        </script>
    </body>
</html>
