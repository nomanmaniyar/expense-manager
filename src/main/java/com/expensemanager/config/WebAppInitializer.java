package com.expensemanager.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    // Spring Root context configurations (JPA, DB, beans)
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { AppConfig.class, JpaConfig.class };
    }

    // Spring MVC context configuration (Controllers, View Resolvers)
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { WebMvcConfig.class };
    }

    // Map DispatcherServlet to "/*"
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
}
