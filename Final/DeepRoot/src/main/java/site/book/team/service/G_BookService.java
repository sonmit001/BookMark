/*
 * @Project : DeepRoot
 * @FileName : G_BookService.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.team.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import site.book.team.dao.G_BookDAO;
import site.book.team.dto.G_BookDTO;
import site.book.user.dao.U_BookDAO;
import site.book.user.dto.U_BookDTO;

/**
 * @Class : G_BookService.java
 * @Date : 2018. 6. 7.
 * @Author : 김희준
 */
@Service
public class G_BookService {
	//희준
	@Autowired
	private SqlSession sqlsession;
	
	//태웅
	
	
	//희준
	// 그룹이 추가한 북마크 수
	public List<HashMap<String, String>> numOfBookByDate() {
		G_BookDAO bookDAO = sqlsession.getMapper(G_BookDAO.class);
		List<HashMap<String, String>> map = null;
		
		try {
			map = bookDAO.numOfBookByDate();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	// 완료된 그룹 북마크 가져오기
	public List<G_BookDTO> getCompletedTeamBookmark(int gid) {
		
		G_BookDAO bookDAO = sqlsession.getMapper(G_BookDAO.class);
		List<G_BookDTO> list = null;
		
		try {
			list = bookDAO.getCompletedTeamBookmark(gid);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	//태웅
	// 진행중인 그룹에서 카테고리만 가져오기
	public List<G_BookDTO> getGroupCategoryList(String gid) {
		G_BookDAO dao = sqlsession.getMapper(G_BookDAO.class);
		List<G_BookDTO> list = null;
		try {
			list = dao.getGroupCategoryList(gid);
		}catch (Exception e) {
			/*e.printStackTrace();*/
		}
		
		return list;
	}
	
	// 현재  GBID(Group Bookmark ID)의  max + 1  
	// 그룹 처음 생성 시, 루트 폴더 생성
	@Transactional
	public int getMaxIDandInsertRootFolder(String gid, String uid) {
		G_BookDAO dao = sqlsession.getMapper(G_BookDAO.class);
		int isInsert = 0;
		try {
			int gbid = dao.getMaxGBID();
			isInsert = dao.insertRootFolder(gid, uid);
			// Root 폴터 생성 성공이라면, 
			if(isInsert > 0) {
				isInsert = gbid; // GBID return;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return isInsert;
	}
	
	// 한 URL을 자신의 그룹 북마크에 추가 
	public int insertGroupBookmark(G_BookDTO gbook) {
		G_BookDAO dao = sqlsession.getMapper(G_BookDAO.class);
		int result = 0;
		
		try {
			result = dao.insertGroupBookmark(gbook);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	//명수
	//그룹페이지 jstree 가져오기
	public JSONArray getTeamJstree(String gid, String uid) {
		G_BookDAO dao = sqlsession.getMapper(G_BookDAO.class);
		JSONArray jsonArray = new JSONArray();
	
		List<G_BookDTO> list;
		
		try {
			list = dao.getTeamJstree(gid);
			if(list.size() ==0) {
				
				JSONObject jsonobject = new JSONObject();
				//그룹에 아무 카테고리가 없을 경우 root 생성
				dao.insertRootFolder(gid, uid);
				int gbid = dao.getCurrentGBID();
				
				jsonobject.put("id", gbid);
				jsonobject.put("parent", "#");
				jsonobject.put("text", "ROOT");
				jsonobject.put("icon", "fa fa-folder");
				jsonobject.put("uid", uid);
				
				jsonArray.put(jsonobject);
					
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
					jsonobject.put("id", list.get(i).getGbid());
					jsonobject.put("text", list.get(i).getUrlname());
					jsonobject.put("uid",uid);
					
					jsonArray.put(jsonobject);
					
				}
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return jsonArray;
	}
	
}
