package com.adrian.cheetahdigital;

import com.adrian.cheetahdigital.model.Result;
import com.adrian.cheetahdigital.service.PairingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@SpringBootTest
class CheetahDigitalApplicationTests {

	@Autowired
	private PairingService pairingService;

	@DisplayName("Successful call")
	@Test
	void success() {
		try {
			List<Result> results = pairingService.getResultList();
			for (Result result : results) {
				System.out.println(result.getFirstName() + ", " + result.getSecondName() + " " + result.getTags() + " ");
			}
			Assertions.assertTrue(true);
		}  catch (Exception e) {
			Assertions.fail();
		}
	}

}
