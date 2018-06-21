/*
 * @Project : DeepRoot
 * @FileName : TeamService.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.team.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import site.book.team.dao.G_BookDAO;
import site.book.team.dao.G_MemberDAO;
import site.book.team.dao.TeamDAO;
import site.book.team.dto.G_MemberDTO;
import site.book.team.dto.S_TeamDTO;
import site.book.team.dto.TeamDTO;

/**
 * @Class : TeamService.java
 * @Date : 2018. 6. 8.
 * @Author : 김희준
 */
@Service
public class TeamService {
	
	@Autowired
	private SqlSession sqlsession;
	
	
	// 희준
	
	// 소셜 그룹 리스트 가져오기
	public List<S_TeamDTO> getSocialGroupList() {
		TeamDAO teamDAO = sqlsession.getMapper(TeamDAO.class);
		G_BookDAO g_bookDAO = sqlsession.getMapper(G_BookDAO.class);
		
		List<S_TeamDTO> list = null;
		
		try {
			list = teamDAO.socialGroupList();
			for(S_TeamDTO team : list) {
				team.setView(g_bookDAO.selectGroupViews(team.getGid()));
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	// 소셜 그룹 삭제하기
	public int deleteSocialGroup(int gid) {
		TeamDAO teamDAO = sqlsession.getMapper(TeamDAO.class);
		int row = 0;
		
		try {
			row = teamDAO.deleteSocialGroup(gid);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return row;
	}

	// 모든 그룹 리스트 가져오기
	public List<TeamDTO> getGroupList() {
		TeamDAO teamDAO = sqlsession.getMapper(TeamDAO.class);
		List<TeamDTO> list = null;
		
		try {
			list = teamDAO.selectGroupList();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	// 그룹 삭제하기
	public int deleteGroup(int gid) {
		TeamDAO teamDAO = sqlsession.getMapper(TeamDAO.class);
		int row = 0;
		
		try {
			row = teamDAO.deleteGroup(gid);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return row;
	}
	
	// 그룹 추가하기
	@Transactional
	public int addGroup(String gname, G_MemberDTO member) {
		TeamDAO teamDAO = sqlsession.getMapper(TeamDAO.class);
		G_MemberDAO g_memberDAO = sqlsession.getMapper(G_MemberDAO.class);
		
		int row = 0;
		
		try {
			row = teamDAO.insertGroup(gname);
			int gid = teamDAO.selectLastGroupID();
			member.setGid(gid);
			row = g_memberDAO.insertGMember(member);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return row;
	}
	
	// 그룹 완료하기
	public int completedGroup(TeamDTO team) {
		TeamDAO teamDAO = sqlsession.getMapper(TeamDAO.class);
		int row = 0;
		
		try {
			row = teamDAO.completedGroup(team);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return row;
	}
	
	
	// 명수
	// 완료 그룹 리스트 가져오기
	public List<TeamDTO> getCompletedTeamList(String uid) {
		
		TeamDAO teamDAO = sqlsession.getMapper(TeamDAO.class);
		List<TeamDTO> dtolist = teamDAO.getCompletedTeamList(uid);

		return dtolist;
	}

	// 내 그룹 리스트 가져오기
	public List<TeamDTO> getTeamList(String uid) {

		TeamDAO teamDAO = sqlsession.getMapper(TeamDAO.class);
		List<TeamDTO> dtolist = teamDAO.getTeamList(uid);
		return dtolist;
	}
	
	// 완료 그룹 삭제하기
	public int deleteCompletedTeam(String uid) {
		
		TeamDAO teamDAO = sqlsession.getMapper(TeamDAO.class);
		int result = teamDAO.deleteCompletedTeam(uid);
		
		return result;
		
	}
}
