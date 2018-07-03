


//Header Alarm socket connect
function alarmConnect(stompClient, userid) {

    // 메세지 구독
    // WebSocketMessageBrokerConfigurer의 configureMessageBroker() 메소드에서 설정한 subscribe prefix("/subscribe")를 사용해야 함
    stompClient.subscribe('/subscribe/alarm/' + userid, function(message) {
    	
    	var recv_alarm = JSON.parse(message.body);
    	var recv_gid = recv_alarm.gid;
    	var recv_toid = recv_alarm.toid;
    	var recv_fromid = recv_alarm.fromid;
    	var recv_gname = recv_alarm.gname;
    	var recv_ganame = recv_alarm.gmemo
    	var recv_senddate = recv_alarm.senddate;
    	
    	if($('#alarm_menu_li').children('ul').length == 0) {
    		$('#alarm_menu_li').append('<ul role="menu" class="g_alarm_ul sub-menu"></ul>');
		}
    	
    	var common_form = '<li id="alarmlist' +recv_gid+ '" class="g_alarm_li">'
    						+ '<span class="g_alarm_head">Group&nbsp;: <span class="g_alarm_name">' +recv_gname+ '</span></span>'
    						+ '<i class="fas fa-times g_notice" onclick="deleteMemo(\''+recv_gid+'\',\''+recv_fromid+'\',\''+recv_ganame+'\');"></i>'
    						+ '<br style="clear:both">';
    	
    	if( recv_ganame == "초대" ) {
    		common_form += '<span class="g_alarm_head">From&nbsp;&nbsp;&nbsp;: '
							+ '<span class="g_alarm_name">' +recv_fromid+ '</span>'
							+ '<span class="g_alarm_date">' +recv_senddate+ '</span>'
							+ '</span><br><span class="g_alarm_content">해당 그룹에서 회원님을 초대했습니다!'
							+ '<i class="fas fa-check g_notice_ok" '
							+ 'onclick="inviteOk(\''+recv_toid+'\',\''+recv_gid+'\',\''+recv_gname+'\',\''+recv_fromid+'\',\''+recv_ganame+'\');"></i>'
							+ '</span><br style="clear:both">';
    	
    	}else if( recv_ganame == "강퇴" ) {
    		if( gid == recv_gid ) {
    			$.alert("현재 그룹에서 강퇴당하셨습니다!");
    			setTimeout(function(){ location.replace("/bit/index.do"); }, 3000);
    			return
    			
    		}else {
    			common_form += '<span>해당 그룹에서 회원님을 강퇴했습니다!</span>'
							+ '<i class="fas fa-ban g_notice_no" '
							+ 'onclick="deleteMemo(\''+recv_gid+'\',\''+recv_fromid+'\',\''+recv_ganame+'\');"></i>';
    		}
    	}else {
    		
    		if( gid == recv_gid ) {
    			$.alert("현재 그룹이 그룹장에 의해 완료되었습니다!");
    			setTimeout(function(){ location.replace("/bit/index.do"); }, 3000);
    			return
    			
    		}else {
        		common_form += '<span class="g_alarm_head">From&nbsp;&nbsp;&nbsp;: '
								+ '<span class="g_alarm_name">'+recv_fromid+'</span>'
								+ 'onclick="deleteMemo(\''+recv_gid+'\',\''+recv_fromid+'\',\''+recv_ganame+'\');"></i>'
							 + '</span><br><span>해당 그룹이 완료되었습니다!</span>';
    		}
    	}
    	
    	common_form += '</li>';
    	//console.log(common_form);
    	$('.g_alarm_ul').prepend(common_form);
    	
    });
   
    
}

//Header Alarm socket disconnect
function alarmDisconnect() {
    stompClient.disconnect();
}