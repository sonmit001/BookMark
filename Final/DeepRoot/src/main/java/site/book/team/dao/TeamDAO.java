/*
 * @Project : DeepRoot
 * @FileName : TeamDAO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.team.dao;

import java.sql.SQLException;
import java.util.List;

import site.book.team.dto.S_TeamDTO;

/**
 * @Class : TeamDAO.java
 * @Date : 2018. 6. 8.
 * @Author : 김희준
 */
public interface TeamDAO {
	
	// 소셜 그룹 리스트 가져오기
	public List<S_TeamDTO> socialGroupList() throws ClassNotFoundException, SQLException;
	
	// 소셜 그룹 삭제
	public int deleteSocialGroup(int gid) throws ClassNotFoundException, SQLException;
}
