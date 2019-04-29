<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/includes/htmlhead.jsp">
	<jsp:param name="pageTitle" value="ASACS Shelter" />
</jsp:include>
<style>
.count {
	font-size: 50px;
}
</style>
<script type="text/javascript">
function updateCount(countType, direction){	
    var data = {countType, direction};

    $.ajax({
        data: data,
        type: 'POST',
        url: '/ShelterServlet?action=update',
        success: function(data, textStatus, xhr) {
            var json = $.parseJSON(xhr.responseText);
            updateView(json);
        },
        error: function(xhr, textStatus, errorThrown) {
			$("#messagesarea").html("<p class='alert alert-danger' role='alert'><span class='glyphicon glyphicon-exclamation-sign' aria-hidden='true' style='color:red'></span> Error updating[" + direction + "] " + countType + ": " + errorThrown + "<br /></p>");

        },
        complete: function(xhr, textStatus){
            $( ".loading").hide();
        }
    });
}

function updateView(counts){
    $("#MaleBunkCount").text(counts["MaleBunks"]);
    $("#FemaleBunkCount").text(counts["FemaleBunks"]);
    $("#MixedBunkCount").text(counts["MixedBunks"]);
    $("#totalBunkCount").text(counts["TotalBunks"]);
    $("#FamilyRoomCount").text(counts["FamilyRooms"]);
}

setInterval(function(){
    var data = {};

    $.ajax({
        data: data,
        type: 'POST',
        url: '/ShelterServlet?action=getCounts',
        success: function(data, textStatus, xhr) {
            var json = $.parseJSON(xhr.responseText);
            updateView(json);
        },
        error: function(xhr, textStatus, errorThrown) {
            console.error(textStatus);
        },
        complete: function(xhr, textStatus){
        	console.debug("Completed updating counts.");
        }
    });
}, 10000);
</script>
</head>
<body>
	<div class="container">
		<%@ include file="/jsp/includes/header.jsp"%>

		<h1>Manage Bunks / Family Rooms</h1>
		<%@ include file="/jsp/includes/messages.jsp"%>

		<div class="row">
			<div class="col-md-3">
				<div class="panel panel-default">
					<div class="panel-heading text-center">
						<h3 class="panel-title">Male Bunks</h3>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-md-12 count text-center">
								<span id="MaleBunkCount"><c:out value="${bunkcounts[0]}"></c:out></span>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<a href="javascript:updateCount('MaleBunkCount','dec');" class="btn btn-default btn-lg"><span
									class="glyphicon glyphicon-minus" aria-hidden="true"></span></a>
							</div>
							<div class="col-md-6">
								<a href="javascript:updateCount('MaleBunkCount','inc');" class="btn btn-default btn-lg"><span
									class="glyphicon glyphicon-plus" aria-hidden="true"></span></a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-3">
				<div class="panel panel-default">
					<div class="panel-heading text-center">
						<h3 class="panel-title">Female Bunks</h3>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-md-12 count text-center">
								<span id="FemaleBunkCount"><c:out value="${bunkcounts[1]}"></c:out></span>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<a href="javascript:updateCount('FemaleBunkCount','dec');" class="btn btn-default btn-lg"><span
									class="glyphicon glyphicon-minus" aria-hidden="true"></span></a>
							</div>
							<div class="col-md-6">
								<a href="javascript:updateCount('FemaleBunkCount','inc');" class="btn btn-default btn-lg"><span
									class="glyphicon glyphicon-plus" aria-hidden="true"></span></a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-3">
				<div class="panel panel-default">
					<div class="panel-heading text-center">
						<h3 class="panel-title">Mixed Bunks</h3>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-md-12 count text-center">
								<span id="MixedBunkCount"><c:out value="${bunkcounts[2]}"></c:out></span>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<a href="javascript:updateCount('MixedBunkCount','dec');" class="btn btn-default btn-lg"><span
									class="glyphicon glyphicon-minus" aria-hidden="true"></span></a>
							</div>
							<div class="col-md-6">
								<a href="javascript:updateCount('MixedBunkCount','inc');" class="btn btn-default btn-lg"><span
									class="glyphicon glyphicon-plus" aria-hidden="true"></span></a>
							</div>
						</div>
					</div>
				</div>				
			</div>
			<div class="col-md-3">
				<div class="panel panel-default">
					<div class="panel-heading text-center">
						<h3 class="panel-title">Total Bunks</h3>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-md-12 count text-center">
								<span id="totalBunkCount"><c:out value="${bunkcounts[3]}"></c:out></span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-3">
				<div class="panel panel-default">
					<div class="panel-heading text-center">
						<h3 class="panel-title">Family Rooms</h3>
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-md-12 count text-center">
								<span id="FamilyRoomCount"><c:out value="${familyroomcount}"></c:out></span>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<a href="javascript:updateCount('FamilyRoomCount','dec');" class="btn btn-default btn-lg"><span
										class="glyphicon glyphicon-minus" aria-hidden="true"></span></a>
							</div>
							<div class="col-md-6">
								<a href="javascript:updateCount('FamilyRoomCount','inc');" class="btn btn-default btn-lg"><span
										class="glyphicon glyphicon-plus" aria-hidden="true"></span></a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-9">
			</div>
		</div>
		<%@ include file="/jsp/includes/footer.jsp"%>
	</div>
</body>
</html>