/*
 * @Project : DeepRoot
 * @FileName : G_BookService.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.team.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import site.book.team.dao.G_BookDAO;

/**
 * @Class : G_BookService.java
 * @Date : 2018. 6. 7.
 * @Author : 김희준
 */
@Service
public class G_BookService {
	
	@Autowired
	private SqlSession sqlsession;
	
	// 그룹이 추가한 북마크 수
	public List<HashMap<String, String>> numOfBookByDate() {
		G_BookDAO bookDAO = sqlsession.getMapper(G_BookDAO.class);
		List<HashMap<String, String>> map = null;
		
		try {
			map = bookDAO.numOfBookByDate();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return map;
	}
}
