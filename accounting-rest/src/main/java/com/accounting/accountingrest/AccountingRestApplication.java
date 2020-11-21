package com.accounting.accountingrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.accounting.accountingrest.controller", "com.accounting.accountingrest.service"})
@Component
public class AccountingRestApplication {
	public static void main(String[] args) {
		SpringApplication.run(AccountingRestApplication.class, args);
	}
}
