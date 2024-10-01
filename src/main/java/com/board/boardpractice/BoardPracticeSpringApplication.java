package com.board.boardpractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class BoardPracticeSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardPracticeSpringApplication.class, args);
    }

}
