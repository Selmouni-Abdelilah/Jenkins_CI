package com.tp4.app;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AppApplicationTests {

	@Test
	void contextLoads() {
	}

    @Test
    void testAddition() {
        // Test addition of two numbers
        int result = add(3, 5);
        Assertions.assertEquals(8, result, "3 + 5 should equal 8");
    }

    private int add(int a, int b) {
        return a + b;
    }
}
