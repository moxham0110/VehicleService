package com.pmoxham.vehiclemanagement;

import com.intuit.karate.junit5.Karate;
import com.intuit.karate.junit5.Karate.Test;

public class KarateTestRunner {

    @Karate.Test
    Karate runAll() {
        return Karate.run("classpath:features").relativeTo(getClass());
    }
}


