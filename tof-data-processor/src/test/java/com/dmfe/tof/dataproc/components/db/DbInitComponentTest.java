package com.dmfe.tof.dataproc.components.db;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

@Log4j2
class DbInitComponentTest {

    @BeforeAll
    static void setup() {
        log.info("@BeforeAll - executes once before all test methods in this class.");
    }

    @BeforeEach
    void init() {
        log.info("@BeforeEach - executes once before each test method in this class.");
    }

    @AfterEach
    void tearDown() {
        log.info("@AfterEach - executed after each test method.");
    }

    @AfterAll
    static void done() {
        log.info("@AfterAll - executed after all test methods.");
    }

    @Test
    void testOnApplicationEvent() {

    }

    @Test
    void testAnother() {

    }

    @DisplayName("Single test successful")
    @Test
    void testSingleSuccessTest() {
        log.info("Success");
    }

    @Test
    @Disabled("Not implemented yet")
    void testShowSomething() {

    }

    @Test
    void lambdaExpression() {
        assertTrue(Stream.of(1, 2, 3)
                .mapToInt(i -> i)
                .sum() > 5, "Sum should be greater than 5");
    }

    @Test
    void groupAssertions() {
        int[] numbers = {0, 1, 2, 3, 4};

        assertAll("numbers",
                () -> assertEquals(0, numbers[0]),
                () -> assertEquals(3, numbers[3]),
                () -> assertEquals(4, numbers[4])
        );
    }

    @Test
    void shouldThrowException() {
        Throwable exception = assertThrows(UnsupportedOperationException.class, () -> {
            throw new UnsupportedOperationException("Not supported");
        });

        assertEquals("Not supported", exception.getMessage());
    }
}
