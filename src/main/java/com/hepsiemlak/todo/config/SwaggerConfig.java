package com.hepsiemlak.todo.config;

import java.time.LocalDate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	public static final String HEPSI_EMLAK_TO_DO_SERVICE_TAG = "To Do Service";

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("To Do API Reference")
                .license("Hepsi Emlak")
                .licenseUrl("https://www.hepsiemlak.com")
                .termsOfServiceUrl("")
                .contact(new Contact("Hepsi Emlak", "https://www.hepsiemlak.com/", "bilgi@hepsiemlak.com"))
                .version("1.0.0")
                .build();
    }

    @Bean
    public Docket customImplementation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select().paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.hepsiemlak.todo.api"))
                .build()
                .pathMapping("/")
                .useDefaultResponseMessages(false)
                .directModelSubstitute(LocalDate.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
        		.tags(new Tag(HEPSI_EMLAK_TO_DO_SERVICE_TAG, "TO_DO Service API"));
    }
}