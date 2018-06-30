
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
    var scrollPos = $('.chat-element').scrollTop();
    var date_eq = $(".chat-element").children(".divider").length - 1;

    $('.chat-element').scroll(function() {
        var curScrollPos = $(this).scrollTop();
        var date_line = $(".divider:eq(" + date_eq + ")").position().top;

        if (curScrollPos > scrollPos) { //Scrolling Down
            if(date_line <= 35 ) {
                var temp = $(".divider:eq(" + date_eq + ") > span").text(); // 가장 맨 위의 내용
                $("#header-date").text(temp);
                if( date_eq < $(".chat-element").children(".divider").length - 1 ) { date_eq += 1; }
            }
        } else { //Scrolling Up
            if(date_line > 30 ) {
                if( date_eq > 0 ) { date_eq -= 1; }
                var temp = $(".divider:eq(" + date_eq + ") > span").text(); // 가장 맨 위의 내용
                $("#header-date").text(temp);
            }
        }
        scrollPos = curScrollPos;
    });
});
