package com.expensemanager;

import com.expensemanager.config.WebAppInitializer;
import java.io.File;
import java.util.Collections;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.SpringServletContainerInitializer;

public class AppRunner {

    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8082); // Changed to 8082 to avoid "Address already in use"
        tomcat.getConnector(); // trigger initialization
        
        String contextPath = "";
        String docBase = new File(".").getAbsolutePath();
        Context context = tomcat.addContext(contextPath, docBase);

        // Map Spring to Tomcat
        context.addServletContainerInitializer(
                new SpringServletContainerInitializer(),
                Collections.singleton(WebAppInitializer.class));

        System.out.println("Starting Tomcat Embedded on port 8082...");
        tomcat.start();
        tomcat.getServer().await();
    }
}
