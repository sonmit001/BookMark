package site.book.social.dao;

import java.sql.SQLException;

import site.book.team.dto.G_BookDTO;

/**
 * @Class : S_BookmarkDAO.java
 * @Date : 2018. 6. 22.
 * @Author : 정민재
 */

public interface S_BookmarkDAO {
	
	// 민재
	// 그룹 북마크로 URL 하나 insert
	public int insertGroupBookmark(G_BookDTO gbook) throws ClassNotFoundException, SQLException;
	
	
	
	//진수
}
