<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<div id="messagesarea">
	<c:if test="${not empty errors}">
	<p class="alert alert-danger" role="alert">	
		<c:forEach items="${errors}" var="error">
			<span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true" style="color:#a94442"></span> <c:out value="${error}"></c:out><br />		      	
		</c:forEach>
		<c:remove var="session.errors" scope="session" />
	</p>	   	
	</c:if>

	<c:if test="${not empty messages}">
	<p class="alert alert-success" role="alert">	
		<c:forEach items="${messages}" var="message">
			<span class="glyphicon glyphicon-ok" aria-hidden="true" style="color:green"></span> <c:out value="${message}"></c:out><br />
		</c:forEach>
		<c:remove var="session.messages" scope="session" />
	</p>	
	</c:if>
	</div>	