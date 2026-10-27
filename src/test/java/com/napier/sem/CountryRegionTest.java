package com.napier.sem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CountryRegionTest {

    @Test
    void testCountryRegionReportGettersAndSetters() {
        Country country = new Country();
        country.setCode("CAR");
        country.setName("Caribbean");
        country.setContinent("North America");
        country.setRegion("Caribbean");
        country.setPopulation(40000000);

        assertEquals("CAR", country.getCode());
        assertEquals("Caribbean", country.getName());
        assertEquals("North America", country.getContinent());
        assertEquals("Caribbean", country.getRegion());
        assertEquals(40000000, country.getPopulation());
    }
}