<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

      <div class="header clearfix">
        <nav>
          <ul class="nav nav-pills pull-right">
            <li role="presentation" class="active"><a href="/HomeServlet">Home</a></li>
            <li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"><c:out value="${authenticatedUser.account.userName}"></c:out><b class="caret"></b></a>
	            <ul class="dropdown-menu">
	                <li style="margin: 10px;">
	                    <div class="navbar-content">
		                    <div>
		                        <span><c:out value="${authenticatedUser.lastName}"></c:out>, <c:out value="${authenticatedUser.firstName}"></c:out></span>
		                        <p class="text-muted small"><c:out value="${authenticatedUser.email}"></c:out></p>
		                        <div class="divider">
		                        </div>
		                    </div>
	                    </div>
	                    <div class="navbar-footer">
							<div class="navbar-footer-content">
								<div>
									<a href="/LogonServlet?action=logoff" class="btn btn-default btn-sm pull-right">Sign Out</a>
								</div>	
							</div>
	                    </div>
	                </li>
	            </ul>                    
            </li>
          </ul>
        </nav>
        <h3 class="text-muted">ASACS - <c:out value="${authenticatedUsersSite.shortName}"></c:out>
        </h3>
      </div>