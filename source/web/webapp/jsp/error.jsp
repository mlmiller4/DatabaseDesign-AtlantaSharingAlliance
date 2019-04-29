<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/includes/htmlhead.jsp">
    <jsp:param name="pageTitle" value="ASACS Error Page"/>
</jsp:include>
</head>
<body>
<div class="container">
	<%@ include file="/jsp/includes/header.jsp" %>
	
	<h1>Error</h1>
	
	<%@ include file="/jsp/includes/messages.jsp" %>	
</div>
</body>
</html>