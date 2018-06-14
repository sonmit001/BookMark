/*
 * @Project : DeepRoot
 * @FileName : TopDAO.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/


package site.book.social.dao;

import java.sql.SQLException;
import java.util.List;

import site.book.social.dto.TopDTO;

/**
 * @Class : TopDAO.java
 * @Date : 2018. 6. 14.
 * @Author : 김희준
 */
public interface TopDAO {
	
	// 개인 Top5 가져오기
	public List<TopDTO> selectUTop5() throws ClassNotFoundException, SQLException;
	
	// 개인 Top5 Urlname 가져오기
	public String selectUTop5Urlname(String url) throws ClassNotFoundException, SQLException;
		
	// 그룹 Top5 가져오기
	public List<TopDTO> selectGTop5() throws ClassNotFoundException, SQLException;
	
	// 그룹 Top5 Urlname 가져오기
	public String selectGTop5Urlname(String url) throws ClassNotFoundException, SQLException;
		
	// 전체 Top5 가져오기
	public List<TopDTO> selectATop5() throws ClassNotFoundException, SQLException;
	
	// 전체 Top5 Urlname 가져오기
	public String selectATop5Urlname(String url) throws ClassNotFoundException, SQLException;	
	
}
