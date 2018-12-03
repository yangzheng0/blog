package com.blog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}
	
	@Override
	public void run(String... args){
		System.err.println("blog =======> 启动完成");
	}
}
