package com.napier.sem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CityContinentTest {

    @Test
    void testCityContinentReportGettersAndSetters() {
        City city = new City();
        city.setName("Tokyo");
        city.setCountryCode("JPN");
        city.setDistrict("Tokyo-to");
        city.setPopulation(7980230);

        assertEquals("Tokyo", city.getName());
        assertEquals("JPN", city.getCountryCode());
        assertEquals("Tokyo-to", city.getDistrict());
        assertEquals(7980230, city.getPopulation());
    }
}