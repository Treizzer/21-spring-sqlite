package com.treizer.spring_sqlite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.treizer.spring_sqlite.persistence.repository.IUserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class SpringSqliteApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSqliteApplication.class, args);
    }

    @Autowired
    private IUserRepository userRepository;

    @Bean
    CommandLineRunner init() {
        return args -> {
            this.userRepository.findAll().forEach(user -> {
                log.info(user.toString());
            });
        };
    }

}
