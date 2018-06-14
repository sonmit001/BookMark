/*
 * @Project : DeepRoot
 * @FileName : TopService.java
 * @Date : 2018. 6. 5.
 * @Author : 김희준
*/

package site.book.social.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import site.book.social.dao.TopDAO;
import site.book.social.dto.TopDTO;

/**
 * @Class : TopService.java
 * @Date : 2018. 6. 14.
 * @Author : 김희준
 */
@Service
public class TopService {

	@Autowired
	private SqlSession sqlsession;

	// 개인 Top5 가져오기
	public List<TopDTO> getUTop5() {
		TopDAO top_dao = sqlsession.getMapper(TopDAO.class);
		List<TopDTO> list = null;

		try {
			list = top_dao.selectUTop5();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		// 반복문 돌면서 가장 urlname 넣어주기
		for (int i = 0; i < list.size(); i++) {
			try {
				list.get(i).setUrlname(top_dao.selectUTop5Urlname(list.get(i).getUrl()));
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	// 그룹 Top5 가져오기
	public List<TopDTO> getGTop5() {
		TopDAO top_dao = sqlsession.getMapper(TopDAO.class);
		List<TopDTO> list = null;

		try {
			list = top_dao.selectGTop5();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		// 반복문 돌면서 가장 urlname 넣어주기
		for (int i = 0; i < list.size(); i++) {
			try {
				list.get(i).setUrlname(top_dao.selectGTop5Urlname(list.get(i).getUrl()));
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	// 전체 Top5 가져오기
	public List<TopDTO> getATop5() {
		TopDAO top_dao = sqlsession.getMapper(TopDAO.class);
		List<TopDTO> list = null;

		try {
			list = top_dao.selectATop5();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		// 반복문 돌면서 가장 urlname 넣어주기
		for (int i = 0; i < list.size(); i++) {
			try {
				list.get(i).setUrlname(top_dao.selectATop5Urlname(list.get(i).getUrl()));
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}

		return list;
	}
}
