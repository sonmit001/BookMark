/*
 * @Project : DeepRoot
 * @FileName : A_BookDAO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.admin.dao;

import java.sql.SQLException;
import java.util.List;

import site.book.admin.dto.A_BookDTO;

/**
 * @Class : A_BookDAO.java
 * @Date : 2018. 6. 7.
 * @Author : 김희준
 */
public interface A_BookDAO {
	
	// URL 전체 보기
	public List<A_BookDTO> selectAllBook() throws ClassNotFoundException, SQLException;
	
	// URL 하나 보기
	public A_BookDTO selectBook(int abid) throws ClassNotFoundException, SQLException;
	
	// URL 추가하기
	public int insertBook(A_BookDTO book) throws ClassNotFoundException, SQLException;
	
	// URL 수정하기
	public int updateBook(A_BookDTO book) throws ClassNotFoundException, SQLException;
	
	// URL 삭제하기
	public int deleteBook(int abid) throws ClassNotFoundException, SQLException;
}
