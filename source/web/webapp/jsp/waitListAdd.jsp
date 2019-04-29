<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/includes/htmlhead.jsp">
    <jsp:param name="pageTitle" value="ASACS Family Room Waiting List - Add Client Page"/>
</jsp:include>
<style>
.row-striped:nth-of-type(odd){
  background-color: #efefef;
}

.row-striped:nth-of-type(even){
  background-color: #ffffff;
}
</style>
<script>
$( function() {
    $("#searchTerm").keyup(function(){   	 
        var filter = $(this).val(), count = 0;
 
        // Loop through the comment list
        $("div.searchable").each(function(){
 
            // If the list item does not contain the text phrase fade it out
            if ($(this).text().search(new RegExp(filter, "i")) < 0) {
                $(this).fadeOut();
 
            // Show the list item if the phrase matches and increase the count by 1
            } else {
                $(this).show();
                count++;
            }
        });
    });	
} );
</script>
</head>
<body>
<div class="container">
	<%@ include file="/jsp/includes/header.jsp" %>
	<h1>Add Client to Family Room Waiting List</h1>
	<%@ include file="/jsp/includes/messages.jsp" %>
	<div class="form-group">
		<label for="searchTerm">Search:</label>
		<input type="text" id="searchTerm" name="searchTerm" class="form-control" autofocus/>
	</div>
	
	<div class="container-fluid" style="width: 100%; padding: 0px">
	 	<div class="row" style="background-color: #f0f0f0;">
		  	<div class="col-md-4">Name</div>
		  	<div class="col-md-4">Identifier</div>
		  	<div class="col-md-4">Phone</div>
		  	<div class="col-md-4"></div>
	    </div>	
		<c:forEach items="${availableClients}" var="client">
		<div class="row row-striped searchable" style="padding:5px 0;">
		  	<div class="col-md-1"><a href="/WaitListServlet?action=add&clientIdentifier=<c:out value="${client.identifier}"></c:out>" class="btn btn-default btn-xs" role="button"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Add</a></div>
		  	<div class="col-md-4"><a href="/WaitListServlet?action=add&clientIdentifier=<c:out value="${client.identifier}"></c:out>"><c:out value="${client.name}"></c:out></a></div>
		  	<div class="col-md-4"><c:out value="${client.identifier}"></c:out></div>
		  	<div class="col-md-3"><c:out value="${client.phone}"></c:out></div>
		  	<!-- <div class="col-md-6"><c:out value="${client.description}"></c:out></div>  -->
		</div>
		</c:forEach>
	</div>
	<%@ include file="/jsp/includes/footer.jsp" %>	
</div>
</body>
</html>