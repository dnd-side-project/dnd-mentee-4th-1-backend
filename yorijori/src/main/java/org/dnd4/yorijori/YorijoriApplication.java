package org.dnd4.yorijori;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
public class YorijoriApplication {

	public static void main(String[] args) {
		SpringApplication.run(YorijoriApplication.class, args);
	}

}
