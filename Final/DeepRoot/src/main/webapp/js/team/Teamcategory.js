var urlpid = null;
var gid2 = null;
var role = null;
function jstree(grid , gid, uid ,nname){
	gid2 =gid;
	role = grid;
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
							var target = node.text;
							var type= '#';
							var new_name = '#';
							var location = par.text;
							
							if(node.a_attr.href =='#')
								type='폴더';
							else
								type='URL';
							
												
							if(op=='move_node'){
							// dnd 일경우 more.core =ture 일 경우에만 메세지 보내기
								
								if(par.a_attr.href != "#"){ // 최상단(root)와 동급 불가										
									return false;	
								}
								if(more.core){
									new_name = pos.text
									
									sendmessage()
								}//dnd 성공
								
							}else if	(op == 'rename_node'){
								new_name = pos;
								sendmessage()
							}else if(op =='delete_node'){
								sendmessage()
							}else if(op == 'create_node'){
								sendmessage()
							}
							
							function sendmessage() {
								var op_msg = "";
		                        
		                        switch(op){
		                            case 'create_node':   doing = "생성"; 
		                            break;
		                            case 'rename_node':   doing = "수정";
		                            break;
		                            case 'delete_node':   doing = "삭제";
		                            break;
		                            case 'move_node':   doing = "이동"; 
		                            break;
		                        }
		                        
		                        if(new_name == "#" || new_name == null){
		                        	op_msg =  location + "폴더에서 "+target+"("+type+")를 "+doing+"하였습니다.";             
		         	            }else{
		         	            	op_msg =  location + "폴더에서 "+target+"("+type+")를 "+new_name+"으로 "+doing+"하였습니다.";    
		         	            }
		                        console.log("jstree 보내기전 ");
								stompClient.send("/JSTREE/" + gid, {}, JSON.stringify({
						           	nname: nname
						        }));
								console.log("jstree 보낸후 ");
								//희준이 message 틀
								stompClient.send("/chat/" + gid, {}, JSON.stringify({
									content: op_msg,
						           	nname: nname,
						           	profile: profile
						        }));
								console.log("chat 보낸후 ");
							}
							
							//DND 처리 
							if(op === "move_node"){ // dnd 이벤트 일때 
								var dragnode = node.id;
								var dropnode = par.id;
								
								form = {dragnode : dragnode , dropnode : dropnode};
								
								$.ajax({	
									
									url : 'dropTeamNode.do',
									type : 'POST',
									data : form,
									beforeSend : function(){
									},
									success : function(data){
									}
								})
								return true;
							}
							return true;	
						}
					},
					"plugins" : [ "dnd","contextmenu" ], //drag n drop , 과 우클릭시 플러그인 가져옴

					/*우클릭 메뉴 설정*/
					"contextmenu" : { 
						"select_node" : false, // 우클릭 했을 경우 왼클릭되는거 막음
						
						/*왼쪽 jstree  우클릭시 생성되는 메뉴 구성하기 START*/
						"items" : customMenu
					}			    
				})	
				.bind("loaded.jstree", function (event, data) {
					$('#jstree_container').jstree("open_all");
					var test = data.instance._model.data
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
	        				//console.log(result.result);
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
     						//console.log(result.result);
						}
					})  
		    	})
		    	.bind("select_node.jstree",function(e,data){
		    		var href = data.node.a_attr.href;
		    		if(href !='#'){
						window.open(href); 
		    		}
			
		    	})
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

function addUrlLevel1() {
	$(".addUrlLevel1").show();
	$(".addUrlLevel2").hide();
	$(".addUrlLevel2").hide();
}

function openAddUrlLevel2() {
	
	var url = $("#url_btn").val().trim();
	
	if(url == ""){
		$.alert("URL을 입력해주세요");
	}else {
		$.ajax({
    		url: "/bit/admin/preview.do",
			type: "post",
			data : {
				url : url // URL 주소
			},
			beforeSend: function() {
				
				$("#title_btn").css("cursor", "wait ");
         		$("#title_btn").val("");
         		//console.log("부모 ID : " + urlpid);
         		
         		var text = $("#jstree_container").jstree(true).get_node(urlpid).text;
         		//console.log("카테고리 : " + text + "/////")
         		$("#category_btn").val(text);
         		addUrlLevel2();
            },
            complete: function() {
            	$("#title_btn").css("cursor", "default");
            },
			success : function(data){
				$("#title_btn").val(data.title);
			},
    	});
	}
	
}

//2단계 폼 보여주기
function addUrlLevel2() {
	$(".addUrlLevel2").show();
	$(".addUrlLevel1").hide();
}

//url 추가
function addUrl(){
	var url = $('#url_btn').val(); //추가 url 값
	var title = $('#title_btn').val(); // 추가 url 명값
	var tree = $("#jstree_container").jstree(true);
	var form = {url : url , urlname : title , pid : urlpid, gid:gid2}
	//console.log(form);
	 if(title == ""){
		 $.alert("제목을 입력해주세요")
	 }else {
	$.ajax({
		url: "addTeamFolderOrUrl.do",
		type :"POST",
		data : form,
		beforeSend : function(){
				},
				success : function(data){
					$('#linkAdd_btn').modal("toggle"); 
					var par_node = $('#jstree_container').jstree(true).get_node(urlpid);
					var node_id = $.trim(data.result);
					tree.create_node(par_node , {text : title , id : node_id  , icon : "https://www.google.com/s2/favicons?domain="+ url ,uid: uid ,a_attr : {href: url}} ,"last",function(new_node){
				 	});
			 	 	}
   		  	})
	 }
	
}

function customMenu($node){
	var node_uid = $node.original.uid;
	var href = $node.a_attr.href;
	var tree = $("#jstree_container").jstree(true);	
	urlpid = $node.id;
	// 링크 만들기, 폴더 만들기, 이름 바꾸기, 삭제
	var items = { 
			"link_create" : {
				"icon" : "fa fa-plus",
				"separator_before": false,
				"separator_after": false,
				"label": "URL 추가",
				"action": function (obj) {
					$('#form_btn')[0].reset();// form 내부 값 reset
					$('#linkAdd_btn').modal(); //url 추가 모달 창 띄우기
					addUrlLevel1()
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
					var form = {urlname : "새 폴더", pid : par , gid : gid};// 해당 유저의 아이디 가져오기
					
					$.ajax({
						url: "addTeamFolderOrUrl.do",
						type :"POST",
						data : form,
						beforeSend : function(){
						},
		     			success : function(data){
		     				var node_id = $.trim(data.result);
		     				
		     				/*왼쪽 jstree 새폴더 생성과 동시에 이름 수정하게 하기*/							     			
		     				tree.create_node(par_node , {text : "새 폴더" , id : node_id  ,icon : "fa fa-folder",uid: uid ,a_attr : {href: '#'}} ,"last",function(new_node){
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
			"editurl" : {
				"separator_before"	: false,
				"separator_after"	: false,
				"label" : "URL 수정",
				"action" : function(obj){
					
					$('#form3')[0].reset();	// url 모달창 reset
					$('#editurl').modal();	//url 수정 모달창 띄우기
					
					var inst = $.jstree.reference(obj.reference);
					var url = inst.get_node(obj.reference).a_attr.href;
					var id = inst.get_node(obj.reference).id;
					
					$('#editurlval').val(url);
					
					$('#editurlsubmit').on("click",function(){
						
						var newurl = $('#editurlval').val();
						var form = {gbid : id, url : newurl }
						
						$.ajax({
							
							url: "editTeamUrl.do",
							type: "POST",
							data: form ,
							beforeSend : function(){
							},
							success: function(data){
								$('#editurl').modal("toggle");
								//href 가 반드시 http 로 시작해야한다.
								$(inst.get_node(obj.reference).a_attr).attr("href", newurl);
								$.jstree.reference('#jstree_container').set_icon(inst.get_node(obj.reference), "https://www.google.com/s2/favicons?domain="+ newurl);
							}
						}) 
					})
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
	if(role == '3'){ // 일반 그룹
		if(href == '#'){ // 폴더
			if(uid == node_uid){ // 자기꺼
				delete items.remove;
				delete items.editurl;
			}else{ // 남이 생성한거
				delete items.rename;
				delete items.remove;
				delete items.editurl;
			}
		}else{ // 링크	
			if(uid == node_uid){ // 자기꺼			
				delete items.folder_create;	
				delete items.link_create;
			}else{ // 남이 생성한거	
				delete items.link_create;
				delete items.folder_create;
				delete items.remove;	
				delete items.rename;
				delete items.editurl;
			}
		}
	}else{//매니저 그룹장
		if(href == '#'){// 폴더
			delete items.editurl;
		}else{ //url
			delete items.folder_create;
			delete items.link_create;
		}
	}
	return items;
}