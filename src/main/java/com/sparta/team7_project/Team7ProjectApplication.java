package com.sparta.team7_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
//@EnableJpaAuditing없으면 entity패키지의 Timestamped 클래스의 .data.annotation.LastModifiedDate같은 날짜 자동생성이 안된다.
public class Team7ProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(Team7ProjectApplication.class, args);
    }

}
