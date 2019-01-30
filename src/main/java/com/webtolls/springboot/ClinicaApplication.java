package com.webtolls.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages="com.webtolls.springboot.model")// escaneia as classes q estan dentro deste pacote
@ComponentScan(basePackages= {"com.*"}) // obriga o spring a mapear todos os pacotes que começam com "com.", desa forma o spring encontra os nossos metodos das class controlles
@EnableJpaRepositories(basePackages= {"com.webtolls.springboot.repository"}) // informa onde esta os nossos repositorys
@EnableTransactionManagement // para que possamos trabalhar com persistência de dados no BD
public class ClinicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClinicaApplication.class, args);
	}
	
}

