<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<jsp:include page="/jsp/includes/htmlhead.jsp">
	    <jsp:param name="pageTitle" value="ASACS Web Reports"/>
	</jsp:include>
</head>
<body>
<div class="container">
	<h1>Web Reports</h1>
<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">Select a Report</h3>
  </div>
  <div class="panel-body">
	<div class="list-group">
		<a href="/webreports/WebReportServlet?reportType=BUNKS" class="list-group-item" role="button"><span class="glyphicon glyphicon-globe" aria-hidden="true"></span> Available Bunks/Rooms Report</a><br/>
		<a href="/webreports/WebReportServlet?reportType=MEALS" class="list-group-item" role="button"><span class="glyphicon glyphicon-globe" aria-hidden="true"></span> Available Meals Report</a><br>
		<a href="/webreports/WebReportServlet?reportType=OUTS" class="list-group-item" role="button"><span class="glyphicon glyphicon-globe" aria-hidden="true"></span> Outstanding Requests Report</a>
	 </div>
  </div>
</div>
</div>
</body>
</html>