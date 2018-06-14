package site.book.user.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

import com.gargoylesoftware.htmlunit.javascript.host.Console;

import site.book.user.dto.U_BookDTO;
import site.book.user.service.U_BookService;
import site.book.user.service.UserService;
//user/mycategory.do";
@Controller
@RequestMapping("/user/")
public class UserController {
	
	// 변수 Start
	
	// 태웅
	@Autowired
    private View jsonview;
	
	@Autowired
	private UserService userservice;
	// 희준
	
	
	// 명수
	@Autowired
	U_BookService u_bookservice;
	
	@Autowired
	private MailSender mailSender;
	
	// 변수 End
	
	// 함수 Start
	
	// 태웅
	@RequestMapping(value="idcheck.do", method = RequestMethod.POST)
	public View userIdCheck(@RequestParam("uid") String uid, Model model) {
		//System.out.println(uid);
		int result = userservice.checkUserID(uid);
		if(result > 0) {
			model.addAttribute("result", "fail");
		}else {
			model.addAttribute("result", "pass");
		}
		
		return jsonview;
	}
	
	@RequestMapping(value="nnamecheck.do", method = RequestMethod.POST)
	public View userNnameCheck(@RequestParam("nname") String nname, Model model) {
		//System.out.println(nname);
		int result = userservice.checkUserNickname(nname);
		if(result > 0) {
			model.addAttribute("result", "fail");
		}else {
			model.addAttribute("result", "pass");
		}
		
		return jsonview;
	}
	// 희준
	
	
	// 명수
	@RequestMapping("mybookmark.do")
	public String mybookmark() {
		
		return "kms.myCategory";
	}
	
	//해당 유저의 카테고리를 보내준다.
	@RequestMapping("getCategoryList.do")	
	public void getCategoryList(String uid , HttpServletResponse res) {
		
		res.setCharacterEncoding("UTF-8");
		
		JSONArray jsonArray = new JSONArray();	
		List<U_BookDTO> list = u_bookservice.getCategoryList(uid);
		
		System.out.println(list);
		
		if(list.size() ==0) {
			
			JSONObject jsonobject = new JSONObject();
			
			int ubid = u_bookservice.getmaxid();	// max(ubid) +1 한 값이다.
			int result = u_bookservice.insertRootFolder(ubid, uid);
			
			//처음 가입한 유저일 경우 root폴더 생성해 준다.
			if(result ==1 ) {	
				
				jsonobject.put("id", ubid);
				jsonobject.put("parent", "#");
				jsonobject.put("text", "첫 카테고리");
				jsonobject.put("icon", "fa fa-folder-o");
				jsonobject.put("uid", uid);
				
				jsonArray.put(jsonobject);
				
			}
		}else {
			
			for(int i =0;i<list.size();i++) {
				
				JSONObject jsonobject = new JSONObject();
				
				String parentid = String.valueOf(list.get(i).getPid());
				
				if(parentid.equals("0") || parentid.equals(""))
					jsonobject.put("parent", "#");
				else
					jsonobject.put("parent", parentid);
				
				if(list.get(i).getUrl() == null)
					jsonobject.put("icon", "fa fa-folder-o");	//favicon 추가
				else {
					jsonobject.put("icon", "https://www.google.com/s2/favicons?domain="+list.get(i).getUrl());	//favicon 추가
				}
				jsonobject.put("id", list.get(i).getUbid());
				jsonobject.put("text", list.get(i).getUrlname());
				jsonobject.put("uid",uid);
				jsonobject.put("sname", list.get(i).getSname());
				jsonobject.put("htag", list.get(i).getHtag());
				
				jsonArray.put(jsonobject);
				
			}
		}
		try {
			res.getWriter().println(jsonArray);
		}catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}
	
	//해당 노드의 url 추출
	@RequestMapping("getUrl.do")
	public void getUrl(int ubid , HttpServletResponse res) {	
		
		res.setCharacterEncoding("UTF-8");
		
		List<U_BookDTO> list = u_bookservice.getUrl(ubid);
		System.out.println(list);
		JSONArray jsonArray = new JSONArray();	
		HashMap<String, String> href = new HashMap();
		
		
		for(int i =0;i<list.size();i++) {
			
			JSONObject jsonobject = new JSONObject();

			href.put("href", list.get(i).getUrl());
			
			jsonobject.put("id", list.get(i).getUbid());
			jsonobject.put("parent", "#");
			jsonobject.put("text", list.get(i).getUrlname());
			jsonobject.put("icon", "https://www.google.com/s2/favicons?domain="+list.get(i).getUrl());	//favicon 추가
			
			String htag = String.valueOf(list.get(i).getHtag());
			System.out.println("아래에");
			System.out.println(htag);
			
			if(htag.equals("") || htag.equals("null")) {
				System.out.println("없는서");
				jsonobject.put("sname", "#");
				jsonobject.put("htag", "#");
			}else {
				System.out.println("잇는거");
				jsonobject.put("sname", list.get(i).getSname());
				jsonobject.put("htag", list.get(i).getHtag());
			}
				
			jsonobject.put("test", "dd");
			jsonobject.put("a_attr", href);
			
			jsonArray.put(jsonobject);
			
		}
		
		try {
			res.getWriter().println(jsonArray);
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	
	
	//urlname 수정
	@RequestMapping("updateNodeText.do")	
	public void updateNodeText(@RequestParam HashMap<String, String> param, HttpServletResponse res) {
		
		int result = u_bookservice.updateNodeText(param);
		System.out.println(param);
		
		try {
			res.getWriter().println(result);
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	
	//폴더 & url & 공유일 경우 공유로 추가
	@RequestMapping("addFolderOrUrl.do")
	public void addFolder(U_BookDTO dto , HttpServletResponse res) {
		
		int ubid = u_bookservice.getmaxid();	// max(ubid) +1 한 값이다.
		dto.setUbid(ubid);
			
		System.out.println(dto.toString());
		int result = u_bookservice.addFolderOrUrl(dto);
		
		try {
			res.getWriter().println(ubid);
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	
	//url 혹은 폴더 삭제
	@RequestMapping("deleteNode.do")	
	public void deleNode(HttpServletRequest req , HttpServletResponse res) {
		res.setCharacterEncoding("UTF-8");
		//mysql에 cascade 햇기 때문에 url이든 폴더를 지우려고 하든 상위의 ubid를 보내부면 알아서 참조하는 모든 데이터가 삭제된다,.
		System.out.println("ddd");
		String nodeid = req.getParameter("node");
		u_bookservice.deleteFolderOrUrl(nodeid);
		
		try {
			res.getWriter().println("success");
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	
	//url update
	@RequestMapping("editUrl.do")	
	public void editUrl(U_BookDTO dto , HttpServletResponse res) {
		
		res.setCharacterEncoding("UTF-8");
		
		int result = u_bookservice.editUrl(dto);
		
		try {
			res.getWriter().println(result);
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
	}
	
	//드래그 드랍 했을 경우 부모 id 바꾸기
	@RequestMapping("dropNode.do")	
	public void dropNode(HttpServletResponse res , @RequestParam HashMap<String, String> param) {
		
		res.setCharacterEncoding("UTF-8");
		System.out.println("아래에 param");
		System.out.println(param);
		int result = u_bookservice.dropNode(param);
		
		try {
			res.getWriter().println(result);
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
	
	// email 보내기 받는 사람 주소 변경하기
	@RequestMapping("recommend.do")
	public void recommend(HttpServletResponse res, String url , String text) {	
			// 내용 알 맞게 변경하기
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setSubject("뿌리 깊은 마크 URL 추천 ");
		message.setFrom("bitcamp104@gmail.com");
		message.setText(url +" "+ text);
		message.setTo("sonmit002@naver.com");
		
		try {
			 mailSender.send(message);
			 res.getWriter().println("메일보내기 성공");
			
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	//url 공유하기 위에 눌렀을 경우 & url 공유 취소 했을 경우 & url 공유 수정 했을 경우
	@RequestMapping("shareUrlEdit.do")
	public void shareUrlEdit(U_BookDTO dto , HttpServletResponse res) {	
		
		res.setCharacterEncoding("UTF-8");
		int result = u_bookservice.shareUrlEdit(dto);
		
		try {
			res.getWriter().println(result);
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
	}
	
	//ROOT 카테고리 추가 
	@RequestMapping("addRoot.do")
	public void addRoot(String uid , HttpServletResponse res) {
		
		res.setCharacterEncoding("UTF-8");
		
		int ubid = u_bookservice.getmaxid();
		int result = u_bookservice.insertRootFolder(ubid, uid);
		
		if(result == 1) {
			try {
				res.getWriter().println(ubid);
			} catch (IOException e) {			
				e.printStackTrace();
			}
		}
	}
	
	// 함수 End
}
