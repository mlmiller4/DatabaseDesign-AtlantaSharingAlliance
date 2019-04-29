<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/jsp/includes/htmlhead.jsp">
	<jsp:param name="pageTitle" value="ASACS View Food Bank Items"/>
</jsp:include>
<style>
	.count {
	font-size: 50px;
	}
	.ui-widget.ui-widget-content {
		border: 10px solid #c5c5c5;
	}
	.ui-widget-header {
		border: 1px solid #dddddd;
		background: #337ab7;
		color: #ffffff;
		font-weight: bold;
	}	
</style>
<script type="text/javascript">

function addCriteria(){

	var lastTerm = $(".searchcriteria").children(".row").length;
	var newId = lastTerm + 1;
	
	if(newId > 1){
    	var connectTerm = "<select id='sel-connect-" + lastTerm + "' name='sel-connect-" + lastTerm + "' class='form-control'>";
    	connectTerm += "<option value='AND'>AND</option>";
    	connectTerm += "<option value='OR'>OR</option>";
    	connectTerm += "</select>";
    	$("#criteria-" + lastTerm + " > .form-group > #sc-connect").html(connectTerm);
	}
	
	criteriaSelect = "" + 
	"<div class='row' id='criteria-" + newId + "'>" +
	"	<div class='form-group'>" +
	"		<div class='col-sm-3' id='sc-column'>" +
	"			<select id='sel-criteria-" + newId + "' name='sel-criteria-" + newId + "' onchange='showCriteria(this, "  + newId + ")' class='form-control'>" +
	"				<option value='0'>	</option>" +
	"				<option value='1'>Site</option>" +
	"				<option value='2'>Expiration date</option>" +
	"				<option value='3'>Storage type</option>" +
	"				<option value='4'>Main category</option>" +
	"				<option value='5'>Sub category</option>" +
	"				<option value='6'>name/description</option>" +
	"			</select>" +
	"		</div>" +
	"		<div class='col-sm-3' id='sc-selector'></div>" +
	"		<div class='col-sm-3' id='sc-value'></div>" +
	"		<div class='col-sm-3' id='sc-connect'></div>" +
	"	</div>" + 
	"</div>";
	
	$(".searchcriteria").append(criteriaSelect);
    $( ".loading").hide();
}
function clearCriteria(){
	var lastTerm = $(".searchcriteria").empty();
	
	if ( $.fn.dataTable.isDataTable( 'table' ) ) {
	    table = $('table').DataTable();
		table.clear().draw();
	}	
    $("table tbody").empty();
    
    table = $('table').DataTable( {
        bFilter: false,
        retrieve: true
    } );
    
    $( ".loading").hide();
}

function showCriteria(chosenCriteria, newId){
	var selected = parseInt($("#" + chosenCriteria.id).find(":selected").val());
	switch(selected) {
    case 1://site
    	sitesSelect = "<select id='sel-value-" + newId + "' name='sel-value-" + newId + "' class='form-control'>";
    	var sites = getSites();
    	for(i = 0; i < sites.length; i++){
    		sitesSelect += "<option value='" + sites[i].siteId + "'>" + sites[i].siteName + "</option>";
    	};
    	sitesSelect += "</select>";
    	getColumn( $("#" + chosenCriteria.id), "#sc-selector" ).empty();    	
    	getColumn( $("#" + chosenCriteria.id), "#sc-value" ).html(sitesSelect);

        break;
    case 2://expirationdt
    	selectorSelect = "<select id='sel-selector-" + newId + "' name='sel-selector-" + newId + "' class='form-control'>";
    	selectorSelect += "<option value='BEFORE'>Before</option>";
    	selectorSelect += "<option value='AFTER'>After</option>";
    	selectorSelect += "</select>";
    	getColumn( $("#" + chosenCriteria.id), "#sc-selector" ).html(selectorSelect);
    	getColumn( $("#" + chosenCriteria.id), "#sc-value" ).html("<input type='text' id='sel-value-" + newId + "' name='sel-value-" + newId + "' class='form-control'></input>");
		$('#sel-value-' + newId).datepicker({dateFormat: "yy-mm-dd"});
        break;
    case 3://storagetype
    	selectBox = "<select id='sel-value-" + newId + "' name='sel-value-" + newId + "' class='form-control'>";
    	var options = getStorageTypes();
    	for(i = 0; i < options.length; i++){
    		selectBox += "<option value='" + options[i].storageTypeName + "'>" + options[i].storageTypeValue + "</option>";
    	};
    	selectBox += "</select>";
    	getColumn( $("#" + chosenCriteria.id), "#sc-selector" ).empty();
    	getColumn( $("#" + chosenCriteria.id), "#sc-value" ).html(selectBox);

        break;
    case 4://main category
    	selectBox = "<select id='sel-value-" + newId + "' name='sel-value-" + newId + "' class='form-control'>";
    	var options = getCategories();
    	for(i = 0; i < options.length; i++){
    		selectBox += "<option value='" + options[i].categoryName + "'>" + options[i].categoryValue + "</option>";
    	};
    	selectBox += "</select>";
    	getColumn( $("#" + chosenCriteria.id), "#sc-selector" ).empty();
    	getColumn( $("#" + chosenCriteria.id), "#sc-value" ).html(selectBox);

        break;
    case 5://sub category
    	selectBox = "<select id='sel-value-" + newId + "' name='sel-value-" + newId + "' class='form-control'>";
    	var options = getFoodCategories();
    	for(i = 0; i < options.length; i++){
    		selectBox += "<option value='" + options[i].categoryName + "'>" + options[i].categoryValue + "</option>";
    	};
    	
    	options = getSupplyCategories();
    	for(i = 0; i < options.length; i++){
    		selectBox += "<option value='" + options[i].categoryName + "'>" + options[i].categoryValue + "</option>";
    	};
    	selectBox += "</select>";
    	getColumn( $("#" + chosenCriteria.id), "#sc-selector" ).empty();
    	getColumn( $("#" + chosenCriteria.id), "#sc-value" ).html(selectBox);

        break;
    case 6://name
    	selectorSelect = "<select id='sel-selector-" + newId + "' name='sel-selector-" + newId + "' class='form-control'>";
    	selectorSelect += "<option value='MATCH'>Equals</option>";
    	selectorSelect += "<option value='LIKE'>Like</option>";
    	selectorSelect += "</select>";
    	getColumn( $("#" + chosenCriteria.id), "#sc-selector" ).html(selectorSelect);
    	getColumn( $("#" + chosenCriteria.id), "#sc-value" ).html("<input type='text' id='sel-value-" + newId + "' name='sel-value-" + newId + "' class='form-control'></input>");

        break;        
	}
}

function getSites(){
	var sites = [];
	<c:forEach items="${sites}" var="site">
	sites.push({siteName:'<c:out value="${site.shortName}"></c:out>', siteId: <c:out value="${site.id}"></c:out>});
	</c:forEach>	
	
	return sites;
}

function getCategories(){
	var categories = [];
	<c:forEach items="${categories}" var="category">
	categories.push({categoryName:'<c:out value="${category}"></c:out>', categoryValue: '<c:out value="${category.value}"></c:out>'});
	</c:forEach>	
	
	return categories;
}

function getFoodCategories(){
	var categories = [];
	<c:forEach items="${foodcategories}" var="category">
	categories.push({categoryName:'<c:out value="${category.name}"></c:out>', categoryValue: '<c:out value="${category.value}"></c:out>'});
	</c:forEach>	
	
	return categories;
}

function getSupplyCategories(){
	var categories = [];
	<c:forEach items="${supplycategories}" var="category">
	categories.push({categoryName:'<c:out value="${category.name}"></c:out>', categoryValue: '<c:out value="${category.value}"></c:out>'});
	</c:forEach>	
	
	return categories;
}

function getStorageTypes(){
	var storageTypes = [];
	<c:forEach items="${storageTypes}" var="storageType">
	storageTypes.push({storageTypeName:'<c:out value="${storageType.name}"></c:out>', storageTypeValue: '<c:out value="${storageType.value}"></c:out>'});
	</c:forEach>	
	
	return storageTypes;
}

function getColumn(elem, columnName){
	return elem.parents(".row").children(".form-group").children(columnName);
}


function search(){
	var formData = $( "#searchItems" ).serialize();
	$("#messagesarea").empty();

    $.ajax({
        data: formData,
        type: 'POST',
        url: '/ViewItemsServlet',
        success: function(data, textStatus, xhr) {
            var json = $.parseJSON(xhr.responseText);
            updateView(json);
        },
        error: function(xhr, textStatus, errorThrown) {
			$("#messagesarea").html("<p class='alert alert-danger' role='alert'><span class='glyphicon glyphicon-exclamation-sign' aria-hidden='true' style='color:red'></span> Error Searching : " + errorThrown + "<br /></p>");

        },
        complete: function(xhr, textStatus){
            $( ".loading").hide();
        }
    });	
}

var dialog, dialog2, form;
  
function requestItem(itemId, itemName, itemQty){
	$("#request-itemId").val(itemId);
	$("#request-itemName").html(itemName);
	$("#request-itemCount").html(itemQty);
	$("#quantity").val('');
	dialog.dialog( "open" );
}

function updateItem(itemId, itemName, itemQty){
	$("#updqty-itemId").val(itemId);
	$("#updqty-itemName").html(itemName);
	$("#updqty-itemCount").html(itemQty);
	$("#updqty-quantity").val('');
	dialog2.dialog( "open" );
}

function deleteItem(itemId){
    $( ".loading").show();
	var formData = { 'action':'removeItem', 'itemId':itemId }

	  $.ajax({
	        data: formData,
	        type: 'POST',
	        url: '/ItemServlet',
	        success: function(data, textStatus, xhr) {
	            if(xhr.responseText == "Success"){
	            	search();
					$("#messagesarea").html("<p class='alert alert-success' role='alert'><span class='glyphicon glyphicon-ok' aria-hidden='true' style='color:green'></span> Successfully deleted Item# " + itemId + "<br /></p>");
	            } else {
					$("#messagesarea").html("<p class='alert alert-danger' role='alert'><span class='glyphicon glyphicon-exclamation-sign' aria-hidden='true' style='color:red'></span> Error deleting Item# "+ itemId +" : " + xhr.responseText + "<br /></p>");
	            }
	        },
	        error: function(xhr, textStatus, errorThrown) {
				$("#messagesarea").html("<p class='alert alert-danger' role='alert'><span class='glyphicon glyphicon-exclamation-sign' aria-hidden='true' style='color:red'></span> Error deleting Item# "+ itemId +" : " + errorThrown + "<br /></p>");

	        },
	        complete: function(xhr, textStatus){
	            $( ".loading").hide();
	        }
	    });	
	
}

function updateView(items){
	if ( $.fn.dataTable.isDataTable( 'table' ) ) {
	    table = $('table').DataTable();
		table.clear().draw();
		table.destroy();
	}	
    $("table tbody").empty();

	for(i=0; i<items.length; i++){
		var category = items[i]["category"];
		var row = "<tr>";
		row += "<td>" + items[i]["site"]["shortName"] + "</td>";
		row += "<td>" + items[i]["name"] + "</td>"
		row += "<td>" + items[i]["category"] + "</td>"
		row += "<td>" + items[i]["storageType"] + "</td>"
		
		var expireFlag = "";
		var parts =items[i]["expireDate"].split('-');
		var expireDate = new Date(parts[0],parts[1]-1,parts[2]); 
		var today = new Date();
		if(expireDate < today){
			expireFlag = " style='color: red;'";
		}	
		row += "<td" + expireFlag + ">" + items[i]["expireDate"] + "</td>"

		if(items[i]["site"]["id"] != <c:out value="${authenticatedUsersSite.id}"></c:out>){
			row += "<td>" + items[i]["numberUnits"] + " <a href='javascript:requestItem(" + items[i]["itemId"] + ", \""  + items[i]["name"] + "\", " + items[i]["numberUnits"] + ")' class='btn btn-default btn-xs' role='button' data-toggle='tooltip' title='Request From Food Bank' " + expireFlag + "><span class='glyphicon glyphicon-new-window' aria-hidden='true'></span></a></td>";
		} else {
			row += "<td>" + items[i]["numberUnits"] + "<br/><a href='javascript:deleteItem(" + items[i]["itemId"] + ")' class='btn btn-default btn-xs' role='button' data-toggle='tooltip' title='Delete From Food Bank'><span class='glyphicon glyphicon-remove' aria-hidden='true'></span></a><a href='javascript:updateItem(" + items[i]["itemId"] + ", \""  + items[i]["name"] + "\", " + items[i]["numberUnits"] + ")' class='btn btn-default btn-xs' role='button' data-toggle='tooltip' title='Change Quantity'><span class='glyphicon glyphicon-pencil' aria-hidden='true'></span></a></td>";
		}
	  	

		if(category == "FOOD"){
			row += "<td>" + items[i]["foodCategory"] + "</td>";
		} else {
			row += "<td>" + items[i]["supplyCategory"] + "</td>";
		}
		row += "</tr>";
		
        $("table tbody").append(row);
	}
	
    table = $('table').DataTable( {
        bFilter: false,
        retrieve: true
    } );
    
    $('[data-toggle="tooltip"]').tooltip(); 
}

$( function() {
    
    function makeRequest(){
    	var itemId = parseInt($("#request-itemId").val());
    	var available = parseInt($("#request-itemCount").html());
    	var itemName = $("#request-itemName").html();
		var requested =  parseInt($("#quantity").val());
		if(requested < 1 || requested > available){
    		alert("Invalid Quantity. [ 1 -  " + available + "]");
		} else {
			var formData = { 'action':'Submit Request', 'itemId':itemId, 'numberUnits':requested }
			  $.ajax({
			        data: formData,
			        type: 'POST',
			        url: '/MakeFoodBankRequestServlet',
			        success: function(data, textStatus, xhr) {
			            if(xhr.responseText == "Success"){
							$("#messagesarea").html("<p class='alert alert-success' role='alert'><span class='glyphicon glyphicon-ok' aria-hidden='true' style='color:green'></span> " + itemName + " request added for quantity = " +requested+ "<br /></p>");
			            }
			        },
			        error: function(xhr, textStatus, errorThrown) {
						$("#messagesarea").html("<p class='alert alert-danger' role='alert'><span class='glyphicon glyphicon-exclamation-sign' aria-hidden='true' style='color:red'></span> Error Searching : " + errorThrown + "<br /></p>");

			        },
			        complete: function(xhr, textStatus){
			            $( ".loading").hide();
			        }
			    });				
			
	        dialog.dialog( "close" );
		}
    }
    
    function updateItemQty(){
        $( ".loading").show();
        var itemId = $("#updqty-itemId").val();
        var qty =  $("#updqty-quantity").val();
    	var formData = { 'action':'updateQty', 'itemId':itemId, 'newQty':qty }

    	  $.ajax({
    	        data: formData,
    	        type: 'POST',
    	        url: '/ItemServlet',
    	        success: function(data, textStatus, xhr) {
    	            if(xhr.responseText == "Success"){
    	            	search();
    					$("#messagesarea").html("<p class='alert alert-success' role='alert'><span class='glyphicon glyphicon-ok' aria-hidden='true' style='color:green'></span> Successfully update Item# " + itemId + " to quantity = " + qty + "<br /></p>");
    	            } else {
    					$("#messagesarea").html("<p class='alert alert-danger' role='alert'><span class='glyphicon glyphicon-exclamation-sign' aria-hidden='true' style='color:red'></span> Error updating Item# "+ itemId +" : " + xhr.responseText + "<br /></p>");
    	            }
    	        },
    	        error: function(xhr, textStatus, errorThrown) {
    				$("#messagesarea").html("<p class='alert alert-danger' role='alert'><span class='glyphicon glyphicon-exclamation-sign' aria-hidden='true' style='color:red'></span> Error updating Item# "+ itemId +" : " + errorThrown + "<br /></p>");

    	        },
    	        complete: function(xhr, textStatus){
		            search();
    	            $( ".loading").hide();
    	        }
    	    });	
    	
        dialog2.dialog( "close" );
    }
    
    dialog = $( "#dialog-form" ).dialog({
        autoOpen: false,
        height: 250,
        width: 250,
        modal: true,
        buttons: {
          "Request": makeRequest,
          Cancel: function() {
            dialog.dialog( "close" );
          }
        },
        close: function() {
        }
      }); 
    
    dialog2 = $( "#dialog-quantity" ).dialog({
        autoOpen: false,
        height: 250,
        width: 250,
        modal: true,
        buttons: {
          "Update": updateItemQty,
          Cancel: function() {
            dialog.dialog( "close" );
          }
        },
        close: function() {
        }
      }); 
});
</script>
</head>
<body>
<div class="container">
	<%@ include file="/jsp/includes/header.jsp" %>
	
	<h1>View Food Bank Inventory</h1>
	
	<%@ include file="/jsp/includes/messages.jsp" %>
	Viewing from <c:out value="${authenticatedUsersSite.shortName}"></c:out>
	<c:if test="${searchEnabled}">
	<div class="panel panel-default">
	  <div class="panel-heading">
	    <h3 class="panel-title">Search Criteria</h3>
	  </div> 
	  <div class="panel-body">
	  	<a href="javascript:addCriteria();" class="btn btn-default" role="button"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Add Criteria</a>
	  
	  	<form id="searchItems">
	  	<input type="hidden" value="search" name="action"/>
		<div class='row' style="background-color: #337ab7; color: white; margin: 10px 0 10px 0;">
			<div class='col-sm-3'>Search By</div>
			<div class='col-sm-3'></div>
			<div class='col-sm-3'>Value</div>
			<div class='col-sm-3'></div>
		</div>
	  	<div class="searchcriteria" style="padding:5px 0 20px 0">			
		</div>
		</form>
		<a href="javascript:search();" class="btn btn-default btn-md" role="button"><span class="glyphicon glyphicon-search" aria-hidden="true"></span> Search</a>
		<a href="javascript:clearCriteria();" class="btn btn-default btn-md" role="button"><span class="glyphicon glyphicon-refresh" aria-hidden="true"></span> Reset</a>
	  </div>
	</div>
	</c:if>
	<table class="table table-striped table-hover">
		<thead>
			<tr>
				<td>Site</td>				
				<td>Name</td>			
				<td>Category</td>
				<td>Storage Type</td>
				<td>Expiration Date</td>
				<td>Number of Units</td>
				<td>Sub-Category</td>			
			</tr>
		</thead>
		<tbody>
        <c:forEach items="${items}" var="item">
	        <tr>
	            <td><c:out value="${item.site.shortName}"></c:out></td>
	            <td><c:out value="${item.name}"></c:out></td>
	            <td><c:out value="${item.category.value}"></c:out></td>
	            <td><c:out value="${item.storageType.value}"></c:out></td>
	            <td><fmt:formatDate type="date" value="${item.expireDate}" /></td>
	            <td><c:out value="${item.numberUnits}"></c:out> 
	            <a href='javascript:deleteItem(<c:out value="${item.itemId}"></c:out>)' class='btn btn-default btn-xs' role='button' data-toggle='tooltip' title='Delete From Food Bank'><span class='glyphicon glyphicon-remove' aria-hidden='true'></span></a>
	            <a href='#?<c:out value="${item.itemId}"></c:out>' class='btn btn-default btn-xs' role='button' data-toggle='tooltip' title='Change Quantity'><span class='glyphicon glyphicon-pencil' aria-hidden='true'></span></a>
	            </td>
                <c:choose>
                <c:when test="${item.category == 'FOOD'}">
                    <td><c:out value="${item.foodCategory.value}"></c:out></td>
                </c:when>
                <c:otherwise>
                    <td><c:out value="${item.supplyCategory.value}"></c:out></td>
                </c:otherwise>
                </c:choose>
	        </tr>
		</c:forEach>
		</tbody>
	</table>	
	
	<%@ include file="/jsp/includes/footer.jsp" %>
<div id="dialog-form" title="Make Food Bank Request"> 
	<h4><span id="request-itemName"></span></h4>
	<p class="validateTips">Available Quantity:<span id="request-itemCount"></span></p>
	<form>
	  <fieldset>
	    <label for="name">Requested Quantity</label>
	    <input type="text" name="quantity" id="quantity" value="" class="text ui-widget-content ui-corner-all">
	    <input type="hidden" name="request-itemId" id="request-itemId">
	    <input type="submit" style="position:absolute; top:-1000px">
	  </fieldset>
	</form>
</div>	
<div id="dialog-quantity" title="Update Food Bank Quantity"> 
	<h4><span id="updqty-itemName"></span></h4>
	<p class="validateTips">Current Quantity:<span id="updqty-itemCount"></span></p>
	<form>
	  <fieldset>
	    <label for="name">New Quantity</label>
	    <input type="text" name="updqty-quantity" id="updqty-quantity" value="" class="text ui-widget-content ui-corner-all">
	    <input type="hidden" name="updqty-itemId" id="updqty-itemId">
	    <input type="submit" style="position:absolute; top:-1000px">
	  </fieldset>
	</form>
</div>	
</div>
</body>
</html>