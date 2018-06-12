<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
	<title>Home</title>
	
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	
	<script type="text/javascript">
		$(function() {
			$("#check-user-id").on("dblclick", function(){ });
		    $("#check-user-id").on("click", function(){
		    	$.ajax({ 
		    		url:"user/idcheck.do",
		            type:"POST",
		            data:{uid: $("#uid_join").val()},
		            dataType:"json",
		            success:function(data){  
		            	alert(data.result);
		            },
		            error:function(e){  
		                alert(e.responseText);  
		            }  
		        });
		    });
		    
		    $("#check-user-nickname").on("dblclick", function(){ });
		    $("#check-user-nickname").on("click", function(){
		    	$.ajax({ 
		    		url:"user/nnamecheck.do",
		            type:"POST",
		            data:{nname: $("#nname_join").val()},
		            dataType:"json",
		            success:function(data){  
		            	alert(data.result);
		            },
		            error:function(e){
		                alert(e.responseText);  
		            }  
		        });
		    });
		});
	</script>
</head>
<body>
	

	
	
	
</body>
</html>
