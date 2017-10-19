package com.sinog2c.mvc.listener;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class YwgkListener implements ServletContextListener   {
	private ServletContext context = null;   
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		//容器启动后 放置全局变量
		context = event.getServletContext();  
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("ywgk.properties");
		Properties properties = new Properties();
		try {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Iterator<Entry<Object, Object>> it = properties.entrySet().iterator();  
        while (it.hasNext()) {  
            Entry<Object, Object> entry = it.next();  
            String key = entry.getKey().toString();  
            String value = entry.getValue()==null?"":entry.getValue().toString();  
            context.setAttribute(key, value);
        }  
		
	}
}
