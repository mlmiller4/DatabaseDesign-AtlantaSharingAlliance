<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<jsp:include page="/jsp/includes/htmlhead.jsp">
	    <jsp:param name="pageTitle" value="Food Bank Request Page"/>
	</jsp:include>

<!-- <script type="text/javascript">
	var msg = "Succeeded.";
	alert(msg);
</script> --> 
<!-- <script>
function myFunction(){
	confirm("Item Requested!");
}

</script> -->
</head>
<body>
<div class="container">
	<%@ include file="/jsp/includes/header.jsp" %>

	<h3>Request Food/Supply Items</h3>
	
	<%@ include file="/jsp/includes/messages.jsp" %>
	<form action="/MakeFoodBankRequestServlet" onsubmit="return validate();" method="POST">
		<div class="form-group">
			<label for="itemId">Item ID:</label> 
			<input type="text" id="itemId" name="itemId" class="form-control" value=<%out.println(session.getAttribute("itemId")); session.removeAttribute("itemId");%> />
		</div>
		<div class="form-group">
			<label for="numberUnits">Number of Units:</label> 
			<input type="text" id="numberUnits" name="numberUnits" class="form-control" value=<%out.println(session.getAttribute("numUnits")); session.removeAttribute("numUnits");%> />                                                    
		</div>	
	</form>
	<input type="submit" value="Submit Request" id="action" name="action" class="btn btn-default" class="form-control"/>
	<a href="/ViewItemsServlet?action=viewAll" class="btn btn-default" role="button">Return</a>                                             
	

	<hr />
	<br/>
	<%@ include file="/jsp/includes/footer.jsp" %>
</div>
</body>
</html>