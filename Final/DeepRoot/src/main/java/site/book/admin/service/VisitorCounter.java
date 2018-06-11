/*
 * @Project : DeepRoot
 * @FileName : VisitorCounter.java
 * @Date : 2018. 6. 10.
 * @Author : 김희준
*/


package site.book.admin.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import site.book.admin.dto.VisitorDTO;

/**
 * @Class : VisitorCounter.java
 * @Date : 2018. 6. 10.
 * @Author : 김희준
 */

public class VisitorCounter implements HttpSessionListener{

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		System.out.println("visitor 들어옴");
		
		HttpSession session = se.getSession();
		
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext()); 
		VisitorService visitor_service = (VisitorService)context.getBean("visitorService");
		
		//request를 파라미터에 넣지 않고도 사용할수 있도록 설정
		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		
		VisitorDTO visitor = new VisitorDTO();
		
		visitor.setVip(req.getRemoteAddr());
		visitor.setVagent(req.getHeader("User-Agent"));
		System.out.println("refer : " + req.getHeader("referer"));
		
/*		if(req.getHeader("referer") == null) {
			visitor.setVrefer("");
		}else {
			visitor.setVrefer(req.getHeader("referer"));
		}*/
		
		visitor_service.insertVisitor(visitor);
		
		System.out.println("visitor insert");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		
	}

}
