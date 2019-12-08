package com.javacodegeeks.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class HelloWorldController {

	@GetMapping("/hello")
	public String sayHello() throws InterruptedException {
		/*for(int i=0; i<=10000;i++){
			System.out.println(i);
		}
		Thread.sleep(30 * 1000);*/
		String hostname="******UNKONWN HOST*******";
		String hostAddr="******UNKONWN HOST ADDR*******";
		/*try {
			 
		    InetAddress inetHost = InetAddress.getByName("ocscl2dva004.logistics.fedex.com");
		    hostname = inetHost.getHostName();
		    hostAddr = inetHost.getHostAddress();
		    System.out.println("The host name was: " + hostname);
		    System.out.println("The hosts IP address is: " + inetHost.getHostAddress());
		 
		} catch(UnknownHostException ex) {
		 
		    System.out.println("Unrecognized host");
		}*/
		
		return "Hello world !!!!!!";
		//return "hostname: "+hostname+" Host Addr: "+hostAddr;
	}
}
