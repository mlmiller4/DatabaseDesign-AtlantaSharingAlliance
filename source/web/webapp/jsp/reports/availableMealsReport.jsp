<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<jsp:include page="/jsp/includes/htmlhead.jsp">
	    <jsp:param name="pageTitle" value="ASACS Remaining Meals"/>
	</jsp:include>
	
	<script type="text/javascript">
	$(document).ready(function() {
	    var table = $('#bunksTable').DataTable( {
	        "ajax": '/asacs-api/webreports/mealsReport'
	    } );
	    setInterval( function () {
	        table.ajax.reload();
	    }, 3000 );
	} );
	</script>
	
	<style>
	.count {
		font-size: 50px;
	}
	</style>
</head>
<body>
<div class="container">
	<h1 class="bg-primary" style="padding: 20px;">Remaining Meals</h1>
	<a href="/webreports/WebReportServlet" class="btn btn-default btn-xs" role="button"><span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span> Web Reports</a><br />
	<br/>
	<div class="row">
		<div class="col-md-6 text-center">
			<div class="panel panel-default">
				<div class="panel-heading text-center">
					<h3 class="panel-title">ASACS Available Meal Count</h3>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-md-12 count text-center">
							<span id="TotalMealsCount"><c:out value="${totalMealsCount}"></c:out></span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-6">
			<div class="panel panel-default">
				<div class="panel-heading text-center">
					<h3 class="panel-title">Items in Need</h3>
				</div>
				<div class="panel-body">
					<c:forEach items="${ neededItems }" var="neededItem">
						<h3><c:out value="${neededItem}"></c:out></h3><br/>
					</c:forEach>
				</div>
			</div>		
		</div>
	</div>
	<h3 class="bg-primary" style="padding:5px 25px;"> Meal Counts By Site</h3>
	<div style="margin-left: auto; margin-right: auto; max-width: 800px; padding-top: 25px;">
		<table id="bunksTable" class="display" cellspacing="0" width="100%">
	        <thead>
	            <tr>
	            	<th>Site Name</th>
	                <th>Vegetables</th>
	                <th>Nuts/Grains/Beans</th>
	                <th>Meat/Seafood</th>
	                <th>Dairy/Eggs</th>
	                <th>Remaining Meals</th>
	            </tr>
	        </thead>
	        <tfoot>
	            <tr>
	            	<th>Site Name</th>
	                <th>Vegetables</th>
	                <th>Nuts/Grains/Beans</th>
	                <th>Meat/Seafood</th>
	                <th>Dairy/Eggs</th>
	                <th>Remaining Meals</th>
	           </tr>
	        </tfoot>
	    </table>
	</div>
</div>
</body>
</html>