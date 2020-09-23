package com.slaand.site;

import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit5.FlywayTestExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@ExtendWith({FlywayTestExtension.class})
@FlywayTest
class AccnJavaSiteApplicationTests {

	@Test
	void contextLoads() {
	}

}
