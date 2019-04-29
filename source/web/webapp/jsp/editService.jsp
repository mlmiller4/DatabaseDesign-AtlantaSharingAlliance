<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/includes/htmlhead.jsp">
    <jsp:param name="pageTitle" value="ASACS Edit Service Page"/>
</jsp:include>

<script type="text/javascript">
function validate(){
	var isValid = true;
	if(!isValid){
		document.getElementById("errorDiv").innerText = "";
	}
	
	return isValid;
}
</script>
</head>
<body>
<div class="container">
	<%@ include file="/jsp/includes/header.jsp" %>
	<h1>Modify Service</h1>
	<h2><c:out value="${service.serviceCategory.label}"></c:out></h2>
	<%@ include file="/jsp/includes/messages.jsp" %>	

	<form action="/ServiceServlet" onsubmit="return validate();" method="POST">
		<div id="clientservicesDiv" class="extraInfo">
			<div class="form-group"><label for="description">Description</label><textarea rows="5" cols="50" id="description" name="description" class="form-control"><c:out value="${service.description}"></c:out></textarea></div>
			<div class="form-group"><label for="hoursofoperation">Hours of Operation</label><textarea rows="5" cols="50" id="hoursofoperation" name="hoursofoperation" class="form-control"><c:out value="${service.hoursOperation}"></c:out></textarea></div>
			<div class="form-group"><label for="conditionsofuse">Conditions of Use</label><textarea rows="5" cols="50" id="conditionsofuse" name="conditionsofuse" class="form-control"><c:out value="${service.conditionUse}"></c:out></textarea></div>
		</div>
		<br/>
		<c:if test="${service.serviceCategory == 'SHELTER' }">
		<div id="shelterDiv" class="extraInfo">
			<div class="form-group"><label for="familyRoomCount">Family Room Count</label><input type="text" id="familyRoomCount" name="familyRoomCount" maxlength="4" value="<c:out value="${service.familyRoomCount}"></c:out>" class="form-control"/></div>
			<div class="form-group"><label for="maleBunkCount">Male Bunk Count</label><input type="text" id="maleBunkCount" name="maleBunkCount" maxlength="4" value="<c:out value="${service.maleBunkCount}"></c:out>" class="form-control"/></div>
			<div class="form-group"><label for="femaleBunkCount">Female Bunk Count</label><input type="text" id="femaleBunkCount" name="femaleBunkCount" maxlength="4" value="<c:out value="${service.femaleBunkCount}"></c:out>" class="form-control"/></div>
			<div class="form-group"><label for="mixedBunkCount">Mixed Bunk Count</label><input type="text" id="mixedBunkCount" name="mixedBunkCount" maxlength="4" value="<c:out value="${service.mixedBunkCount}"></c:out>" class="form-control"/></div>
		</div>
		</c:if>
		<c:if test="${service.serviceCategory == 'SOUPKITCHEN' }">
		<div id="soupkitchenDiv" class="extraInfo">
			<div class="form-group"><label for="availableSeats">Available Seats</label><input type="text" id="availableSeats" name="availableSeats" maxlength="4" value="<c:out value="${service.availableSeats}"></c:out>" class="form-control"/></div>
		</div>
		</c:if>
		<input type="hidden" name="serviceId" value='<c:out value="${service.serviceId}"></c:out>'/>
		<input type="hidden" name="serviceCategory" value="<c:out value="${service.serviceCategory}"></c:out>"/>
		<input type="hidden" name="action" value="edit"/>
		<input type="submit" value="Update" class="btn btn-default"/>
	</form>
	<%@ include file="/jsp/includes/footer.jsp" %>
</div>
</body>
</html>