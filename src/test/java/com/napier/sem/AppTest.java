package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    static App app;

    @BeforeAll
    static void init() {
        app = new App();
    }

    @Test
    void testAppNotNull() {
        assertNotNull(app, "App instance should be initialized");
    }

    @Test
    void printCountryPopulationNull() {
        // Test handling of null country selection without database connection
        app.printCountryPopulation(null);
    }

    @Test
    void printCountryPopulationEmpty() {
        // Test handling of empty country selection
        app.printCountryPopulation("");
    }

    @Test
    void printContinentPopulationNull() {
        // Test handling of null continent selection
        app.printContinentPopulation(null);
    }

    @Test
    void printContinentPopulationEmpty() {
        // Test handling of empty continent selection
        app.printContinentPopulation("");
    }

    @Test
    void printRegionPopulationNull() {
        // Test handling of null region selection
        app.printRegionPopulation(null);
    }

    @Test
    void printRegionPopulationEmpty() {
        // Test handling of empty region selection
        app.printRegionPopulation("");
    }
}