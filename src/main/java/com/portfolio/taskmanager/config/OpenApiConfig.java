package com.portfolio.taskmanager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Task Manager API")
                        .version("1.0.0")
                        .description("""
                                REST API for task management.
                                
                                Features:
                                - Full CRUD operations
                                - Filter by status and priority
                                - Keyword search
                                - Statistics summary
                                
                                Portfolio project built with Spring Boot + JPA + H2.
                                """)
                        .contact(new Contact()
                                .name("Your Name")
                                .email("your@email.com")
                                .url("https://github.com/your-username")));
    }
}
