/*
 * @Project : DeepRoot
 * @FileName : TopService.java
 * @Date : 2018. 6. 14.
 * @Author : 김희준
 */

package site.book.social.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import site.book.social.dto.TopDTO;
import site.book.social.service.TopService;
import site.book.team.dto.S_TeamDTO;
import site.book.team.service.TeamService;
import site.book.user.dto.S_U_BookDTO;
import site.book.user.service.U_BookService;

/**
 * @Class : SocialController.java
 * @Date : 2018. 6. 14.
 * @Author : 김희준
 */
@Controller
@RequestMapping("/social/")
public class SocialController {
	/* 민재 파라미터 */
	@Autowired
	private TopService top_service;
	
	/* 진수햄 파라미터 */
	@Autowired
	private U_BookService u_bookservice;
	@Autowired
	private TeamService teamservice;
	
	
	/* 민재 & 진수 함수 */
	@RequestMapping("social.do")
	public String social(Model model) {
		
		List<TopDTO> u_top5 = top_service.getUTop5();
		model.addAttribute("u_top5", u_top5);
		
		List<TopDTO> g_top5 = top_service.getGTop5();
		model.addAttribute("g_top5", g_top5);
		
		List<TopDTO> a_top5 = top_service.getATop5();
		model.addAttribute("a_top5", a_top5);
		/*u_booklist start*/
		List<S_U_BookDTO> u_list= u_bookservice.getSocialBookmarkList();
		model.addAttribute("u_list",u_list);
		
		List<S_TeamDTO> g_list=teamservice.getSocialGroupList();
		model.addAttribute("g_list", g_list);
		
		return "social.social";
	}
	
	/*u_booklist end*/
}
