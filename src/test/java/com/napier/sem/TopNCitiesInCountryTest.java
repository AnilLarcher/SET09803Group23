package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for issue #45: top N cities in a country.
 * These cover input validation, so no database connection is needed.
 */
public class TopNCitiesInCountryTest {
    static App app;

    @BeforeAll
    static void init() {
        app = new App();
    }

    @Test
    void topNCitiesNullNumber() {
        assertNull(app.getTopNCitiesbyPopinCountry(null, "Japan"));
    }

    @Test
    void topNCitiesZeroNumber() {
        assertNull(app.getTopNCitiesbyPopinCountry(0, "Japan"));
    }

    @Test
    void topNCitiesNegativeNumber() {
        assertNull(app.getTopNCitiesbyPopinCountry(-3, "Japan"));
    }

    @Test
    void topNCitiesNullCountry() {
        assertNull(app.getTopNCitiesbyPopinCountry(5, null));
    }

    @Test
    void topNCitiesEmptyCountry() {
        assertNull(app.getTopNCitiesbyPopinCountry(5, "   "));
    }

    @Test
    void topNCitiesNoConnection() {
        // Valid input but no database connection should fail gracefully
        assertNull(app.getTopNCitiesbyPopinCountry(5, "Japan"));
    }

    @Test
    void cityCountryName() {
        City city = new City();
        city.setCountryName("Japan");
        assertEquals("Japan", city.getCountryName());
    }
}
