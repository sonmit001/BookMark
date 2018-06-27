package site.book.social.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import site.book.social.dao.S_BookmarkDAO;
import site.book.social.dao.TopDAO;
import site.book.team.dto.G_BookDTO;
import site.book.user.dto.U_BookDTO;

/**
 * @Class : SufingService.java
 * @Date : 2018. 6. 22.
 * @Author : 정진수
 */

@Service
public class SurfingService {

	@Autowired
	private SqlSession sqlsession;
	
	//해당 회원 북마크 가져오기
	public List<U_BookDTO> getCategoryList(String nname) {
		
		TopDAO dao = sqlsession.getMapper(TopDAO.class);
		List<U_BookDTO> list = dao.getCategoryList(nname);
		
		return list;
	}
	
	// 민재
	// 한 URL을 자신의 그룹 북마크에 추가 
	public int insertGroupBookmark(G_BookDTO gbook) {
		S_BookmarkDAO dao = sqlsession.getMapper(S_BookmarkDAO.class);
		int result = 0;
		
		try {
			result = dao.insertGroupBookmark(gbook);
		}catch (Exception e) {
			/*e.printStackTrace();*/
		}
		
		return result;
	}
	
}
