<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.LinkedHashMap"%>
<%@ page import="pdprof.db.connections.Employee"%>
<%
int threads = Integer.parseInt((String) request.getAttribute("threads"));
int timeout = Integer.parseInt((String) request.getAttribute("timeout"));
String action = (String) request.getAttribute("action");
String uri = request.getRequestURI().substring(0, request.getRequestURI().lastIndexOf("/"));
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Populate access script</title>
</head>
<body>
	<form action="script" method="get">
		<table>
			<tr>
				<td align="right">Threads:</td>
				<td align="left"><input type="text" name="threads" size="10"></input></td>
			</tr>
			<tr>
				<td align="right">Timeout(ms):</td>
				<td align="left"><input type="text" name="timeout" size="10"></input></td>
			</tr>
		</table>
		<input type="submit" value="populate">
	</form>
	<hr>
</br>
Command with following options</br>
Access Thread count: <%=threads %></br>
Connection Keep time(ms): <%=timeout %></br>
<pre>
seq 1 <%=threads%> | xargs -P <%=threads%> --replace time curl -o response.{}.txt http://<%=request.getServerName()%>:<%=request.getServerPort()%><%=uri%>/db?timeout=<%=timeout%>
</pre>

	</br>
</body>
</html>