package com.javacodegeeks.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@GetMapping("/hello")
	public String sayHello() throws InterruptedException {
		/*for(int i=0; i<=10000;i++){
			System.out.println(i);
		}
		Thread.sleep(30 * 1000);*/
		String hostname="******UNKONWN HOST*******";
		String hosrAddr="******UNKONWN HOST ADDR*******";
		try {
			 
		    InetAddress inetHost = InetAddress.getByName("ocscl1dva003.logistics.fedex.com");
		    hostname = inetHost.getHostName();
		    hosrAddr = inetHost.getHostAddress();
		    System.out.println("The host name was: " + hostName);
		    System.out.println("The hosts IP address is: " + inetHost.getHostAddress());
		 
		} catch(UnknownHostException ex) {
		 
		    System.out.println("Unrecognized host");
		}
		
		return "Hello world with rolling updates, hostname: "+hostname+" Host Addr: "+hosrAddr;
	}
}
