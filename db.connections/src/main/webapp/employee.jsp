<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.LinkedHashMap"%>
<%@ page import="pdprof.db.connections.Employee"%>
<%
String result = (String) request.getAttribute("result");
LinkedHashMap<String, Employee> employee = (LinkedHashMap<String, Employee>) request.getAttribute("employee");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>EMPLOYEE table operation</title>
</head>
<body>
	<form action="db" method="get">
		<button name="action" value="init">Recreate Table</button>
		</br>
		<hr>
		<table>
			<tr>
				<td align="right">ID:</td>
				<td align="left"><input type="text" name="id" size="10"></input></td>
			</tr>
			<tr>
				<td align="right">NAME:</td>
				<td align="left"><input type="text" name="name" size="128"></input></td>
			</tr>
		</table>
		<button name="action" value="insert">Insert new NAME(ignore
			ID)</button>
		<button name="action" value="update">Update name of ID</button>
		<button name="action" value="delete">Delete entry of
			ID(ignore NAME)</button>
	</form>
	<hr>
	<%
	if (result != null) {
	%>
	<%=result%>
	</br>
	<hr>
	<%
	}
	%>
	<table>
		<%
		for (Employee emp : employee.values()) {
		%>
		<tr>
			<td><%=emp.getId()%></td>
			<td><%=emp.getName()%></td>
		</tr>
		<%
		}
		%>
	</table>
	<hr>
	Call JPA Action
	<form action="employee" method="get">
		<table>
			<tr>
				<td align="right">NAME:</td>
				<td align="left"><input type="text" name="name" size="128"></input></td>
			</tr>
		</table>
		<button name="action" value="insert">JPA Insert new NAME</button>

	</form>
	<a href="employee">
   		<button>JPA Get list</button>
	</a>
</body>
</html>