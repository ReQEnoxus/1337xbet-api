package com.enoxus.xbetapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

@SpringBootApplication
@EnableSwagger2
public class XbetApiApplication {

    @Bean
    public DecimalFormat decimalFormat() {
        DecimalFormat df = new DecimalFormat("#.###", new DecimalFormatSymbols(Locale.US));
        df.setRoundingMode(RoundingMode.FLOOR);

        return df;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.enoxus.xbetapi.controllers"))
                .paths(PathSelectors.any())
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(XbetApiApplication.class, args);
    }

}
