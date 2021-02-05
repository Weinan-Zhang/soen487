package com.soen487.rest.project.data.service.artist;

import com.soen487.rest.project.data.service.artist.controller.ArtistServiceServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients("com.soen487.rest.project.data.service.artist")
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public ServletRegistrationBean ArtistServiceServletBean() {
		ServletRegistrationBean bean = new ServletRegistrationBean(
				new ArtistServiceServlet(), "/artist/*");
		bean.setLoadOnStartup(1);
		return bean;
	}
}
