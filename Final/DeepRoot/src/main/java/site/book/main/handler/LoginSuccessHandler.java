package site.book.main.handler;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import site.book.user.dto.UserDTO;
import site.book.user.service.UserService;

/**
 * @Class : AdminController.java
 * @Date : 2018. 6. 12.
 * @Author : 김태웅
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		// authentication.getName(): userid
		request.setAttribute("userid", authentication.getName());
		request.setAttribute("msg", "success");
		
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		boolean authorized = authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
		
		//System.out.println(authorized);
		
		if(authorized) {
			System.out.println("????");
			request.setAttribute("ROLE", "ADMIN");
		}else {
			request.setAttribute("ROLE", "USER");
		}
		
		request.getRequestDispatcher("/joinus/login.do").forward(request, response);
				
	}
}
