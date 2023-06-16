package com.example.techtest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.example.techtest.repository", "com.example.techtest.repository.**.*"})

public class TechTestApplication {

  public static void main(String[] args) {
    SpringApplication.run(TechTestApplication.class, args);
  }

}
