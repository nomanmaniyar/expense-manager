package com.expensemanager;

import com.expensemanager.config.WebAppInitializer;
import java.io.File;
import java.io.InputStream;
import java.util.Collections;
import java.util.Properties;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.SpringServletContainerInitializer;

public class AppRunner {

    public static void main(String[] args) throws Exception {
        // Get the active environment profile (e.g., dev, uat) from system properties
        String env = System.getProperty("app.env");
        String configFilename = "application" + (env != null && !env.isEmpty() ? "-" + env : "") + ".properties";

        // Load properties manually since Spring Context is not yet initialized
        int port = 8080; // Default fallback port
        try (InputStream input = AppRunner.class.getClassLoader().getResourceAsStream(configFilename)) {
            if (input != null) {
                Properties prop = new Properties();
                prop.load(input);
                if (prop.getProperty("server.port") != null) {
                    port = Integer.parseInt(prop.getProperty("server.port"));
                }
            } else if (env != null) {
                // If specific env file not found, try falling back to default application.properties
                try (InputStream defaultInput = AppRunner.class.getClassLoader().getResourceAsStream("application.properties")) {
                    if (defaultInput != null) {
                        Properties prop = new Properties();
                        prop.load(defaultInput);
                        if (prop.getProperty("server.port") != null) {
                            port = Integer.parseInt(prop.getProperty("server.port"));
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Could not load properties for port configuration. Using default.");
        }

        // Allow system environment variables or JVM arguments to override properties
        String envPort = System.getenv("PORT");
        if (envPort != null) {
            port = Integer.parseInt(envPort);
        }

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.getConnector(); // trigger initialization
        
        String contextPath = "";
        String docBase = new File(".").getAbsolutePath();
        Context context = tomcat.addContext(contextPath, docBase);

        // Map Spring to Tomcat
        context.addServletContainerInitializer(
                new SpringServletContainerInitializer(),
                Collections.singleton(WebAppInitializer.class));

        System.out.println("Starting Tomcat Embedded on port " + port + "...");
        tomcat.start();
        tomcat.getServer().await();
    }
}
