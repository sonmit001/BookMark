
/* 초대 수락 */
function inviteOk(user_nname, gid, gname, fromid, alarm_kind){
	var alarm_item = $("#alarmlist" + gid);
	$.ajax({
		url: "/bit/alarm/joinGroup.do",
		type: "post",
		data : { gid: gid, nname: user_nname, gname: gname },
		success : function(data){
			if( data.result.trim() == "joined" ) {
				$.alert("'" + gname + "'에 가입되셨습니다!");
				deleteMemo(gid, fromid, alarm_kind);
				location.href = data.path;
				
			}else if( data.result.trim() == "already" ){
				$.alert("이미 '" + gname + "' 그룹원이십니다!");
				deleteMemo(gid, fromid, alarm_kind);
			}else {
				$.alert("잠시후 다시 시도해주세요!");
			}
		}
	});
}
/* 쪽지 없애기 */
function deleteMemo(gid, fromid, alarm_kind){
	var alarm_item = $("#alarmlist" + gid);
	var gaid = 0;
	if(alarm_kind == '강퇴') {gaid = 3;}
	else if(alarm_kind == '완료') {gaid = 2;}
	else {gaid = 1;}
	$.ajax({
		url: "/bit/alarm/deleteMemo.do",
		type: "post",
		data : { gid: gid, fromid: fromid, gaid: gaid },
		success : function(data){
			if( data.result == "deleted" ) {
				alarm_item.remove();
				
				if($('.g_alarm_ul').children().length == 0) {
					$('.g_alarm_ul').remove();
				}
			}else {
				/*$.alert("잠시후 다시 시도해주세요!");*/
			}
		}
	});
	return
}


$(function() {
	/* 그룹 완료 쪽지 확인 */
	
	
	/* 그룹 강퇴 쪽지 확인 */
})


/*Alarm icon script START*/

$(function() {
	$('#alarm_menu').click(function(){
		$("#counter").fadeOut("slow");
	})
})

/*Alarm icon script END*/