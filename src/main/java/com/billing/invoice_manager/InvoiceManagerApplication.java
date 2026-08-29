package com.billing.invoice_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class InvoiceManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvoiceManagerApplication.class, args);
	}

}
