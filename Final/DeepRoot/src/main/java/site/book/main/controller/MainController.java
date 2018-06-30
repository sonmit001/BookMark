/*
 * @Project : DeepRoot
 * @FileName : MainController.java
 * @Date : 2018. 6. 7.
 * @Author : 김희준
*/


package site.book.main.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;

import site.book.admin.dto.A_BookDTO;
import site.book.admin.dto.A_CategoryDTO;
import site.book.admin.dto.NoticeDTO;
import site.book.admin.service.A_BookService;
import site.book.admin.service.A_CategoryService;
import site.book.admin.service.NoticeService;
import site.book.team.dto.G_AlarmDTO;
import site.book.team.dto.G_MemberDTO;
import site.book.team.dto.G_MyAlarmDTO;
import site.book.team.dto.TeamDTO;
import site.book.team.service.G_AlarmService;
import site.book.team.service.TeamService;
import site.book.user.dto.EmailAuthDTO;
import site.book.user.dto.UserDTO;
import site.book.user.service.UserService;

/**
 * @Class : MainController.java
 * @Date : 2018. 6. 11.
 * @Author : 김희준, 김태웅
 */
@Controller
public class MainController {
	
	// 변수 Start
	
	// 태웅
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private A_CategoryService a_category_service;
	
	@Autowired
	private A_BookService a_book_service;
	
	@Autowired
	private UserService user_service;
	
	@Autowired
	G_AlarmService galarmservice;
	
	// 희준
	@Autowired
	private View jsonview;
	
	@Autowired
	private TeamService teamservice;
	
	@Autowired
	private NoticeService notice_service;
	
	// 명수
	
	// 변수 End
	
	// 함수 Start
	
	// 태웅
	
	/*메인 화면 데이터 출력*/
	@RequestMapping(value="/index.do", method=RequestMethod.GET)
	public String initMain(HttpServletRequest req, Model model) {
		//System.out.println("홈: 메인 페이지");
		
		List<A_CategoryDTO> categoryList = a_category_service.getCategorys();
		model.addAttribute("categoryList", categoryList);
		
		List<A_BookDTO> bookList = a_book_service.getMainBooks();
		model.addAttribute("bookList", bookList);
		
		HttpSession session = req.getSession();
		String uid = (String)session.getAttribute("info_userid");
		
		if(uid != null) {
			List<TeamDTO> headerTeamList = teamservice.getTeamList(uid);
			model.addAttribute("headerTeamList", headerTeamList);
		}
		
		// 그룹 초대/강퇴/완료 알람  쪽지 리스트
		List<G_MyAlarmDTO> headerAlarmList = galarmservice.getAlarmList(uid);
		model.addAttribute("headerAlarmList", headerAlarmList);
		
		// 관리자 공지사항 쪽지 리스트
		List<NoticeDTO> headerNoticeList = notice_service.getNotices();
		model.addAttribute("headerNoticeList", headerNoticeList);
		
		return "home.index";
	}
	
	/* URL Click Function */
	@RequestMapping(value="/clickurl.do")
	public View clickURL(HttpServletRequest req, Model model, String abid) {
		
		System.out.println(abid);
		int result = a_book_service.clickURL(abid);
		
		if(result > 0) {
			model.addAttribute("click", "1");
		}else {
			model.addAttribute("click", "0");
		}
		
		return jsonview;
	}
	
	/* Log in */
	@RequestMapping(value="/joinus/login.do")
	public View login(HttpServletRequest request, HttpServletResponse response, 
			HttpSession session, Model model, UserDTO user) {
		
		// process message from Handler and JSON data response
		if(request.getAttribute("msg").equals("fail")) {
			model.addAttribute("login", "fail");
		}else {
			String userid = (String)request.getAttribute("userid");
			user.setUid(userid);
			user = user_service.getMember(user.getUid());
			model.addAttribute("login", "success");
			
			String role = (String)request.getAttribute("ROLE");
			if(role.equals("ADMIN")) {
				model.addAttribute("path", "admin/main.do");
			}else {
				model.addAttribute("path", "index.do");
			}
			
			// set info session userid
			session.setAttribute("info_userid", user.getUid());
			session.setAttribute("info_usernname", user.getNname());
			session.setAttribute("info_userprofile", user.getProfile());
		}
		
		return jsonview;
	}
	
	/* Roll in */
	@RequestMapping(value="/joinus/rollin.do", method=RequestMethod.POST)
	public View rollin(HttpServletRequest request, HttpServletResponse response, 
			UserDTO user, Model model) {
		
		user.setPwd(this.bCryptPasswordEncoder.encode(user.getPwd()));
		//System.out.println(user);
		
		int result = user_service.rollinUser(user);
		if(result > 0) {
			model.addAttribute("rollin", "pass");
		}else {
			model.addAttribute("rollin", "fail");
		}
		
		return jsonview;
	}
	
	/* Send email & Save email, authcode */
	@RequestMapping(value="/joinus/emailsend.do", method=RequestMethod.POST)
	public View emailConfirm(HttpServletRequest request, HttpServletResponse response, 
			EmailAuthDTO auth, Model model) {
		
		System.out.println(auth);
		int result = user_service.confirmEmail(auth);
		if(result > 0) {
			model.addAttribute("email", "pass");
		}else {
			model.addAttribute("email", "fail");
		}
		return jsonview;
	}
	
	/* check Authcode */
	@RequestMapping(value="/joinus/emailauth.do", method=RequestMethod.POST)
	public View checkAuthcode(HttpServletRequest request, HttpServletResponse response, 
			EmailAuthDTO auth, Model model) {
		
		System.out.println(auth);
		int result = user_service.checkAuthcode(auth);
		if(result > 0) {
			model.addAttribute("auth", "pass");
		}else {
			model.addAttribute("auth", "fail");
		}
		return jsonview;
	}
	
	/* Check UID */
	@RequestMapping(value="/joinus/checkuid.do", method=RequestMethod.POST)
	public View checkUid(HttpServletRequest request, HttpServletResponse response, 
			UserDTO user, Model model) {
		
		//System.out.println(user);
		int result = user_service.checkUserID(user.getUid());
		if(result > 0) {
			model.addAttribute("result", "fail");
		}else {
			model.addAttribute("result", "pass");
		}
		
		return jsonview;
	}
	
	/* Check Nickname */
	@RequestMapping(value="/joinus/checknname.do", method=RequestMethod.POST)
	public View checkNname(HttpServletRequest request, HttpServletResponse response, 
			UserDTO user, Model model) {

		//System.out.println(user);
		int result = user_service.checkUserNickname(user.getNname());
		if(result > 0) {
			model.addAttribute("result", "fail");
		}else {
			model.addAttribute("result", "pass");
		}
		
		return jsonview;
	}
	
	/* 회원정보 수정 페이지 GET */
	@RequestMapping(value="/myInfo.do", method=RequestMethod.GET)
	public String initMemberInfo(Model model) {
		
		return "member.myinfo";
	}
	
	/* 회원정보 수정 페이지 POST */
	@RequestMapping(value="/myInfo.do", method=RequestMethod.POST)
	public String editMemberInfo(HttpServletRequest request, HttpSession session,
			UserDTO user, @RequestParam("uploadFile") MultipartFile file, 
			Model model) {
		
		//비밀번호 재암호화
		user.setPwd(this.bCryptPasswordEncoder.encode(user.getPwd()));
		//result = UID.확장자
		String result = user_service.editMember(request, user, file);
		//System.out.println("회원수정: " + result);
		
		// .확장자명이 있다면 session update
		if(result.split("\\.").length > 1)
			session.setAttribute("info_userprofile", result);
		return "redirect:index.do";
	}
	
	/* 회원정보 수정 페이지 비밀번호 재확인 */
	@RequestMapping(value="/reconfirm.do", method=RequestMethod.POST)
	public View confirmMemberPWD(Model model, UserDTO input_data) {
		
		//getMember 통해  해당 회원정보 가져옴
		UserDTO member = user_service.getMember(input_data.getUid());
		//DB에서 가져온 암호화된 문자열
		String encodedPassword = member.getPwd();
		
		boolean result = bCryptPasswordEncoder.matches(input_data.getPwd(), encodedPassword);
		if(result) {
			model.addAttribute("result", "pass");
		}else {
			model.addAttribute("result", "fail");
		}
		return jsonview;
	}
	
	/* 회원 탈퇴 */
	@RequestMapping(value="/rollout.do", method=RequestMethod.GET)
	public String rolloutMember(HttpServletRequest request, Model model) {
		
		String uid = (String)request.getParameter("uid");
		//System.out.println(uid);
		int result = user_service.deleteMember(uid);

		if(result > 0) {
			model.addAttribute("result", "pass");
		}else {
			model.addAttribute("result", "fail");
		}
		return "member.logout";
	}
	
	/* 비밀번호 찾기 */
	// 비밀번호 찾기 시, 회원인지 확인
	@RequestMapping(value="/confirmuser.do", method=RequestMethod.POST)
	public View confirmUser(HttpServletRequest request, Model model, EmailAuthDTO user) {

		System.out.println(user);
		int result = user_service.confirmUser(user);

		if(result > 0) {
			model.addAttribute("result", "member");
		}else {
			model.addAttribute("result", "who");
		}
		return jsonview;
	}
	
	// 회원 여부 확인 후, 임시 비밀번호 전송
	@RequestMapping(value="/findpwd.do", method=RequestMethod.POST)
	public View findUserPwd(HttpServletRequest request, Model model,
			EmailAuthDTO authcode, UserDTO user) {

		//System.out.println(authcode);
		//System.out.println(user);
		int resultAuth = user_service.checkAuthcode(authcode);
		
		// 인증코드가 정확하다면,
		if(resultAuth > 0) {
			// 회원에게 임시 비밀번호 발급
			user_service.findUserPwd(user);
			model.addAttribute("result", "success");
			model.addAttribute("path", "index.do");
		}else {
			model.addAttribute("result", "fail");
		}
		return jsonview;
	}
	/* 비밀번호 찾기 END */
	
	// 미리보기 기능 추가 상세정보 웹크롤링(World Ranking, Sub-URL)
	@RequestMapping("previewdetail.do")
	public View WebCrawling2(String abid, Model model) {
		A_BookDTO book = a_book_service.getBook(Integer.parseInt(abid));
		String url = book.getUrl();
	
		try {
			/* <Woong> Web Crawling - SubURL & Daily Visitor & World Rank */
			String fixed_suburl = "https://www.alexa.com/siteinfo/"; // subURL Top5
			String fixed_visitor = "http://website.informer.com/";	// Daily Visitor & World Rank
			
			// subURL Top5 START
			Document document = Jsoup.connect(fixed_suburl+url).get();
			Element content = document.getElementById("subdomain_table");
			Elements subURL = content.getElementsByClass("word-wrap");
			Elements percent = content.getElementsByClass("text-right");
			
			List<List<String>> url_percent = new ArrayList<>();
			
			try {
				for(int i = 0; i < 5; i++) {
					List<String> temp = new ArrayList<>();
					String sub = subURL.get(i).select("span").text();
					String rate = percent.get(i+1).select("span").text();
					temp.add(sub);
					temp.add(rate);
					url_percent.add(temp);
				}
			}catch (Exception e) {
				
			}
			model.addAttribute("suburl", url_percent);
			
			// Daily Visitor & World Rank Top5 START
			document = Jsoup.connect(fixed_visitor+url).get();
			content = document.getElementById("whois");
			Element visitor_content = content.getElementById("visitors");
			Element rank_content = content.getElementById("alexa_rank");
			
			String visitor = visitor_content.select("b").text().trim().replace(" ", ",");
			String rank = rank_content.select("b").text().trim();

			model.addAttribute("visitor", visitor);
			model.addAttribute("rank", rank);
			
		} catch (Exception e) {
			/*e.printStackTrace();*/
		}
		return jsonview;
	}

	// 희준
	// WebCrawling을 통해 해당 사이트에 title, type, imge, url, description 가져오기
	@RequestMapping("preview.do")
	public View WebCrawling(String abid, Model model) {
		
		A_BookDTO book = a_book_service.getBook(Integer.parseInt(abid));
		String url = book.getUrl();
		
		Document doc;
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		String[] REQUIRED_META = new String[] { "og:title", "og:type", "og:image", "og:url", "og:description" };
		try {
			doc = Jsoup.connect(url).userAgent(
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36")
					.referrer("http://www.google.com").get();
			Elements ogElements = doc.select("meta[property^=og], meta[name^=og]");
			for (Element e : ogElements) {
				String target = e.hasAttr("property") ? "property" : "name";

				if (!result.containsKey(e.attr(target))) {
					result.put(e.attr(target), new ArrayList<String>());
				}
				result.get(e.attr(target)).add(e.attr("content"));
			}
			for (String s : REQUIRED_META) {
				if (!(result.containsKey(s) && result.get(s).size() > 0)) {
					if (s.equals(REQUIRED_META[0])) {
						result.put(REQUIRED_META[0], Arrays.asList(new String[] { doc.select("title").eq(0).text() }));
					} else if (s.equals(REQUIRED_META[1])) {
						result.put(REQUIRED_META[1], Arrays.asList(new String[] { "website" }));
					} else if (s.equals(REQUIRED_META[2])) {
						result.put(REQUIRED_META[2],
								Arrays.asList(new String[] { doc.select("img").eq(0).attr("abs:src") }));
					} else if (s.equals(REQUIRED_META[3])) {
						result.put(REQUIRED_META[3], Arrays.asList(new String[] { doc.baseUri() }));
					} else if (s.equals(REQUIRED_META[4])) {
						result.put(REQUIRED_META[4], Arrays.asList(new String[] { doc
								.select("meta[property=description], meta[name=description]").eq(0).attr("content") }));
					}
				}
			}
			for (String s : result.keySet()) {model.addAttribute(s.substring(3), result.get(s));}

		} catch (Exception e) {
			/*e.printStackTrace();*/
		}
		return jsonview;
	}
	
	// 공지사항 최신순으로 3개 가져오기
	@RequestMapping("/getNotices.do")
	public View getNotices(Model model) {
		
		List<NoticeDTO> noticeList = notice_service.getNotices();
		model.addAttribute("noticeList", noticeList);
		
		return jsonview;
	}
	
	// 그룹 추가 / 추가 시 그룹장까지 Transaction으로 처리
	@RequestMapping("/addGroup.do")
	public View addGroup(HttpServletRequest req, String gname, Model model) {
		HttpSession session = req.getSession();
		String uid = (String)session.getAttribute("info_userid");
		
		G_MemberDTO member = new G_MemberDTO();
		member.setUid(uid);
		member.setGrid(1);
		
		TeamDTO newTeam = teamservice.addGroup(gname, member);
		model.addAttribute("newTeam", newTeam);
		
		return jsonview;
	}
	
	// 명수
	
	
	// 함수 End
}
