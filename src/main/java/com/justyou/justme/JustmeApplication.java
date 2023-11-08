package com.justyou.justme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories
@EnableRedisRepositories
public class JustmeApplication {
    public static void main(String[] args) {
        SpringApplication.run(JustmeApplication.class, args);
    }
}
