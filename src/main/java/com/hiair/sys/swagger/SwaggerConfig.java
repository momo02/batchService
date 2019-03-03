package com.hiair.sys.swagger;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebMvc
@ComponentScan(basePackages = "com.hiair.app")
public class SwaggerConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
	@Bean
	public Docket api() {
		
		 // 에러 리스트
        List<ResponseMessage> responseMessageStatus = Arrays.asList( 
            new ResponseMessageBuilder().code(500).message("서버처리 중 오류 발생").build(), //responseModel(new ModelRef("RestResponse")).
            new ResponseMessageBuilder().code(400).message("클라이언트 오류 , Validation 등의 오류 표출").build(),
            new ResponseMessageBuilder().code(401).message("인증 실패 관련 오류").build(),
            new ResponseMessageBuilder().code(404).message("리소스를 못찾음").build()
        );
		
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				// 현재 RequestMapping으로 할당된 모든 URL 리스트를 추출
				.apis(RequestHandlerSelectors.any()) 
				.paths(PathSelectors.ant("/rest/scheduler/**")) // 그중 /schedule/** 인 URL들만 필터링
				.build()
				.apiInfo(apiInfo())
				//기본 번황 Message 설정
		        .useDefaultResponseMessages(false)
		        // RequestMethod 설정
		        .globalResponseMessage(RequestMethod.POST, responseMessageStatus);
	}

	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
        .title("하이에어 - 배치 API 테스트 스웨거")
        .version("0.0.1")
        .build();
    }
}




