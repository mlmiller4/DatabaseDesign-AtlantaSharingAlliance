<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/includes/htmlhead.jsp">
	<jsp:param name="pageTitle" value="ASACS Bulk Upload Page" />
</jsp:include>

<script>
	var full = location.protocol + '//' + location.hostname
			+ (location.port ? ':' + location.port : '');
</script>
</head>

<body>
	<div class="container">
		<%@ include file="/jsp/includes/header.jsp" %>
		<%@ include file="/jsp/includes/messages.jsp" %>
	<div class="panel panel-default">
	  <div class="panel-heading">
	    <h1 class="panel-title">Bulk Upload Users</h1>
	  </div>
	  <div class="panel-body">
		<h4>Header:</h4>
		<samp> EMAIL,FIRSTNAME,LASTNAME,ROLE,SITEID,USERNAME,PASSWORD </samp>
		
		<form action="/asacs-api/bulk/addUsersCsv" method="post" enctype="multipart/form-data">
			<div class="form-group">
				<label for="inputFile">Select a file:</label> <input type="file" id="file" name="file" size="50" class="form-control">
			</div>
			<button type="submit" class="btn btn-primary">Upload It</button>
		</form>
	  </div>
	</div>
	<div class="panel panel-default">
	  <div class="panel-heading">
	    <h1 class="panel-title">Bulk Upload Donations</h1>
	  </div>
	  <div class="panel-body">
		<h4>Header:</h4>
		<samp>SITEID,CATEGORY,SUBCATEGORY,EXPIREDATE,NAME,NUMBERUNITS,STORAGETYPE</samp>
		
		<form action="/asacs-api/bulk/addDonationsCsv" method="post" enctype="multipart/form-data">
			<div class="form-group">
				<label for="inputFile">Select a file:</label> <input type="file" id="file" name="file" size="50" class="form-control">
			</div>
			<button type="submit" class="btn btn-primary">Upload It</button>
		</form>
	  </div>
	</div>	
	<%@ include file="/jsp/includes/footer.jsp" %>	
</body>
</html>