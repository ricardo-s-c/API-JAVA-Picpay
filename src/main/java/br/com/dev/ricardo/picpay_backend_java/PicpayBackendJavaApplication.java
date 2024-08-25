package br.com.dev.ricardo.picpay_backend_java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

@EnableJdbcAuditing
@SpringBootApplication
public class PicpayBackendJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PicpayBackendJavaApplication.class, args);
	}

}
