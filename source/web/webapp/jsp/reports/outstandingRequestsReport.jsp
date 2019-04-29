<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <jsp:include page="/jsp/includes/htmlhead.jsp">
        <jsp:param name="pageTitle" value="ASACS Outstanding Requests Report"/>
    </jsp:include>

    <style>
        .red{background-color: #ff5f63;}
    </style>

    <script type="text/javascript">
        $(document).ready(function() {
            var table = $('#itemsTable').DataTable( {
                ajax: '/asacs-api/foodbank/outstandingRequestReport'
            } );
            $('tr').click(function () {
                alert("A Click!")
            } );
            $('table').load(function () {
                if (document.getElementsByTagName) {
                    var table = document.getElementById("itemsTable");
                    var rows = table.getElementsByTagName("tr");
                    for (i = 0; i < rows.length; i++) {
                        //manipulate rows
                        alert("Row 1")
                        var columns = rows[i].getElementsByTagName("td")
                        if (columns[10].innerText == "True") {
                            rows[i].className = "red";
                        }
                    }
                }
            });
            setInterval( function () {
                table.ajax.reload();
            }, 3000000 );
        } );



    </script>
</head>
<body>
<div class="container">
    <%@ include file="/jsp/includes/header.jsp" %>
    <h1>Outstanding Requests Report</h1>
    <%@ include file="/jsp/includes/messages.jsp" %>

    <div style="margin-left: -100px; margin-right: auto; max-width: 800px; padding-top: 25px;">
        <table id="itemsTable" class="display" cellspacing="0" width="100%">
            <thead>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>ReqDateTime</th>
                <th>UserId</th>
                <th>Status</th>
                <th>NumRequested</th>
                <th>NumFilled</th>
                <th>Storage Type</th>
                <th>Category</th>
                <th>Sub-Category</th>
                <th>isShortFall</th>
            </tr>
            </thead>

            <tfoot>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>ReqDateTime</th>
                <th>UserId</th>
                <th>Status</th>
                <th>NumRequested</th>
                <th>NumFilled</th>
                <th>Storage Type</th>
                <th>Category</th>
                <th>Sub-Category</th>
                <th>isShortFall</th>
            </tr>
            </tfoot>
        </table>
    </div>
    <%@ include file="/jsp/includes/footer.jsp" %>
</div>
</body>
</html>