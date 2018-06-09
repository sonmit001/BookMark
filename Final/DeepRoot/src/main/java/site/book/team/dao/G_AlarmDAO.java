/*
 * @Project : DeepRoot
 * @FileName : G_AlarmDAO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.team.dao;

import java.sql.SQLException;

/**
 * @Class : G_AlarmDAO.java
 * @Date : 2018. 6. 8.
 * @Author : 김희준
 */
public interface G_AlarmDAO {
	
	// 회원이 보내거나 받은 모든 그룹알림 지우기(블랙리스트시 사용)
	public int deleteAllGroupAlarm(String uid) throws ClassNotFoundException, SQLException;
}
