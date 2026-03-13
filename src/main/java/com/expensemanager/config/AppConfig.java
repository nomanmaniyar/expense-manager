package com.expensemanager.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
    "com.expensemanager.service",
    "com.expensemanager.repository"
})
public class AppConfig {
    // Other root application beans can be declared here.
}
