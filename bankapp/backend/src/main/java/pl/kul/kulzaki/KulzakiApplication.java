package pl.kul.kulzaki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import pl.kul.kulzaki.Config.ApplicationConfig;


@SpringBootApplication
@Import(ApplicationConfig.class)
public class KulzakiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KulzakiApplication.class, args);
	}

}
