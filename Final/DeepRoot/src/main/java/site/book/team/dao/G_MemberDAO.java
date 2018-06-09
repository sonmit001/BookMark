/*
 * @Project : DeepRoot
 * @FileName : G_MemberDAO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.team.dao;

import java.sql.SQLException;

/**
 * @Class : G_MemberDAO.java
 * @Date : 2018. 6. 8.
 * @Author : 김희준
 */
public interface G_MemberDAO {
	
	// 회원이 진행하고 있는 모든 그룹 탈퇴(블랙리스트시 사용)
	public int leaveAllGroup(String uid) throws ClassNotFoundException, SQLException;
}
