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
            <c:forEach var="entry" items="${orders.orders}">
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <a>
                            ${entry.key}
                        </a>
                    </div>
                    <div class="panel-body">
                        <c:forEach var="photo" varStatus="status" items="${entry.value}">
                            <c:if test="${status.count % 4 == 1}" ><div class="row"></c:if>

                                    <div class="col-md-3 col-sm-6">

                                        <ul class="list-group">
                                            <li class="list-group-item">
                                                <a class="thumbnail" photo-id="${photo.photo.id}" photo-title="${photo.photo.title}" data-toggle="modal" href="#myModal" >
                                                <img style="min-height: 128px; max-height: 128px;" src="${pageContext.request.contextPath}/photo/<c:out value="${photo.photo.id}"/>" alt="<c:out value="${photo.photo.title}"/>" >
                                            </a>
                                        </li>

                                        <li class="list-group-item text-center">
                                            <h4><c:out value="${photo.photo.title}"/></h4>
                                        </li>

                                        <li class="list-group-item photo-counter">
                                            <table class="table table-condensed">
                                                <c:forEach var="photoCounter" items="${photo.counters}">
                                                    <tr>
                                                        <td><h4>${photoCounter.type.size}</h4></td>
                                                        <td>
                                                            <input type="text" class="form-control item-counter" photo-type="${photoCounter.type}" photo-id="${photo.photo.id}" value="${photoCounter.counter}" disabled>
                                                        </td>
                                                    </tr>        
                                                </c:forEach>
                                            </table>
                                        </li>
                                    </ul>
                                </div>
                                <c:if test="${status.count % 4 == 0}" ></div></c:if>
                            </c:forEach>
                    </div>
                </div>
            </c:forEach>
        </div>
        <!-- Modal -->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">Modal title</h4>
                    </div>
                    <div class="modal-body" ></div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
        <script>
            $(document).ready(function() {
                var mediumPhotoUrlBase = "${pageContext.request.contextPath}/photo/medium/";
                var changeImage = function(photoId, photoName) {
                    var photoUrl = mediumPhotoUrlBase + photoId;
                    $(".modal-body").html('<a class="thumbnail" ><img src="' + photoUrl + '"></a>');
                    $(".modal-title").text(photoName);
                };
                var photoPanels = $(".thumbnail");
                photoPanels.each(function() {
                    var photoId = $(this).attr("photo-id");
                    var photoName = $(this).attr("photo-title");

                    var modalButton = $(this);
                    modalButton.click(function() {
                        changeImage(photoId, photoName);
                    });
                });
            });
        </script>
    </body>
</html>
