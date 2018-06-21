<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- adminTable CSS START -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/mainbooklist.css">
<!-- adminTable CSS END -->



<script type="text/javascript">
	
	$(function(){
		var categorylist = new Array(); 
		
		<c:forEach items="${categorylist}" var="category" varStatus="status">
			categorylist.push(new Array("${category.acid}", "${category.color}"));
		</c:forEach>
		
		// console.log(categorylist);
		// DataTable 적용
		$.each(categorylist, function(index, element){
			//console.log(element);
			$('table[id="'+ element[0] + '"]').DataTable({
				responsive: true
			});
			
			// ColorPicker
			
			var color = element[1];
			if(color == "#000"){
				color = "#FFFFFF";
			}
			
			$('span[id="'+ element[0] + '"]').css("color", color);
			console.log(color);
			
			$(".categoryColor"+element[0]).colorPick({
	            'initialColor': element[1],
	            'onColorSelected': function() {
	            	var selectColor = this.color;
	            	if(selectColor == "#000"){
	            		selectColor = "#FFFFFF";
	    			}
	                this.element.css({
	                    'backgroundColor': this.color,
	                    'color': this.color
	                });
	                
	                $.ajax({
	            		url: "editCategoryCclor.do",
	    				type: "post",
	    				data : {
	    					acid : element[0], // 카테고리 ID
	    					color : this.color // 카테고리 색상
	    				},
	    				success : function(data){
	    					console.log(data);
	    				}
	            	});
	                
	                $('span[id="'+ element[0] + '"]').css("color", selectColor);
	            }
	        });
		});
	});
	
</script>

<!-- Main Content START -->
<div class="content-wrapper" style="min-height: 913px;">
	<section class="content-header">
		<h1>
			Main Bookmark List
			<!-- Category insert / Delete START -->
			<span class="btn-category" data-toggle="dropdown"><i
				class="fa fa-th-list category-dropdown"></i></span>
			<ul class="dropdown-menu category">
				<li><a data-toggle="modal" onclick="openCategoryModal();">카테고리
						추가<i class="fa fa-plus"></i>
				</a></li>
				<li><a data-toggle="modal" onclick="openCategoryDeleteModal();">카테고리
						삭제<i class="fa fa-minus"></i>
				</a></li>
			</ul>
			<!-- Category inert / Delete END -->
		</h1>
		<ol class="breadcrumb">
			<li><a href="javascript:;"><i class="fa fa-home"></i>Home</a></li>
			<li class="active">main bookmark list</li>
		</ol>
	</section>

	<!-- Category lsit Dropdown START -->
	<section class="content-category">
		<div class="row">
			<div class="col-sm-12 category">
				<!-- Category lsit Dropdown START -->
				<button class="btn-category categorylist" data-toggle="dropdown"
					type="button">
					카테고리<i class="fa fa-caret-right"></i>
				</button>
				<ul class="dropdown-menu categorylist">
					<c:forEach items="${categorylist}" var="category">
						<li><a href="#${category.acid}">${category.acname}</a></li>
					</c:forEach>
				</ul>
				<!-- Category lsit Dropdown END -->
			</div>
		</div>
	</section>
	<!-- Category lsit Dropdown END -->

	<!--groupList table start-->
	<section class="content">
		<div id="page-wrapper">
			<div class="row">
				<!-- Category List Table START -->
				<c:forEach items="${url_by_category}" var="list">
					<c:forEach items="${list}" var="hashmap">
						<div class="col-sm-6">
						<div class="panel">
							<!-- Category Name & edit & insert START -->

							<div class="panel-heading">
								<span id="${hashmap.key.acid}"> ${hashmap.key.acname}</span>
								<!--color picker START -->
								<button class="colorPickSelector categoryColor${hashmap.key.acid}"></button>
								<!--color picker END -->
								<i class="fas fa-pencil-alt" data-toggle="modal"
									onclick="openCategoryEditModal(${hashmap.key.acid}, '${hashmap.key.acname}');"></i>
								<div class="pull-right">
									<i class="fa fa-plus-circle i-plus-circle" data-toggle="modal"
										onclick="openUrlModal(${hashmap.key.acid}, '${hashmap.key.acname}');"></i>
								</div>
							</div>
							<!-- /.panel-heading -->


							<!-- Category Name & edit & insert END -->
							<div class="panel-body">
								<table width="100%" class="table table-hover"
									id="${hashmap.key.acid}">
									<thead>
										<tr>
											<th>사이트명</th>
											<th>URL 주소</th>
											<th>Actions</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${hashmap.value}" var="book">
											<tr id="${book.abid}">
												<td>${book.urlname}</td>
												<td><a href="${book.url}" target="_blank">${book.url}</a></td>
												<td><i class="fas fa-pencil-alt url-action" onclick="openUrlEditModal(${hashmap.key.acid}, '${hashmap.key.acname}', '${book.url}', ${book.abid});"></i><i
													class="fas fa-trash-alt url-action"
													onclick="deleteUrl(${book.abid})"></i></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					</c:forEach>	
				</c:forEach>
			</div>
			<!-- /.row -->
		</div>
		<!-- /#page-wrapper -->
		<!--categoryModal script start-->
		<script type="text/javascript">
        	/*카테고리 추가 모달 바로열기*/
            function showCategoryForm() {
            	$('.addCategoryBox').fadeIn('fast');
                $('.addCategory-footer').fadeIn('fast');
                $('.modal-title').html('카테고리 추가');
            }
        	
            function openCategoryModal() {
            	showCategoryForm();
                setTimeout(function() {
                	$('#addCategoryModal').modal('show');
                }, 230);
           }
            
           function addAdminCategory() {
        	   //console.log("click");
        	   //console.log($("#acname1").val().trim());
        	   if($("#acname1").val().trim() == ""){
        		   $.alert("카테고리 명을 입력해주세요");
        		   $("#acname1").focus();
		 	   }else {
		 		  $("#addCategoryForm").submit();
		 		  $("#addCategoryForm")[0].reset();
		 		  setTimeout(function() {
	               	$('#addCategoryModal').modal('hide');
	              }, 230);
		 	   }
		   }
        </script>
		<!--categoryModal script end-->

		<!--categoryModal start-->
		<div class="modal fade addCategory" id="addCategoryModal">
			<div class="modal-dialog">

				<div class="modal-content">
					<form id="addCategoryForm" name="addCategory" method="post" action="addCategory.do"
						accept-charset="UTF-8">

						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title"></h4>
						</div>

						<div class="modal-body">
							<div class="box">
								<!--카테고리명 입력-->
								<div class="form addCategoryBox">
									<label class="control-label" for="acname">추가할 카테고리명을
										입력하세요</label> <input id="acname1" class="form-control" type="text"
										placeholder="카테고리명" name="acname" /><br>
								</div>
							</div>
						</div>

						<div class="modal-footer">
							<div class="addCategory-footer">
								<input class="btn btn-default btn-comp" type="button" value="등록"
									onclick="addAdminCategory();">
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!--categoryModal end-->

		<!--categoryEditModal script start-->
		<script>
                        /*카테고리 수정 모달 바로열기*/
                        function showCategoryEditForm() {
                            $('.editCategoryBox').fadeIn('fast');
                            $('.editCategory-footer').fadeIn('fast');
                            $('.modal-title').html('카테고리 수정');
                        }

                        function openCategoryEditModal(acid, acname) {
                        	$("#editCategoryID").val(acid); // 카테고리 ID hidden type에 넣어주기
                        	$("#acname2").val(acname); // 카테고리 명 미리 입력해주기
                        	
                            showCategoryEditForm();
                            setTimeout(function() {
                                $('#editCategoryModal').modal('show');
                            }, 230);
                        }
                        
                        function editAdminCategory() {
                        	$.confirm({
            					title : '카테고리 수정',
            					content : '수정하시겠습니까?',
            					theme: 'dark',
            					type: 'orange',
            					backgroundDismiss: true,
            					closeIcon: true,
            					buttons: {
            				        '수정': {
            				        	btnClass : 'btn-danger',
            				        	keys: ['enter'],
            				        	action : function () {
            				        		$("#editCategoryForm").submit();
            	              		 		$("#editCategoryForm")[0].reset();
            	              		 		setTimeout(function() {
            	              	            	$('#editCategoryForm').modal('hide');
            	              	            }, 230);
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
                    </script>
		<!--categoryEditModal script end-->

		<!--categoryEditModal start-->
		<div class="modal fade editCategory" id="editCategoryModal">
			<div class="modal-dialog">

				<div class="modal-content">
					<form id="editCategoryForm" name="editCategory" method="post" action="editCategory.do"
						accept-charset="UTF-8">

						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title"></h4>
						</div>

						<div class="modal-body">
							<div class="box">
								<!--카테고리명 입력-->
								<div class="form editCategoryBox">
									<input type="hidden" id="editCategoryID" name="acid">
									<label class="control-label" for="acname">카테고리명을
										입력하세요</label> <input id="acname2" class="form-control" type="text"
										placeholder="acname" name="acname"><br>
								</div>
							</div>
						</div>

						<div class="modal-footer">
							<div class="editCategory-footer">
								<input class="btn btn-default btn-comp" type="button" value="수정"
									onclick="editAdminCategory();">
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!--categoryEditModal end-->

		<!--categoryDeleteModal script start-->
		<script>
                        /*카테고리 삭제 모달 바로열기*/
                        function showCategoryDeleteForm() {
                            $('.deleteCategoryBox').fadeIn('fast');
                            $('.deleteCategory-footer').fadeIn('fast');
                            $('.modal-title').html('카테고리 삭제');
                        }

                        function openCategoryDeleteModal() {
                            showCategoryDeleteForm();
                            setTimeout(function() {
                                $('#deleteCategoryModal').modal('show');
                            }, 230);

                        }
                        
                        function deleteAdminCategory() {
                        	var js = $.confirm({
            					title : '카테고리 삭제',
            					content : '삭제하시겠습니까?',
            					closeIcon: true,
            					type: 'orange',
            					theme: 'dark',
            					backgroundDismiss: true,
            					buttons: {
            				        '삭제': {
            				        	btnClass : 'btn-danger',
            				        	keys: ['enter'],
            				        	action : function () {
            				        		$("#deleteCategoryForm").submit();
            	              		 		$("#deleteCategoryForm")[0].reset();
            	              		 		setTimeout(function() {
            	              	            	$('#deleteCategoryModal').modal('hide');
            	              	            }, 230);
            				        	}
            				        },
            				     
            				        '취소': {
            				        	btnClass : 'btn-success',
            				        	action : function() {
            				        		
            				        	}
            				        }
            				    },
            				    
            				    onContentReady: function(){
            				    	jc.$icon.css("color: white");
            				    }
            				    
            				});
                        	
                        }
                        
                    </script>
		<!--categoryDeleteModal script end-->

		<!--categoryDeleteModal start-->
		<div class="modal fade deleteCategory" id="deleteCategoryModal">
			<div class="modal-dialog">

				<div class="modal-content">
					<form id="deleteCategoryForm" name="deleteCategory" method="post" action="deleteCategory.do"
						accept-charset="UTF-8">

						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title"></h4>
						</div>

						<div class="modal-body">
							<div class="box">
								<!--카테고리명 입력-->
								<div class="form deleteCategoryBox">
									<label class="control-label" for="acnameList2">삭제할
										카테고리를 선택하세요</label>&nbsp; <select id="acnameList2"
										class="form-control" name="acid">
										<c:forEach items="${categorylist}" var="category">
											<option value="${category.acid}">${category.acname}</option>
										</c:forEach>
									</select><br> <input id="acname3" class="form-control"
										type="hidden" name="acname"><br>
								</div>
							</div>
						</div>

						<div class="modal-footer">
							<div class="deleteCategory-footer">
								<input class="btn btn-default btn-comp" type="button" value="삭제"
									onclick="deleteAdminCategory();">
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!--categoryDeleteModal end-->

		<!--urlAddModal script start-->
		<script>
                        /*URL 추가 1단계 모달*/
                        function showUrlForm() {
                            $('.categoryBox').fadeOut('fast', function() {
                                $('.addUrlBox').fadeIn('fast');
                                $('.category_unshared-footer').fadeOut('fast', function() {
                                    $('.url-footer').fadeIn('fast')
                                })
                            });
                            $('.modal-title').html('1단계');

                        }
                        
                        /* URL 1단계에서 다음 눌렀을 때 WebCrawling을 통해 비동기로 타이틀 가져오기 */
                        function getTitleWithWebCrawling() {
                        	var url = $("#url").val().trim();
                        	
	                       	if(url == ""){
	                     		$("#url").focus();
	                     	}else {
	                     		preview(url);
             		 	   	}
                        }
                        
                        function preview(url){
                        	$.ajax({
                        		url: "preview.do",
                				type: "post",
                				data : {
                					url : url // 북마크 ID
                				},
                				success : function(data){
                					$("#addUrlname").val(data.title);
                					showUrlStep2Form();
                				},
                        	});
                        }
                        
                        /*URL 추가 2단계 모달*/
                        function showUrlStep2Form() {
                            $('.addUrlBox').fadeOut('fast', function() {
                                $('.categoryBox').fadeIn('fast');
                                $('.url-footer').fadeOut('fast', function() {
                                    $('.category_unshared-footer').fadeIn('fast')

                                });
                            });
                            $('.modal-title').html('2단계');

                        }

                        /*URL 추가 1단계 모달 바로열기*/
                        function openUrlModal(acid, acname) {
                        	$("#acname4").val(acname);
                        	$("#adminCategoryID").val(acid);
                        	
                            showUrlForm();
                            setTimeout(function() {
                                $('#addUrlModal').modal('show');
                            }, 230);

                        }
                        
                        /* URL 추가 */
                        function addBookmarkButton() {
							var urlname = $("#addUrlname").val().trim();
                        	
	                       	if(urlname == ""){
	                     		$("#addUrlname").focus();
	                     	}else {
	                     		$("#addUrlForm").submit();
	                     		$("#addUrlForm")[0].reset();
	              		 		setTimeout(function() {
	              	            	$('#addUrlModal').modal('hide');
	              	            }, 230);
             		 	   	}
                        }
                                    
                    </script>

		<!--urlAddModal script end-->
		<!--urlAddModal start-->
		<div class="modal fade addUrl" id="addUrlModal">
			<div class="modal-dialog">

				<div class="modal-content">
					<form id="addUrlForm" name="addUrl" method="post" action="addUrl.do"
						accept-charset="UTF-8">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title"></h4>
						</div>

						<div class="modal-body">
							<div class="box">
								<!--1단계 URL 입력-->
								<div class="form addUrlBox">
									<label class="control-label" for="url">URL을 입력하세요</label> <input
										id="url" class="form-control" type="text" placeholder="URL"
										name="url"><br>
									<input type="hidden" id="adminCategoryID" name="acid">
								</div>
							</div>

							<div class="box">
								<!--2단계 카테고리 선택-->
								<div class="categoryBox" style="display: none;">
									<div class="form">
										<label class="control-label" for="urlname">제목</label> <input
											id="addUrlname" class="form-control" type="text"
											placeholder="URLNAME" name="urlname"><br> <label
											class="control-label" for="acname4">선택된 카테고리</label>&nbsp; <input
											id="acname4" class="form-control" type="text"
											placeholder="선택된 카테고리 (readonly)" name="acname" readonly><br>
									</div>
								</div>
							</div>
						</div>

						<div class="modal-footer">
							<div class="url-footer">
								<input class="btn btn-default btn-next" type="button" value="다음"
									onclick="getTitleWithWebCrawling()">
							</div>
							<div class="category_unshared-footer" style="display: none">
								<input class="btn btn-default btn-prev" type="button" value="이전"
									onclick="showUrlForm()"> <input
									class="btn btn-default btn-comp" type="button" value="등록"
									onclick="addBookmarkButton()">
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!--urlAddModal end-->
		
		<!--urlEditModal script start-->
		<script>
                        /*URL 수정 1단계 모달*/
                        function showEditUrlForm() {
                            $('.categoryBox').fadeOut('fast', function() {
                                $('.editUrlBox').fadeIn('fast');
                                $('.category_unshared-footer').fadeOut('fast', function() {
                                    $('.url-footer').fadeIn('fast')
                                })
                            });
                            $('.modal-title').html('1단계');

                        }
                        
                        /* URL 1단계에서 다음 눌렀을 때 WebCrawling을 통해 비동기로 타이틀 가져오기 */
                        function getTitleEditUrl() {
                        	var url = $("#editurl").val().trim();
                        	
	                       	if(url == ""){
	                     		$("#editurl").focus();
	                     	}else {
	                     		editUrlPreview(url);
             		 	   	}
                        }
                        
                        function editUrlPreview(url){
                        	$.ajax({
                        		url: "preview.do",
                				type: "post",
                				data : {
                					url : url // 북마크 ID
                				},
                				success : function(data){
                					$("#editUrlname").val(data.title);
                					showUrlEditStep2Form();
                				}
                        	});
                        };
                        
                        /*URL 수정 2단계 모달*/
                        function showUrlEditStep2Form() {
                            $('.editUrlBox').fadeOut('fast', function() {
                                $('.categoryBox').fadeIn('fast');
                                $('.url-footer').fadeOut('fast', function() {
                                    $('.category_unshared-footer').fadeIn('fast')

                                });
                            });
                            $('.modal-title').html('2단계');

                        }

                        /*URL 수정 1단계 모달 바로열기*/
                        function openUrlEditModal(acid, acname, url, abid) {
                        	$("#editurl").val(url);
                        	$("#acname5").val(acname);
                        	$("#editAdminCategoryID").val(acid);
                        	$("#editUrlID").val(abid);
                        	
                        	showEditUrlForm();
                            setTimeout(function() {
                                $('#editUrlModal').modal('show');
                            }, 230);

                        }
                        
                        /* URL 수정  */
                        function editBookmarkButton() {
							var urlname = $("#editUrlname").val().trim();
                        	
	                       	if(urlname == ""){
	                     		$("#editUrlname").focus();
	                     	}else {
	                     		$.confirm({
	            					title : 'URL 수정',
	            					content : '수정하시겠습니까?',
	            					type: 'orange',
	            					theme: 'dark',
	            					backgroundDismiss: true,
	            					closeIcon: true,
	            					buttons: {
	            				        '수정': {
	            				        	btnClass : 'btn-danger',
	            				        	keys: ['enter'],
	            				        	action : function () {
	            				        		$("#editUrlForm").submit();
	            	                     		$("#editUrlForm")[0].reset();
	            	              		 		setTimeout(function() {
	            	              	            	$('#editUrlModal').modal('hide');
	            	              	            }, 230);
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
                        }
                                    
                    </script>
		<!--urlEditModal script end-->
		
		<!--urlEditModal start-->
		<div class="modal fade editUrl" id="editUrlModal">
			<div class="modal-dialog">

				<div class="modal-content">
					<form id="editUrlForm" name="editUrl" method="post" action="editUrl.do"
						accept-charset="UTF-8">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true">&times;</button>
							<h4 class="modal-title"></h4>
						</div>

						<div class="modal-body">
							<div class="box">
								<!--1단계 URL 입력-->
								<div class="form editUrlBox">
									<label class="control-label" for="editurl">URL을 입력하세요</label> <input
										id="editurl" class="form-control" type="text" placeholder="URL"
										name="url"><br>
									<input type="hidden" id="editAdminCategoryID" name="acid">
									<input type="hidden" id="editUrlID" name="abid">
								</div>
							</div>

							<div class="box">
								<!--2단계 카테고리 선택-->
								<div class="categoryBox" style="display: none;">
									<div class="form">
										<label class="control-label" for="urlname">제목</label> <input
											id="editUrlname" class="form-control" type="text"
											placeholder="URLNAME" name="urlname"><br> <label
											class="control-label" for="acname5">선택된 카테고리</label>&nbsp; <input
											id="acname5" class="form-control" type="text"
											placeholder="선택된 카테고리 (readonly)" name="acname" readonly><br>
									</div>
								</div>
							</div>
						</div>

						<div class="modal-footer">
							<div class="url-footer">
								<input class="btn btn-default btn-next" type="button" value="다음"
									onclick="getTitleEditUrl()">
							</div>
							<div class="category_unshared-footer" style="display: none">
								<input class="btn btn-default btn-prev" type="button" value="이전"
									onclick="showEditUrlForm()"> <input
									class="btn btn-default btn-comp" type="button" value="수정"
									onclick="editBookmarkButton()">
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!--urlEditModal end-->
		
		
		<!-- urlDelete script Start -->
		<script type="text/javascript">
			function deleteUrl(abid) {
				$.confirm({
					title : 'URL 삭제',
					content : '삭제하시겠습니까?',
					theme: 'dark',
					type: 'orange',
					backgroundDismiss: true,
					closeIcon: true,
					buttons: {
				        '삭제': {
				        	btnClass : 'btn-danger',
				        	keys: ['enter'],
				        	action : function () {
				        		$("#" + abid).remove(); // dataTable에서 지우기
								$.ajax({
									url: "deleteUrl.do",
									type: "post",
									data : {
										abid : abid // 관리자북마크 ID
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
		</script>
		<!-- urlDelete script End -->
		
	</section>
</div>
<!-- Main Content END -->


