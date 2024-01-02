package com.hepsiemlak.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan(basePackages = "com.hepsiemlak.todo")
@EnableAspectJAutoProxy
public class ToDoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToDoServiceApplication.class, args);
	}
}
