


$(function() {
	// Header Alarm socket connect
	if(userid != null || userid != "") {
		alarmConnect(userid);
	}
	
});

//Header Alarm socket connect
function alarmConnect(userid) {
    // WebSocketMessageBrokerConfigurer의 registerStompEndpoints() 메소드에서 설정한 endpoint("/endpoint")를 파라미터로 전달
    var ws = new SockJS("/bit/endpoint");
    stompClient = Stomp.over(ws);
    stompClient.connect({}, function(frame) {
        // 메세지 구독
        // WebSocketMessageBrokerConfigurer의 configureMessageBroker() 메소드에서 설정한 subscribe prefix("/subscribe")를 사용해야 함
    	var user_nname = userid;
    	console.log(userid);
    	
        stompClient.subscribe('/subscribe/alarm/' + user_nname, function(message) {
        	
        	var recv_alarm = JSON.parse(message.body);
        	var gid = recv_alarm.gid;
        	var toid = recv_alarm.toid;
        	var fromid = recv_alarm.fromid;
        	var gname = recv_alarm.gname;
        	var ganame = recv_alarm.gmemo
        	var senddate = recv_alarm.senddate;
        	
        	if($('#alarm_menu_li').children('ul').length == 0) {
        		$('#alarm_menu_li').append('<ul role="menu" class="g_alarm_ul sub-menu"></ul>');
			}
        	
        	var common_form = '<li id="alarmlist' +gid+ '" class="g_alarm_li">'
        						+ '<span class="g_alarm_head">Group&nbsp;: <span class="g_alarm_name">' +gname+ '</span></span>'
        						+ '<i class="fas fa-times g_notice" onclick="deleteMemo(\''+gid+'\',\''+fromid+'\',\''+ganame+'\');"></i>'
        						+ '<br style="clear:both">';
        	
        	if( ganame == "초대" ) {
        		common_form += '<span class="g_alarm_head">From&nbsp;&nbsp;&nbsp;: '
								+ '<span class="g_alarm_name">' +fromid+ '</span>'
								+ '<span class="g_alarm_date">' +senddate+ '</span>'
								+ '</span><br><span class="g_alarm_content">해당 그룹에서 회원님을 초대했습니다!'
								+ '<i class="fas fa-check g_notice_ok" '
								+ 'onclick="inviteOk(\''+toid+'\',\''+gid+'\',\''+gname+'\',\''+fromid+'\',\''+ganame+'\');"></i>'
								+ '</span><br style="clear:both">';
        	
        	}else if( ganame == "강퇴" ) {
        		common_form += '<span>해당 그룹에서 회원님을 강퇴했습니다!</span>'
        						+ '<i class="fas fa-ban g_notice_no" '
        						+ 'onclick="deleteMemo(\''+gid+'\',\''+fromid+'\',\''+ganame+'\');"></i>';
        	
        	}else {
        		common_form += '<span class="g_alarm_head">From&nbsp;&nbsp;&nbsp;: '
								+ '<span class="g_alarm_name">'+fromid+'</span>'
								+ 'onclick="deleteMemo(\''+gid+'\',\''+fromid+'\',\''+ganame+'\');"></i>'
							 + '</span><br><span>해당 그룹이 완료되었습니다!</span>';
        	}
        	
        	common_form += '</li>';
        	//console.log(common_form);
        	$('.g_alarm_ul').prepend(common_form);
        	
        });
    }, 
    function(message) {
        stompClient.disconnect();
    });
    ws.onclose = function() {
    	alarmDisconnect();
        location.href = "/bit/index.do";
    };
}

//Header Alarm socket disconnect
function alarmDisconnect() {
    stompClient.disconnect();
}