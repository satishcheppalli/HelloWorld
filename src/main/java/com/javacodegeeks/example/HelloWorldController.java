package com.javacodegeeks.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@GetMapping("/hello")
	public String sayHello() throws InterruptedException {
		for(int i=0; i<=1000000;i++){
			System.out.println(i);
		}
		Thread.sleep(60 * 1000);
		return "Hello world with rolling updates...";
	}
}
