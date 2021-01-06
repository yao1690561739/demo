package com.yao.system.config.swagger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author YL
 * @date 2020/9/29 13:53
 * @description swagger配置
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(name = "project.swagger-enabled", havingValue = "true", matchIfMissing = true)
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("demo")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yao.system.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Demo后台管理系统数据接口")
                .description("Swagger是一个规范和完整的框架，用于生成、描述、调用和可视化RESTful风格的Web服务。")
                .termsOfServiceUrl("http://localhost")
                .contact(new Contact("YL", "", ""))
                .version("v1.0.0")
                .build();
    }
}
