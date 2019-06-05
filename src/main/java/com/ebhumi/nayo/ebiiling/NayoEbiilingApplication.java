package com.ebhumi.nayo.ebiiling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;


@SpringBootApplication
public class NayoEbiilingApplication extends SpringBootServletInitializer {


	public static void main(String[] args) {
		SpringApplication.run(NayoEbiilingApplication.class, args);


	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(NayoEbiilingApplication.class);
	}

}
