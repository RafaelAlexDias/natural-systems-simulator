package mssn;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class SmokeTest {

    @Test
    void processingCoreIsOnClasspath() throws Exception {
        assertNotNull(Class.forName("processing.core.PApplet"));
    }

    @Test
    void mainClassLoads() {
        assertNotNull(new Main());
    }
}