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
                    <c:if test="${status.count % 4 == 1}" ><div class="row"></c:if>
                        <div class="col-md-3 col-sm-6 photo-panel" photo-id="${photo.photo.id}" photo-title="${photo.photo.title}">

                            <ul class="list-group">
                                <li class="list-group-item">
                                    <a class="thumbnail" data-toggle="modal" href="#myModal">
                                        <img style="min-height: 128px; max-height: 128px;" src="${pageContext.request.contextPath}/photo/<c:out value="${photo.photo.id}"/>" alt="<c:out value="${photo.photo.title}" />" >
                                    </a>
                                </li>
                                <li class="list-group-item text-center">
                                    <c:out value="${photo.photo.title}"/>
                                </li>
                                <li class="list-group-item text-center">
                                    <table class="table table-condensed">
                                        <c:forEach var="photoCounter" items="${photo.counters}">
                                            <tr class="photo-counter">
                                                <td>
                                                    <button type="button" class="btn btn-default"><span class="glyphicon glyphicon-minus"></span></button>
                                                </td>
                                                <td>
                                                    <h4>${photoCounter.type.size}</h4>
                                                </td>
                                                <td>
                                                    <input type="text" class="form-control item-counter" photo-type="${photoCounter.type}" photo-id="${photo.photo.id}" value="${photoCounter.counter}" disabled>
                                                </td>
                                                <td>
                                                    <button type="button" class="btn btn-default"><span class="glyphicon glyphicon-plus"></span></button>
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
                var incUrlBase = "${pageContext.request.contextPath}/photo/inc/";
                var decUrlBase = "${pageContext.request.contextPath}/photo/dec/";

                var incUrl = function(photoId, photoType, isInc) {
                    var baseUrl = incUrlBase;
                    if (!isInc) {
                        baseUrl = decUrlBase;
                    }
                    return baseUrl + photoType + "/" + photoId;
                };

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

                var photoPanels = $(".photo-panel");
                photoPanels.each(function() {
                    var photoId = $(this).attr("photo-id");
                    var photoName = $(this).attr("photo-title");

                    var modalButton = $(".thumbnail:first", this);
                    modalButton.click(function() {
                        changeImage(photoId, photoName);
                    });
                });


                var listGroups = $(".list-group");
                listGroups.each(function() {


                    var photoCounter = $(".photo-counter", this);
                    photoCounter.each(function() {

                        var itemCounter = $(".item-counter:first", this);
                        var minusButton = $(".glyphicon-minus:first", this).parent();
                        var plusButton = $(".glyphicon-plus:first", this).parent();
                        var photoId = itemCounter.attr("photo-id");
                        var photoType = itemCounter.attr("photo-type");

                        var showChangedCounter = function(changedCounter) {
                            console.log("changedCounter: " + changedCounter)
                            itemCounter.val(changedCounter);
                            setEnableButtons(true);
                        };

                        plusButton.click(function() {
                            console.log("+ " + photoId);
                            setEnableButtons(false);
                            $.post(incUrl(photoId, photoType, true), showChangedCounter);
                        });

                        minusButton.click(function() {
                            console.log("- " + photoId);
                            setEnableButtons(false);
                            $.post(incUrl(photoId, photoType, false), showChangedCounter);
                        });
                    });
                });
                console.log("jQuery ready!");
            });
        </script>

    </body>
</html>
