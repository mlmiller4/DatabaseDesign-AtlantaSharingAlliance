<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<jsp:include page="/jsp/includes/htmlhead.jsp">
	    <jsp:param name="pageTitle" value="ASACS Manage Site Page"/>
	</jsp:include>
</head>
<body>
<div class="container">
	<%@ include file="/jsp/includes/header.jsp" %>
	
	<h1>Site Management</h1>
	<address>
	  <strong><c:out value="${site.shortName}"></c:out></strong><br>
	  <c:out value="${site.streetAddress}"></c:out><br>
	  <c:out value="${site.city}"></c:out>, <c:out value="${site.state}"></c:out> <c:out value="${site.zip}"></c:out><br>
	  <abbr title="Phone">P:</abbr> <c:out value="${site.phoneNumber}"></c:out>
	</address>
	
	<%@ include file="/jsp/includes/messages.jsp" %>
	
	<div class="panel panel-default">
	  <div class="panel-heading">
	    <h3 class="panel-title">Available Services</h3>
	  </div>
	  <div class="panel-body">
		<c:if test="${not empty addSerivcesActive}">
		<a href="/ServiceServlet?action=add&init=true" class="btn btn-default btn-xs" role="button"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Add Service</a>
		</c:if>	
	  </div>
		<table class="table table-striped table-hover">
		<thead>
		<tr>
			<th>Service Type</th>
			<th>Description</th>
			<th>Hours of Operation</th>
			<th>Conditions of Use</th>
			<th></th>
			<c:if test="${not empty removeSerivcesActive}">
			<th></th>
			</c:if>
		</tr>
		</thead>
		<tbody>
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
		<tr>
			<td><c:out value="${service.serviceCategory.label}"></c:out></td>
			<c:choose>
			<c:when test="${service.serviceCategory == 'FOODBANK'}">
			<td colspan="4"></td>
			</c:when>
			<c:otherwise>
			<td><c:out value="${service.description}"></c:out></td>
			<td><c:out value="${service.hoursOperation}"></c:out></td>
			<td><c:out value="${service.conditionUse}"></c:out></td>
			<td><a href="/ServiceServlet?action=edit&init=true&serviceId=<c:out value="${service.serviceId}"></c:out>&serviceCategory=<c:out value="${service.serviceCategory}"></c:out>" class="btn btn-xs" role="button" alt="Edit">
					<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
				</a>
			</td>
			</c:otherwise>
			</c:choose>
			<c:if test="${not empty removeSerivcesActive}">
			<td><a href="/ServiceServlet?action=remove&serviceId=<c:out value="${service.serviceId}"></c:out>&serviceCategory=<c:out value="${service.serviceCategory}"></c:out>" class="btn btn-xs" role="button" alt="Remove">
					<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
				</a>
			</td>
			</c:if>
		</tr>
		</c:forEach>
		</tbody>
		</table>
	</div>
	<%@ include file="/jsp/includes/footer.jsp" %>
</div>
</body>
</html>