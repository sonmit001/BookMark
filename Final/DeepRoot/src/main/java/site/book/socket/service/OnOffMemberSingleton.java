package site.book.socket.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Class : ChatController.java
 * @Date : 2018. 6. 30.
 * @Author : 김태웅
 */
public class OnOffMemberSingleton {
	
	private static Map<String, Map<String, String>> online = new HashMap<>();
	
	public OnOffMemberSingleton() {}
	//Map<GID, Map<UID, "ON">>
	public static Map<String, Map<String, String>> getInstance () {
		return online;
	}
	
}
