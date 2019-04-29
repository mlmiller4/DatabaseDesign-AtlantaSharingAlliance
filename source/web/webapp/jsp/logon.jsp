<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<jsp:include page="/jsp/includes/htmlhead.jsp">
	    <jsp:param name="pageTitle" value="ASACS Logon Page"/>
	</jsp:include>

	<script type="text/javascript">
	function validate(){
		var username = document.getElementById("uname").value;
		var password = document.getElementById("pwd").value;
		var isValid = notEmpty(username) && notEmpty(password);
		if(!isValid){
			document.getElementById("errorDiv").innerText = "Enter a valid username/password";
		}
		
		return isValid;
	}

	function notEmpty(value){
		return (value != null) && (value.trim().length > 0);
	}
	</script>
	
	<style>
	.panel > .panel-heading {
	    background-image: none;
		background-color: #337ab7;
		/*border: 3px solid #2e6da4;*/
		color: #fff
	}

	color: #fff;
	background-color: #337ab7;
	</style>
</head>
<body>
<div class="container">
	<h1>ASACS Site Management</h1>
	<div style="height:45px">
	<a href="/webreports/WebReportServlet" class="btn btn-default pull-right" role="button"><span class="glyphicon glyphicon-globe" aria-hidden="true"></span> Web Reports</a><br />
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">User Logon</h3>
		</div>
		<div class="panel-body">
			<%@ include file="/jsp/includes/messages.jsp" %>
		
			<form action="/LogonServlet" onsubmit="return validate();" method="POST">
				<div class="form-group"><label for="uname">Username:</label> <input type="text" id="uname" name="uname" maxlength="25" class="form-control" value="emp1"><br/></div>
				<div class="form-group"><label for="pwd">Password:</label> <input type="password" id="pwd" name="pwd" maxlength="50" class="form-control" value="gatech123"><br/></div>
				<input type="submit" class="btn btn-primary" value="Logon" />
			</form>
		</div>
	</div>
</div>
</body>
</html>