<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<nav class="navbar navbar-default" role="navigation">
    <div class="collapse navbar-collapse navbar-ex1-collapse">
        <ul class="nav navbar-nav">
            <li><a href="${pageContext.request.contextPath}/gallery/"><spring:message code="navbar.gallery" /></a></li>
            <li><a href="${pageContext.request.contextPath}/order/"><spring:message code="navbar.order" /></a></li>
            <li><a href="${pageContext.request.contextPath}/order/myclosed/"><spring:message code="navbar.myclosedorders" /></a></li>

            <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                <li><a href="${pageContext.request.contextPath}/order/allactual"><spring:message code="navbar.allactualorders" /></a></li>
                <li><a href="${pageContext.request.contextPath}/users"><spring:message code="navbar.users" /></a></li>
            </sec:authorize>
        </ul>
            
        <ul class="nav navbar-nav navbar-right">      
  <!--
  <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="language" /><b class="caret"></b></a>
           <ul class="dropdown-menu">
                <li><a href="?language=en">English</a></li>
                <li><a href="?language=hu_HU">Magyar</a></li>
            </ul>
            </li>
  -->
            <li><a>${username}</a></li>
            <li>
                <a href="${pageContext.request.contextPath}/logout<c:out value="${i.id}"/>" type="button" class="btn btn-danger">
                <span class="glyphicon glyphicon-log-out"></span>
                </a>
            </li>
        </ul>
    </div>
</nav>