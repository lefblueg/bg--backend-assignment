package com.marscolony.reviews.cmd.api;

import com.marscolony.reviews.core.api.configuration.AxonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@Import(AxonConfig.class)
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)

public class ReviewCommandApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewCommandApplication.class, args);
	}

}
