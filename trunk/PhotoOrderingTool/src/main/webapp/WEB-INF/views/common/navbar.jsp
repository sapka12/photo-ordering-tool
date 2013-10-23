<nav class="navbar navbar-default" role="navigation">
    <div class="collapse navbar-collapse navbar-ex1-collapse">
        <ul class="nav navbar-nav">
            <li><a href="${pageContext.request.contextPath}/gallery/">Galleries</a></li>
            <li><a href="${pageContext.request.contextPath}/order/">Order</a></li>
            <li><a href="${pageContext.request.contextPath}/order/myclosed/">Closed Orders</a></li>

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
</nav>