package com.megalab.articlesite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing

public class ArticleSiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArticleSiteApplication.class, args);
    }

}
