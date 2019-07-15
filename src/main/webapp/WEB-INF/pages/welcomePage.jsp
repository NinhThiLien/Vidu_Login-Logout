<%@ page language="java" contentType="text/html; charset=ISO-8859-1" isELIgnored="false"
    pageEncoding="ISO-8859-1"%>
<%@page session="false"%>
<html>
<head>
<title>${title}</title>
</head>
<body>
	<jsp:include page="_menu.jsp" />
  <h1>Message : ${message}</h1>
</body>
</html>