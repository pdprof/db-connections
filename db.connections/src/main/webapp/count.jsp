<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
String valueStr = request.getParameter("value");
int value = 0;
if (valueStr != null) {
	try { value = Integer.parseInt(valueStr); } catch (Exception e) {}
}
String countStr = (String)session.getAttribute("count");
int count = 0;
if (countStr != null) {
	try { count = Integer.parseInt(countStr); } catch (Exception e) {}
}
count = count + value;
session.setAttribute("count", "" + count);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Count up/down</title>
</head>
<body>
Count was added by <%= value %> </br>
Count is now <%= count %> </br>
<hr>
<form action="count.jsp" method="get">
<select name="value" size="5">
<option value="-2">-2</option>
<option value="-1">-1</option>
<option value="0">0</option>
<option value="1" selected>1</option>
<option value="2">2</option>
</select>
<input type="submit" value="add">
</form>
<hr>
<pre>
<%= session.toString() %>
</pre>
</body>
</html>