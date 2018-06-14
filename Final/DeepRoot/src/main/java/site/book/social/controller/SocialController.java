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

/**
 * @Class : SocialController.java
 * @Date : 2018. 6. 14.
 * @Author : 김희준
 */
@Controller
@RequestMapping("/social/")
public class SocialController {
	
	@Autowired
	private TopService top_service;
	
	@RequestMapping("social.do")
	public String social(Model model) {
		
		List<TopDTO> u_top5 = top_service.getUTop5();
		model.addAttribute("u_top5", u_top5);
		
		List<TopDTO> g_top5 = top_service.getGTop5();
		model.addAttribute("g_top5", g_top5);
		
		List<TopDTO> a_top5 = top_service.getATop5();
		model.addAttribute("a_top5", a_top5);
		
		return "khj.social";
	}
}
