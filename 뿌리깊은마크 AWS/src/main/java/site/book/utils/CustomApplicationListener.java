package site.book.utils;

import java.util.List;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.web.context.support.XmlWebApplicationContext;

import site.book.admin.dto.A_BookDTO;
import site.book.admin.dto.A_CategoryDTO;
import site.book.admin.service.A_BookService;
import site.book.admin.service.A_CategoryService;

@SuppressWarnings("rawtypes")
public class CustomApplicationListener implements ApplicationListener {

	@Autowired
	private A_CategoryService a_category_service;
	
	@Autowired
	private A_BookService a_book_service;
	
	private ServletContext servletContext;
	
	private static final Logger logger = LoggerFactory.getLogger(CustomApplicationListener.class);
	
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		//logger.info((String) event.getSource());
		if(event.getSource() instanceof XmlWebApplicationContext) {
			logger.info("onApplicationEvent start");
			XmlWebApplicationContext webApplicationContext = (XmlWebApplicationContext) event.getSource();
			servletContext = webApplicationContext.getServletContext();
			startApplication();
		}
	}
	
	public void startApplication() {
		
		try {
			List<A_CategoryDTO> categoryList = a_category_service.getCategorys();
			servletContext.setAttribute("categoryList", categoryList);
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		try {
			List<A_BookDTO> bookList = a_book_service.getMainBooks();
			servletContext.setAttribute("bookList", bookList);
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
	}

}
