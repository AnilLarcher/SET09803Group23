package com.napier.sem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegionPopulationTest extends ReportDatabaseFixture {
    @Test
    void regionIncludesOnlyCountriesInTheSelectedRegion() {
        assertEquals(1_500L, app.getTotalPopulationofRegion("Caribbean"));
        assertEquals(2_500L, app.getTotalPopulationofRegion("North America"));
        assertEquals(0L, app.getTotalPopulationofRegion("Eastern Africa"));
    }
}
