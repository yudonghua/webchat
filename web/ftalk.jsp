<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="javax.servlet.http.*,javax.servlet.*,websocket.Data" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<% 
String suser = request.getSession().getAttribute("username").toString();
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!<%=suser%></h1>
    </body>
</html>
