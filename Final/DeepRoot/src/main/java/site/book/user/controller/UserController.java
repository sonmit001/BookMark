package site.book.user.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import site.book.team.dto.G_BookDTO;
import site.book.team.dto.G_MemberDTO;
import site.book.team.dto.TeamDTO;
import site.book.team.service.G_BookService;
import site.book.team.service.G_MemberService;
import site.book.team.service.TeamService;
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
	
	@Autowired
	private TeamService teamservice;
	
	@Autowired
	private G_MemberService g_memberservice;
	
	// 명수
	@Autowired
	U_BookService u_bookservice;
	
	@Autowired
	private MailSender mailSender;
	
	@Autowired
	G_BookService g_bookservice;
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
	
	@RequestMapping("leaveGroup.do")
	public View leaveGroup(HttpServletRequest req, G_MemberDTO member, Model model) {
		HttpSession session = req.getSession();
		String uid = (String)session.getAttribute("info_userid");
		member.setUid(uid);
		
		int row = g_memberservice.leaveGroup(member);
		
		String data = (row == 1) ? "성공" : "실패";
		model.addAttribute("data", data);
		
		return jsonview;
	}
	
	@RequestMapping("addGroup.do")
	public String addGroup(HttpServletRequest req, String gname) {
		System.out.println("그룹 추가");
		HttpSession session = req.getSession();
		String uid = (String)session.getAttribute("info_userid");
		
		G_MemberDTO member = new G_MemberDTO();
		member.setUid(uid);
		member.setGrid(1);
		
		teamservice.addGroup(gname, member);
		
		return "redirect:mybookmark.do";
	}
	
	@RequestMapping("completedGroup.do")
	public String completedGroup(TeamDTO team) {
		
		teamservice.completedGroup(team);
		
		return "redirect:mybookmark.do";
	}
	
	
	// 명수
	@RequestMapping("mybookmark.do")
	public String mybookmark(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession();
		String uid = (String)session.getAttribute("info_userid");
		System.out.println("uid : " + uid);
		
		List<TeamDTO> teamList = teamservice.getTeamList(uid);
		model.addAttribute("teamList", teamList);
		
		List<TeamDTO> completedTeamList = teamservice.getCompletedTeamList(uid);
		model.addAttribute("completedTeamList", completedTeamList);
		
		return "mypage.myCategory";
	}
	
	//해당 유저의 카테고리를 보내준다.
	@RequestMapping("getCategoryList.do")	
	public void getCategoryList(HttpServletRequest req , HttpServletResponse res) {
		
		res.setCharacterEncoding("UTF-8");
		
		HttpSession session = req.getSession();
        String uid = (String)session.getAttribute("info_userid");
        
        System.out.println("uid : " + uid);
		
		
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
				jsonobject.put("icon", "fa fa-folder");
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
					jsonobject.put("icon", "fa fa-folder");	//favicon 추가
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
	public void addFolder(U_BookDTO dto ,HttpServletRequest req, HttpServletResponse res) {
		
		int ubid = u_bookservice.getmaxid();	// max(ubid) +1 한 값이다.
		HttpSession session = req.getSession();
        String uid = (String)session.getAttribute("info_userid");
        
        dto.setUbid(ubid);
        dto.setUid(uid);
        
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
	public void addRoot(HttpServletRequest req , HttpServletResponse res) {
		
		res.setCharacterEncoding("UTF-8");
		
		HttpSession session = req.getSession();
        String uid = (String)session.getAttribute("info_userid");
        System.out.println("uid : " + uid);
        
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
	
	@RequestMapping("getCompletedTeamBookmark.do")
	public void getCompletedTeamBookmark(HttpServletResponse res, int gid) {
		
		res.setCharacterEncoding("UTF-8");
		System.out.println("너 들어오니");
		System.out.println(gid);
		
		JSONArray jsonArray = new JSONArray();	
		List<G_BookDTO> list = g_bookservice.getCompletedTeamBookmark(gid);
		
		System.out.println(list);
		
		for(int i =0; i<list.size(); i++) {
			
			JSONObject jsonobject = new JSONObject();
			
			String parentid = String.valueOf(list.get(i).getPid());
			
			if(parentid.equals("0") || parentid.equals(""))
				jsonobject.put("parent", "#");
			else
				jsonobject.put("parent", parentid);
			
			if(list.get(i).getUrl() == null)
				jsonobject.put("icon", "fa fa-folder");
			else
				jsonobject.put("icon", "https://www.google.com/s2/favicons?domain="+list.get(i).getUrl());
			
			jsonobject.put("id", list.get(i).getGbid());
			jsonobject.put("text", list.get(i).getUrlname());
			
			jsonArray.put(jsonobject);
		}
		
		try {
			res.getWriter().println(jsonArray);
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
	}
	// 함수 End
}
