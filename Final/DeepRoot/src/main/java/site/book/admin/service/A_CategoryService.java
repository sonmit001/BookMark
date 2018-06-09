/*
 * @Project : DeepRoot
 * @FileName : A_CategoryService.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.admin.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import site.book.admin.dao.A_CategoryDAO;
import site.book.admin.dto.A_CategoryDTO;

@Service
public class A_CategoryService {
	
	@Autowired
	private SqlSession sqlsession;
	
	// 전체 카테고리
	public List<A_CategoryDTO> getCategorys(){
		A_CategoryDAO categoryDAO = sqlsession.getMapper(A_CategoryDAO.class);
		List<A_CategoryDTO> list = null;
		
		try {
			list = categoryDAO.selectAllCategory();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 카테고리 추가
	public int addCategory(A_CategoryDTO category) {
		
		A_CategoryDAO categoryDAO = sqlsession.getMapper(A_CategoryDAO.class);
		int row = 0;
		try {
			row = categoryDAO.insertCategory(category);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return row;
	}
	
	// 카테고리 수정
	public int updateCategory(A_CategoryDTO category) {
		A_CategoryDAO categoryDAO = sqlsession.getMapper(A_CategoryDAO.class);
		int row = 0;
		
		try {
			row = categoryDAO.updateCategory(category);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return row;
	}
	
	// 카테고리 삭제
	public int deleteCategory(int acid) {
		A_CategoryDAO categoryDAO = sqlsession.getMapper(A_CategoryDAO.class);
		int row = 0;
		
		try {
			row = categoryDAO.deleteCategory(acid);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return row;
	}
	
}
