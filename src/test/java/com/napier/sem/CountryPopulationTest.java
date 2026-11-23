package com.napier.sem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountryPopulationTest extends ReportDatabaseFixture {
    @Test
    void countryCanBeSelectedByNameOrCode() {
        assertEquals(500L, app.getTotalPopulationofCountry("Barbados"));
        assertEquals(1_000L, app.getTotalPopulationofCountry("jam"));
        assertEquals(0L, app.getTotalPopulationofCountry("Unknown"));
    }
}
