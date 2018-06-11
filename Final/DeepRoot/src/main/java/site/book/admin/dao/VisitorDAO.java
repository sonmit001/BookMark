/*
 * @Project : DeepRoot
 * @FileName : VisitorDAO.java
 * @Date : 2018. 6. 10.
 * @Author : 김희준
*/


package site.book.admin.dao;

import java.sql.SQLException;

import site.book.admin.dto.VisitorDTO;

/**
 * @Class : VisitorDAO.java
 * @Date : 2018. 6. 10.
 * @Author : 김희준
 */
public interface VisitorDAO {
	
	public int insertVisitor(VisitorDTO visitor) throws ClassNotFoundException, SQLException;
}
