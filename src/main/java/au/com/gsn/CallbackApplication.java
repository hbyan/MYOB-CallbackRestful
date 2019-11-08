package au.com.gsn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class CallbackApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(CallbackApplication.class, args);
	}
}
