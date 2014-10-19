package com.fire.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

/**
 * The listener interface for receiving init events. The class that is interested in processing a init event implements this interface, and the object
 * created with that class is registered with a component using the component's <code>addInitListener<code> method. When
 * the init event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see InitEvent
 */
public class InitListener implements ServletContextListener {

	private static final Logger logger = Logger.getLogger(InitListener.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @seejavax.servlet.ServletContextListener#contextDestroyed(javax.servlet. ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet .ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext servletContext = servletContextEvent.getServletContext();
		String rootPath = servletContext.getRealPath("/");
		System.setProperty("REAL_PATH", rootPath);
		logger.info("System.getProperty('REAL_PATH')====>" + rootPath);
	}

}
