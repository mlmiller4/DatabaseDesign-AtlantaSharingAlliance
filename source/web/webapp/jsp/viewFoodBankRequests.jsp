<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/includes/htmlhead.jsp">
	<jsp:param name="pageTitle" value="ASACS View Food Bank Items"/>
</jsp:include>
<style>
	.count {
	font-size: 50px;
	}
</style>
</head>
<body>
<div class="container">
	<%@ include file="/jsp/includes/header.jsp" %>
	
	<h1>Pending Food Bank Requests</h1>

	<%@ include file="/jsp/includes/messages.jsp" %>

	<div class="panel panel-default">
	  <div class="panel-heading">
	    <h3 class="panel-title">Pending Requests</h3>
	  </div> 
	  <div class="panel-body">
		<table class="table table-striped table-hover" border="0">
			<thead>
				<tr>
					<td>Item ID</td>
<!-- 					<td>Requested Date & Time</td> -->
					<td>Name</td>			
<!-- 					<td>Category</td> -->
<!-- 					<td>Storage Type</td> -->
<!-- 					<td>Expiration Date</td>					 -->
<!-- 					<td>Sub-Category</td> -->
					<td>Requesting Site</td>
					<td>Requesting User</td>
					<td>Number of Units Requested</td>
<!-- 					<td>Number of Units Available</td>					 -->	
					<td>Status</td>
					<td>Number of Units Filled</td>												
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${items}" var="item">
				
				<tr>
					<td><c:out value="${item.itemId}"></c:out></td>
					<td><c:out value="${item.name}"></c:out></td> 
<%-- 					<td><fmt:formatDate type="date" value="${item.reqDateTime}" /></td> --%>
					<td><c:out value="${item.requesteeSiteId}"></c:out></td>
					<td><c:out value="${item.userId}"></c:out></td>
					<td><c:out value="${item.numRequested}"></c:out></td>
					<td><c:out value="${item.strStatus}"></c:out></td>
					<td><c:out value="${item.numFilled}"></c:out></td>
					
<%-- 					<td><c:out value="${item.name}"></c:out></td> --%>
<%-- 					<td><c:out value="${item.category.value}"></c:out></td> --%>
<%-- 					<td><c:out value="${item.storageType.value}"></c:out></td>					 --%>
<%-- 					<td><fmt:formatDate type="date" value="${item.expireDate}" /></td> --%>
<%-- 					<td><c:out value="${item.category.value}"></c:out></td> --%>
<%-- 					<c:choose> --%>
<%-- 					<c:when test="${item.category == 'FOOD'}"> --%>
<%-- 						<td><c:out value="${item.foodCategory.value}"></c:out></td> --%>
<%-- 					</c:when> --%>
<%-- 					<c:otherwise> --%>
<%-- 						<td><c:out value="${item.supplyCategory.value}"></c:out></td> --%>
<%-- 					</c:otherwise> --%>
<%-- 					</c:choose> --%>
					
<%-- 					<td><c:out value="${item.numAvailable}"></c:out></td> --%>
					
					
					
					<td><a href="/RequestsServlet?action=grant&itemId=<c:out value="${item.itemId}"></c:out>" class="btn btn-default" role="button">Grant</a></td>
					<td><input type="text" name="numberOfUnits" id="numUnits" maxlength="2" size="2"></td>
					<td><label for="numUnits">Units</label></td>
					<td><a href="/RequestsServlet?action=deny&itemId=<c:out value="${item.itemId}"></c:out>" class="btn btn-default" role="button">Deny</a></td>
					


<%-- 					<td><a href="/MakeFoodBankRequestServlet?init=true&itemId=<c:out value="${item.itemId}"></c:out>&numUnits=<c:out value="${item.numberUnits}"></c:out>" class="btn btn-default" role="button">Request</a></td> --%>
				</tr>
						
			</c:forEach>
			</tbody>



</body>
</html>