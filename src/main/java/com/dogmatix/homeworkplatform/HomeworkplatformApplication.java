package com.dogmatix.homeworkplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
    "com.dogmatix.homeworkplatform",
    "com.dogmatix.homeworkplatform.RolesAndPermitions.Repository.UserRepository"
})
public class HomeworkplatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeworkplatformApplication.class, args);
	}

}
