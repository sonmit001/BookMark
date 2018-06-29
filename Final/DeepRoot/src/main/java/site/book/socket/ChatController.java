/*
 * @Project : DeepRoot
 * @FileName : ChatController.java
 * @Date : 2018. 6. 27.
 * @Author : 김희준
*/


package site.book.socket;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import site.book.user.dto.UserDTO;
import site.book.user.service.UserService;

/**
 * @Class : ChatController.java
 * @Date : 2018. 6. 27.
 * @Author : 김희준
 */
@Controller
public class ChatController {
	
	@Autowired
	private UserService userservice;
	
	// 채팅 메세지 전달
    @MessageMapping("/chat/{room}")
    @SendTo("/subscribe/chat/{room}")
    public ChatMessage sendChatMessage(@DestinationVariable("room") String room, ChatMessage message, SimpMessageHeaderAccessor headerAccessor, Principal principal) {
        System.out.println("채팅 메세지 들어옴");
    	
    	message.setDatetime(LocalDateTime.now().toString());
    	
    	try {
			fileWrite(room, message);
		} catch (IOException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
    	
        return message;
    }
    
    // 파일 쓰기
    public void fileWrite(String gid, ChatMessage message) throws IOException, InterruptedException, ExecutionException {
    	String spath = this.getClass().getResource("").getPath();
    	spath = spath.substring(1);
		int index = spath.indexOf("WEB-INF");
		spath = spath.substring(0, index);
		spath += "team/chat/" + gid +".txt";
		
    	String fileName = spath;
        Path path = Paths.get(fileName);
        System.out.println("path : " + path);
        
        BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        String str = message.getProfile() + "|" + message.getNname() + "|" + message.getDatetime() + "|" + message.getContent() + "&";
        writer.write(str);
        //writer.write(System.getProperty("line.separator"));
        writer.flush();
        writer.close();
        
    }
	
    
    //명수
    //jstree 변경 메시지
    @MessageMapping("/JSTREE/{room}")
    @SendTo("/subscribe/JSTREE/{room}")
    public JstreeAlarm sendJstree(@DestinationVariable("room") String room, JstreeAlarm message, SimpMessageHeaderAccessor headerAccessor, Principal principal) {
        System.out.println("jstree 들어옴");
    	System.out.println(message);
    	
    	
        return message;
    }
}
