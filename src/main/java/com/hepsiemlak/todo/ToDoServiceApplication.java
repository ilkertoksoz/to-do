package com.hepsiemlak.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan(basePackages = "com.hepsiemlak.todo")
public class ToDoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToDoServiceApplication.class, args);
	}
}
