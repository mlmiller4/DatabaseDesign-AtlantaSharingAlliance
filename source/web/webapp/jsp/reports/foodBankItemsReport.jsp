<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<jsp:include page="/jsp/includes/htmlhead.jsp">
	    <jsp:param name="pageTitle" value="ASACS Food Bank Items Report"/>
	</jsp:include>
	
	<script type="text/javascript">
	$(document).ready(function() {
	    var table = $('#itemsTable').DataTable( {
	        ajax: '/asacs-api/foodbank/itemsReport'
	    } );
	    setInterval( function () {
	        table.ajax.reload();
	    }, 30000 );
	} );
	</script>
</head>
<body>
<div class="container">
	<%@ include file="/jsp/includes/header.jsp" %>
	<h1>Food Bank Items</h1>
	<%@ include file="/jsp/includes/messages.jsp" %>	

	<div style="margin-left: auto; margin-right: auto; max-width: 800px; padding-top: 25px;">
		<table id="itemsTable" class="display" cellspacing="0" width="100%">
	        <thead>
	            <tr>
	            	<th>Id</th>
	                <th>Name</th>
	                <th>Category</th>
	                <th>Sub-Category</th>
	                <th>Storage Type</th>
	                <th>Expiration</th>
	                <th>Units</th>
	            </tr>
	        </thead>
	        <tfoot>
	            <tr>
	            	<th>Id</th>
	                <th>Name</th>
	                <th>Category</th>
	                <th>Sub-Category</th>
	                <th>Storage Type</th>
	                <th>Expiration</th>
	                <th>Units</th>
	           </tr>
	        </tfoot>
	    </table>
	</div>
	<%@ include file="/jsp/includes/footer.jsp" %>	
</div>
</body>
</html>