package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CountryTest {
    static Country country;

    @BeforeAll
    static void init() {
        country = new Country();
        country.setCode("ABW");
        country.setName("Aruba");
        country.setContinent("North America");
        country.setRegion("Caribbean");
        country.setPopulation(103000);
    }

    @Test
    void testCountryGettersAndSetters() {
        assertEquals("ABW", country.getCode());
        assertEquals("Aruba", country.getName());
        assertEquals("North America", country.getContinent());
        assertEquals("Caribbean", country.getRegion());
        assertEquals(103000, country.getPopulation());
    }

    @Test
    void testCountryNotNull() {
        assertNotNull(country);
    }
}