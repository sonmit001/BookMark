    


function showRegisterForm(){
       $('.loginBox').fadeOut('fast',function(){
           $('.registerBox').fadeIn('fast');
           $('.login-footer').fadeOut('fast',function(){
               $('.register-footer').fadeIn('fast');
           });
           $('.modal-title').html('Register with');
       });
       $('.error').removeClass('alert alert-danger').html('');

   }
   function showLoginForm(){
       $('.findBox').fadeOut('fast');  
       $('.registerBox').fadeOut('fast',function(){
           $('.social').fadeIn('fast');
           $('.division').fadeIn('fast');
           $('.loginBox').fadeIn('fast');
           $('.register-footer').fadeOut('fast',function(){
               $('.find-footer').fadeOut('fast',function(){
               $('.login-footer').fadeIn('fast')
               });
           });

           $('.modal-title').html('Login with');
       });      
        $('.error').removeClass('alert alert-danger').html('');
   }
   function showFindForm(){
       $('.social').fadeOut('fast');
       $('.division').fadeOut('fast');
       $('.loginBox').fadeOut('fast',function(){
           $('.findBox').fadeIn('fast');
           $('.login-footer').fadeOut('fast',function(){
               $('.find-footer').fadeIn('fast');
           })
           $('.modal-title').html('Find password')
       })
       $('.error').removeClass('alert alert-danger').html('');
   }

   function openLoginModal(){
       showLoginForm();
       setTimeout(function(){
           $('#loginModal').modal('show');    
       }, 230);

   }
   function openRegisterModal(){
       showRegisterForm();
       setTimeout(function(){
           $('#loginModal').modal('show');    
       }, 230);

   }
   function openFindModal(){
       showFindForm();
       setTimeout(function(){
           $('#loginModal').modal('show');    
       }, 230);
   }

   function loginAjax(){
       /*
       $.post( "/login", function( data ) {
               if(data == 1){
                   window.location.replace("/home");            
               } else {
                    shakeModal();
               }
           });
       */

       /*   임시 테스트   */
        shakeModal_login();
}

   function shakeModal_login(){
       $('#loginModal .modal-dialog').addClass('shake');
                $('.error').addClass('alert alert-danger').html("이메일/비밀번호가 존재하지 않습니다.");
                $('input[type="password"]').val('');
                setTimeout( function(){
                   $('#loginModal .modal-dialog').removeClass('shake');
       }, 1000 );
   }
        /*
* 회원가입 유효성 확인
*/
$(function(){
   /*
   //이메일 확인 함수 ()
   $('#uid_join').blur(function() {
       var email = $('#uid_join').val();
       var regex = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;    //이메일 유효성 검사 검증 해봐야함 아직 안해봄
       if((email== '') || !(email.length >= 3 && email.length <= 18)){
           $('#loginModal .modal-dialog').addClass('shake');
                $('.error').addClass('alert alert-danger').html("이메일은 3자~18자 사이로 입력해야 합니다.");
                $('#uid_join').val('');
                setTimeout( function(){
                       $('#loginModal .modal-dialog').removeClass('shake');
           }, 1000 );
           $("#uid_join").focus();
           
       } else if( !(regex.test(email)) ) {
           $('#loginModal .modal-dialog').addClass('shake');
                $('.error').addClass('alert alert-danger').html("형식에 맞지 않은 이메일 입니다.");
                $('#uid_join').val('');
                setTimeout( function(){
                       $('#loginModal .modal-dialog').removeClass('shake');
           }, 1000 );
           $("#uid_join").focus();
       }
       
       else{
           $('.error').removeClass('alert alert-danger').html('');
           var data = {uid:$('#uid_join').val()};
           $.ajax({
               url:"",
               data:data,
               success:function(responsedata){
                   if(responsedata == 1) {
                       console.log("등록가능한 메일");
                   } else {
                       console.log("중복된 이메일");
                       $('#loginModal .modal-dialog').addClass('shake');
                       $('.error').addClass('alert alert-danger').html("중복된 이메일 입니다.");
                       $('#uid_join').val('');
                       setTimeout( function(){
                               $('#loginModal .modal-dialog').removeClass('shake');
                       }, 1000 );
                       $("#uid_join").focus();
                   }
               }
           })
       }
   })
   */
   
   //비밀번호 길이 확인 함수
   $('#pwd_join').blur(function(){
       /*var pwd = $('#pwd_join').val();
       var pwd_con = $('#pwd_confirmation').val();*/
       if( ($('#pwd_join').val().trim()=="") || !($('#pwd_join').val().length >= 5 && $('#pwd_join').val().length <= 15) ){
               $('#loginModal .modal-dialog').addClass('shake');
               $('.error').addClass('alert alert-danger').html("비밀번호는 5자~15자 사이로 만들어야 합니다.");
               setTimeout( function(){
                   $('#loginModal .modal-dialog').removeClass('shake');
           }, 1000 );
           $("#pwd_join").focus();
       } else {
           $('.error').removeClass('alert alert-danger').html('');
       }
   })

   
   //비밀번호 동일 확인 함수
   $('#pwd_confirmation').blur(function(){
       /*var pwd = $('#pwd_join').val();
       var pwd_con = $('#pwd_confirmation').val();*/
       if( !($('#pwd_join').val() == $('#pwd_confirmation').val()) ){
               $('#loginModal .modal-dialog').addClass('shake');
               $('.error').addClass('alert alert-danger').html("입력한 비밀번호가 다릅니다.");
               setTimeout( function(){
                   $('#loginModal .modal-dialog').removeClass('shake');
           }, 1000 );
           $("#pwd_confirmation").focus();
       } else {
           $('.error').removeClass('alert alert-danger').html('');
       }
   })
})
