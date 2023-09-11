package com.example.gameRecommend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebMvc
@EnableSwagger2  //开启swagger2
public class SwaggerConfig {

    //配置了Swagger的Docker的Bean实例
    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(true) //配置是否启用Swagger，如果是false，在浏览器将无法访问
                .select()// 通过.select()方法，去配置扫描接口,RequestHandlerSelectors配置如何扫描接口
                .apis(RequestHandlerSelectors.basePackage("com.example.vueblog.controller")).build();
    }

    //配置swagger信息 apiInfo
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
            .title("test").description("yanshi").build();


    }
}