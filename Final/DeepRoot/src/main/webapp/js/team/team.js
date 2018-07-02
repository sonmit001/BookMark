/*team.jsp 에서 gid, uid 를 가져오기 위한 함수*/
var gid;
var uid;

function get_info(gid, uid){
	gid = gid;
	uid = uid;
}


/* 멤버 초대 */
function member_insert(){
    $.confirm({
        title: '멤버 초대',
        content: '' +
        '<form id="insertMember" action="" class="formGroup" method="post">' +
        '<div class="form-group">' +
        '<label>추가 할 멤버의 이메일을 입력하세요</label>' +
        '<input type="text" name="uid" class="insertUid form-control"/>' +
        '<input type="hidden" name="gid" value="'+gid+'" class="banName form-control"/>' +
        '</div>' +
        '</form>',
        closeIcon: true,
        closeIconClass: 'fa fa-close',
        buttons: {
            formSubmit: {
                text: '초대',
                btnClass: 'btn-success',
                keys: ['enter'],
                action: function () {
                    var toid = this.$content.find('.insertUid').val();
                    if(!toid){
	                    $.alert('닉네임을 적어주세요');
	                    return false;
	                }

                    $.ajax({
                		url: "invite.do",
            			type: "post",
            			data : { toid : toid, gid: gid },
            			success : function(data){
            				var msg = data.result.trim().toUpperCase();
            				if(msg == "SUCCESS") {
            					$.alert("초대 쪽지가 전달되었습니다!" + "\n(" + toid + ")");
            				} else if(msg == "FAIL") {
            					$.alert("존재하지 않는 이메일입니다!");
            				} else if(msg == "SELF") {
            					$.alert("본인을 초대하실 수 없습니다!");
            				} else if(msg == "ALREADY") {
            					$.alert("이미 초대된 사용자입니다!");
            				} else {
            					$.alert("잠시후 다시 시도해주세요!");
            				}
            			}
                	});


                }
            },
            '취소': {
                btnClass: 'btn-red',
                action: function () {
                //close
                }
            },
        }
    });
}

/* 그룹 탈퇴 */
function group_leave(){
    $.confirm({
        title: '그룹 탈퇴',
        content: '' +
        '<form id="leaveGroup" action="" class="formGroup" method="post">' +
        '<div class="form-group">' +
        '<label>그룹을 탈퇴하시겠습니까</label>' +
        '<input type="hidden" name="uid" value="'+uid+'" class="leaveUid form-control"/>' +
        '<input type="hidden" name="gid" value="'+gid+'" class="banName form-control"/>' +
        '</div>' +
        '</form>',
        closeIcon: true,
        closeIconClass: 'fa fa-close',
        buttons: {
            formSubmit: {
                text: '탈퇴',
                btnClass: 'btn-success',
                action: function () {
                    var name = this.$content.find('.leaveUid').val();

                    $("#leaveGroup").submit();

                }
            },
            '취소': {
                btnClass: 'btn-red',
                action: function () {
                //close
                }
            },
        }
    });
}



/* 그룹 완료 */
function group_complete(){
    $.confirm({
        title: '그룹 완료',
        content: '' +
        '<form id="completeGroup" action="" class="formGroup" method="post">' +
        '<div class="form-group">' +
        '<label>해시태그를 입력하세요</label>' +
        '<input type="text" name="htag" class="htagName form-control" required/>' +
        '<input type="hidden" name="gid" value="'+gid+'" class="banName form-control"/>' +
        '</div>' +
        '</form>',
        closeIcon: true,
        closeIconClass: 'fa fa-close',
        buttons: {
            formSubmit: {
                text: '완료',
                btnClass: 'btn-success',
                action: function () {
                    var name = this.$content.find('.htagName').val();
                    if(!name){
	                    $.alert('해시태그를 적어주세요');
	                    return false;
	                }

                    $("#completeGroup").submit();

                }
            },
            '취소': {
                btnClass: 'btn-red',
                action: function () {
                //close
                }
            },
        }
    });
}



/* 마우스 오른쪽 이벤트 (회원강퇴) 추가*/
$(function() {
    $.contextMenu({
        selector: '.member', 
        callback: function(key, opt) {
            var targetNname = opt.$trigger.text().trim();
            
            if(key == "ban"){
            	member_ban(targetNname);
            }
        },
        items: {
            "ban": {name: "강퇴"}
        }
    });   
});

/* 멤버 강퇴 START */
function member_ban(targetNname){
    $.confirm({
        title: '멤버 강퇴',
        content: '' +
        '<form id="banMember" action="banMember.do" class="formGroup" method="post" onsubmit="return false;">' +
        '<div class="form-group">' +
        '<label>['+targetNname+'] 회원을 강퇴하시겠습니까</label>' +
        '<input type="hidden" name="nname" value="' + targetNname + '" class="banName form-control"/>' +
        '<input type="hidden" name="gid" value="' + gid + '" class="banName form-control"/>' +
        '</div>' +
        '</form>',
        closeIcon: true,
        closeIconClass: 'fa fa-close',
        buttons: {
            formSubmit: {
                text: '강퇴',
                btnClass: 'btn-success',
                action: function () {
                    $("#banMember").submit();
                }
            },
            '취소': {
                btnClass: 'btn-red',
                action: function () {
                //close
                }
            },
        },
        onContentReady: function(){
        	// 그룹원 강퇴 ajaxFrom()
        	$("#banMember").ajaxForm({
        		success: function(data, statusText, xhr, $form){
        			console.log(data);
        			var recv_data = data.result.trim();
        			
        			if(recv_data == 'fired') {
        				$.alert('해당 그룹원이 강퇴되었습니다!');
        			}else if(recv_data == 'empty') {
        				$.alert('해당 그룹원이 존재하지 않습니다!');
        			}else {
        				$.alert('잠시후 다시 시도해주세요!');
        			}
        		}
        	});
        }

    });
}

/* 멤버 강퇴 END */



	