//package ssg.com.houssg.config;
//
//import java.lang.annotation.Annotation;
//import java.util.Arrays;
//import java.util.List;
//import java.util.function.Predicate;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import io.swagger.annotations.AuthorizationScope;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiKey;
//import springfox.documentation.service.SecurityReference;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spi.service.contexts.SecurityContext;
//import springfox.documentation.spring.web.plugins.Docket;
//
//@Configuration
//public class SwaggerConfig {
//
//  public static final String AUTHORIZATION_HEADER = "Authorization";
//
//  @Bean
//  public Docket api() {
//    return new Docket(DocumentationType.SWAGGER_2).securityContexts(
//            Arrays.asList(securityContext())).securitySchemes(Arrays.asList(apiKey())).select()
//        .apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
//  }
//
//  private ApiKey apiKey() {
//    return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
//  }
//
//  private SecurityContext securityContext() {
//    return SecurityContext.builder().securityReferences(defaultAuth()).build();
//  }
//
//  List<SecurityReference> defaultAuth() {
//    AuthorizationScope authorizationScope = new AuthorizationScope() {
//		
//		@Override
//		public Class<? extends Annotation> annotationType() {
//			// TODO Auto-generated method stub
//			return null;
//		}
//		
//		@Override
//		public String scope() {
//			// TODO Auto-generated method stub
//			return null;
//		}
//		
//		@Override
//		public String description() {
//			// TODO Auto-generated method stub
//			return null;
//		}
//	};("global", "accessEverything");
//    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//    authorizationScopes[0] = authorizationScope;
//    return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
//  }
//}