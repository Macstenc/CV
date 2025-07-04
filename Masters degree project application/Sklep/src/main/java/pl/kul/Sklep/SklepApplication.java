package pl.kul.Sklep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableMongoRepositories("pl.kul.Sklep.Repository")
@ComponentScan("pl.kul.Sklep.*")
@EnableScheduling
public class SklepApplication {

	public static void main(String[] args) {
		SpringApplication.run(SklepApplication.class, args);
	}
}
