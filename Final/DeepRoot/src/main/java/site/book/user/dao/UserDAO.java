package site.book.user.dao;

import java.sql.SQLException;
import java.util.List;

import site.book.user.dto.UserDTO;

/**
 * @Class : UserDAO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
 */
public interface UserDAO {
	
	// 태웅
	
	// 희준
	
	//전체 회원수
	public int allUser() throws ClassNotFoundException, SQLException;
	
	// 신규 가입자 수
	public int newUser() throws ClassNotFoundException, SQLException;
	
	// 블랙리스트 추가
	public int blacklist(String uid) throws ClassNotFoundException, SQLException;
	
	// 유저리스트
	public List<UserDTO> getUserList() throws ClassNotFoundException, SQLException;
	
	// 회원이 작성한 카테고리 및 URL 지우기
	public int deleteUserBook(String uid) throws ClassNotFoundException, SQLException;
	
	
	// 명수
}
