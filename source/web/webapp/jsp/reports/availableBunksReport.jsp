<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<style>
	.odd{ background-color: #f9f9f9 !important; }
	.even{ background-color: #f9f9f9 !important; }
	.sorting_1{ background-color: #f1f1f1 !important; }
	</style>
	
	<jsp:include page="/jsp/includes/htmlhead.jsp">
	    <jsp:param name="pageTitle" value="ASACS Available Bunks"/>
	</jsp:include>
	
	<script type="text/javascript">
	var sheltersInfo;
	$.ajax({
	     data: {},
	     type: 'GET',
	     url: '/asacs-api/webreports/shelters',
	     success: function(data, textStatus, xhr) {
	    	 sheltersInfo = $.parseJSON(xhr.responseText);
	     },
	     error: function(xhr, textStatus, errorThrown) {
	         alert(textStatus);
	     },
	     complete: function(xhr, textStatus){
	     }
	});		
	
	$(document).ready(function() {
	    var table = $('#bunksTable').DataTable( {
	        ajax: '/asacs-api/webreports/shelterReport',
	        language: {
	            "emptyTable":"Sorry, all shelters are currently at maximum capacity."
	        },
	        rowGroup: {
	        	startRender: function ( rows, group ) {
	        		var shelterInfo = getShelterInfo(group);
	        		if(shelterInfo == null)
	        			return $('<tr/>')
	                	.append( '<td colspan="6">' + 
	                	'<div class="row">' + 
	                    '<div class="col-xs-8">'+group+'</div> <small>Loading detail ...</small>'  +
	                	'</td>' )
	                	
	                	

	                return $('<tr/>')
	                	.append( '<td colspan="6">' + 
	                	'<div class="row">' + 
	                    '<div class="col-xs-12">' +
	                    '<strong>' + group + '</strong> <small>[' +  shelterInfo["site"]["phoneNumber"] + ']</small><br>' + 
						'<em>' + shelterInfo["site"]["streetAddress"] +' | ' +
						shelterInfo["site"]["city"] + ', ' + shelterInfo["site"]["state"] + '</em><br>' + 
	                	'</div>'  +
	                	'</div>'  +
	                	'<div class="row">' +
	                    '<div class="col-xs-12">' + shelterInfo["conditionUse"] + '</div>'  +
	                	'</div>' +
	                	
	                	'<div class="row">' +
	                    '<div class="col-xs-12">' + shelterInfo["hoursOperation"] + '</div>'  +
	                	'</div>' +	                	
	                	
	                    '</td>' )
	            },	        	
	            dataSrc: 0
	        }
	    } );
	    setInterval( function () {
	    	table.ajax.reload();
	    }, 3000 );
	} );
	
	function getShelterInfo(siteName){
		if(sheltersInfo == undefined){
			return null;
		}
		return sheltersInfo[siteName];
	}
	</script>
</head>
<body>
<div class="container">
<h1 class="bg-primary" style="padding: 20px;">Available Bunks/Rooms</h1>
<a href="/webreports/WebReportServlet" class="btn btn-default btn-xs" role="button"><span class="glyphicon glyphicon-arrow-left" aria-hidden="true"></span> Web Reports</a><br />

<div style="margin-left: auto; margin-right: auto; max-width: 800px; padding-top: 25px;">
	<table id="bunksTable" class="display cell-border" cellspacing="0" width="100%">
        <thead>
            <tr>
            	<th>Site Name</th>
                <th>Family Rooms</th>
                <th>Male Bunks</th>
                <th>Female Bunks</th>
                <th>Mixed Bunks</th>
                <th>Total Bunks</th>
            </tr>
        </thead>
        <tfoot>
            <tr>
            	<th>Site Name</th>
                <th>Family Rooms</th>
                <th>Male Bunks</th>
                <th>Female Bunks</th>
                <th>Mixed Bunks</th>
                <th>Total Bunks</th>
            </tr>
        </tfoot>
    </table>
</div>
</div>
</body>
</html>