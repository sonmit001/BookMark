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


<title>my BookMark</title>
</head>
<body>
	<script type="text/javascript">
		$(document).ready(function(){
			
			var urlpid = null;
			var firstclick = 0;
			var child_data = null;
			
			$.ajax({
				url : "getCategoryList.do",
				type:"POST",
				dataType:"json",
				data : {uid : "user1@naver.com"},
				success : function(data){	
					console.log(data);
			
					$("#jstree_container").on("click",'.jstree-anchor',function(e){
						$('#jstree_container').jstree(true).toggle_node(e.target);	
						
					}).
					jstree({	
							"core": {
								"dblclick_toggle" : false,
							'data' : data,
							'themes':{
								'name' : 'proton',
								'responsive' : true,
								"dots": false,
								
							},
							"check_callback" : function(op, node, par, pos, more){
								console.log("아래에 무엇이 실행되는 것인지");
								console.log(op);
								if(op === "move_node"){ // dnd 이벤트 일때 
									console.log(op);//move_node
									console.log(node);//실제 select 한node
									console.log(par);// select node 사위 헐 여기서 나옴 childe
	
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
								}else if(op === "create_node"){   
									
									$("#jstree_container_child").jstree(true).redraw_node(par, true);
									console.log("dd");
									console.log(node);
									console.log(par);
								
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
								  var tree_child = $("#jstree_container_child").jstree(true);
						    	  
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
									            		  var htag = $('#htag').val();
									            		  var sname = $.trim($('#sname').val());
									            		 // var parent = par;
									            		  console.log(url,title,par,htag,sname); //확인
									            		  
									            		  if($.trim(htag) == ""){
									            			  var form = {url : url , urlname : title , pid : par , uid : "user1@naver.com" }
									            		  }else{
									            			  var form = {url : url , urlname : title , pid : par, uid:  "user1@naver.com" , htag : htag , sname : sname}
									            		  }
									            		  
									            		  $.ajax({
									            			  url: "addFolderOrUrl.do",
									            			  type :"POST",
									            			  data : form,
									            			  success : function(data){//나중에 sequence 나 autoincrement 사용해서 하나 올린 값을 받아서 insert 해주고 data 보내주어 view단 node 생성해주기
									            					
									            				  $('#linkAdd').modal("toggle"); // 모달 창 닫아주기
									            				  var node_id = $.trim(data);
									            				  
									            				  tree_child.create_node( null , {text : title , id : node_id , a_attr : {href : url} , icon : "https://www.google.com/s2/favicons?domain="+ url} ,"last",function(new_node){
									          				 		console.log(new_node.id);
									          				 	});
									            					console.log(data);	//id 확인
								              			  }
								          			  })
								          			})
								                }
								            },
								            "folder_create": {
								                "separator_before": false,
								                "separator_after": false,
								                "_disabled"			: false, 
								                "label": "그룹 추가",
								                "action": function (obj) { 
								                	
								                	var inst = $.jstree.reference(obj.reference);
								                	console.log(inst.get_node(obj.reference));//내가 우클릭한 노드의 값
								                	var par_node = inst.get_node(obj.reference);
								                	
								                	var par = inst.get_node(obj.reference).id;
	 												var form = {urlname : "새 폴더", pid : par , uid : "user1@naver.com"}	// 해당 유저의 아이디 가져오기
	 												
								               		  $.ajax({
								            			  url: "addFolderOrUrl.do",
								            			  type :"POST",
								            			  data : form,
								            			  success : function(data){
								            				  
								            				 var node_id = $.trim(data);
									            				 
								            				 	tree.create_node(par_node , {text : "새 폴더" , id : node_id } ,"last",function(new_node){
								            				 		console.log(new_node.id);
								            				 		new_node.id = node_id;
								            				 		tree.edit(new_node);
								            				 	});
							              			 	 }
							          			 	})
								                }
								            },
								            "rename": {
								                "separator_before": false,
								                "separator_after": false,
								                "label": "이름 수정",
								                "action": function (obj) { 		
								                	
								                	tree.edit($node);			
								                	console.log($node);
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

	 							child_data = data;
	 							console.log("under");
	 							
	 								console.log("refresh");
	 								console.log($("#jstree_container_child").jstree(true).settings);
	 								$("#jstree_container_child").jstree(true).settings.core.data = data;
	 								$("#jstree_container_child").jstree(true).refresh();
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
			    	.bind('before_open.jstree',function(obj,stric,c){
			    		console.log("dom 선택 불러오는 중");
			    		console.log(obj);
			    		console.log(stric);
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
			
			
			$('#addroot').on("click",function(){
				
				  var tree = $("#jstree_container").jstree(true);
				  
				  var form = {uid : "user1@naver.com"};
				  
				  $.ajax({
						
					  url : "addRoot.do",
					  type : "POST",
					  data : form,
					  success : function(data){
						  
						  var ubid = $.trim(data);
						  
						  tree.create_node( null , {text : "새 카테고리" , id : ubid , icon : "fa fa-folder-o"} ,"last",function(new_node){
							  new_node = ubid;
							  tree.edit(new_node);
	
							  
	      				 	});
					  }
				  })
			})
			
			
			
			
			
			$("#testing").on("click",function(){
				
				var tree_child = $("#jstree_container_child").jstree(true);
				
				console.log(urlpid);
				
				if(urlpid == null){
					alert("노드를 선택해 주세요");
					return false;
					
				}
				
          	  $('#form_btn')[0].reset();// modal input text 창 초기화
          	  
          	  
          	  $('#linkAdd_btn').modal(); // url 추가하는 modal 창이 나온다.
          	  
          	  var par =urlpid; // 내가 우 클릭한 node의 id를 새로 생성하는 url의 부모로 지정
          	  
           	  $('#linkAddSubmit_btn').on("click",function(){ // modal에서 보내기 선택한 것임
          		  
          		  var sharing = 0; //일단 default 0은 비공유
          		  var url = $('#url_btn').val(); //추가 url 값
          		  var title = $('#title_btn').val(); // 추가 url 명값
          		  var htag = $('#htag_btn').val();
        		  var sname = $.trim($('#sname_btn').val());
          		 // var parent = par;
          		  console.log(url,title,par,sname,htag); //확인
          		  
          		  var result = $("#share").prop("checked"); //공유 체크여부 확인
          		  
          		  if($.trim($('#htag_btn').val())==""){
          		  var form = {url : url , urlname : title , pid : urlpid , uid:  "user1@naver.com"};
          		  }else{
          			var form = {url : url , urlname : title , pid : urlpid , uid:  "user1@naver.com",htag : htag , sname : sname};
          		  }
          		  $.ajax({
          			  url: "addFolderOrUrl.do",
          			  type :"POST",
          			  data : form,
          			  success : function(data){//나중에 sequence 나 autoincrement 사용해서 하나 올린 값을 받아서 insert 해주고 data 보내주어 view단 node 생성해주기
          					
          				  $('#linkAdd_btn').modal("toggle"); // 모달 창 닫아주기
          					console.log(data);	//id 확인
          					var node_id = $.trim(data);
          					tree_child.create_node( null , {text : title , id : node_id , a_attr : {href : url} , icon : "https://www.google.com/s2/favicons?domain="+ url} ,"last",function(new_node){
        				 		console.log(new_node.id);
        				 	});
        			  }
    			  })
    			}) 
				
			});
					
					
	
					
					
					
			
					
			$("#jstree_container_child").jstree({
					
					"core" : {
						'data' : child_data,
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
							
							console.log($node.original.htag);
							var htag = $node.original.htag;
							
							if(htag == '#'){
							
							return{
								
						            "rename": {
						                "separator_before": false,
						                "separator_after": false,
						                "label": "이름 수정",
						                "action": function (obj) { 
						                	console.log("d이름수정");
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
//							                			node 도 url 수정해야 한다. 
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
							}else{
								
								return{
									
						            "rename": {
						                "separator_before": false,
						                "separator_after": false,
						                "label": "이름 수정",
						                "action": function (obj) { 
						                	console.log("d이름수정");
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
						            },
						            "share":{
						            	 "separator_before": true,
							              "separator_after": false,
							                "label": "공유",
							                "action"			: false,
							                "submenu" :{
							                	"editing" :{
							                		"separator_before"	: false,
													"separator_after"	: false,
							                		"label": "수정하기",
							                		"action" : function(data){
							                			
							                		}
							                	},
							                	"dimiss" :{
							                		"separator_before"	: false,
													"separator_after"	: false,
							                		"label" : "취소하기",
							                		"action" : function(data){
							                			
							                		}
							                	}
							                	
							                }
						            	
						            	
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
				.bind("delete_node.jstree",function(event,data){
				console.log("자식에서 삭제되는 것인지");

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
								<td class="info" style="vertical-align: middle;">해시태그 :</td>
								<td><input type="text" id="htag" name="htag"
									class="form-control"></td>
							</tr>
							<tr>
								<td class="info" style="vertical-align: middle;">공유제목 :</td>
								<td><input type="text" id="sname" name="sname"
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
	
	
		<div class="modal fade" id="linkAdd_btn" role="dialog">
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
					<form id="form_btn">
						<table class="table">
							<colgroup>
								<col width="30%">
								<col width="70%">
							</colgroup>
							<tr>
								<td class="info" style="vertical-align: middle;">URL :</td>
								<td><input type="text" id="url_btn" name="url_btn"
									class="form-control"></td>
							</tr>
							<tr>
								<td class="info" style="vertical-align: middle;">제목 :</td>
								<td><input type="text" id="title_btn" name="title_btn"
									class="form-control"></td>
							</tr>
							<tr>
								<td class="info" style="vertical-align: middle;">해시태그 :</td>
								<td><input type="text" id="htag_btn" name="htag_btn"
									class="form-control"></td>
							</tr>
							<tr>
								<td class="info" style="vertical-align: middle;">공유제목 :</td>
								<td><input type="text" id="sname_btn" name="sname_btn"
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
						<button class="btn btn-default btn-sm" id="linkAddSubmit_btn">추가하기</button>
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