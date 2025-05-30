package com.xpert;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Basic sanity test to verify the Spring application context loads successfully.
 * This test fails if any bean initialization or configuration issues occur.
 */
@SpringBootTest
class XpertApplicationTests {

    @Test
    @Disabled("Disabled during initial project setup. Re-enable when database is configured.")
    void contextLoads() {
        // Intentionally empty: test will fail if application context cannot be started
    }
}
