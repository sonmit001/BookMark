/*
 * @Project : DeepRoot
 * @FileName : A_BookService.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.admin.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import site.book.admin.dao.A_BookDAO;
import site.book.admin.dto.A_BookDTO;

@Service
public class A_BookService {
	
	@Autowired
	private SqlSession sqlsession;
	
	// 전체 URL 가져오기
	public List<A_BookDTO> getBooks() {
		A_BookDAO bookDAO = sqlsession.getMapper(A_BookDAO.class);
		List<A_BookDTO> list = null;
		
		try {
			list = bookDAO.selectAllBook();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return list;
		
	}
	
	// URL 하나 가져오기
	public A_BookDTO getBook(int abid) {
		A_BookDAO bookDAO = sqlsession.getMapper(A_BookDAO.class);
		A_BookDTO book = null;
		
		try {
			book = bookDAO.selectBook(abid);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return book;
	}
	
	// URL 추가하기
	public int addBook(A_BookDTO book) {
		A_BookDAO bookDAO = sqlsession.getMapper(A_BookDAO.class);
		int row = 0;
		
		try {
			row = bookDAO.insertBook(book);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return row;
	}
	
	// URL 수정하기
	public int updateBook(A_BookDTO book) {
		A_BookDAO bookDAO = sqlsession.getMapper(A_BookDAO.class);
		int row = 0;
		
		try {
			row = bookDAO.updateBook(book);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return row;
	}
	
	// URL 삭제하기
	public int deleteBook(int abid) {
		A_BookDAO bookDAO = sqlsession.getMapper(A_BookDAO.class);
		int row = 0;
		
		try {
			row = bookDAO.deleteBook(abid);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return row;
	}
	
	// 카테고리 별 URL 가져오기
	public List<A_BookDTO> getCategoryURL(int acid) {
		A_BookDAO bookDAO = sqlsession.getMapper(A_BookDAO.class);
		List<A_BookDTO> list = null;
		
		try {
			list = bookDAO.selectCategoryURL(acid);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	/* 2018-06-11(MON): 김태웅 추가 */
	// Main에서 URL 전체 보기
	public List<A_BookDTO> getMainBooks() {
		A_BookDAO bookDAO = sqlsession.getMapper(A_BookDAO.class);
		List<A_BookDTO> list = null;
		
		try {
			list = bookDAO.selectAllBookMain();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
}
