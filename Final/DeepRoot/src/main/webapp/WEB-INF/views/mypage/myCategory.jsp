<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
	function deleteGroup(gid) {
		$.confirm({
			title : '그룹 삭제',
			content : '삭제하시겠습니까?',
			theme: 'light',
			backgroundDismiss: true,
			closeIcon: true,
		    closeIconClass: 'fa fa-close',
			buttons: {
		        '삭제': {
		        	btnClass : 'btn-danger',
		        	keys: ['enter'],
		        	action : function () {
		        		$("#"+gid).remove(); // 그룹리스트에서 지우기
		    			$.ajax({
		    				url: "leaveGroup.do",
		    				type: "post",
		    				data : {
		    					gid : gid // 그룹 ID
		    				},
		    				success : function(data){
		    					console.log(data);
		    				}
		    			});
		        	}
		        },
		     
		        '취소': {
		        	btnClass : 'btn-success',
		        	action : function() {
		        		
		        	}
		        }
		    }
		});
	}
	
	function deleteCompletedGroup(gid) {
		$.confirm({
			title : '완료된 그룹 삭제',
			content : '삭제하시겠습니까?',
			theme: 'light',
			backgroundDismiss: true,
			closeIcon: true,
		    closeIconClass: 'fa fa-close',
			buttons: {
		        '삭제': {
		        	btnClass : 'btn-danger',
		        	keys: ['enter'],
		        	action : function () {
		        		$("#completed"+gid).remove(); // 완료된 그룹리스트에서 지우기
		    			$.ajax({
		    				url: "leaveGroup.do",
		    				type: "post",
		    				data : {
		    					gid : gid // 그룹 ID
		    				},
		    				success : function(data){
		    					console.log(data);
		    				}
		    			});
		        	}
		        },
		     
		        '취소': {
		        	btnClass : 'btn-success',
		        	action : function() {
		        		
		        	}
		        }
		    }
		});
	}
	
	function addGroup() {
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
	
	function completedGroup(gid) {
		$.confirm({
		    title: '그룹 완료',
		    content: '' +
		    '<form id="completedGroupForm" action="${pageContext.request.contextPath}/user/completedGroup.do" class="formName" method="post">' +
		    '<div class="form-group">' +
		    '<label>해시태그</label>' +
		    '<input type="text" name="htag" placeholder="#해쉬태그" class="name form-control" required />' +
		    '<input type="hidden" class="gid" name="gid" />' + 
		    '</div>' +
		    '</form>',
		    closeIcon: true,
		    closeIconClass: 'fa fa-close',
		    
		    buttons: {
		        formSubmit: {
		            text: '완료',
		            btnClass: 'btn-blue',
		            action: function () {
		                var name = this.$content.find('.name').val();
		                this.$content.find('.gid').val(gid);
		                if(!name){
		                    $.alert('해시태그를 적어주세요');
		                    return false;
		                }
		                $("#completedGroupForm").submit();
		                
		            }
		        },
		                    취소: function () {
		            //close
		        },
		    }
		    
		});
	}
	

	function testing_modal(d){
		console.log(d.id);
		var gid = d.id; // 클릭한 완료된 그룹의 id 입니다.
		
		$.ajax({
			
			url : "getCompletedTeamBookmark.do",
			type : "POST",
			data : {gid : "4"},	/* group id 를 넣어야 한다. */
			dataType :"json",
			success : function(obj){
				
				console.log(obj);
				
				$("#group_bookmark_modal").jstree({
					
					"core" : {
						'data' : obj,
						'themes':{
							'name' : 'proton',
							'responsive' : true,
							'dots' : false,
						}
					},
					"plugins" : ["checkbox" ]
					
				})
				
			}
		})
		
		$('#completedGroupModal').modal();
		
	}
</script>
<div class="container">
	<div class="row" style="padding-top: 150px;"></div>
	<div class="row my-row-bg">
		<!-- 카테고리 div -->
		<div class="col-lg-4 mydiv-height my-bookmark-div">
			<img
				src="${pageContext.request.contextPath}/images/mypage/left_spring.png"
				alt="" class="spring left-top"> <img
				src="${pageContext.request.contextPath}/images/mypage/left_spring.png"
				alt="" class="spring left-bottom">
			<!-- 마이 북마크 Heading -->
			<div class="heading my-bookmark-list">
				<i class="material-icons md-32">archive</i><span
					class="mypage-title">마이 북마크</span>
				<button type="button" class="my-boomark-btn" id="addroot">New
					Category</button>
			</div>
			<div
				style="background-color: white; border-radius: 15px 15px 15px 15px">
				<div id="jstree_container"></div>
			</div>
		</div>

		<!-- 선택한 폴더(카테고리)의 URL -->
		<div class="col-lg-4 mydiv-height my-bookmark-print-div">
			<img
				src="${pageContext.request.contextPath}/images/mypage/right_spring.png"
				alt="" class="spring right-top"> <img
				src="${pageContext.request.contextPath}/images/mypage/right_spring.png"
				alt="" class="spring right-bottom">
			<!-- 출력 Div Heading -->
			<div class="heading my-bookmark-print-list ">
				<i class="material-icons md-32 pull-left">view_list</i><span
					class="mypage-title pull-left">리스트</span> <span
					class="mypage-title">&nbsp;</span>
				<button type="button" class="my-boomark-btn" id="addurl">Add
					URL</button>
			</div>
			<div>
				<div id="jstree_container_child"></div>
			</div>

		</div>

		<!-- 내가 참여하는 그룹 리스트 -->
		<div class="col-lg-3 mydiv-height">
			<!-- 참여중인 그룹리스트 -->
			<div class="group-list-div panel group-list-panel">
				<div class="heading-post-top group-list">
					<i class="material-icons md-36">playlist_play</i><span
						class="mypage-title">그룹리스트</span>
				</div>
				<div class="panel-body">
					<ul class="group-list-list">
						<c:forEach items="${teamList}" var="team">
							<li id="${team.gid}" class="list-group-item"><label
								class="my-group-list"> ${team.gname} </label>
								<div class="pull-right action-buttons">
									<c:choose>
										<c:when test="${team.grid == '1'}">
											<a class="completed"><span
												class="glyphicon glyphicon-check"
												onclick="completedGroup(${team.gid})"></span></a>
										</c:when>
										<c:otherwise>
											<a class="trash"><span class="glyphicon glyphicon-trash"
												onclick="deleteGroup(${team.gid})"></span></a>
										</c:otherwise>
									</c:choose>
								</div></li>
						</c:forEach>
						<li class="list-group-item"><a class="plus"><span
								class="glyphicon glyphicon-plus-sign" onclick="addGroup()"></span></a>
							<label class="my-group-list"> 그룹을 만드시겠습니까? </label></li>
					</ul>
				</div>
			</div>

			<!-- 완료된 그룹리스트 -->
			<div
				class="completed-group-list-div panel group-completed-list-panel">
				<div class="heading-post-bottom group-completed-list">
					<i class="material-icons md-36">playlist_add_check</i><span
						class="mypage-title">완료된 그룹</span>
				</div>
				<div class="panel-body-scroll">
					<ul class="group-list-list">
						<c:forEach items="${completedTeamList}" var="completedTeam">
							<li id="${completedTeam.gid}" class="list-group-item" onclick="testing_modal(this)" >
								<label class="my-group-list"> ${completedTeam.gname} </label>
								<div class="pull-right action-buttons">
									<a class="trash"><span class="glyphicon glyphicon-trash" onclick="deleteCompletedGroup(${completedTeam.gid})"></span></a>
								</div>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</div>
	

	<!-- 완료된 그룹 가져오기 Modal -->
	<div id="completedGroupModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="socialGroupModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="gridSystemModalLabel">Title</h4>
				</div>
				<div class="modal-body">
					<div class="completed-modal-left">
		                <h4 class="completed-modal-from"><b>From : </b></h4>
		
		                <div id="jstree-from-left">
		
		                </div>
		            </div>
		            <div class="completed-modal-right">
		                <h4 class="completed-modal-to"><b>To : </b></h4>
		
		                <!-- Dropdown -->
		                <div class="dropdown completed-modal-dropdown">
		                    <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		                        Dropdown button <span class="caret"></span>
		                    </button>
		                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
		                        <li id="completed-modal-mybook" class="dropdown-item" href="#">나의 북마크</li>
		                        <hr class="divider-hr">
		                        <li class="dropdown-item dropdown-submenu">
		                            <a tabindex="-1" href="#">나의 그룹북마크</a>
		                            <ul class="dropdown-menu">
		                              <li class="dropdown-group-item"><span tabindex="-1">Group 1</span></li>
		                              <li class="dropdown-group-item"><span>Group 2</span></li>
		                              <li class="dropdown-group-item"><span>Group 3</span></li>
		                            </ul>
		                        </li>
		                    </div>
		                    <script type="text/javascript">
		                        $('#completed-modal-mybook').click(function() {
		                            $('#dropdownMenuButton').text($(this).text());
		                        });
		                        $('.dropdown-group-item').click(function() {
		                            $('#dropdownMenuButton').text($(this).text());
		                        });
		                    </script>
		                </div>
		
		                <div id="jstree-to-right">
		
		                </div>
		            </div>
				</div>
				<hr class="float-clear-hr">
					<div id="group_bookmark_modal"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary">Save changes</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</div>
