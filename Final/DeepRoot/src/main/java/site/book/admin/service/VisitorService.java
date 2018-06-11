/*
 * @Project : DeepRoot
 * @FileName : VisitorService.java
 * @Date : 2018. 6. 10.
 * @Author : 김희준
*/


package site.book.admin.service;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import site.book.admin.dao.VisitorDAO;
import site.book.admin.dto.VisitorDTO;

/**
 * @Class : VisitorService.java
 * @Date : 2018. 6. 10.
 * @Author : 김희준
 */

@Service
public class VisitorService {
	
	@Autowired
	private SqlSession sqlsession;
	
	// 방문자 기록
	public int insertVisitor(VisitorDTO	visitor) {
		VisitorDAO visitor_dao = sqlsession.getMapper(VisitorDAO.class);
		int row = 0;
		
		try {
			row = visitor_dao.insertVisitor(visitor);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return row;
	}
}
