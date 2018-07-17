// 화면 전환시 채팅 스크롤 최하단으로 위치
$(".chat-element").scrollTop($(".chatting-contents").height());
$('#chat-textbox-text').each(function() {
    this.contentEditable = true;
});
$('#chat-textbox-text').click(function() {
    $('#chat-textbox-text').focus()
});
$('#chat-textbox-text').keyup(function (e) {return});
$('#chat-textbox-text').keydown(function (e) {
    if( e.shiftKey && e.keyCode == 13 ) {
        e.stopPropagation();
        $('#chat-textbox-text').append('\n');
    } else if(e.keyCode == 13) { // Ctrl-Enter pressed
        event.preventDefault();
        sendMessage();
        $('#chat-textbox-text').html('');
    }
});

$(function() {
    
	connect();
	jstreetable();
	myContextMenu();
	
	var lastDate = null;
	
	$.each(chatList, function(index, value){
		position = index;
		if(index < 50){
			chatList[index] = chatList[index].split('▣');
			// <div id="2018-06-27" class="divider"><hr class="left"/><span>2018-06-27</span><hr class="right"/></div>
			//console.log(chatList[index]);
			var chatListIndex = chatList[index];
			
			var time =  chatListIndex[2].split("T");
			
			if(lastDate == null){
				lastDate = time[0];
				var Now = new Date();
				var NowTime = Now.getFullYear();
				if(Now.getMonth() < 10){
					NowTime += '-0' + (Now.getMonth() + 1) ;
				}else {
					NowTime += '-' + (Now.getMonth() + 1) ;
				}
				
				if(Now.getDate() < 10){
					NowTime += '-0' + Now.getDate();
				}else {
					NowTime += '-' + Now.getDate();
				}
				
				var today = time[0];
				//console.log("today" + NowTime);
				if(NowTime == time[0]){
					today = "Today";
				}
				
				var date = '<div id="' + time[0]+ '" class="divider"><hr class="left"/><span>' + today + '</span><hr class="right"/></div>';
				$(".chatting-contents").prepend(date);
			}else if(lastDate != time[0]){
				console.log("lastdate: " + lastDate);
				console.log("time[0] :" + time[0])
				
				var date = '<div id="' + time[0]+ '" class="divider"><hr class="left"/><span>' + time[0] + '</span><hr class="right"/></div>';
				$(".chatting-contents").prepend(date);
				lastDate = time[0];
			}
			
			time[1] = time[1].split(":");
        	var hour = time[1][0];
        	var min = time[1][1];
        	var ampm = "";
        	if(hour > 12) {
        		ampm = "PM";
        		hour -= 12;
        	}else {
        		ampm = "AM";
        	}
			
			var chat_list_div = "";
			chat_list_div += '<img class="chatting-profile-img" onerror="this.src=\'/bit/images/profile.png\'" src="/bit/images/profile/' + chatListIndex[0] + '">';
			chat_list_div += '<div class="chatting-text-div">';
			chat_list_div += '<p class="chatting-userid">';
			chat_list_div += chatListIndex[1] + '<span class="chatting-time">' + hour + "시&nbsp;" + min + '분&nbsp;' + ampm + '</span>';
			chat_list_div += '</p>';
			chat_list_div += '<span class="chatting-text">';
			chat_list_div += chatListIndex[3];
			chat_list_div += '</span>';
			chat_list_div += '</div>';  	
			
            //console.log(chat_list_div);
            $("#" + time[0]).after(chat_list_div);
            $(".chat-element").scrollTop($(".chatting-contents").height());
		}else {
			position = 50;
			return false;
		}
		
		if(index == chatList.length -1){
			position = -1;
		}
		
	});

	var scrollTop = $('.chat-element').scrollTop();
	
	var scrollPos = $('.chat-element').scrollTop();
    var date_eq = $(".chatting-contents").children(".divider").length - 1;
    
	$('.chat-element').scroll(function() {
        var curScrollPos = $(this).scrollTop();
        var date_line = $(".divider:eq(" + date_eq + ")").position().top;

        if (curScrollPos > scrollPos) { //Scrolling Down
            if(date_line <= 35 ) {
                var temp = $(".divider:eq(" + date_eq + ") > span").text(); // 가장 맨 위의 내용
                //console.log("donw: " + date_line);
                //console.log(temp);
                $("#header-date").text(temp);
                if( date_eq < $(".chatting-contents").children(".divider").length - 1 ) { date_eq += 1; }
                
            }
        } else { //Scrolling Up
            if(date_line > 30 ) {
                if( date_eq > 0 ) { date_eq -= 1; }
                var temp = $(".divider:eq(" + date_eq + ") > span").text(); // 가장 맨 위의 내용
                //console.log("up: " + date_line);
                //console.log(temp);
                $("#header-date").text(temp);
                
            }
        }
        
        scrollPos = curScrollPos;
        
        if($(this).scrollTop() == 0){
        	//console.log("scroll TOP");
        	
        	if(position > 0){
        		
        		
        		
        		if(chatList.length - position > 50){
        			for(var index=position; index < position+50; index++){
        				chatList[index] = chatList[index].split('▣');
        				// <div id="2018-06-27" class="divider"><hr class="left"/><span>2018-06-27</span><hr class="right"/></div>
        				//console.log(chatList[index]);
        				var chatListIndex = chatList[index];
        				
        				var time =  chatListIndex[2].split("T");
        				
        				if(lastDate == null){
        					lastDate = time[0];
        					var Now = new Date();
        					var NowTime = Now.getFullYear();
        					if(Now.getMonth() < 10){
        						NowTime += '-0' + (Now.getMonth() + 1) ;
        					}else {
        						NowTime += '-' + (Now.getMonth() + 1) ;
        					}
        					NowTime += '-' + Now.getDate();
        					
        					var today = time[0];
        					//console.log("today" + NowTime);
        					if(NowTime == time[0]){
        						today = "Today";
        					}
        					
        					var date = '<div id="' + time[0]+ '" class="divider"><hr class="left"/><span>' + today + '</span><hr class="right"/></div>';
        					$(".chatting-contents").prepend(date);
        				}else if(lastDate != time[0]){
        					var date = '<div id="' + time[0]+ '" class="divider"><hr class="left"/><span>' + time[0] + '</span><hr class="right"/></div>';
        					$(".chatting-contents").prepend(date);
        					lastDate = time[0];
        				}
        				
        				time[1] = time[1].split(":");
        	        	var hour = time[1][0];
        	        	var min = time[1][1];
        	        	var ampm = "";
        	        	if(hour > 12) {
        	        		ampm = "PM";
        	        		hour -= 12;
        	        	}else {
        	        		ampm = "AM";
        	        	}
        				
        				var chat_list_div = "";
        				chat_list_div += '<img class="chatting-profile-img" onerror="this.src=\'/bit/images/profile.png\'" src="/bit/images/profile/' + chatListIndex[0] + '">';
        				chat_list_div += '<div class="chatting-text-div">';
        				chat_list_div += '<p class="chatting-userid">';
        				chat_list_div += chatListIndex[1] + '<span class="chatting-time">' + hour + "시&nbsp;" + min + '분&nbsp;' + ampm + '</span>';
        				chat_list_div += '</p>';
        				chat_list_div += '<span class="chatting-text">';
        				chat_list_div += chatListIndex[3];
        				chat_list_div += '</span>';
        				chat_list_div += '</div>';  	
        				
        	            //console.log(chat_list_div);
        	            $("#" + time[0]).after(chat_list_div);
        	            //$(".chat-element").scrollTop($(".chatting-contents").height());
        			}
        			position += 50;
        			$(this).scrollTop(50 * 42);
        		}else {
        			for(var index = position; index < chatList.length; index++){
        				chatList[index] = chatList[index].split('▣');
        				// <div id="2018-06-27" class="divider"><hr class="left"/><span>2018-06-27</span><hr class="right"/></div>
        				//console.log(chatList[index]);
        				var chatListIndex = chatList[index];
        				
        				var time =  chatListIndex[2].split("T");
        				
        				if(lastDate == null){
        					lastDate = time[0];
        					var Now = new Date();
        					var NowTime = Now.getFullYear();
        					if(Now.getMonth() < 10){
        						NowTime += '-0' + (Now.getMonth() + 1) ;
        					}else {
        						NowTime += '-' + (Now.getMonth() + 1) ;
        					}
        					NowTime += '-' + Now.getDate();
        					
        					var today = time[0];
        					//console.log("today" + NowTime);
        					if(NowTime == time[0]){
        						today = "Today";
        					}
        					
        					var date = '<div id="' + time[0]+ '" class="divider"><hr class="left"/><span>' + today + '</span><hr class="right"/></div>';
        					$(".chatting-contents").prepend(date);
        				}else if(lastDate != time[0]){
        					var date = '<div id="' + time[0]+ '" class="divider"><hr class="left"/><span>' + time[0] + '</span><hr class="right"/></div>';
        					$(".chatting-contents").prepend(date);
        					lastDate = time[0];
        				}
        				
        				time[1] = time[1].split(":");
        	        	var hour = time[1][0];
        	        	var min = time[1][1];
        	        	var ampm = "";
        	        	if(hour > 12) {
        	        		ampm = "PM";
        	        		hour -= 12;
        	        	}else {
        	        		ampm = "AM";
        	        	}
        				
        				var chat_list_div = "";
        				chat_list_div += '<img class="chatting-profile-img" onerror="this.src=\'/bit/images/profile.png\'" src="/bit/images/profile/' + chatListIndex[0] + '">';
        				chat_list_div += '<div class="chatting-text-div">';
        				chat_list_div += '<p class="chatting-userid">';
        				chat_list_div += chatListIndex[1] + '<span class="chatting-time">' + hour + "시&nbsp;" + min + '분&nbsp;' + ampm + '</span>';
        				chat_list_div += '</p>';
        				chat_list_div += '<span class="chatting-text">';
        				chat_list_div += chatListIndex[3];
        				chat_list_div += '</span>';
        				chat_list_div += '</div>';  	
        				
        	            //console.log(chat_list_div);
        	            $("#" + time[0]).after(chat_list_div);
        	            //$(".chat-element").scrollTop($(".chatting-contents").height());
        			}
        			$(this).scrollTop((chatList.length - position) * 42);
        			console.log((chatList.length - position) * 42);
        			position = -1;
        		}
        	}
        }
	});
});

//채팅방 연결
function connect() {
    //console.log("connect");
    // WebSocketMessageBrokerConfigurer의 registerStompEndpoints() 메소드에서 설정한 endpoint("/endpoint")를 파라미터로 전달
    var ws = new SockJS("/bit/endpoint");
    stompClient = Stomp.over(ws);
    stompClient.connect({}, function(frame) {
        // 메세지 구독
        stompClient.subscribe('/subscribe/chat/' + gid, function(message) {
        	var new_chat = JSON.parse(message.body);
        	
        	var time =  new_chat.datetime.split("T");
        	
			if(!$("#" + time[0]).length){
				var Now = new Date();
				var NowTime = Now.getFullYear();
				if(Now.getMonth() < 10){
					NowTime += '-0' + (Now.getMonth() + 1) ;
				}else {
					NowTime += '-' + (Now.getMonth() + 1) ;
				}
				if(Now.getDate() < 10){
					NowTime += '-0' + Now.getDate();
				}else {
					NowTime += '-' + Now.getDate();
				}
				
				var today = time[0];
				if(NowTime == time[0]){
					today = "Today";
				}
				
				var date = '<div id="' + time[0]+ '" class="divider"><hr class="left"/><span>' + today + '</span><hr class="right"/></div>';
				$(".chatting-contents").append(date);
        	}
        	
        	time[1] = time[1].split(":");
        	var hour = time[1][0];
        	var min = time[1][1];
        	var ampm = "";
        	if(hour > 12) {
        		ampm = "PM";
        		hour -= 12;
        	}else {
        		ampm = "AM";
        	}
        	//console.log(new_chat.nname);
        	var chat_div = "";
        	chat_div += '<img class="chatting-profile-img" onerror="this.src=\'/bit/images/profile.png\'" src="/bit/images/profile/' + new_chat.profile + '">';
        	chat_div += '<div class="chatting-text-div">';
        	chat_div += '<p class="chatting-userid">';
        	chat_div += new_chat.nname + '&nbsp;<span class="chatting-time">' + hour + "시&nbsp;" + min + '분&nbsp;' + ampm + '</span>';
        	chat_div += '</p>';
        	chat_div += '<span class="chatting-text">';
            chat_div += new_chat.content;
            chat_div += '</span>';
            chat_div += '</div>';
            
            $(".chatting-contents").append(chat_div);
            $(".chat-element").scrollTop($(".chatting-contents").height());
        });
        
        //JSTREE 알림 메시지 ex) 누구님이 무엇을 수정했습니다
 		stompClient.subscribe('/subscribe/JSTREE/' + gid,function(message){
 			var body = JSON.parse(message.body);
            var whosend = body.nname;
            if(nname == whosend){
            	
            }else{
	             
	            form = {gid : gid}
	            $.ajax({
	             
	            	url : "getTeamJstree.do",
	         		type:"POST",
	         		data :form,
	         		dataType:"json",
	         		success : function(data){
	         			$("#jstree_container").jstree(true).settings.core.data = data;
						$("#jstree_container").jstree(true).refresh();
	         		}
	             })
            }
        });
 		
 		stompClient.send('/online/' + gid , {}, JSON.stringify({nname: nname, status: "ON"}));
 		stompClient.subscribe('/subscribe/online/' + gid, function(message) {
 			var new_connect = JSON.parse(message.body);
 			var temp_member = new_connect.nname;
 			
 			var your_grid = ($('#' + temp_member).attr("data-grid") != null) ? $('#' + temp_member).attr("data-grid") : "3";
			var insertOnline = '<p id="'+ temp_member +'"' + ' class="member" data-grid="' +your_grid+ '">'
								+ '<i class="fas fa-circle online-ico"></i>'
								+ temp_member
						  +'</p>';
			
			//var $who = $('#' + temp_member).clone();
			$('#' + temp_member).remove();
			$('#online-member').prepend(insertOnline);
			
			if(your_grid == "1") {
				$("#" + temp_member).append('<i class="fas fa-crown group-master"></i>');
			}else if(your_grid == "2") {
				$("#" + temp_member).append('<i class="fas fa-chess-knight group-manager"></i>');
			}
 		});

 		stompClient.subscribe('/subscribe/offline/' + gid, function(message) {
 			var new_connect = JSON.parse(message.body);
 			var temp_member = new_connect.nname;

 			var your_grid = ($('#' + temp_member).attr("data-grid") != null) ? $('#' + temp_member).attr("data-grid") : "3";
			var insertOffline = '<p id="' + temp_member + '"' + ' class="member" data-grid="' +your_grid+ '">'
								+ '<i class="fas fa-circle offline-ico"></i>'
								+ temp_member
						  +'</p>';
			
			//var $who = $('#' + temp_member).clone();
			$('#' + temp_member).remove();
			$('#offline-member').prepend(insertOffline);
			
			if(your_grid == "1") {
				$("#" + temp_member).append('<i class="fas fa-crown group-master"></i>');
			}else if(your_grid == "2") {
				$("#" + temp_member).append('<i class="fas fa-chess-knight group-manager"></i>');
			}
 		});
 		
 		// Header Alarm socket connect
 		if(userid != null || userid != "") {
 			alarmConnect(stompClient, userid);
 		}
 		
    }, function(message) {
        stompClient.disconnect();

    });
    
    
    ws.onclose = function() {
    	disconnect();
        location.href = "/bit/user/mybookmark.do";
    };
}
 
// 채팅 메세지 전달
function sendMessage() {
	//console.log("click");
	
    var str = $("#chat-textbox-text").html().trim();
    str = str.replace(/ /gi, '&nbsp;')
    str = str.replace(/\n|\r/g, '<br>');
    str = str.replace(/"/gi, '&uml;');
    if(str.length > 0) {
        stompClient.send("/chat/" + gid, {}, JSON.stringify({
           	content: str,
           	nname: nname,
           	profile: profile
        }));
    }
}
 
// 채팅방 연결 끊기
function disconnect() {
	stompClient.send("/offline/" + gid, {}, JSON.stringify({
       	nname: nname,
       	status: "OFF"
    }));
	
    stompClient.disconnect();
}

/* Chatting End */


