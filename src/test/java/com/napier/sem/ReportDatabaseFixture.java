package com.napier.sem;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

abstract class ReportDatabaseFixture {
    Connection connection;
    App app;

    @BeforeEach
    void createWorld() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:;MODE=MySQL");
        app = new App(connection);
        try (Statement sql = connection.createStatement()) {
            sql.execute("""
                    CREATE TABLE country (
                        Code CHAR(3) PRIMARY KEY, Name VARCHAR(60), Continent VARCHAR(40),
                        Region VARCHAR(40), Population INT, Capital INT
                    )
                    """);
            sql.execute("""
                    CREATE TABLE city (
                        ID INT PRIMARY KEY, Name VARCHAR(60), CountryCode CHAR(3),
                        District VARCHAR(40), Population INT
                    )
                    """);
            sql.execute("""
                    CREATE TABLE countrylanguage (
                        CountryCode CHAR(3), Language VARCHAR(40), Percentage DECIMAL(4,1),
                        PRIMARY KEY (CountryCode, Language)
                    )
                    """);
            sql.execute("""
                    INSERT INTO country VALUES
                        ('JAM', 'Jamaica', 'North America', 'Caribbean', 1000, 1),
                        ('BRB', 'Barbados', 'North America', 'Caribbean', 500, 3),
                        ('CAN', 'Canada', 'North America', 'North America', 2500, 4),
                        ('IND', 'India', 'Asia', 'Southern Asia', 4000, 5),
                        ('NPL', 'Nepal', 'Asia', 'Southern Asia', 2000, NULL)
                    """);
            sql.execute("""
                    INSERT INTO city VALUES
                        (1, 'Kingston', 'JAM', 'Kingston Parish', 640),
                        (2, 'Port Royal', 'JAM', 'Kingston Parish', 30),
                        (3, 'Bridgetown', 'BRB', 'Saint Michael', 220),
                        (4, 'Ottawa', 'CAN', 'Ontario', 900),
                        (5, 'New Delhi', 'IND', 'Delhi', 700),
                        (6, 'Saint Ann''s Bay', 'JAM', 'Saint Ann', 50),
                        (7, 'Kingston', 'CAN', 'Ontario', 80)
                    """);
            sql.execute("""
                    INSERT INTO countrylanguage VALUES
                        ('JAM', 'English', 90.1), ('BRB', 'English', 80.0),
                        ('CAN', 'English', 75.0), ('IND', 'Hindi', 60.0),
                        ('CAN', 'Chinese', 2.4), ('IND', 'Chinese', 3.3),
                        ('JAM', 'Spanish', 4.5), ('CAN', 'Spanish', 3.4),
                        ('IND', 'Arabic', 0.2), ('CAN', 'Arabic', 0.2),
                        ('CAN', 'French', 15.0)
                    """);
        }
    }

    @AfterEach
    void closeWorld() throws SQLException {
        connection.close();
    }
}
