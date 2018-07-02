package site.book.socket;

import java.util.HashMap;
import java.util.Map;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import site.book.socket.dto.OnlineMemberDTO;
import site.book.socket.service.OnOffMemberSingleton;

/**
 * @Class : ChatController.java
 * @Date : 2018. 6. 30.
 * @Author : 김태웅
 */
@Controller
public class ChatOnOffController {
	
	// 접속시, 온라인 상태 소켓 전송
    @MessageMapping("/online/{room}")
    @SendTo("/subscribe/online/{room}")
    public OnlineMemberDTO sendOnlineMessage(@DestinationVariable("room") String room, @Payload OnlineMemberDTO member) throws Exception {
    	
    	//System.out.println("ON" + member);
    	// 온라인 유저 구독하면, Map(gid: [])에 추가
    	Map<String, Map<String, String>> online_list = OnOffMemberSingleton.getInstance();
    	
    	// 그룹 생성후 처음 들어온 경우,
    	if( !online_list.containsKey(room) ) {
    		online_list.put(room, new HashMap<String, String>());
    	}
    	
    	// 해당 당의 온라인 유저에 추가
    	if( member != null ) {
    		Map<String, String> online_users = online_list.get(room);
        	online_users.put(member.getNname(), member.getStatus());

        	// 다시 온라인 Map에 저장
        	online_list.put(room, online_users);
    	}
    	
    	System.out.println(online_list);
    	
        return member;
    }
    
    // 종료시, 오프라인 상태 소켓 전송
    @MessageMapping("/offline/{room}")
    @SendTo("/subscribe/offline/{room}")
    public OnlineMemberDTO sendOfflineMessage(@DestinationVariable("room") String room, @Payload OnlineMemberDTO member) throws Exception {
    	
    	System.out.println("OFF" + member);
    	
    	// 유저 Socket Close, Map(gid: [])에 삭제
    	Map<String, Map<String, String>> online_list = OnOffMemberSingleton.getInstance();
    	Map<String, String> online_users = online_list.get(room);
    	
    	// 해당 당의 온라인 유저에 추가
    	online_users.remove(member.getNname());
    	System.out.println(online_list);
    	
        return member;
    }
    
    
}
