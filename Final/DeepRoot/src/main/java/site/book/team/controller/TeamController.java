/*
 * @Project : DeepRoot
 * @FileName : TeamController.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.team.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import site.book.team.dto.G_BookDTO;
import site.book.team.dto.TeamDTO;
import site.book.team.service.G_BookService;
import site.book.team.service.TeamService;

@Controller
@RequestMapping("/team/")
public class TeamController {

	
	//변수 STRAT
	
	//명수
	@Autowired
	TeamService teamservice;
	
	@Autowired
    private View jsonview;
	

	
	
	//태웅
	
	
	
	
	
	
	
	//변수 END
	
	
	//함수 STRAT
	
	//명수
	// 완료 그룹 리스트
	@RequestMapping("getCompletedTeamList.do")
	public View getCompletedTeamList(String uid ,  Model model) {
		
		List<TeamDTO> teamlist = teamservice.getCompletedTeamList(uid);
		
		model.addAttribute("teamlist",teamlist);
		return jsonview;
	}
	
	@RequestMapping("getTeamList.do")
	public View getTeamList(HttpServletRequest req,  Model model) {
		HttpSession session = req.getSession();
		String uid = (String)session.getAttribute("info_userid");
		
		List<TeamDTO> teamlist = teamservice.getTeamList(uid);
		
		model.addAttribute("teamlist", teamlist);
		return jsonview;
	}
	
	@RequestMapping("deleteCompletedTeam.do")
	public View deleteCompletedTeam(String uid, Model model) {
		
		int result = teamservice.deleteCompletedTeam(uid);
		
		model.addAttribute("result", result);
		return jsonview;
	}
	

	
	//태웅
	
	
	
	//함수 END
	
}
