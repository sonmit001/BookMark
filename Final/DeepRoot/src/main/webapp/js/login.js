
/* Login / Roll_in / Password find script START */

function showRegisterForm() {
	$('.findBox').fadeOut('fast');
    $('.loginBox').fadeOut('fast', function() {
		$('.registerBox').fadeIn('fast');
        $('.login-footer').fadeOut('fast', function() {
            $('.register-footer').fadeIn('fast');
        });
        $('.modal-title').html('Register with');
    	
    });
    $('.error').removeClass('alert alert-danger').html('');
}

function showLoginForm() {
    $('.findBox').fadeOut('fast');
    $('.registerBox').fadeOut('fast', function() {
        $('.social').fadeIn('fast');
        $('.division').fadeIn('fast');
        $('.loginBox').fadeIn('fast');
        $('.register-footer').fadeOut('fast', function() {
            $('.find-footer').fadeOut('fast', function() {
                $('.login-footer').fadeIn('fast')
            });
        });

        $('.modal-title').html('Login with');
    });
    $('.error').removeClass('alert alert-danger').html('');
}

function showFindForm() {
    $('.social').fadeOut('fast');
    $('.division').fadeOut('fast');
    $('.loginBox').fadeOut('fast', function() {
        $('.findBox').fadeIn('fast');
        $('.login-footer').fadeOut('fast', function() {
            $('.find-footer').fadeIn('fast');
        })
        $('.modal-title').html('Find password')
    })
    $('.error').removeClass('alert alert-danger').html('');
}

function openLoginModal() {
    showLoginForm();
    setTimeout(function() {
        $('#loginModal').modal('show');
    }, 230);

}

function openRegisterModal() {
    showRegisterForm();
    setTimeout(function() {
        $('#loginModal').modal('show');
    }, 230);

}

function openFindModal() {
    showFindForm();
    setTimeout(function() {
        $('#loginModal').modal('show');
    }, 230);
}

function shakeModal_login() {
    $('#loginModal .modal-dialog').addClass('shake');
    $('.error').addClass('alert alert-danger').html("이메일/비밀번호가 존재하지 않습니다.");
    $('input[type="password"]').val('');
    setTimeout(function() {
        $('#loginModal .modal-dialog').removeClass('shake');
    }, 1000);
}

/*
 * 회원가입 유효성 확인
 */
$(function() {
    //비밀번호 길이 확인 함수
    $('#pwd_join').keyup(function() {
        if (($('#pwd_join').val().trim() == "") || !($('#pwd_join').val().length >= 5 && $('#pwd_join').val().length <= 15)) {
            $('.error').addClass('alert alert-danger').html("비밀번호는 5자~15자 사이로 만들어야 합니다.");
        } else {
            $('.error').removeClass('alert alert-danger').html('');
        }
    });

    //비밀번호 동일 확인 함수
    $('#pwd_confirmation').keyup(function() {
        /*var pwd = $('#pwd_join').val();
        var pwd_con = $('#pwd_confirmation').val();*/
        if (!($('#pwd_join').val() == $('#pwd_confirmation').val())) {
            $('.error').addClass('alert alert-danger').html("입력한 비밀번호가 다릅니다.");
        } else {
            $('.error').removeClass('alert alert-danger').html('');
        }
    });
    
    //이메일 형식 확인 함수
    $('#uid_join').keyup(function() {
        /*var email = $('#uid_join').val();*/
        var regex = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/; 
        if(($('#uid_join').val().trim() == "") || !($('#uid_join').val().length >= 5 )){
                 $('.error').addClass('alert alert-danger').html("이메일은 5자 이상 입력해야 합니다.");
            
        } else if( !(regex.test($('#uid_join').val())) ) {
                 $('.error').addClass('alert alert-danger').html("형식에 맞지 않은 이메일 입니다.");
        } else {
            $('.error').removeClass('alert alert-danger').html('');
        }
    });
    
    //인증키 확인 함수
    $('#authcode_join').keyup(function(){
        if( ($('#authcode_join').val().length >= 11 ) ) {
            $('.error').addClass('alert alert-danger').html("형식에 맞지 않은 인증키 입니다.");
        }
        else{
            $('.error').removeClass('alert alert-danger').html('');
        }
    });
})

/*
 * 회원가입 Ajax + 유효성 확인
 */
$(function() {
    /* 회원가입 Ajax() START */
	
	
	// 이메일 형식 확인 함수
    $('#uid_join').keyup(function() {
        var regex = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/; 
        
        // 이메일 길이 확인
        if(($('#uid_join').val().trim() == "") || !($('#uid_join').val().length >= 5 )){
                 $('.error').addClass('alert alert-danger').html("이메일은 5자 이상 입력해야 합니다.");
        } 
        // 이메일 형식 확인
        else if( !(regex.test($('#uid_join').val())) ) {
                 $('.error').addClass('alert alert-danger').html("형식에 맞지 않은 이메일 입니다.");
        }
        else{
            $('.error').removeClass('alert alert-danger').html('');
        }
    })
    
    // 이메일 authcode 형식 확인
    $('#authcode_join').keyup(function(){
    	// authcode 길이 확인
        if( ($('#authcode_join').val().length >= 11 ) ) {
            $('.error').addClass('alert alert-danger').html("형식에 맞지 않은 인증키 입니다.");
        }
        else{
            $('.error').removeClass('alert alert-danger').html('');
        }
    })
    
    
	
	$("#uid_join").blur(function(){
		// 1. User ID Check
        $.ajax({ 
    		url:"joinus/checkuid.do",
            type:"POST",
            data: {	"uid": $('#uid_join').val() },
            dataType:"json", 
            beforeSend: function() {
                //마우스 커서를 로딩 중 커서로 변경
                $('html').css("cursor", "wait");
            },
            complete: function() {
                //마우스 커서를 원래대로 돌린다
                $('html').css("cursor", "auto");
            },
            success:function(data){
            	// 사용 가능한 ID
            	if(data.result == 'pass') {
            		$('.error').removeClass('alert alert-danger').html('');
            		$('.error').addClass('alert alert-success').html("사용 가능한 이메일입니다");
            		$('#uid_join').removeClass('clear_join').addClass('clear_join');
            		$('#auth-div').css('display', 'block');
            		// 2. 가입자 이메일로 인증키 전송
            		$.ajax({ 
                		url:"joinus/emailsend.do",
                        type:"POST",
                        data: {	"uid": $('#uid_join').val() },
                        dataType:"json",
                        success:function(data){
                        	if(data.email == 'pass') {
                        		$('.error').removeClass('alert alert-danger').html('');
                        		$('.error').addClass('alert alert-success').html("해당 이메일로 인증키가 발송되었습니다.");
                        		alert("해당 이메일로 인증키가 발송되었습니다.");
                        	}
                        	else {
                        		$('#loginModal .modal-dialog').addClass('shake');
                        		$('.error').addClass('alert alert-danger').html("존재하지 않는 이메일입니다. 확인 부탁드립니다.");
            	                    setTimeout(function() {
            	                        $('#loginModal .modal-dialog').removeClass('shake');
            	                    }, 500);
                        		$("#authcode_join").focus();
                        	}
                        },
                        error:function(e){  
                        	console.log("Error: " + e.responseText); 
                        }
                    });
            	}
            	// 사용 불가능한 ID
            	else { 
            		$('#loginModal .modal-dialog').addClass('shake');
            		$("#uid_join").val("");
            		$("#uid_join").focus();
            		$('.error').removeClass('alert alert-danger').addClass('alert alert-danger').html("이미 가입된 이메일입니다");
                    setTimeout(function() {
                        $('#loginModal .modal-dialog').removeClass('shake');
                    }, 500);
            	}
            },
            error:function(e){  
            	console.log("Error: " + e.responseText); 
            }
        });
    });
	
	//3.비밀번호 길이 확인 함수
    $('#pwd_join').blur(function() {
        if (($('#pwd_join').val().trim() == "") || !($('#pwd_join').val().length >= 5 && $('#pwd_join').val().length <= 15)) {
            $('.error').addClass('alert alert-danger').html("비밀번호는 5~15자로 입력해주세요");
            //$('#pwd_join').focus();
        } else {
            $('.error').removeClass('alert alert-danger').html('');
            $('#pwd_join').removeClass('clear_join').addClass('clear_join');
        }
    });

    //4.비밀번호 동일 확인 함수
    $('#pwd_confirmation').keyup(function() {
        if (!($('#pwd_join').val() == $('#pwd_confirmation').val())) {
            $('.error').addClass('alert alert-danger').html("입력한 비밀번호가 다릅니다.");
        } else {
        	$('.error').removeClass('alert alert-danger').html('');
        	$('#pwd_confirmation').removeClass('clear_join').addClass('clear_join');
        }
    });
	
    //5.닉네임 중복확인
	$("#nname_join").blur(function(){
        $.ajax({ 
    		url:"joinus/checknname.do",
            type:"POST",
            data: {	"nname": $('#nname_join').val() },
            dataType:"json", 
            beforeSend: function() {
                $('html').css("cursor", "wait");
            },
            complete: function() {
                $('html').css("cursor", "auto");
            },
            success:function(data){
            	if(data.result == 'pass') {
            		$('.error').removeClass('alert alert-danger').html('');
            		$('.error').addClass('alert alert-success').html("사용 가능한 닉네임입니다");
            		$('#nname_join').removeClass('clear_join').addClass('clear_join');
            	}
            	else {
            		$('#loginModal .modal-dialog').addClass('shake');
            		$('.error').addClass('alert alert-danger').html("해당 닉네임이 이미 존재합니다.");
	                    setTimeout(function() {
	                        $('#loginModal .modal-dialog').removeClass('shake');
	                    }, 500);
            		$("#nname_join").focus();
            	}
            },
            error:function(e){  
            	console.log("Error: " + e.responseText); 
            }
        });
    });
	
	//5.이메일 authcode 확인
	$("#authcode_join").keyup(function(){
        $.ajax({ 
    		url:"joinus/emailauth.do",
            type:"POST",
            data: {	"uid": $('#uid_join').val(), "authcode": $("#authcode_join").val() },
            dataType:"json", 
            beforeSend: function() { //마우스 커서를 로딩 중으로
                $('html').css("cursor", "wait");
            },
            complete: function() {
                $('html').css("cursor", "auto");
            },
            success:function(data){
            	if(data.auth == 'pass') {
            		$('.error').removeClass('alert alert-danger').html('');
            		$('.error').addClass('alert alert-success').html("인증키가 확인되었습니다.");
            		$("#authcode_join").prop("disabled", true);
            		$("#authcode_check").prop("disabled", true);
            		$('#authcode_join').removeClass('clear_join').addClass('clear_join');
            		
            	}else {
            		$('#loginModal .modal-dialog').addClass('shake');
            		$('.error').addClass('alert alert-danger').html("잘못된 인증키 입니다.");
	                    setTimeout(function() {
	                        $('#loginModal .modal-dialog').removeClass('shake');
	                    }, 500);
            		$("#authcode_check").prop("disabled", false);
            	}
            },
            error:function(e){  
            	console.log("Error: " + e.responseText); 
            }
        });
    });
	
	$("#authcode_check").prop("disabled", true);
	$("#rollinAjax").prop("disabled", true);
	
	$("#agree-site-rule").on("dblclick", function(){});
	$("#agree-site-rule").on("click", function(){ 
		if($(this).is(':checked')) {
			if($('#uid_join').hasClass('clear_join') && $('#pwd_join').hasClass('clear_join')
					&& $('#pwd_confirmation').hasClass('clear_join') && $('#nname_join').hasClass('clear_join')
					&& $('#authcode_join').hasClass('clear_join')) {
				$("#rollinAjax").prop("disabled", false);
			}else {
				alert("위 항목을 모두 작성해주세요");
				$("#agree-site-rule").prop("checked", false);
			}
		}else {
			console.log("xxxx");
			$("#rollinAjax").prop("disabled", true);
		}
	});
	
	$("#authcode_check").on("dblclick", function(){ });
    $("#authcode_check").on("click", function(){
    	$.ajax({ 
    		url:"joinus/emailsend.do",
            type:"POST",
            data: {	"uid": $('#uid_join').val() },
            dataType:"json",
           /*  crossDomain: false, */
            success:function(data){
            	if(data.email == 'pass') {
            		alert("인증 코드가 재발송되었습니다.");
            		$("#authcode_check").prop("disabled", true);
            	}else { 
            		$('#loginModal .modal-dialog').addClass('shake');
            		$('.error').addClass('alert alert-danger').html("잘못된 접근입니다. 확인 부탁드립니다.");
	                    setTimeout(function() {
	                        $('#loginModal .modal-dialog').removeClass('shake');
	                    }, 500);
            		$("#uid_join").focus();
            	}
            },
            error:function(e){  
            	console.log("Error: " + e.responseText); 
            }
        });
    });
	
	$("#rollinAjax").on("dblclick", function(){ });
    $("#rollinAjax").on("click", function(){
    	$.ajax({ 
    		url:"joinus/rollin.do",
            type:"POST",
            data: {	"uid": $('#uid_join').val(),
            		"nname": $('#nname_join').val(),
            		"pwd": $('#pwd_join').val()
            },
            dataType:"json",
           /*  crossDomain: false, */
            success:function(data){
            	if(data.rollin == 'pass') {
            		location.href="index.do"; 
            	}
            	else { 
            		$("#rollinAjax > strong").html("잘못된 접근입니다. 잠시후 다시 시도해주세요.");
            	}
            },
            error:function(e){  
            	console.log("Error: " + e.responseText); 
            }
        });
    });
    /* 회원가입 Ajax() END */
    
    
    /* 로그인 Ajax() START */
    
    // 이메일 형식 확인 함수
    $('#uid').keyup(function() {
        var regex = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/; 
        
        // 이메일 길이 확인
        if(($('#uid').val().trim() == "") || !($('#uid').val().length >= 5 )){
                 $('.error').addClass('alert alert-danger').html("이메일은 5자 이상 입력해야 합니다.");
        } 
        // 이메일 형식 확인
        else if( !(regex.test($('#uid').val())) ) {
                 $('.error').addClass('alert alert-danger').html("형식에 맞지 않은 이메일 입니다.");
        }
        else{
            $('.error').removeClass('alert alert-danger').html('');
        }
    })
    
    //비밀번호 길이 확인 함수
    $('#pwd').blur(function() {
        if (($('#pwd').val().trim() == "") || !($('#pwd').val().length >= 5 && $('#pwd').val().length <= 15)) {
            $('.error').addClass('alert alert-danger').html("비밀번호는 5~15자로 입력해주세요");
            //$('#pwd_join').focus();
        } else {
            $('.error').removeClass('alert alert-danger').html('');
            $('#pwd').removeClass('clear_join').addClass('clear_join');
        }
    });
    
	$("#loginAjax").on("dblclick", function(){ });
    $("#loginAjax").on("click", function(){
    	$.ajax({ 
    		url:"security/login",
            type:"POST",
            data:{uid: $("#uid").val(), pwd: $("#pwd").val()},
            dataType:"json",
           /*  crossDomain: false, */
            success:function(data){
            	if(data.login == 'success') {
            		location.href = data.path;
            	}
            	else {  
	            	$("#pwd").val('');
	        		$("#login-form > strong").html("아이디 또는 비밀번호 오류입니다.");
            	}
            },
            error:function(e){  
            	console.log("Error: " + e.responseText); 
            }
        });
    });
    /* 로그인 Ajax() END */
});

/**************************  테이블  **********************************/
$(function() {
    $(document).on("click", ".url", function() {
        //console.log(this.dataset.url);
        window.open(this.dataset.url, '_blank');
    });
});


/* Login / Roll_in / Password find script END */


/*  Password find script START */
$(function() {
	$('#check_email_find').dblclick(function() { return });
	$('#check_email_find').click(function() {
		$.ajax({ 
    		url:"confirmuser.do",
            type:"POST",
            data:{uid: $("#uid_find").val()},
            dataType:"json",
            beforeSend: function() {$('html').css("cursor", "wait");},
            complete: function() {$('html').css("cursor", "auto");},
            success:function(data){
            	console.log(data);
            	if(data.result == 'member') {
            		$('#check_email_find').css('display', 'none');
            		$('.confrim_code_find').css('display', 'block');
            		$('#loginModal .modal-dialog').addClass('shake');
            		$('.error').addClass('alert alert-success').html("회원님 이메일로 인증키가 발송되었습니다.");
	                    setTimeout(function() {
	                        $('#loginModal .modal-dialog').removeClass('shake');
	                    }, 500);
            	}
            	else {
	            	$('#loginModal .modal-dialog').addClass('shake');
            		$('.error').addClass('alert alert-danger').html("가입된 회원이 아니십니다. 확인 부탁드립니다.");
	                    setTimeout(function() {
	                        $('#loginModal .modal-dialog').removeClass('shake');
	                    }, 500);
            	}
            },
            error:function(e){  
            	console.log("Error: " + e.responseText); 
            }
        });
	});
	
	$('#find-password').dblclick(function() { return });
	$('#find-password').click(function() {
		console.log('#find-password AJAX START');
		$.ajax({ 
    		url:"findpwd.do",
            type:"POST",
            data:{uid: $("#uid_find").val(), authcode: $("#authcode_find").val()},
            dataType:"json",
            beforeSend: function() {$('html').css("cursor", "wait");},
            complete: function() {$('html').css("cursor", "auto");},
            success:function(data){
            	if(data.result == 'success') {
            		alert('발송된 임시 비밀번호로 로그인 해주세요');
            		console.log(data.path);
            		location.href = data.path;
            	}
            	else {
            		$('#loginModal .modal-dialog').addClass('shake');
            		$('.error').addClass('alert alert-danger').html("잘못된 인증키입니다. 확인 부탁드립니다.");
	                    setTimeout(function() {
	                        $('#loginModal .modal-dialog').removeClass('shake');
	                    }, 500);
            	}
            },
            error:function(e){  
            	console.log("Error: " + e.responseText); 
            }
        });
	});
});
/*  Password find script END */




