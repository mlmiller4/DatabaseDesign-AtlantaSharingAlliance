<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<jsp:include page="/jsp/includes/htmlhead.jsp">
	    <jsp:param name="pageTitle" value="ASACS Donations Page"/>
	</jsp:include>
	
	<script type="text/javascript">
	function chooseCategory(){
		var selectedStorageType = $('#category').find(":selected").val();
		if(selectedStorageType == "Food"){
			$("#foodCategoryDiv").show();
			$("#supplyCategoryDiv").hide();
		}
		if(selectedStorageType == "Supply"){
			$("#foodCategoryDiv").hide();
			$("#supplyCategoryDiv").show();
		}		
	}
	
	$( function() {
	    $( "#datePicker" ).datepicker({
	        altField: "#expireDate",
	        altFormat: "yy-mm-dd",
	        dateFormat: "MM dd yy",
	        changeYear: true,
	        appendText: "(yyyy-mm-dd)"
	    });
	} );
	
	</script>
</head>
<body>
<div class="container">
	<%@ include file="/jsp/includes/header.jsp" %>
	
	<h1>Donated Items</h1>
	
	<%@ include file="/jsp/includes/messages.jsp" %>
	<form action="/ItemServlet" onsubmit="return validate();" method="POST">
		<div class="form-group">
			<label for="donateTo">Donate to Food Bank:</label> 
			<select id="donateTo" name="donateTo" class="form-control">
				<c:forEach items="${sites}" var="site">
					<option value ="<c:out value="${site.id}"></c:out>"><c:out value="${site.shortName}"></c:out></option>
				</c:forEach>
			</select>
		</div>
		<div class="form-group">
			<label for="category">Category:</label> 
			<select id="category" name="category" onchange="chooseCategory()" class="form-control">
				<c:forEach items="${categories}" var="category">
					<option value ="<c:out value="${category.value}"></c:out>"><c:out value="${category.value}"></c:out></option>
				</c:forEach>
			</select>
		</div>
		<div class="form-group" id="foodCategoryDiv">
			<label for="foodItemType">Food Category:</label> 
			<select id="foodItemType" name="foodItemType" class="form-control">
				<c:forEach items="${foodItemTypes}" var="foodItemType">
					<option value ="<c:out value="${foodItemType.name}"></c:out>"><c:out value="${foodItemType.value}"></c:out></option>
				</c:forEach>
			</select>
		</div>		
		<div class="form-group" id="supplyCategoryDiv" style="display: none">
			<label for="supplyItemType">Supply Category:</label> 
			<select id="supplyItemType" name="supplyItemType" class="form-control">
				<c:forEach items="${supplyItemTypes}" var="supplyItemType">
					<option value ="<c:out value="${supplyItemType.name}"></c:out>"><c:out value="${supplyItemType.value}"></c:out></option>
				</c:forEach>
			</select>
		</div>	
		<div class="form-group">
			<label for="storageType">Storage Type:</label> 
			<select id="storageType" name="storageType" class="form-control">
				<c:forEach items="${storageTypes}" var="storageType">
					<option value ="<c:out value="${storageType.name}"></c:out>"><c:out value="${storageType.value}"></c:out></option>
				</c:forEach>
			</select>
		</div>			
		<div class="form-group">
			<label for="name">Name:</label> 
			<input type="text" id="name" name="name" class="form-control"/>
		</div>
		<div class="form-group">
			<label for="numberUnits">Number of Units:</label> 
			<input type="text" id="numberUnits" name="numberUnits" class="form-control"/>
		</div>	
		<div class="form-group">
			<label for="expireDate">Expiration Date:</label> 
			<input type="text" id="datePicker" name="datePicker" class="form-control" />
			<input type="hidden" id="expireDate" name="expireDate" />
		</div>
		<input type="submit" value="Add Donation" id="action" name="action" class="btn btn-default" class="form-control"/>
	</form>
	<hr />
	<br/>
	<%@ include file="/jsp/includes/footer.jsp" %>
</div>
</body>
</html>