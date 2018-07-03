<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="se" uri="http://www.springframework.org/security/tags" %>

<script>
	/* Chatting Start */
	var stompClient = null;
	var gid = '<c:out value="${gid}"/>';
	var nname = '<c:out value="${nname}"/>';
	var profile = '<c:out value="${profile}"/>';
	var chatList = new Array(); // 전체 카테고리 리스트 비동기로 받아오기
	var enable = '<c:out value="${enabled}"/>';
	var uid = '<c:out value="${uid}"/>';
	var position = 0;
	<c:forEach items="${filecontentlist}" var="filecontent">
		chatList.push("${filecontent}");
	</c:forEach>
	var grid = '<c:out value="${grid}"/>';
	var gname ='<c:out value="${gname}"/>';

</script>

	<!-- 전체 Body Div START -->
    <div class="container-fluid team-container">
        <div id="main-row" class="row">
            <div class="col-sm-12 whole-body">
			    <!-- body TOP div START -->
			    <section class="my">
			        <div class="container-fluid top">
			            <div class="row">
			                <div class="col-sm-12 top-content">
			                    <div class="col-sm-7 teamname">
			                    	<i class="fas fa-thumbtack teamname-ico"></i>${gname}
			                    </div>
			                    <div class="col-sm-5 option">
			                        <div class="zoom">
									    <a class="zoom-fab zoom-btn-large" id="zoomBtn"><i class="fa fa-bars"></i></a>
									    <ul class="zoom-menu">
									      	<li><a class="zoom-fab zoom-btn-sm zoom-btn-person scale-transition scale-out" onclick="group_leave();"><i class="fas fa-sign-out-alt"></i></a></li>
									      	<li><a class="zoom-fab zoom-btn-sm zoom-btn-feedback scale-transition scale-out" onclick="group_complete();"><i class="fas fa-check"></i></a></li>
									    </ul>
							  		</div>
			                    </div>
			                </div>
			            </div>
			        </div>
			     </section>
			    <!-- body TOP div END -->
			
			    <!-- body Content START -->
			    <div class="container-fluid content">
			        <div class="row">
			            <!-- Group Category div START -->
			            <section class="col-sm-3 group-category">
			                <div class="group-category-div">
			                    <div class="group-category-header">
			                        <span><i class="fas fa-chalkboard-teacher"></i> Group Category</span>
			                    </div>
			                    <div class="group-category-body">
			                        <div id="jstree_container" class="jstree-from">                    
			                        </div>
			                    </div>
			                    <div class="group-category-footer">
								<div id="ohsnap"></div>
			                    </div>
			                </div>
			            </section>
			            <!-- Group Category div END -->
			
			            <!-- Group Chart div START -->
			            <section class="col-sm-6 chat">
			                <div class="chat-content-div">
			                    <div class="chat-header">
			                        <span class="chatting-roomname"><i class="far fa-comments"></i>&nbsp;&nbsp;ALL</span>
			                        <div class="user-status">
			                            <span><i class="far fa-star"></i></span>
			                        </div>
			                        <div class="header-divider"><hr class="left"/><span id="header-date">today</span><hr class="right"/></div>
			                    </div>
			
			                    <div class="chat-element">
			                        <div class="chatting-contents">
			                           
			                        </div>
			                    </div>
			
			                    <div class="chat-inputbox-div">
			                        <div class="chat-inputbox-bg">
			                            <div class="chat-textbox">
			                                <div id="chat-textbox-text">
			
			                                </div>
			                            </div>
			                            <div id="chat-textbox-icon">
			                            	<div class="flexbox">
			
			                            	</div>
			                            	<i class="fas fa-share-square"></i>
			                            </div>
			                        </div>
			                    </div>
			                </div>
			            </section>
			            <!-- Group Chart div END -->
			
			            <!-- Group Member div START -->
			            <section class="col-sm-2 group-member">
			                <div class="group-member-content">
			                    <div>
			                        <div class="group-member-header">
			                            <p><i class="far fa-address-card"></i> Member <i class="member_insert_ico fas fa-user-plus" onclick="member_insert();"></i></p>
			                        </div>
			                    </div>
			                    <div class="onoffline-content">
			                        <div class="online-content">
			                            <div class="online">
			                            	<i class="fas fa-toggle-on"></i>온라인
			                            </div>
			                            <div id="online-member" class="online-member">
			                            	
			                            </div>
			                        </div>
			                        <div class="offline-content">
			                            <div class="offline">
			                            	<i class="fas fa-toggle-off"></i>오프라인
			                            </div>
			                            <div id="offline-member" class="offline-member">
			                            
			                            </div>
			                        </div>
			                    </div> 
			                    
			                    
			                    <script type="text/javascript">
			                    $(document).ready(function() {
			                    	var onlinelist = JSON.parse('${onlinelist}');
                            		//console.log(onlinelist.hasOwnProperty("민재"));
                            		//console.log('${gmemberlist}');
                            		
                            		var memberNnameList = new Array(); // 전체 카테고리 리스트 비동기로 받아오기
                            		var memberUidList = new Array(); // 전체 카테고리 리스트 비동기로 받아오기
                            		<c:forEach items="${gmemberlist}" var="member">
                            			memberNnameList.push("${member.nname}");
                            			memberUidList.push("${member.uid}");
	                            	</c:forEach>
	                            	
	                            	$.each(memberNnameList, function(index, element) {
	                            		var member = element;
	                            		//console.log(member);
	                            		if(onlinelist.hasOwnProperty(member)) {
                            				var insertOnline = '<p id="' + member + '"' + ' class="member">' 
				                								+ '<img class="member-ico" src="/bit/images/profile.png" '
				                								+ 'onerror="this.src=' + "'/bit/images/profile.png'\">" + member
				                							  + '</p>';
				                			$('#online-member').prepend(insertOnline);
	                            			
	                            		}else {
	                            			var insertOffline = '<p id="' + member + '"' + ' class="member">' 
					            								+ '<img class="member-ico" src="/bit/images/profile.png" '
					            								+ 'onerror="this.src=' + "'/bit/images/profile.png'\">" + member
					            							  +'</p>';
					            			$('#offline-member').prepend(insertOffline);
	                            		}
	                            	});
			                    });
                            	</script>
                            	   
			                </div>
			            </section>
			            <!-- Group Member div END -->
			        </div>
			    </div>
			    <!-- body Content END -->
    		</div>
    	</div>
    </div>
    
    
<!-- 그룹 북마크 마이카테고리로 가져가기 div START -->
<div id="fromGroupToMy" class="modal fade" tabindex="-1" role="dialog">
	<div class="main-modal-controller">
		<div class="main-modal-center">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="gridSystemModalLabel">북마크 가져가기</h4>
					</div>
					<div class="modal-body">
						<div class="completed-modal-left">
							<h4 class="completed-modal-from"><b>URL :</b>
							<input type="text" id="modalurl"class="indishare-url" readonly></h4>
			            </div>
			            <hr>
			            <div class="completed-modal-left abc123">
			                <h4 class="completed-modal-to"><b>가져가기 :  My Bookmark</b></h4>
			                <!-- <div class="completed-modal-dropdown">
			                       My Bookmark
			                </div> -->
			                <div id="jstree-to-mybookmark" style="clear: both;"></div>
			            </div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default indishare" data-dismiss="modal">취소</button>
						<button id="into-my-bookmark" type="button" class="btn btn-primary">확인</button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div>
	</div>
</div><!-- /.modal -->

<!--마이북마크 가져오기 -->
<div id="fromMytoGroup" class="modal fade" tabindex="-1" role="dialog">
	<div class="main-modal-controller">
		<div class="main-modal-center">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="gridSystemModalLabel">북마크 가져오기</h4>
					</div>
					<div class="modal-body">
						<div class="completed-modal-left">
							<h4 class="completed-modal-from"><b>폴더 :</b>
							<input type="text" id="addUrlFolder"class="indishare-url" readonly></h4>
			            </div>
			            <hr>
			            <div class="completed-modal-left abc123">
			                <h4 class="completed-modal-to"><b>가져오기 : </b></h4>
			                <div id="jstree-from-mybook" style="clear: both;"></div>
			            </div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default indishare" data-dismiss="modal">취소</button>
						<button id="from-my-bookmark" type="button" class="btn btn-primary">확인</button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div>
	</div>
</div><!-- /.modal -->
    
