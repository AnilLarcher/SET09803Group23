package com.napier.sem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DistrictPopulationTest extends ReportDatabaseFixture {
    @Test
    void districtAddsOnlyThePopulationsOfItsCities() {
        assertEquals(670L, app.getTotalPopulationofDistrict("Kingston Parish"));
        assertEquals(220L, app.getTotalPopulationofDistrict("Saint Michael"));
        assertEquals(0L, app.getTotalPopulationofDistrict("Unknown"));
    }
}
