package com.shreyas.springbootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ServletComponentScan
@SpringBootApplication(scanBasePackages = "com.shreyas.springbootstrap")
@EnableJpaRepositories("com.shreyas.springbootstrap.repository")
@EntityScan("com.shreyas.springbootstrap.datamodel")
public class ShreyasApplication{

    public static void main(String[] args) {
        SpringApplication.run(ShreyasApplication.class, args);
    }

}
