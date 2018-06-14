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
import org.springframework.web.servlet.View;

import site.book.admin.dto.A_BookDTO;
import site.book.admin.dto.A_CategoryDTO;
import site.book.admin.service.A_BookService;
import site.book.admin.service.A_CategoryService;
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
	
	// 희준
	@Autowired
	private View jsonview;
	
	
	// 명수
	
	// 변수 End
	
	// 함수 Start
	
	// 태웅
	
	/*메인 화면 데이터 출력*/
	@RequestMapping(value="/index.do", method=RequestMethod.GET)
	public String initMain(Model model) {
		//System.out.println("홈: 메인 페이지");
		
		List<A_CategoryDTO> categoryList = a_category_service.getCategorys();
		model.addAttribute("categoryList", categoryList);
		
		List<A_BookDTO> bookList = a_book_service.getMainBooks();
		model.addAttribute("bookList", bookList);
		
		return "home.index";
	}
	
	/* Log in */
	@RequestMapping(value="/joinus/login.do")
	public View login(HttpServletRequest request, HttpServletResponse response, 
			HttpSession session, Model model) {
		
		// process message from Handler and JSON data response
		if(request.getAttribute("msg").equals("fail")) {
			model.addAttribute("login", "fail");
		}else {
			String userid = (String)request.getAttribute("userid");
			model.addAttribute("login", "success");
			System.out.println(userid);

			// set info session userid
			session.setAttribute("info_userid", userid);
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
			model.addAttribute("rollin", "success");
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
			model.addAttribute("email", "success");
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
			model.addAttribute("auth", "success");
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
	
	// 희준
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
			e.printStackTrace();
		}
		return jsonview;
	}
	
	@RequestMapping("/categoryList.do")
	public View getCategoryList(Model model) {
		List<A_CategoryDTO> categoryList = a_category_service.getCategorys();
		model.addAttribute("categoryList", categoryList);
		
		return jsonview;
	}
	
	// 명수
	
	
	// 함수 End
}
