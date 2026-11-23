package com.napier.sem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CityPopulationTest extends ReportDatabaseFixture {
    @Test
    void cityReturnsOnlyTheNamedCityIncludingNamesWithAnApostrophe() {
        assertEquals(30L, app.getTotalPopulationofCity("Port Royal"));
        assertEquals(50L, app.getTotalPopulationofCity("Saint Ann's Bay"));
        assertEquals(0L, app.getTotalPopulationofCity("Unknown"));
    }

    @Test
    void citiesSharingANameCanBeSelectedIndividuallyById() {
        assertEquals(0L, app.getTotalPopulationofCity("Kingston"));
        assertEquals(640L, app.getTotalPopulationofCity(1));
        assertEquals(80L, app.getTotalPopulationofCity(7));
        assertEquals(0L, app.getTotalPopulationofCity(999));
    }
}
