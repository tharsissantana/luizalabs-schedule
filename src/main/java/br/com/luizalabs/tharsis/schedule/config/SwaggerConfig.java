package br.com.luizalabs.tharsis.schedule.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.luizalabs.tharsis.schedule.controller"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, responseMessage())
                .globalResponseMessage(RequestMethod.POST, responseMessage())
                .globalResponseMessage(RequestMethod.PUT, responseMessage())
                .globalResponseMessage(RequestMethod.DELETE, responseMessage())
                .apiInfo(apiInfo());
    }

    private List<ResponseMessage> responseMessage() {
        return Arrays.asList(
                new ResponseMessageBuilder().code(500).message("Ocorreu um erro inesperado").build());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API Luizalabs Schedule")
                .description("API de agndamento de envio de mensagens")
                .version("1.0.0")
                .license("GNU General Public License v3.0")
                .licenseUrl("https://www.gnu.org/licenses/gpl-3.0.en.html")
                .contact(new Contact("Tharsis Santana", "https://github.com/tharsissantana", "tharsis.ssantana@gmail.com"))
                .build();
    }
}
