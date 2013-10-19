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
                <c:forEach var="photo" varStatus="status" items="${photos}">
                    <c:if test="${status.count % 6 == 1}" >
                        <div class="row">
                        </c:if>
                        <div class="col-md-2 col-sm-4">

                            <ul class="list-group">
                                <li class="list-group-item">
                                    <a class="thumbnail" data-toggle="modal" href="#myModal" style="min-height: 110px;">
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
        <!-- Modal -->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">Modal title</h4>
                    </div>
                    <div class="modal-body" />
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
        <script>
            $(document).ready(function() {
                var mediumPhotoUrlBase = "${pageContext.request.contextPath}/photo/medium/";
                var incUrlBase = "${pageContext.request.contextPath}/photo/inc/";
                var decUrlBase = "${pageContext.request.contextPath}/photo/dec/";

                var setEnableButtons = function(enable) {
                    if (enable) {
                        $(".glyphicon-minus").parent().removeAttr('disabled');
                        $(".glyphicon-plus").parent().removeAttr('disabled');
                    } else {
                        $(".glyphicon-minus").parent().attr('disabled', 'disabled');
                        $(".glyphicon-plus").parent().attr('disabled', 'disabled');
                    }
                };
                var changeImage = function(photoId, photoName) {
                    var photoUrl = mediumPhotoUrlBase + photoId;
                    $(".modal-body").html('<a class="thumbnail" ><img src="' + photoUrl + '"></a>');
                    $(".modal-title").text(photoName);
                };

                var listGroups = $(".list-group");
                listGroups.each(function() {
                    var itemCounter = $(".item-counter:first", this);
                    var showChangedCounter = function(changedCounter) {
                        itemCounter.val(changedCounter);
                        setEnableButtons(true);
                    };
                    var count = itemCounter.val();
                    var photoId = itemCounter.attr("photo-id");
                    var photoName = $("h4:first", this).text();
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
                    var modalButton = $(".thumbnail:first", this);
                    modalButton.click(function() {
                        changeImage(photoId, photoName);
                    });
                });
            });
        </script>
    </body>
</html>
