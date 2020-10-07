package mts.demo.booksSpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BooksSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooksSpringBootApplication.class, args);
	}

}