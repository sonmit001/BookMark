/*
 * @Project : DeepRoot
 * @FileName : U_BookService.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준, 김명수
*/


package site.book.user.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import site.book.user.dao.U_BookDAO;
import site.book.user.dto.S_U_BookDTO;
import site.book.user.dto.U_BookDTO;

/**
 * @Class : U_BookService.java
 * @Date : 2018. 6. 8.
 * @Author : 김희준, 김명수
 */
@Service
public class U_BookService {
	
	// 변수 Start
	
	// 태웅
	
	
	// 희준
	
	
	// 명수
	@Autowired
	private SqlSession sqlsession;

	// 변수 End
	
	// 함수 Start
	
	// 태웅
	
	
	// 희준
	
	// 개인이 추가한 북마크 수
	public List<HashMap<String, String>> numOfBookByDate() {
		U_BookDAO bookDAO = sqlsession.getMapper(U_BookDAO.class);
		List<HashMap<String, String>> map = null;
		
		try {
			map = bookDAO.numOfBookByDate();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	// 소셜 개인 북마크 리스트
	public List<S_U_BookDTO> getSocialBookmarkList() {
		U_BookDAO bookDAO = sqlsession.getMapper(U_BookDAO.class);
		List<S_U_BookDTO> list = null;
		
		try {
			list = bookDAO.socialBookmarkList();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 소셜 개인 북마크 삭제
	public int deleteSocialBookmark(int ubid) {
		U_BookDAO bookDAO = sqlsession.getMapper(U_BookDAO.class);
		int row = 0;
		
		try {
			row = bookDAO.deleteSocialBookmark(ubid);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return row;
	}
	
	// 명수
	public List<U_BookDTO> getCategoryList(String uid) {	//해당
		
		U_BookDAO dao = sqlsession.getMapper(U_BookDAO.class);
		List<U_BookDTO> list = dao.getCategoryList(uid);
		
		return list;
	}

	public int insertRootFolder(int ubid, String uid) {
		
		U_BookDAO dao = sqlsession.getMapper(U_BookDAO.class);
		int result = dao.insertRootFolder(ubid, uid);
		
		return 0;
	}

	public int getmaxid() {

		U_BookDAO dao = sqlsession.getMapper(U_BookDAO.class);
		int maxid = dao.getMaxId();
		
		return maxid;
	}

	public int updateNodeText(int id, String text) {

		U_BookDAO dao = sqlsession.getMapper(U_BookDAO.class);
		int result = dao.updateNodeText(id, text);
		
		return result;
	}

	public int addFolderOrUrl(U_BookDTO dto) {

		U_BookDAO dao = sqlsession.getMapper(U_BookDAO.class);
		int result = dao.addFolderOrUrl(dto);
		
		return result;
	}

	public void deleteFolderOrUrl(String str) {
		
		U_BookDAO dao = sqlsession.getMapper(U_BookDAO.class);
		dao.deleteFolderOrUrl(str);
		
	}

	public int editUrl(U_BookDTO dto) {

		U_BookDAO dao = sqlsession.getMapper(U_BookDAO.class);
		int result = dao.editUrl(dto);
		
		return result;
	}

	public List<U_BookDTO> getUrl(int ubid) {

		U_BookDAO dao = sqlsession.getMapper(U_BookDAO.class);
		List<U_BookDTO> list = dao.getUrl(ubid);
		
		return list;
	}

	public int dropNode(int dragnode, int dropnode) {

		U_BookDAO dao = sqlsession.getMapper(U_BookDAO.class);
		int result = dao.dropNode(dragnode, dropnode);
		return 0;
	}

	// 함수 End
}
