package site.book.main.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * @Class : LoginFailHandler.java
 * @Date : 2018. 6. 12.
 * @Author : 김태웅
 */
public class LoginFailHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		request.setAttribute("msg", "fail");
		request.getRequestDispatcher("/joinus/login.do").forward(request, response);
	}
}
