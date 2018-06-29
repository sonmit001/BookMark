function jstree(role , gid, uid,nname){

	form = {gid : gid}
	/* 그룹 시작시 jstree 가져오기 */
	$.ajax({
		url : "getTeamJstree.do",
		type:"POST",
		data :form,
		dataType:"json",
		success : function(data){	
			/*왼쪽 jstree 시작하기 jstree 생성하고 싶은 div의 id를 적어준다.*/					
			$("#jstree_container")
				.jstree({	
					"core": {
						"data" : data, //ajax로 가져온 json data jstree에 넣어주기
						'themes':{
							'name' : 'proton', //테마 이름
							'responsive' : true,
							"dots": false, // 연결선 없애기
						},
						"check_callback" : function(op, node, par, pos, more){ // 특정 이벤트 실행 전에 잡아 낼 수 있음
							console.log("//////////////////");
							console.log(par);
							console.log(pos);
							console.log(more);
							console.log(node);
							console.log(node.text);
							/*	private String nname;
	private String doing;
	private String target;
	private String location;
	private String type;
	private String newnameorplace;*/
							stompClient.send("/JSTREE/" + gid, {}, JSON.stringify({
					           	doing : op,
					           	target : node.text,
					           	location : par.text,
					           	nname: nname
					           	
					        }));
							
							//권한 검사해서 DND 가능자와 아닌자 구분
							if(op === "move_node"){ // dnd 이벤트 일때 
								var dragnode = node.id;
								var dropnode = par.id;
								
								form = {dragnode : dragnode , dropnode : dropnode};
								
								$.ajax({	
									
									url : 'dropNode.do',
									type : 'POST',
									data : form,
									beforeSend : function(){
										$('#loading').html(" SAVING<span><img src='../images/throbber.gif' /></span>");
									},
									success : function(data){
										$('#loading').html("");
									}
								})
								return true;
							}else if(op === "create_node"){   //폴더 생성시 실행 되는 callback 함수
								return true;
							}else if(op == "copy_node"){	// 오른쪽 url 왼쪽 폴더로 옮기면 실행되는데 이때도 drag drop으로 처리함
								
								$.ajax({										
									url : 'dropNode.do',
									type : 'POST',
									data : {dragnode : node.id, dropnode : par.id},
									beforeSend : function(){
										$('#loading').html(" SAVING<span><img src='../images/throbber.gif' /></span>");
									},
									success : function(){
										$('#loading').html("");
										$('#jstree_container').jstree().deselect_all(true);											
										$('#jstree_container').jstree(true).select_node(par.id);											
									}
								});
								return false;	
							}
							return true;	
						}
					},
					"plugins" : [ "dnd","contextmenu" ], //drag n drop , 과 우클릭시 플러그인 가져옴

					"contextmenu" : { //우클릭시 생성되는 것들 설정
						"select_node" : false, // 우클릭 했을 경우 왼클릭되는거 막음
						
						/*왼쪽 jstree  우클릭시 생성되는 메뉴 구성하기 START*/
						"items" : function($node){ //우클릭된 node(폴더)의 정보를 가져온다.
							
							var node_uid = $node.original.uid;
							var href = $node.a_attr.href;
							var tree = $("#jstree_container").jstree(true);
							
							if(node_uid = uid){console.log("ddddddddddd");}
							// 링크 만들기, 폴더 만들기, 이름 바꾸기, 삭제
							return {
								"link_create" : {
									"icon" : "fa fa-plus",
									"separator_before": false,
									"separator_after": false,
									"label": "URL 추가",
									"action": function (obj) { 
										
										/* tree_child.create_node( null , {text : title , id : node_id , a_attr : {href : url} , icon : "https://www.google.com/s2/favicons?domain="+ url} ,"last",function(new_node){
							     						 //console.log(new_node.id);
							     					 });
							     					*/
									}
								},
								"folder_create": {
									"icon" : "fa fa-plus-circle",
									"separator_before": false,
									"separator_after": false,
									"_disabled" : false, 
									"label": "그룹 추가",
									"action": function (obj) {
										var inst = $.jstree.reference(obj.reference);
										var par_node = inst.get_node(obj.reference);
										var par = inst.get_node(obj.reference).id;
										var form = {urlname : "새 폴더", pid : par ,gid:gid};// 해당 유저의 아이디 가져오기
										
										$.ajax({
											url: "addTeamFolderOrUrl.do",
											type :"POST",
											data : form,
											beforeSend : function(){
							     				},
							     				success : function(data){
							     					var node_id = $.trim(data.result);
							     					/*왼쪽 jstree 새폴더 생성과 동시에 이름 수정하게 하기*/
							     					tree.create_node(par_node , {text : "새 폴더" , id : node_id  ,icon : "fa fa-folder",uid: uid} ,"last",function(new_node){
							     						new_node.id = node_id;
							     						tree.edit(new_node);
						            				 	});
					              			 	 	}
						               		  	})
									}
								},
								"rename" : {
									"icon" : "fa fa-edit",
									"separator_before": false,
									"separator_after": false,
									"label": "이름 수정",
									"action" : function (obj) {
										/*왼쪽 jstree 이름 수정하기 아래에 함수 있음*/
										tree.edit($node);			
									}
								},
								"remove" : {
									"icon" : "fa fa-trash",
									"separator_before": false,
									"separator_after": false,
									"label": "삭제",
									"action": function (obj) { 
										tree.delete_node($node);
									}
								}
						        };						
						}
					}			    
				})	
				.bind("loaded.jstree", function (event, data) {
					$('#jstree_container').jstree("open_all");
					var test = data.instance._model.data
				})
				.bind("select_node.jstree", function (e, data) {
					console.log(data);
				}) 
			    .bind('rename_node.jstree', function(event, data){
		    		var node_id = data.node.id;
		    		var node_text = data.text;
		    		/*왼쪽 jstree 폴더 이름 수정하기*/			    		
		    		$.ajax({
	        			url : 'updateTeamNodeText.do',
	        			type: 'POST',
	        			data: {'gbid' : node_id, 'text' : node_text},
	        			beforeSend : function(){
     					},
	        			success : function(result){
	        				console.log(result.result);
	        			}
	        		});   
		    	})
		    	.bind('delete_node.jstree',function(event,data){
			    		/*왼쪽 jstree 폴더 삭제하기*/
		    		var node_id = data.node.id;
		    		var form = {gbid : node_id}
		    		
		    		$.ajax({
		    			url:'deleteTeamNode.do',
		    			type:'POST',
		    			dataType : "json",
		    			data: form,
		    			beforeSend : function(){
     					},
     					success : function(result){
     						console.log(result.result);
						}
					})  
		    	});
		}
	})
	/*왼쪽 jstree 폴더 열렸을 경우 아이콘 변경해 주기*/	
	$("#jstree_container").on('open_node.jstree', function(e,data){
		$.jstree.reference('#jstree_container').set_icon(data.node, "fa fa-folder-open")
	})
	/*왼쪽 jstree 폴더 닫혔을 경우 아이콘 변경해 주기*/
	$("#jstree_container").on('close_node.jstree', function(e,data){
		$.jstree.reference('#jstree_container').set_icon(data.node, "fa fa-folder")
	})	
}

		
