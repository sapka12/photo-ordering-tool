<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Actual Orders</title>
        <%@include file='./common/head.jsp'%>
    </head>
    <body>
        <%@include file='./common/navbar.jsp'%>

        <div class="panel panel-default">
            <div class="panel-body">
                <button type="button" class="btn btn-primary close-orders"><spring:message code="ordersactualpage.closeorders" /></button>
            </div>
        </div>

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
                            <c:if test="${status.count % 4 == 1}" ><div class="row"></c:if>

                                    <div class="col-md-3 col-sm-6">

                                        <ul class="list-group">
                                            <li class="list-group-item">
                                                <a class="thumbnail" data-toggle="modal" href="#myModal" style="min-height: 110px;"><img style="min-height: 128px; max-height: 128px;" src="${pageContext.request.contextPath}/photo/<c:out value="${photo.photo.id}"/>" alt="<c:out value="${photo.photo.title}"/>" ></a>
                                        </li>

                                        <li class="list-group-item text-center">
                                            <c:out value="${photo.photo.title}"/>
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


        <script>
            $(document).ready(function() {


                var setEnableButtons = function(enable) {
                    if (enable) {
                        $(".close-orders").parent().removeAttr('disabled');
                    } else {
                        $(".close-orders").parent().attr('disabled', 'disabled');
                    }
                };
                $(".close-orders").click(function() {
                    console.log("closing...");
                    setEnableButtons(false);
                    var url = "${pageContext.request.contextPath}/order/close";
                    $.post(url, function() {
                        console.log("orders closed.");
                        setEnableButtons(true);
                    });
                });
            });
        </script>

    </body>
</html>
