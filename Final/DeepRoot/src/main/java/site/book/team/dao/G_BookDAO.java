/*
 * @Project : DeepRoot
 * @FileName : G_BookDAO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.team.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * @Class : G_BookDAO.java
 * @Date : 2018. 6. 7.
 * @Author : 김희준
 */
public interface G_BookDAO {
	
	// 그룹이 추가한 북마크 수
	public List<HashMap<String, String>> numOfBookByDate() throws ClassNotFoundException, SQLException;

	// 회원이 그룹에 작성한 URL 지우기
	public int deleteGroupBook(String uid) throws ClassNotFoundException, SQLException;
	
}
