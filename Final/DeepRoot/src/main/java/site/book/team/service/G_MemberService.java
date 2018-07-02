/*
 * @Project : DeepRoot
 * @FileName : G_MemberService.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.team.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import site.book.team.dao.G_MemberDAO;
import site.book.team.dto.G_AlarmDTO;
import site.book.team.dto.G_MemberDTO;

@Service
public class G_MemberService {
	
	@Autowired
	private SqlSession sqlsession;
	
	// 참여하고 있는 그룹 나가기
	public int leaveGroup(G_MemberDTO member) {
		G_MemberDAO g_MemberDAO = sqlsession.getMapper(G_MemberDAO.class);
		int row = 0;
		
		try {
			row = g_MemberDAO.leaveGroup(member);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return row;
	}
	//준석
	//그룹원 리스트 가져오기
	public List<G_MemberDTO> selectGMemberlist(String gid){
		G_MemberDAO g_MemberDAO = sqlsession.getMapper(G_MemberDAO.class);
		List<G_MemberDTO> selectgmemberlist = null;
		
		try {
			selectgmemberlist = g_MemberDAO.selectGMemberlist(gid);
		} catch (ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
		}
		
		return selectgmemberlist;
	}
	
	//태웅
	//그룹원 초대하기: 초대 쪽지 보내기
	public int inviteUser(G_AlarmDTO alarm){
		G_MemberDAO g_MemberDAO = sqlsession.getMapper(G_MemberDAO.class);
		int result = 0;
		
		try {
			result = g_MemberDAO.sendInviteMemo(alarm);
		} catch (Exception e) {
			/*e.printStackTrace();*/
		}
		
		return result;
	}
	
	//그룹원 강퇴하기 + 강퇴 쪽지 보내기
	public int banMember(G_MemberDTO member_ban) {
		G_MemberDAO g_MemberDAO = sqlsession.getMapper(G_MemberDAO.class);
		int result = 0;
		
		try {
			result = g_MemberDAO.banMember(member_ban);
		} catch (Exception e) {
			result = -1;
			/*e.printStackTrace();*/
		}
		
		return result;
	}
}
