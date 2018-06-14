/*
 * @Project : DeepRoot
 * @FileName : U_BookDAO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.user.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import site.book.user.dto.S_U_BookDTO;
import site.book.user.dto.U_BookDTO;

public interface U_BookDAO {
	
	// 태웅
	
	
	// 희준
	
	// 개인이 추가한 북마크수
	public List<HashMap<String, String>> numOfBookByDate() throws ClassNotFoundException, SQLException;
	
	// 소셜 개인 북마크 리스트
	public List<S_U_BookDTO> socialBookmarkList() throws ClassNotFoundException, SQLException;
	
	// 소셜 개인 북마크 삭제
	public int deleteSocialBookmark(int ubid) throws ClassNotFoundException, SQLException;
	
	// 명수
	public List<U_BookDTO> getCategoryList(String uid);

	public int insertRootFolder(int ubid, String uid);

	public int getMaxId();

	public int addFolderOrUrl(U_BookDTO dto);

	public void deleteFolderOrUrl(String str);

	public int editUrl(U_BookDTO dto);

	public List<U_BookDTO> getUrl(int ubid);

	public int dropNode(HashMap<String, String> param);

	public int updateNodeText(HashMap<String, String> param);

	public int shareUrlEdit(U_BookDTO dto);


}
