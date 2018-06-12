/*
 * @Project : DeepRoot
 * @FileName : MainController.java
 * @Date : 2018. 6. 7.
 * @Author : 김희준
*/


package site.book.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import site.book.admin.dto.A_BookDTO;
import site.book.admin.dto.A_CategoryDTO;
import site.book.admin.service.A_BookService;
import site.book.admin.service.A_CategoryService;
import site.book.user.dto.UserDTO;

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
	private A_CategoryService a_category_service;
	
	@Autowired
	private A_BookService a_book_service;
	
	// 희준
	
	
	
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
	/*@RequestMapping(value="/joinus/login.do")
	public String login(Model model, UserDTO user) {
		return "joinus.login";
	}*/
	
	/* Roll in */
	
	// 희준
	
	
	
	
	// 명수
	
	
	// 함수 End
}
