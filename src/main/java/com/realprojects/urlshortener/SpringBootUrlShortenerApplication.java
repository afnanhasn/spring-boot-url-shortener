package com.realprojects.urlshortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication /*this annotation will be responsible to scan for available springboot components from the package
mentioned in line 1 and it's subcomponents, meaning that any springboot component(controller or repository etc) added
in com.realprojects will not be picked up or added to but it needs to be inside com.realprojects.urlshortener, commomn
mistakes which beginners  do and wonder why code is not working*/
@ConfigurationPropertiesScan
public class SpringBootUrlShortenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootUrlShortenerApplication.class, args);
	}

}
