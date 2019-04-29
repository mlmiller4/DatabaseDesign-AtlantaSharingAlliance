<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/includes/htmlhead.jsp">
    <jsp:param name="pageTitle" value="ASACS Add Service Page"/>
</jsp:include>
<script type="text/javascript">
function validate(){
	var isValid = true;
	if(!isValid){
		document.getElementById("errorDiv").innerText = "";
	}
	
	return isValid;
}

function chooseCategory(){
	$(".extraInfo").hide();
	var choosenCategory = $('#selectedService').find(":selected").val();
	var extraInfoDivName = choosenCategory.toLowerCase() + "Div";
	if(choosenCategory == "FOODBANK"){
		$("#clientservicesDiv").hide();
	} else {
		$("#clientservicesDiv").show();
	}
	$("#" + extraInfoDivName).show();
}
</script>
</head>
<body>
<div class="container">
	<%@ include file="/jsp/includes/header.jsp" %>
	
	<h1>Add Service</h1>
	<%@ include file="/jsp/includes/messages.jsp" %>	
	<form action="/ServiceServlet" onsubmit="return validate();" method="POST" class="dark-matter">
	  <div class="form-group">
		<label for="selectedService">Service Category:</label> 
		<select id="selectedService" name="selectedService" onchange="chooseCategory()" class="form-control">
			<option value="CANCELED"></option>
			<c:forEach items="${vacantServices}" var="category">
				<option value ="<c:out value="${category}"></c:out>"><c:out value="${category.label}"></c:out></option>
			</c:forEach>
		</select>
	</div>
	<div style="display: none;" id="clientservicesDiv" class="extraInfo">
		<div class="form-group">
			<label for="description">Description</label>
			<textarea rows="5" cols="50" id="description" name="description" class="form-control" placeholder="Enter service description."></textarea>
		</div>
		<div class="form-group">
			<label for="hoursofoperation">Hours of Operation</label>
			<textarea rows="5" cols="50" id="hoursofoperation" name="hoursofoperation" class="form-control" placeholder="Enter hours of operation."></textarea>
		</div>
		<div class="form-group">			
			<label for="conditionsofuse">Conditions of Use</label>
			<textarea rows="5" cols="50" id="conditionsofuse" name="conditionsofuse" class="form-control" placeholder="Enter conditions of use."></textarea>
		</div>
	</div>
	<br/>
	
	<div style="display: none;" id="foodbankDiv" class="extraInfo">
	</div>
	<div style="display: none;" id="foodpantryDiv" class="extraInfo">
	</div>
	<div style="display: none;" id="shelterDiv" class="extraInfo">
		<div class="form-group">
			<label for="familyRoomCount">Family Room Count</label>
			<input type="text" id="familyRoomCount" name="familyRoomCount" maxlength="4" value="0" class="form-control"/>
		</div>
		<div class="form-group">
			<label for="maleBunkCount">Male Bunk Count</label>
			<input type="text" id="maleBunkCount" name="maleBunkCount" maxlength="4" value="0" class="form-control"/>
		</div>
		<div class="form-group">
			<label for="femaleBunkCount">Female Bunk Count</label>
			<input type="text" id="femaleBunkCount" name="femaleBunkCount" maxlength="4" value="0" class="form-control"/>
		</div>
		<div class="form-group">
			<label for="mixedBunkCount">Mixed Bunk Count</label>
			<input type="text" id="mixedBunkCount" name="mixedBunkCount" maxlength="4" value="0" class="form-control"/>
		</div>
	</div>
	<div style="display: none;" id="soupkitchenDiv" class="extraInfo">
		<div class="form-group">
			<label for="availableSeats">Available Seats</label>
			<input type="text" id="availableSeats" name="availableSeats" maxlength="4" value="0" class="form-control"/>
		</div>
	</div>
	<input type="submit" class="btn btn-default" value="Add" name="action"/>
	</form>
	<%@ include file="/jsp/includes/footer.jsp" %>
</div>
</body>
</html>