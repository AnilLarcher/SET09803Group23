package com.napier.sem;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CapitalCityTest {

    @Test
    void testCapitalCityReportGettersAndSetters() {
        City capital = new City();
        capital.setName("Seoul");
        capital.setCountryCode("KOR");
        capital.setDistrict("Seoul");
        capital.setPopulation(9981619);

        assertEquals("Seoul", capital.getName());
        assertEquals("KOR", capital.getCountryCode());
        assertEquals("Seoul", capital.getDistrict());
        assertEquals(9981619, capital.getPopulation());
    }
}