package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest {
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect("localhost:33060", 10000);

    }

    @Test
    void getCountriesByPopulationTest() {
        ArrayList<Country> expectedCountries = new ArrayList<>();


        ArrayList<Country> actualCountries = app.getCountriesBypopulation();


        //Expects the total number of Countries from the world database and fails if incorrect.
        assertEquals(232, actualCountries.size());

    }


    @Test
    void getTopNCountriesInWorldByPopulationThrowsExceptionTest() {

        ArrayList<Country> actualCountries = app.getTopNCountriesInWorldByPopulation(6);


        //Expects the total number of Countries from the world database and fails if incorrect.
        assertEquals(6, actualCountries.size());


    }
}