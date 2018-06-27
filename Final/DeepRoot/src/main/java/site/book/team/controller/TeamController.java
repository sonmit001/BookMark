/*
 * @Project : DeepRoot
 * @FileName : TeamController.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준, 김태웅
*/


package site.book.team.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import site.book.team.dto.G_BookDTO;
import site.book.team.dto.G_JstreeDTO;
import site.book.team.dto.TeamDTO;
import site.book.team.service.G_BookService;
import site.book.team.service.TeamService;

/**
 * @Class : SocialController.java
 * @Date : 2018. 6. 24.
 * @Author : 김희준, 김태웅
 */
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
	
	@Autowired
	G_BookService gbookservice;
	
	
	
	
	
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
	//해당 유저의 진행중인 그룹의 카테고리만를 보내준다.
	@RequestMapping("getGroupCategoryList.do")	
	public View getGroupCategoryList(HttpServletRequest req, Model model, String gid) {
		HttpSession session = req.getSession();
        String uid = (String)session.getAttribute("info_userid");
        
		List<G_BookDTO> list = gbookservice.getGroupCategoryList(gid);
		JSONArray jsonArray = new JSONArray();
		
		// ROOT Folder가 없을 경우
		if(list.size() == 0) {
			G_JstreeDTO gtree = new G_JstreeDTO();
			// Transacional
			// result[0] : max(ubid) +1 한 값
			// result[1] : 그룹에 카테고리가 없을 경우, 기본 카테고리를  생성해 결과 값
			int result = gbookservice.getMaxIDandInsertRootFolder(gid, uid);
			
			if(result > 0 ) {	
				gtree.setId(result);
				gtree.setParent("#");
				gtree.setText("ROOT");
				gtree.setIcon("fa fa-folder");
				gtree.setUid(uid);

				jsonArray.put(gtree);
			}
		}else {
			for(int i=0; i<list.size(); i++) {
				G_JstreeDTO gtree = new G_JstreeDTO();
				String parentid = String.valueOf(list.get(i).getPid());
				
				//parent category  or child category
				if(parentid.equals("0") || parentid.equals("")) {
					gtree.setParent("#");
				}else {
					gtree.setParent(parentid);
				}
				//Folder favicon 추가 & jsTree Form (JSON data)
				gtree.setId(list.get(i).getGbid());
				gtree.setText(list.get(i).getUrlname());
				gtree.setIcon("fa fa-folder");
				gtree.setUid(uid);

				jsonArray.put(gtree);
			}
		}
		
		model.addAttribute("data", jsonArray);
		return jsonview;
	}
	
	//선택한 그룹으로 해당 URL 추가
	@RequestMapping("addGroupBookmark.do")	
	public View addGroupBookmark(HttpServletRequest req, Model model, G_BookDTO g_book) {
        
		HttpSession session = req.getSession();
        String uid = (String)session.getAttribute("info_userid");
        g_book.setUid(uid);
        
		System.out.println(g_book);
        int result = gbookservice.insertGroupBookmark(g_book);
        
		if(result > 0) {
			model.addAttribute("result", "success");
		}else {
			model.addAttribute("result", "fail");
		}
		
		return jsonview;
	}
	//함수 END
	
}
