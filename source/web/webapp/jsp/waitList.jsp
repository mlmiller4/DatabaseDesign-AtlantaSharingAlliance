<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/includes/htmlhead.jsp">
    <jsp:param name="pageTitle" value="ASACS Manage Family Room Waiting List Page"/>
</jsp:include>
<style>
  #sortable { list-style-type: none; margin: 0 auto; padding: 0; width: 75%; }
  #sortable li { margin: 0 3px 3px 3px; padding: 0.4em; padding-left: 1.5em; font-size: 1.4em; height: auto; /*border: 3px solid #aaaaaa; background: #c59353; color: #ffffff;*/}
  #sortable li span { position: absolute; margin-left: -1.3em;  vertical-align: middle;}
  .delete {  cursor:pointer; float: right; padding: 5px; display: inline; font-family: "Arial"; border: 1px solid #888888; margin: 0; font-size: 12px; line-height: 15px; background-color: white; color: black; /*border-radius: 2px; background: #888888; color: white; */width: auto; height: auto; box-sizing: content-box;}
</style>
<script>
$( function() {
  $( "#sortable" ).sortable({
	  cursor: 'move',
	  axis: 'y',
	  start: function (event, ui) {
	    	ui.item.attr('previndex', ui.item.index());
	  },
	  update: function (event, ui) {
	    	var sorted = $(this).sortable( "serialize", { key: "sort" } );
	    	$(this).sortable('disable');
	    	$(".loading").show();
	        //var order = $(this).sortable('serialize');
            var from = ui.item.attr("previndex") + 1;
            var to = ui.item.index() + 1;
            var entry = ui.item.attr("clientid");
    	    var clientName = ui.item.attr("clientname");

            var data = {entry, from, to, sorted};
            ui.item.removeAttr("previndex");
			$("#messagesarea").html("");

	        // POST to server using $.post or $.ajax
	        $.ajax({
	            data: data,
	            type: 'POST',
	            url: '/WaitListServlet?action=reorder',
	            success: function(data, textStatus, xhr) {
	            	if(xhr.responseText == "SUCCESS"){
		    			$("#messagesarea").html("<p class='alert alert-success' role='alert'><span class='glyphicon glyphicon-ok' aria-hidden='true' style='color:green'></span> Moved " + clientName  + " to position " + to + " on the waiting list.<br /></p>");
	            	} else {
		    			$("#messagesarea").html("<p class='alert alert-danger' role='alert'><span class='glyphicon glyphicon-exclamation-sign' aria-hidden='true' style='color:red'></span> Error moving " + clientName + "<br /></p>");
	            	}	            	
	            }, 
	            error: function(xhr, textStatus, errorThrown) {
	            	$( "#sortable" ).sortable('cancel');
	    			$("#messagesarea").html("<p class='alert alert-danger' role='alert'><span class='glyphicon glyphicon-exclamation-sign' aria-hidden='true' style='color:red'></span> Could not move " + clientName + ": " + errorThrown + "<br /></p>");
	            },
	            complete: function(xhr, textStatus){
	            	$( "#sortable" ).sortable('enable');
	    	    	$(".loading").hide();
	            }
	        });
	  }
	});
  $(".btn").off("click");
  $("#sortable .delete").click(function () {
	    var item = $(this).parent();
	    var clientId = item.attr("clientid");
	    var clientName = item.attr("clientname");
	    $( "#dlgName" ).html( clientName );
	    $( function() {
		    $( "#dialog-confirm" ).dialog({
			    resizable: false,
			    height: "auto",
			    width: 400,
			    modal: true,
			    buttons: {
			    	"Remove": function() {
			          	$( this ).dialog( "close" );
				    	$(".loading").show();
			        	$( "#sortable" ).sortable('disable');
			            var data = {clientId};
		    			$("#messagesarea").html("");

				        $.ajax({
				            data: data,
				            type: 'POST',
				            url: '/WaitListServlet?action=remove',
				            success: function(data, textStatus, xhr) {
				            	if(xhr.responseText == "SUCCESS"){
					        		item.remove();
					    			$("#messagesarea").html("<p class='alert alert-success' role='alert'><span class='glyphicon glyphicon-ok' aria-hidden='true' style='color:green'></span> Removed " + clientName  + "<br /></p>");
				            	} else {
					    			$("#messagesarea").html("<p class='alert alert-danger' role='alert'><span class='glyphicon glyphicon-exclamation-sign' aria-hidden='true' style='color:red'></span> Error removing " + clientName + "<br /></p>");
				            	}
				            }, 
				            error: function(xhr, textStatus, errorThrown) {
				            	$( "#sortable" ).sortable('cancel');
				    			$("#messagesarea").html("<p class='alert alert-danger' role='alert'><span class='glyphicon glyphicon-exclamation-sign' aria-hidden='true' style='color:red'></span> Could not remove " + clientName + ": " + errorThrown + "<br /></p>");
				            }, 
				            complete: function(xhr, textStatus){
				            	$( "#sortable" ).sortable('enable');
				    	    	$(".loading").hide();
				            }
				        });
			        },
			        Cancel: function() {
			          	$( this ).dialog( "close" );
			        }
			    }
		    });
		  } );
	});
  
  $( "#sortable" ).disableSelection();
} );
</script>
</head>
<body>
<div class="container">
	<%@ include file="/jsp/includes/header.jsp" %>
	<h1>Manage Family Room Waiting List</h1>
	<%@ include file="/jsp/includes/messages.jsp" %>
	<div class="panel panel-default">
		<div class="panel-heading">
		  <h3 class="panel-title">Waiting List</h3>
		</div>
		<div class="panel-body">
			<a href="/WaitListServlet?action=add&init=true" class="btn btn-default btn-sm" role="button"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Add Client</a>
			<!-- <a href="/WaitListServlet?action=add&init=true" class="btn btn-default btn-sm" role="button"><span class="glyphicon glyphicon-move" aria-hidden="true"></span> Move</a>  -->
			<br/><br/>
			<ul id="sortable">
				<c:forEach items="${waitList}" var="listentry">
				  <li class="ui-state-default" clientname="<c:out value="${listentry.client.name}"></c:out>" clientid="<c:out value="${listentry.client.identifier}"></c:out>" id="client_<c:out value="${listentry.position}"></c:out>">
				  	<span class="ui-icon ui-icon-arrowthick-2-n-s"></span>
				  	<c:out value="${listentry.client.name}"></c:out>
				  	<button type="button" class="btn btn-default btn-lg delete">
				  		<span class="glyphicon glyphicon-remove" aria-hidden="true" style="position: relative; margin:auto 0;"></span>
				  	</button>
				  </li>
				</c:forEach>
			</ul>
			<div id="dialog-confirm" title="Remove from waitlist" style="display: none;">
			  <p><span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;"></span>You are about to remove <b><span id="dlgName"></span></b> from the waitlist. Are you sure?</p>
			</div>
		</div>
	</div>
	<%@ include file="/jsp/includes/footer.jsp" %>	
</div>
</body>
</html>