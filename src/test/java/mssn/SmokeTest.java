package mssn;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class SmokeTest {

    @Test
    void javafxMainClassLoads() throws Exception {
        assertNotNull(ui.MainApp.class);
    }
}