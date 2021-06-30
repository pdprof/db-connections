<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.LinkedHashMap"%>
<%@ page import="pdprof.db.connections.Employee"%>
<% 
String result = (String)request.getAttribute("result");
LinkedHashMap<String, Employee> employee = (LinkedHashMap<String, Employee>)request.getAttribute("employee");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>EMPLOYEE table operation</title>
</head>
<body>
<% if(result != null)  { %>
	<%= result%> </br>
	<hr>
<% } %>
<table>
<% for(Employee emp: employee.values()) { %>
	<tr>
		<td> <%= emp.getId() %> </td>
		<td> <%= emp.getName() %> </td>
	</tr>
<% } %>
</table>
</body>
</html>