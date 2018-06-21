<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="se" uri="http://www.springframework.org/security/tags" %>

<script type="text/javascript">
	//그룹리스트 비동기로 가져오기
	$(function(){
		$.ajax({
			url: "${pageContext.request.contextPath}/team/getTeamList.do",
		    type: "post",
		    success : function(data){
		    	var html = '<ul role="menu" class="sub-menu">';
		    	console.log(data.teamlist);
		    	var index = 0;
		    	for(var key in data.teamlist){
		    		console.log(data.teamlist[key]);
		    		html += '<li><a href="#">' + data.teamlist[key].gname + '</a></li>';
		    		index += 1;
		    	}
		    	if(index < 10){
		    		html += '<li onclick="headerAddGroup()"><a href="#"><i class="fa fa-plus-circle" style="color: red;"></i>  그룹 추가</a></li>';
		    	}
		    	html += '</ul>';
		    	$("#groupDropdown").append(html);
		    }
		})
	});
	
	function headerAddGroup(gid) {
		$.confirm({
		    title: '그룹 추가',
		    content: '' +
		    '<form id="addGroupForm" action="${pageContext.request.contextPath}/user/addGroup.do" class="formName" method="post">' +
		    '<div class="form-group">' +
		    '<label>그룹명</label>' +
		    '<input type="text" name="gname" placeholder="그룹명" class="name form-control" required />' +
		    '</div>' +
		    '</form>',
		    closeIcon: true,
		    closeIconClass: 'fa fa-close',
		    
		    buttons: {
		        formSubmit: {
		            text: '추가',
		            btnClass: 'btn-blue',
		            action: function () {
		                var name = this.$content.find('.name').val();
		                if(!name){
		                    $.alert('그룹명을 적어주세요');
		                    return false;
		                }
		                $("#addGroupForm").submit();
		                
		            }
		        },
		                    취소: function () {
		            //close
		        },
		    }

		});
	}
</script>

<!-- Header START-->
<header id="header" class="header">
    <div class="navbar navbar-inverse" role="banner">
        <div class="container">
            <div class="navbar-brand">
                <a class="logo-text" href="<%= request.getContextPath() %>/index.do">뿌리깊은마크</a>
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
            </div>
			
            <div class="collapse navbar-collapse">
            	<se:authorize access="isAuthenticated()">
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a href="<%= request.getContextPath() %>/user/mybookmark.do">MyBookmark</a>
                    </li>
                    <li id="groupDropdown" class="dropdown">
                        <a href="#">Group <i class="fa fa-angle-down"></i></a>
                    </li>
                    <li>
                    <!-- Social Link  -->
                        <a href="<%= request.getContextPath() %>/social/social.do">Social</a>
                    <!-- Social Link  -->
                    </li>
                    <li class="dropdown">
                        <a href="#">Notice <i class="fa fa-angle-down"></i></a>
                        <ul role="menu" class="sub-menu">
                            <li>희준이와 아이들에서 초대
                                <input type="checkbox">
                            </li>
                        </ul>
                    </li>
                    <!-- Notice Alarm START -->
                   <li class="dropdown">
                       <a href="#">
                       <img src="<%= request.getContextPath() %>/icon/alert.png" class="notice-alarm" style="margin-top: -3px;"></a>
                       <ul role="menu" class="sub-menu">
                           <li>토요일 12pm~6pm 서버 점검 예정</li>
                       </ul>
                   </li>
                   <!-- Notice Alarm END -->
                   <!-- USER INFO START -->
                   <li>
                       <a href="#">
                       <img class="dropdown header-ico" 
                       		src="<%= request.getContextPath() %>/images/profile/${sessionScope.info_userprofile}"
                       		onerror="<%= request.getContextPath() %>/images/profile.png"> ${sessionScope.info_usernname}</a>
                       <ul role="menu" class="sub-menu">
                           <li><a href="<%= request.getContextPath() %>/myInfo.do">회원정보수정</a></li>
                           <li><a href='<%= request.getContextPath() %>/security/logout'>Logout</a></li>
                       </ul>
                   </li>
                   <!-- USER INFO END -->
                </ul>
                </se:authorize>
            </div>
        </div>
    </div>
</header>
<!-- Header END-->