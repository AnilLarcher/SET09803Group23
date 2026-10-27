package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CityTest {
    static City city;

    @BeforeAll
    static void init() {
        city = new City(1, "Kabul", "AFG", "Kabol", 1780000);
    }

    @Test
    void testCityConstructorAndGetters() {
        assertEquals(1, city.getId());
        assertEquals("Kabul", city.getName());
        assertEquals("AFG", city.getCountryCode());
        assertEquals("Kabol", city.getDistrict());
        assertEquals(1780000, city.getPopulation());
    }

    @Test
    void testCityToStringOrNull() {
        assertNotNull(city);
    }
}