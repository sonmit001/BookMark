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
import site.book.team.dto.G_AlarmDTO;
import site.book.team.dto.G_MemberDTO;
import site.book.team.dto.G_RoleDTO;
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
	private G_MemberDAO g_memberdao;
	
	@Autowired
	private TeamDAO teamDAO;
	//태웅
	public G_RoleDTO isGroupMember(G_MemberDTO member) {
		G_RoleDTO role = null;
		
		try {
			role = g_memberdao.isGroupMember(member);
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return role;
	}
	
	
	
	// 희준
	
	// 소셜 그룹 리스트 가져오기
	public List<S_TeamDTO> getSocialGroupList() {
		List<S_TeamDTO> list = null;
		
		try {
			list = teamDAO.socialGroupList();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	// 소셜 그룹 삭제하기
	public int deleteSocialGroup(int gid) {
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
	public TeamDTO addGroup(String gname, G_MemberDTO member) {
		TeamDTO team = null;
		
		try {
			teamDAO.insertGroup(gname);
			int gid = teamDAO.selectLastGroupID();
			member.setGid(gid);
			g_memberdao.insertGMember(member);
			team = teamDAO.selectGroup(gid);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return team;
	}
	
	// 그룹 완료하기
	@Transactional
	public TeamDTO completedGroup(TeamDTO team, G_AlarmDTO alarm) {
		TeamDTO completedTeam = null;
		try {
			// 그룹 완료 -> 모든 그룹원에게 완료 쪽지 보내기 -> 그룹 select
			teamDAO.completedGroup(team);
			
			List<G_AlarmDTO> alarms = teamDAO.getGroupMember(alarm);
			teamDAO.sendCompletedMemo(alarms);
			
			completedTeam = teamDAO.selectGroup(team.getGid());
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return completedTeam;
	}
	
	
	// 명수
	// 완료 그룹 리스트 가져오기
	public List<TeamDTO> getCompletedTeamList(String uid) {
		
		List<TeamDTO> dtolist = teamDAO.getCompletedTeamList(uid);

		return dtolist;
	}

	// 내 그룹 리스트 가져오기
	public List<TeamDTO> getTeamList(String uid) {
		List<TeamDTO> dtolist = null;
				
		try {
			dtolist = teamDAO.getTeamList(uid);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return dtolist;
	}
	
	// 완료 그룹 삭제하기
	public int deleteCompletedTeam(String uid) {
		
		int result = teamDAO.deleteCompletedTeam(uid);
		
		return result;
		
	}
	
	// 소셜 조회수 증가 
	public int updateGroupViewCount(int gid) {

		int result = 0;
		try {
			result = teamDAO.updateViewCount(gid);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}