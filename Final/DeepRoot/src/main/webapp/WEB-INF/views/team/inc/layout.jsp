<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="se" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
	<title>뿌리깊은마크</title>

	<!-- Latest compiled Bootstrap Common CSS -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script> -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
    <!-- Bootstrap Common CSS END -->
    
    <!-- jQuery Confirm START -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.css">
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.js"></script>
	<!-- jQuery Confirm END -->

	<!-- Common Script JavaScript & CSS START -->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
    <!-- Common Script JavaScript & CSS END -->
    
    <!-- Group Page CSS START -->
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/team/team.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/mainpage/header.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/mainpage/footer.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/mainpage/responsive.css" rel="stylesheet">
    <!-- Group Page CSS START -->
    
    <!--Script Start -->
    <script src="${pageContext.request.contextPath}/js/jstree.min.js"></script>
    <!-- Script END -->
    
    <!-- jQuery Ajax Form START -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.min.js" integrity="sha384-FzT3vTVGXqf7wRfy8k4BiyzvbNfeYjK+frTVqZeNDFl8woCbF0CYG6g2fMEFFo/i" crossorigin="anonymous"></script>
	<!-- jQuery Ajax Form START -->
</head>
<body>

	<div id="main-header">
		<tiles:insertAttribute name="header" />
	</div>
	
	<div id="main">
		<tiles:insertAttribute name="content" />
	</div>
	
	<div id="main-footer">
		<tiles:insertAttribute name="footer" />
	</div>

	<!-- Custom Script START -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/team/team.js"></script>
    <!-- Custom Script END -->

</body>
</html>