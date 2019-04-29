<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/includes/htmlhead.jsp">
    <jsp:param name="pageTitle" value="ASACS Soup Kitchen"/>
</jsp:include>
	<style>
		.count {
			font-size: 50px;
		}
	</style>
	<script type="text/javascript">
		function updateCount(countType, direction){
            var curValue = parseInt($("#" + countType).text());
            if(direction == "dec"){
                curValue -= 1;
            } else {
                curValue += 1;
            }

            var data = {curValue};

            // POST to server using $.post or $.ajax
            $.ajax({
                data: data,
                type: 'POST',
                url: '/SoupKitchenServlet?action=update',
                success: function(data, textStatus, xhr) {
                    $("#" + countType).text(curValue);
                },
                error: function(xhr, textStatus, errorThrown) {
                    alert(textStatus);
                },
                complete: function(xhr, textStatus){
                    $( ".loading").hide();
                }
            });
        }
	</script>
</head>
<body>
<div class="container">
	<%@ include file="/jsp/includes/header.jsp"%>

	<h1>Manage Soup Kitchen Seats</h1>
	<%@ include file="/jsp/includes/messages.jsp"%>

	<div class="row">
		<div class="col-md-3">
			<div class="panel panel-default">
				<div class="panel-heading text-center">
					<h3 class="panel-title">Available Seats</h3>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-md-12 count text-center">
							<span id="availableSeats"><c:out value="${availableSeats}"></c:out></span>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<a href="javascript:updateCount('availableSeats','dec');" class="btn btn-default btn-lg"><span
									class="glyphicon glyphicon-minus" aria-hidden="true"></span></a>
						</div>
						<div class="col-md-6">
							<a href="javascript:updateCount('availableSeats','inc');" class="btn btn-default btn-lg"><span
									class="glyphicon glyphicon-plus" aria-hidden="true"></span></a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/jsp/includes/footer.jsp"%>
</div>
</body>
</html>