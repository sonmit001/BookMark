/*
 * @Project : DeepRoot
 * @FileName : G_BookDAO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.team.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import site.book.team.dto.G_BookDTO;
import site.book.user.dto.U_BookDTO;

/**
 * @Class : G_BookDAO.java
 * @Date : 2018. 6. 7.
 * @Author : 김희준, 김태웅
 */
public interface G_BookDAO {
	//희준
	// 그룹이 추가한 북마크 수
	public List<HashMap<String, String>> numOfBookByDate() throws ClassNotFoundException, SQLException;

	// 회원이 그룹에 작성한 URL 지우기
	public int deleteGroupBook(String uid) throws ClassNotFoundException, SQLException;

	// 완료된 그룹의 북마크 가져오기
	public List<G_BookDTO> getCompletedTeamBookmark(int gid) throws ClassNotFoundException, SQLException;
	
	// 그룹의 총 조회수 가져오기
	public int selectGroupViews(int gid) throws ClassNotFoundException, SQLException;
	
	//태웅
	// 그룹 북마크 중 카테고리만 가져오기(메인 링크 -> 진행중인 그룹 북마크로 가져오기)
	public List<G_BookDTO> getGroupCategoryList(String gid) throws ClassNotFoundException, SQLException;
	// 현재  GBID(Group Bookmark ID)의  max + 1  
	public int getMaxGBID() throws ClassNotFoundException, SQLException;
	// 그룹 처음 생성시 루트 폴더 생성
	public int insertRootFolder(String gid, String uid) throws ClassNotFoundException, SQLException;
	// 그룹 북마크로 URL 하나 insert
	public int insertGroupBookmark(G_BookDTO gbook) throws ClassNotFoundException, SQLException;
}
