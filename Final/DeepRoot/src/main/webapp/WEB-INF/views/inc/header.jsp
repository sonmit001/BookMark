<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="se" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
	// header에 있는 그룹 추가 버튼 클릭 이벤트
	function headerAddGroup(gid) {
		$.confirm({
		    title: '그룹 추가',
		    content: '' +
		    '<form id="addGroupForm" action="${pageContext.request.contextPath}/addGroup.do" class="formName" method="post">' +
		    '<div class="form-group">' +
		    '<label>그룹명</label>' +
		    '<input type="text" name="gname" placeholder="그룹명" class="name form-control" required />' +
		    '</div>' +
		    '</form>',
		    type: 'green',
		    closeIcon: true,
		    buttons: {
		        formSubmit: {
		            text: '추가',
		            btnClass: 'btn-green',
		            action: function () {
		                var name = this.$content.find('.name').val();
		                if(!name){
		                    $.alert('그룹명을 적어주세요');
		                    return false;
		                }
		                $("#addGroupForm").ajaxForm({
		                	success: function(data, statusText, xhr, $form){
		                		var group = '<li><a href="#">' + data.newTeam.gname + '</a></li>';
	                			$("#groupDropdownMenu").children().last().before(group);
		                		if(("#groupDropdownMenu").length > 10){
		                			$("#groupDropdownMenu").children().last().remove();
		                		}
		                	}
		                });
		                $("#addGroupForm").submit();
		                
		            }
		        },
		        '취소': {
		            btnClass: 'btn-red',
		        	action : function () {}
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
					<!-- Group Menu START -->
					<li id="groupDropdown" class="dropdown">
						<a href="#">Group <i class="fa fa-angle-down"></i></a>
						<ul id="groupDropdownMenu" role="menu" class="sub-menu">
						<c:choose>
							<c:when test="${(headerTeamList ne null) && (!empty headerTeamList)}">
								<c:forEach items="${headerTeamList}" var="headerTeam" varStatus="status">
									<li><a href="<%= request.getContextPath() %>/team/main.do?gid=${headerTeam.gid}&gname=${headerTeam.gname}">${headerTeam.gname}</a></li>
									<c:if test="${status.last}">
										<c:if test="${status.count < 10}">
											<li onclick="headerAddGroup()"><a href="#"><i class="fa fa-plus-circle" style="color: red;"></i>&nbsp;&nbsp;그룹 추가</a></li>
										</c:if>
									</c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<li onclick="headerAddGroup()"><a href="#"><i class="fa fa-plus-circle" style="color: red;"></i>&nbsp;&nbsp;그룹 추가</a></li>
							</c:otherwise>
						</c:choose>
						</ul> 
					</li>
					<li>
					<!-- Group Menu END -->
					<!-- Social Link  -->
					<a href="<%= request.getContextPath() %>/social/social.do">Social</a>
					<!-- Social Link  -->
					<!-- Alarm START -->
					</li>
					<li class="dropdown">
						<a href="#">Notice <i class="fa fa-angle-down"></i></a>
						<!-- headerAlarmList -->
						<c:if test="${(headerAlarmList ne null) && (!empty headerAlarmList)}">
						<ul role="menu" class="sub-menu">
							<c:forEach items="${headerAlarmList}" var="alarmList">
								<li data-gid="${alarmList.gid}">
									<span>그룹명: ${alarmList.gname}</span><br>
									<span>From: ${alarmList.fromid}</span><br>
									<span>From: ${alarmList.senddate}</span><br>
									<c:choose>
									<c:when test="${alarmList.ganame == '초대'}">
										<span>해당 그룹에서 회원님을 초대했습니다!</span>
										<i class="fas fa-check g_notice_ok" onclick='inviteOk("${alarmList.gid}")'></i>
										<i class="fas fa-times g_notice_no" onclick='inviteNo("${alarmList.gid}")'></i>
									</c:when>
									<c:when test="${alarmList.ganame == '완료'}">
										<span>해당 그룹이 완료되었습니다!</span>
										<i class="fas fa-check g_notice" onclick=""></i>
									</c:when>
									<c:when test="${alarmList.ganame == '강퇴'}">
										<span>해당 그룹에서 회원님을 강퇴했습니다!</span>
										<i class="fas fa-check g_notice" onclick=""></i>
									</c:when>
									</c:choose>
								</li>
							</c:forEach>
							<script type="text/javascript">console.log("${headerAlarmList}")</script>
						</ul>
						</c:if>
					</li>
					<!-- Alarm START END -->
					<!-- Notice Alarm START -->
					<li id="noticeDropdown" class="dropdown">
						<a href="#">
						<i class="far fa-bell fa-lg notice-alarm" style="margin-top: -3px;"></i>
						<%-- <img src="<%= request.getContextPath() %>/icon/alert.png" class="notice-alarm" style="margin-top: -3px;"></a> --%>
						</a>
						<c:if test="${(headerNoticeList ne null) && (!empty headerNoticeList)}">
							<ul role="menu" class="sub-menu">
							<c:forEach items="${headerNoticeList}" var="headerNotice">
								<li><a href="#">${headerNotice.ncontent}</a></li>
							</c:forEach>
						</ul>
						</c:if>
					</li>
					<!-- Notice Alarm END -->
					<!-- USER INFO START -->
					<li>
						<a class="username" href="#">
							<img class="dropdown header-ico" src="<%= request.getContextPath() %>/images/profile/${sessionScope.info_userprofile}" onerror="this.src='<%= request.getContextPath() %>/images/profile.png'"> 
							${sessionScope.info_usernname}
						</a>
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