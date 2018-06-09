/*
 * @Project : DeepRoot
 * @FileName : NoticeDAO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.admin.dao;

import java.sql.SQLException;

import site.book.admin.dto.NoticeDTO;

/**
 * @Class : NoticeDAO.java
 * @Date : 2018. 6. 8.
 * @Author : 김희준
 */
public interface NoticeDAO {
	
	// 공지사항 쓰기
	public int noticeReg(String ncontent) throws ClassNotFoundException, SQLException;
	
}
