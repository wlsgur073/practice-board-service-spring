package com.board.boardpractice.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;

@Configuration
public class ThymeleafConfig {

    @Bean
    public SpringResourceTemplateResolver thymeleafTemplateResolver(
            SpringResourceTemplateResolver defaultTemplateResolver,
            Thymeleaf3Properties thymeleaf3Properties
    ) {
        // 기존에 실행되는 SpringResourceTemplateResolver에 thymeleaf3Properties를 추가한 것.
        defaultTemplateResolver.setUseDecoupledLogic(thymeleaf3Properties.decoupledLogic());

        return defaultTemplateResolver;
    }

    // Spring Boot 3.X 에서는 해당 방식으로 bean 등록.
    @ConfigurationProperties("spring.thymeleaf3") // application.yml에서 활성화
    public record Thymeleaf3Properties(boolean decoupledLogic) {}

}