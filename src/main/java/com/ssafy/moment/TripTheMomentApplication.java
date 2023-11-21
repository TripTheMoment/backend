package com.ssafy.moment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TripTheMomentApplication {

    public static void main(String[] args) {
        SpringApplication.run(TripTheMomentApplication.class, args);
    }

}
