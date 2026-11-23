package com.napier.sem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContinentPopulationTest extends ReportDatabaseFixture {
    @Test
    void continentIncludesAllItsRegionsAndExcludesOtherContinents() {
        assertEquals(4_000L, app.getTotalPopulationofContinent("North America"));
        assertEquals(6_000L, app.getTotalPopulationofContinent("Asia"));
        assertEquals(0L, app.getTotalPopulationofContinent("Europe"));
    }
}
