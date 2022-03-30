package com.adrian.cheetahdigital;

import com.adrian.cheetahdigital.model.Result;
import com.adrian.cheetahdigital.service.PairingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;
import java.util.List;

@SpringBootApplication
public class CheetahDigitalApplication implements CommandLineRunner {

	private static Logger log = LoggerFactory.getLogger(CheetahDigitalApplication.class);

	@Autowired
	private PairingService pairingService;

	public static void main(String[] args) {
		log.info("Application Started...");
		SpringApplication.run(CheetahDigitalApplication.class, args);
		log.info("Application Finished...");
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Application Running...");

		try {
			List<Result> results = pairingService.getResultList();
			for (Result result : results) {
//			log.info("{}, {} {}", result.getFirstName(), result.getSecondName(), result.getTags());
				// For better readability
				System.out.println(result.getFirstName() + ", " + result.getSecondName() + " " + result.getTags() + " ");
			}
		} catch (FileNotFoundException e){
			log.error("FileNotFoundException: {}", e.getMessage());
		} catch (IllegalArgumentException e){
			log.error("IllegalArgumentException: {}", e.getMessage());
		} catch (Exception e){
			log.error("Exception: {}", e.getMessage());
		}
	}
}
