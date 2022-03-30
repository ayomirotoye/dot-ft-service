package africa.dotpay.ftservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DotFtServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DotFtServiceApplication.class, args);
	}

}
