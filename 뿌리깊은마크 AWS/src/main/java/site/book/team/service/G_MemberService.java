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
import org.springframework.transaction.annotation.Transactional;

import site.book.team.dao.G_MemberDAO;
import site.book.team.dto.G_AlarmDTO;
import site.book.team.dto.G_MemberDTO;
import site.book.user.dto.UserDTO;

@Service
public class G_MemberService {
	
	@Autowired
	private G_MemberDAO g_memberDAO;
	
	// 참여하고 있는 그룹 나가기
	public int leaveGroup(G_MemberDTO member) {
		int row = 0;
		
		try {
			row = g_memberDAO.leaveGroup(member);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return row;
	}
	//준석
	//그룹원 리스트 가져오기
	public List<G_MemberDTO> selectGMemberlist(String gid){
		List<G_MemberDTO> selectgmemberlist = null;
		
		try {
			selectgmemberlist = g_memberDAO.selectGMemberlist(gid);
		} catch (ClassNotFoundException | SQLException e) {
			
			e.printStackTrace();
		}
		
		return selectgmemberlist;
	}
	
	//태웅
	//그룹원 초대하기: 초대 쪽지 보내기
	public int inviteUser(G_AlarmDTO alarm){
		int result = 0;
		
		try {
			result = g_memberDAO.sendInviteMemo(alarm);
		} catch (Exception e) {
			/*e.printStackTrace();*/
		}
		
		return result;
	}
	
	//그룹원 강퇴하기 + 강퇴 쪽지 보내기
	@Transactional
	public int banMember(G_MemberDTO member_ban) {
		int result = 0;
		
		try {
			result = g_memberDAO.banMember(member_ban);
		} catch (Exception e) {
			result = -1;
			/*e.printStackTrace();*/
		}
		
		return result;
	}
	
	// 닉네임으로 상대방 이메일 가져오기
	public String getToUid(String nname) {
		UserDTO member = null;
		
		try {
			member = g_memberDAO.getToUid(nname);
		} catch (Exception e) {
			/*e.printStackTrace();*/
		}
		
		return member.getUid();
	}
	
	// 그룹원 매니저 권한 주기
	public int giveManager(G_MemberDTO member_auth) {
		int result = 0;
		
		try {
			member_auth.setGrid(2);
			result = g_memberDAO.giveManager(member_auth);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	// 그룹원 매니저 권한 뺏기
	public int giveMember(G_MemberDTO member_auth) {
		int result = 0;
		
		try {
			member_auth.setGrid(3);
			result = g_memberDAO.giveManager(member_auth);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
