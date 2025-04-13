package com.xpert.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Utility class to access Spring-managed beans from non-Spring classes.
 */
@Component
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        // Set static context only once
    	 synchronized(SpringContext.class) {
    		        if (SpringContext.context == null) {
    		             SpringContext.context = applicationContext;
    		         }
    		    }
    }

    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }
}
