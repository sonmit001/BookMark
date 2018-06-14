<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>개인 Top5</h3>
	<c:forEach items="${u_top5}" var="u">
		<ul>
			<li>${u.url}</li>
			<li>${u.urlname}</li>
			<li>${u.ucount}</li>
		</ul>
	</c:forEach>
	
	<h3>그룹 Top5</h3>
	<c:forEach items="${g_top5}" var="g">
		<ul>
			<li>${g.url}</li>
			<li>${g.urlname}</li>
			<li>${g.ucount}</li>
		</ul>
	</c:forEach>
	
	<h3>전체 Top5</h3>
	<c:forEach items="${a_top5}" var="a">
		<ul>
			<li>${a.url}</li>
			<li>${a.urlname}</li>
			<li>${a.ucount}</li>
		</ul>
	</c:forEach>
</body>
</html>