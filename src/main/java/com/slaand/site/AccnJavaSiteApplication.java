package com.slaand.site;

import com.slaand.site.annotation.EnableRedisCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@EnableRedisCache
@SpringBootApplication
public class AccnJavaSiteApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AccnJavaSiteApplication.class, args);
	}

}
