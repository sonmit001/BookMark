package site.book.user.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import site.book.team.dao.G_AlarmDAO;
import site.book.team.dao.G_BookDAO;
import site.book.team.dao.G_MemberDAO;
import site.book.user.dao.UserDAO;
import site.book.user.dto.UserDTO;

@Service
public class UserService {
	
	// 변수 Start
	
	// 태웅
	
	
	// 희준
	@Autowired
	private SqlSession sqlsession;
	
	// 명수
	
	
	// 변수 End
	
	// 함수 Start
	
	// 태웅
	// Roll in ID check
	public int checkUserID(String uid) {
		UserDAO userDAO = sqlsession.getMapper(UserDAO.class);
		int row = 0;
		
		try {
			row = userDAO.checkUserID(uid);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return row;
	}
	
	// Roll in Nickname check
	public int checkUserNickname(String nname) {
		UserDAO userDAO = sqlsession.getMapper(UserDAO.class);
		int row = 0;
		
		try {
			row = userDAO.checkUserNickname(nname);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return row;
	}
	
	// 희준
	
	// 전체 회원수 가져오기
	public int getAllUser() {
		UserDAO userDAO = sqlsession.getMapper(UserDAO.class);
		int row = 0;
		
		try {
			row = userDAO.allUser();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return row;
	}
	
	// 신규 가입자수 가져오기
	public int getNewUser() {
		UserDAO userDAO = sqlsession.getMapper(UserDAO.class);
		int row = 0;
		
		try {
			row = userDAO.newUser();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return row;
	}
	
	// 블랙리스트 추가하기
	@Transactional
	public int blacklist(String uid) {
		UserDAO userdao = sqlsession.getMapper(UserDAO.class);
		G_BookDAO gbookdao = sqlsession.getMapper(G_BookDAO.class);
		G_MemberDAO gmemberdao = sqlsession.getMapper(G_MemberDAO.class);
		G_AlarmDAO galarmdao = sqlsession.getMapper(G_AlarmDAO.class);

		int row = 0;
		
		try {
			userdao.blacklist(uid);
			userdao.deleteUserBook(uid);
			gbookdao.deleteGroupBook(uid);
			gmemberdao.leaveAllGroup(uid);
			galarmdao.deleteAllGroupAlarm(uid);
			row = 1;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return row;
	}
	
	// 블랙리스트가 아닌 회원 리스트 가져오기 
	public List<UserDTO> getUserList() {
		UserDAO userDAO = sqlsession.getMapper(UserDAO.class);
		List<UserDTO> list = null;
		
		try {
			list = userDAO.getUserList();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	// 명수
	
	
	// 함수 End
}
