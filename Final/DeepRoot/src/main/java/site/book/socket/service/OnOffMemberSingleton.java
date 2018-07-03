package site.book.socket.service;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

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
	
	public static String returnConvertJson(String nname, String gid){
		Gson gson = new Gson();
		
		// 그룹 생성후 처음 들어온 경우,
    	if( !online.containsKey(gid) ) {
    		online.put(gid, new HashMap<String, String>());
    	}
    	
		online.get(gid).put(nname, "ON");
        String json = gson.toJson(online.get(gid));
        return json;
	}
}
