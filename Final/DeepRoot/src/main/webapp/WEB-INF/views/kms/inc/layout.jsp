<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/dist/themes/proton/style.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/docs.css" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" />

<link rel="icon"
	href="${pageContext.request.contextPath}/resources/assets/favicon.ico"
	type="image/x-icon" />
<link rel="apple-touch-icon-precomposed"
	href="${pageContext.request.contextPath}/resources/assets/apple-touch-icon-precomposed.png" />
<script
	src="${pageContext.request.contextPath}/resources/assets/jquery-1.10.2.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/dist/jstree.min.js"></script>


<title>my</title>
</head>
<body>
	<script type="text/javascript">
		$(document).ready(function(){
			
			var urlpid = null;
			var firstclick = 0;
			
			$.ajax({
				url : "getCategoryList.do",
				type:"POST",
				dataType:"json",
				success : function(data){	
					console.log(data);
			
					$("#jstree_container").jstree({	
							"core": {
							'data' : data,
							'themes':{
								'name' : 'proton',
								'responsive' : true,
								"dots": false,
								
							},
							"check_callback" : function(op, node, par, pos, more){
								if(op === "move_node"){ // dnd 이벤트 일때 
									console.log(op);//move_node
									console.log(node);//실제 select 한node
									console.log(par);// select node 사위 헐 여기서 나옴 childe
									console.log(par.a_attr.href);
									console.log(par.children_d);
	
									console.log("선택 node 의 id 값");
									console.log(node.id);
									var dragnode = node.id;
									console.log("드랍 당한 노드의 id");
									console.log(par.id);
									var dropnode = par.id;
									
									form = {dragnode : dragnode , dropnode : dropnode};
									
									$.ajax({
										
										url : 'dropNode.do',
										type : 'POST',
										data : form,
										success : function(data){
											console.log(data);
											
											
										}
										
										
										
									})
									
									
									
									return true;
								}							
								return true;	
							}
						},
						"plugins" : [ "dnd","contextmenu" ], 

						"contextmenu" : {
							
							"select_node" : false,
							
							"items" : function($node){
						    	
						    	  var href = $node.a_attr.href;
								  var tree = $("#jstree_container").jstree(true);
						    	  
									if(href == null || href == "#"){  
										// 링크 만들기, 폴더 만들기, 이름 바꾸기, 삭제
										return {
								            "link_create": {
								                "separator_before": false,
								                "separator_after": false,
								                "label": "URL 추가",
								                "action": function (obj) { 
								                	
								                	  $('#form')[0].reset();// modal input text 창 초기화
								                	  
									            	  var inst = $.jstree.reference(obj.reference); // 내가 우 클릭한 node의 정보
									            	  console.log(inst.get_node(obj.reference));
									            	  console.log(inst.get_node(obj.reference).id);// 내가 우 클릭한 node의 id 값 새로 생성하는 url의 부모id 값이 된다.
									            	  
									            	  $('#linkAdd').modal(); // url 추가하는 modal 창이 나온다.
									            	  
									            	  var par = inst.get_node(obj.reference).id; // 내가 우 클릭한 node의 id를 새로 생성하는 url의 부모로 지정
									            	  
									            	  $('#linkAddSubmit').on("click",function(){ // modal에서 보내기 선택한 것임
									            		  
									            		  var sharing = 0; //일단 default 0은 비공유
									            		  var url = $('#url').val(); //추가 url 값
									            		  var title = $('#title').val(); // 추가 url 명값
									            		 // var parent = par;
									            		  console.log(url,title,par); //확인
									            		  
									            		  var result = $("#share").prop("checked"); //공유 체크여부 확인
									            		  if(result){ sharing =1;}
									            		  console.log("sharing 여부")
									            		  console.log(sharing);
									            		  
									            		  var form = {url : url , urlname : title , pid : par, uid:  "user1@naver.com"}
									            		  
									            		  $.ajax({
									            			  url: "addFolderOrUrl.do",
									            			  type :"POST",
									            			  data : form,
									            			  success : function(data){//나중에 sequence 나 autoincrement 사용해서 하나 올린 값을 받아서 insert 해주고 data 보내주어 view단 node 생성해주기
									            					
									            				  $('#linkAdd').modal("toggle"); // 모달 창 닫아주기
									            					console.log(data);	//id 확인
								              			  }
								          			  })
								          			})
								                }
								            },
								            "folder_create": {
								                "separator_before": false,
								                "separator_after": false,
								                "label": "그룹 추가",
								                "action": function (obj) { 
								                	
								                	var inst = $.jstree.reference(obj.reference);
								                	var par = inst.get_node(obj.reference).id;
								                	
								                	 $('#form2')[0].reset();
								                	 $('#folderAdd').modal();
								                	 
								                	 $('#folderAddsubmit').on("click",function(){
								                		 
								                		 var foldername = $('#foldername').val();
								                		 var sharing = 0;
								                		 var form = {urlname : foldername, pid : par , uid : "user1@naver.com"}
	
								               		  $.ajax({
								            			  url: "addFolderOrUrl.do",
								            			  type :"POST",
								            			  data : form,
								            			  success : function(data){
								            					console.log(data);
								            				  $('#folderAdd').modal("toggle");
								            					
								            					//id 가져오는 문 만들기
								            					 $('#jstree_container').jstree().create_node(par ,  { "id" : data , "text" : foldername}, "last", function(){
												          });
							              			  }
							          			  })
						                	 })
								                	
								                }
								            },
								            "rename": {
								                "separator_before": false,
								                "separator_after": false,
								                "label": "이름 수정",
								                "action": function (obj) { 					                	
								                	tree.edit($node);				                	
								                }
								            },                         
								            "remove": {
								                "separator_before": false,
								                "separator_after": false,
								                "label": "삭제",
								                "action": function (obj) { 
								                	console.log("삭제 누름");
													tree.delete_node($node);
								                    
								                }
								            }
								        };						
									}
                            }
                        }			    
					})	
					.bind("loaded.jstree", function (event, data) {
							console.log("loaded jstree");
							console.log(data.instance._model.data);
							var test = data.instance._model.data
							console.log(Object.keys(test).length);
	
							var head = 0;
							
							$.each(test,function(key,value){
								console.log(key +"::"+ value.parents.length);
								if(value.parents.length ==1){
									head = key;
								}
							})
							
							console.log(head);
							console.log(data);
							//$("#"+head+"_anchor").addClass("added");
							
					})
				.bind("select_node.jstree", function (e, data) {
					
	 					console.log(data.node.id);
	 					var id = data.node.id;
		
	 					urlpid = id;
	 					
	 					$.ajax({
	 						
	 						url : "getUrl.do",
	 						type : "POST",
	 						dataType:"json",
	 						data : {ubid : id},
	 						success : function(data){
	 							/* var json = JSON.parse(data)
	 							console.log(json); */
	 							console.log("under");
	 							if(firstclick == 1){
	 								console.log("dd");
	 							$('#jstree_container_child').jstree(true).settings.core.data = data;
	 							$('#jstree_container_child').jstree(true).refresh();
	 							}
	 							
	 							
	 							firstclick =1;
	 							$("#jstree_container_child").jstree({
	 								
	 								"core" : {
	 									'data' : data,
	 									'themes' : { 
	 										'name' : 'proton',
	 										'responsive' : true,
	 										"dots": false
	 									},
	 									"check_callback" : function(op, node, par, pos, more){
	 										if(op === "move_node"){ // dnd 이벤트 일때 
	 											console.log(op);//move_node
	 											console.log(node);//실제 select 한node
	 											console.log(par);// select node 사위 헐 여기서 나옴 childe
	 											console.log(par.a_attr.href);
	 											console.log(par.children_d);
	 			
	 											console.log(pos);
	 											console.log(more.ref);// drop한 노드의 정보
	 											if(par.a_attr.href != "#"){ // 최상단(root)와 동급 불가										
	 												return false;	
	 											}
	 											return true;
	 										}							
	 										return true;	
	 									}
	 								},
	 								"plugins" : [ "dnd","contextmenu" ],
	 								
	 								"contextmenu" : {
	 									
	 									"select_node" : false,
	 									"items" : function($node){
	 										
	 										var tree_child = $("#jstree_container_child").jstree(true);
	 										return{
	 											
	 									            "rename": {
	 									                "separator_before": false,
	 									                "separator_after": false,
	 									                "label": "이름 수정",
	 									                "action": function (obj) { 
	 									                	tree_child.edit($node);
	 									                   
	 									                }
	 									            }, 
	 									            "reurl": {
	 									                "separator_before": false,
	 									                "separator_after": false,
	 									                "label": "url 수정",
	 									                "action": function (obj) { 
	 									                	
	 									                	$('#form3')[0].reset();	// url 모달창 reset
	 									                	$('#editurl').modal();	//url 수정 모달창 띄우기
	 									                	 
	 									                	var inst = $.jstree.reference(obj.reference);
	 										                var url = inst.get_node(obj.reference).a_attr.href;
	 										                var id = inst.get_node(obj.reference).id;
	 										                
	 									                	 console.log(url);
	 									                	 $('#editurlval').val(url);
	 									                	 
	 									                	 $('#editurlsubmit').on("click",function(){
	 									                		 
	 									                		 var newurl = $('#editurlval').val();
	 									                		 var form = {ubid : id, url : newurl }
	 									                		 
	 									                		 $.ajax({
	 										                		 
	 										                		 url: "editUrl.do",
	 										                		 type: "POST",
	 										                		 data: form ,
	 										                		 success: function(data){
	 										                			 console.log(data);
	 										                			 $('#editurl').modal("toggle");
	 										                			 
	 										                			 
	 										                		 }
	 										                	 }) 
	 									                		 
	 									                		 
	 									                	 })
	 									                }
	 									            },
	 									            "remove": {
	 									                "separator_before": false,
	 									                "separator_after": false,
	 									                "label": "삭제",
	 									                "action": function (obj) { 
	 									                  	console.log("누름");
	 									                  	tree_child.delete_node($node);
	 									                   
	 									                }
	 									            },
	 									            "recommend" :{
	 									            	"separator_before": false,
	 									                "separator_after": false,
	 									                "label": "관리자 추천",
	 									                "action": function (obj) { 
	 									                	
		 									               	var inst = $.jstree.reference(obj.reference);
	 										                var url = inst.get_node(obj.reference).a_attr.href;
	 										                var text = inst.get_node(obj.reference).text;
	 										                
	 										                form = {url : url , text : text }
	 										                
	 										                $.ajax({
	 										                	
	 										                	url : "recommend.do",
	 										                	type : "POST",
	 										                	data : form,
	 										                	success : function(data){
	 										                		
	 										                		console.log(data);
	 										                		
	 										                	}
	 										                	
	 										                })
	 										               
	 									                }
	 									            	
	 									            }
	 							                 }		
	 									}
	 									
	 								}
	 							})
	 							.bind("select_node.jstree",function(e,data){
	 								
	 								var href = data.node.a_attr.href;
	 								
	 								console.log(href);
	 								
	 							window.open(href); 
	 								
	 							})
	 							.bind('delete_node.jstree',function(event,data){

			    		var node_id = data.node.id;
			    		var form = {node : node_id}
			    		
	 		    		$.ajax({
			    			url:'deleteNode.do',
			    			type:'POST',
			    			dataType : "json",
			    			data: form,
			    			success:function(result){
			    				console.log(result);
			    			}
			    			
			    		})  
			    		
			    	})
			    	.bind('rename_node.jstree', function(event, data){
			    		var node_id = data.node.id;
			    		var node_text = data.text;
			    		console.log(node_id);
			    		console.log(node_text);
			    		
			    		$.ajax({
		        			url : 'updateNodeText.do',
		        			type: 'POST',
		        			data: {'id' : node_id, 'text' : node_text},
		        			success : function(result){
		        				if(result == 1)
		        					alert('수정되었습니다.');
		        				else
		        					alert('수정 실패');
		        			}
		        		});   
			    	})
	 							
	 						}
	 					});
						 
					}) 
			    	.bind('rename_node.jstree', function(event, data){
			    		var node_id = data.node.id;
			    		var node_text = data.text;
			    		console.log(node_id);
			    		console.log(node_text);
			    		
			    		$.ajax({
		        			url : 'updateNodeText.do',
		        			type: 'POST',
		        			data: {'id' : node_id, 'text' : node_text},
		        			success : function(result){
		        				if(result == 1)
		        					alert('수정되었습니다.');
		        				else
		        					alert('수정 실패');
		        			}
		        		});   
			    	})
			    	.bind('before_open.jstree',function(obj,stric,c){
			    		console.log("dom 선택 불러오는 중");
			    		console.log(obj);
			    		console.log(stric);
			    		console.log(c);
			    		//console.log(this);
			    		//console.log(this.get_node(obj,true));
			    		
			    	})
			    	.bind('delete_node.jstree',function(event,data){

			    		var node_id = data.node.id;
			    		var form = {node : node_id}
			    		
	 		    		$.ajax({
			    			url:'deleteNode.do',
			    			type:'POST',
			    			dataType : "json",
			    			data: form,
			    			success:function(result){
			    				console.log(result);
			    			}
			    			
			    		})  
			    	})	;
				}
			})
			
			$("#testing").on("click",function(){
				
				console.log(urlpid);
				

          	  $('#form')[0].reset();// modal input text 창 초기화
          	  
          	  
          	  $('#linkAdd').modal(); // url 추가하는 modal 창이 나온다.
          	  
          	  var par =urlpid; // 내가 우 클릭한 node의 id를 새로 생성하는 url의 부모로 지정
          	  
          	  $('#linkAddSubmit').on("click",function(){ // modal에서 보내기 선택한 것임
          		  
          		  var sharing = 0; //일단 default 0은 비공유
          		  var url = $('#url').val(); //추가 url 값
          		  var title = $('#title').val(); // 추가 url 명값
          		 // var parent = par;
          		  console.log(url,title,par); //확인
          		  
          		  var result = $("#share").prop("checked"); //공유 체크여부 확인
          		  if(result){ sharing =1;}
          		  console.log("sharing 여부")
          		  console.log(sharing);
          		  
          		  var form = {url : url , urlname : title , pid : par, uid:  "user1@naver.com"}
          		  
          		  $.ajax({
          			  url: "addFolderOrUrl.do",
          			  type :"POST",
          			  data : form,
          			  success : function(data){//나중에 sequence 나 autoincrement 사용해서 하나 올린 값을 받아서 insert 해주고 data 보내주어 view단 node 생성해주기
          					
          				  $('#linkAdd').modal("toggle"); // 모달 창 닫아주기
          					console.log(data);	//id 확인
        			  }
    			  })
    			})
				
			});
				
			
		});
	</script>

	<div id="main">
		<tiles:insertAttribute name="content" />
	</div>
	
	<div class="modal fade" id="linkAdd" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">
						<b>URL 추가</b>
					</h4>
				</div>

				<div class="modal-body">
					<form id="form">
						<table class="table">
							<colgroup>
								<col width="30%">
								<col width="70%">
							</colgroup>
							<tr>
								<td class="info" style="vertical-align: middle;">URL :</td>
								<td><input type="text" id="url" name="url"
									class="form-control"></td>
							</tr>
							<tr>
								<td class="info" style="vertical-align: middle;">제목 :</td>
								<td><input type="text" id="title" name="title"
									class="form-control"></td>
							</tr>
							<tr>
								<td><input type="checkbox" id="share"> <label
									for="share">공유하기</label></td>
								<td></td>
							</tr>
						</table>
					</form>
					<div class="modal-footer">
						<!-- type="submit" value="Submit" -->
						<button type="button" class="btn btn-default btn-sm"
							data-dismiss="modal">취소</button>
						<button class="btn btn-default btn-sm" id="linkAddSubmit">추가하기</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="folderAdd" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">
						<b>폴더 추가</b>
					</h4>
				</div>

				<div class="modal-body">
					<form id="form2">
						<table class="table">
							<colgroup>
								<col width="30%">
								<col width="70%">
							</colgroup>
							<tr>
								<td class="info" style="vertical-align: middle;">폴더 이름</td>
								<td><input type="text" id="foldername" name="foldername"
									class="form-control"></td>
							</tr>
						</table>
					</form>
					<div class="modal-footer">
						<!-- type="submit" value="Submit" -->
						<button type="button" class="btn btn-default btn-sm"
							data-dismiss="modal">취소</button>
						<button class="btn btn-default btn-sm" id="folderAddsubmit">추가하기</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	
	<div class="modal fade" id="editurl" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">
						<b>URL 변경</b>
					</h4>
				</div>

				<div class="modal-body">
					<form id="form3">
						<table class="table">
							<colgroup>
								<col width="30%">
								<col width="70%">
							</colgroup>
							<tr>
								<td class="info" style="vertical-align: middle;">URL</td>
								<td><input type="text" id="editurlval" name="editurlval"
									class="form-control"></td>
							</tr>
						</table>
					</form>
					<div class="modal-footer">
						<!-- type="submit" value="Submit" -->
						<button type="button" class="btn btn-default btn-sm"
							data-dismiss="modal">취소</button>
						<button class="btn btn-default btn-sm" id="editurlsubmit">수정하기</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	
</body>
</html>