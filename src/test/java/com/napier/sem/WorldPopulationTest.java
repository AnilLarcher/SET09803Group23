package com.napier.sem;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WorldPopulationTest extends ReportDatabaseFixture {
    @Test
    void worldIncludesEveryCountryEvenWithoutACapital() {
        assertEquals(10_000L, app.getTotalPopulation());
    }

    @Test
    void worldTotalCanExceedTheIntegerRange() throws SQLException {
        try (Statement sql = connection.createStatement()) {
            sql.executeUpdate("UPDATE country SET Population = 2000000000 WHERE Code IN ('IND', 'NPL')");
        }
        assertEquals(4_000_004_000L, app.getTotalPopulation());
    }
}
