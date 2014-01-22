<%@ page trimDirectiveWhitespaces="true" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<body>
<a href="<%= request.getContextPath() %>/twitter/submit.do">Sign in with Twitter</a>
<a href="<%= request.getContextPath() %>/finish">skip</a>
</body>
</html>
