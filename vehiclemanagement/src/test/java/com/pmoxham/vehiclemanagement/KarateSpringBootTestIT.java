package com.pmoxham.vehiclemanagement;

import com.intuit.karate.junit5.Karate;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("dev")
class KarateSpringBootTestIT {

    @BeforeAll
    static void beforeAll() {
        System.setProperty("karate.env", "dev");
    }

    @Karate.Test
    Karate runTests() {
        return Karate.run("classpath:features").relativeTo(getClass());
    }
}
