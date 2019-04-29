<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<jsp:include page="/jsp/includes/htmlhead.jsp">
	    <jsp:param name="pageTitle" value="ASACS Home Page"/>
	</jsp:include>
</head>
<body>
<div class="container">
	<%@ include file="/jsp/includes/header.jsp" %>
	
	<h1>ASACS Management</h1>
	<address>
	  <strong><c:out value="${site.shortName}"></c:out></strong><br>
	  <c:out value="${site.streetAddress}"></c:out><br>
	  <c:out value="${site.city}"></c:out>, <c:out value="${site.state}"></c:out> <c:out value="${site.zip}"></c:out><br>
	  <abbr title="Phone">P:</abbr> <c:out value="${site.phoneNumber}"></c:out>
	</address>
	
	<%@ include file="/jsp/includes/messages.jsp" %>

	<c:set var="hasSoupKitchen" value="false" />
	<c:set var="hasShelter" value="false" />
	<c:set var="hasFoodBank" value="false" />
	<c:set var="hasFoodPantry" value="false" />

	<c:forEach items="${availableServices}" var="service">
	  <c:if test="${service.serviceCategory eq 'FOODBANK'}">
	    <c:set var="hasFoodBank" value="true" />
	  </c:if>
	  <c:if test="${service.serviceCategory eq 'FOODPANTRY'}">
	    <c:set var="hasFoodPantry" value="true" />
	  </c:if>
	  <c:if test="${service.serviceCategory eq 'SHELTER'}">
	    <c:set var="hasShelter" value="true" />
	  </c:if>
	  <c:if test="${service.serviceCategory eq 'SOUPKITCHEN'}">
	    <c:set var="hasSoupKitchen" value="true" />
	  </c:if>
	</c:forEach>
	
	<div class="list-group">
		<a href="/SiteServlet" class="list-group-item" role="button"><span class="glyphicon glyphicon-link" aria-hidden="true"></span> Manage Services</a><br/>
		<c:if test="${hasShelter}">
			<h3 class="bg-primary" style="padding:5px 25px;">Shelter</h3>
			<a href="/WaitListServlet" class="list-group-item" role="button"><span class="glyphicon glyphicon-link" aria-hidden="true"></span> Manage Family Room Waiting List</a><br/>
			<a href="/ShelterServlet?init=true" class="list-group-item" role="button"><span class="glyphicon glyphicon-link" aria-hidden="true"></span> Manage Shelter's Bunks / Family Rooms</a><br/>
		</c:if>
		<c:if test="${hasSoupKitchen}">
			<h3 class="bg-primary" style="padding:5px 25px;">Soup Kitchen</h3>
			<a href="/SoupKitchenServlet?init=true" class="list-group-item" role="button"><span class="glyphicon glyphicon-link" aria-hidden="true"></span> Manage Soup Kitchen</a><br/>
		</c:if>
		<c:if test="${hasFoodBank}">
			<h3 class="bg-primary" style="padding:5px 25px;">Food Bank</h3>
			<a href="/ItemServlet?init=true" class="list-group-item" role="button"><span class="glyphicon glyphicon-link" aria-hidden="true"></span> Add Donations</a><br/>
			<a href="/ViewItemsServlet?action=viewMySite" class="list-group-item" role="button"><span class="glyphicon glyphicon-link" aria-hidden="true"></span> View Food Bank Items</a><br/>
			<a href="/RequestsServlet?init=true" class="list-group-item" role="button"><span class="glyphicon glyphicon-link" aria-hidden="true"></span> View Food Bank Requests</a><br/>
		</c:if>

<%-- 		<c:if test="${hasFoodPantry || hasShelter || hasSoupKitchen}"> --%>
<!-- 			<a href="/MakeFoodBankRequestServlet?init=true" class="list-group-item" role="button"><span class="glyphicon glyphicon-link" aria-hidden="true"></span> Request Food/Supply Items</a><br/> -->
<%-- 		</c:if> --%>

		<h3 class="bg-primary" style="padding:5px 25px;">Miscellaneous</h3>
		<a href="/ViewItemsServlet?init=true" class="list-group-item" role="button"><span class="glyphicon glyphicon-link" aria-hidden="true"></span> Search All Food Banks</a><br/>
<%-- 		<c:if test="${hasFoodPantry || hasShelter || hasSoupKitchen}"> --%>
<!-- 			<a href="/MakeFoodBankRequestServlet?init=true" class="list-group-item" role="button"><span class="glyphicon glyphicon-link" aria-hidden="true"></span> Request Food/Supply Items</a><br/> -->
<%-- 		</c:if> --%>
		<a href="/webreports/WebReportServlet" class="list-group-item" role="button"><span class="glyphicon glyphicon-link" aria-hidden="true"></span> View Web Reports</a><br />
		<a href="/bulkUpload.jsp" class="list-group-item" role="button"><span class="glyphicon glyphicon-link" aria-hidden="true"></span> Bulk Uploads</a><br/>
	</div>	
	<hr />
	<br/>
	<%@ include file="/jsp/includes/footer.jsp" %>
</div>
</body>
</html>