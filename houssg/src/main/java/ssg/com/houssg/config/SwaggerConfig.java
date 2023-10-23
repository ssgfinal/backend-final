package ssg.com.houssg.config;

import java.util.Arrays;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        // "bearer" 스키마 정의
        SecurityScheme bearerScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        // "Refresh-Token" 스키마 정의
        SecurityScheme refreshTokenScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("RefreshToken");

        // "bearerAuth"와 "refreshTokenAuth" 보안 요구사항 정의
        SecurityRequirement bearerSecurityRequirement = new SecurityRequirement().addList("bearerAuth");
        SecurityRequirement refreshTokenSecurityRequirement = new SecurityRequirement().addList("refreshTokenAuth");
        
        
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", bearerScheme)
                        .addSecuritySchemes("refreshTokenAuth", refreshTokenScheme))
                .security(Arrays.asList(bearerSecurityRequirement, refreshTokenSecurityRequirement));
    }
}
