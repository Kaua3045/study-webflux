package com.study.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
@EntityScan(basePackages = "com.study.webflux.models")
public class StudyWebfluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudyWebfluxApplication.class, args);
	}

}
