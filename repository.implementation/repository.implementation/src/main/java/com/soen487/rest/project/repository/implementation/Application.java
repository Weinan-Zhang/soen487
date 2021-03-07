package com.soen487.rest.project.repository.implementation;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//@EnableJpaRepositories	当repository的实现不在当前项目包及其子包时，需要使用这个注释将repository实现类注入到当前项目中
@EntityScan(basePackages = {"com.soen487.rest.project.repository.core"})
@EnableDiscoveryClient
public class Application{
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
