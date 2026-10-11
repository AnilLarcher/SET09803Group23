# World Database Documentation

## Overview

The project includes the World database SQL dump, it located at db/world.sql. It contains three tables: city, country and countrylanguage. This document describes their columns, keys and relationships for issue #19.

Please note, the dataset contains historical sample data.

## city

Each row represents a city. This table stores its name, country code, district and population.

- ID: INT, required, auto-incrementing primary key. Uniquely identifies each city record.
- Name: CHAR(35), required, defaults to an empty string. Stores the city name.
- CountryCode: CHAR(3), required, defaults to an empty string. References country.Code.
- District: CHAR(20), required, defaults to an empty string. Stores the district or region.
- Population: INT, required, defaults to 0. Stores the population recorded in the dataset.

The CountryCode column has an index and an enforced foreign key to country.Code. Multiple cities can reference the same country.


## country

Each row represents a country or territory. This table stores identifying, geographical, population, economic and government information.

- Code: CHAR(3), required, defaults to an empty string. Primary key identifying each country or territory.
- Name: CHAR(52), required, defaults to an empty string. Stores its name.
- Continent: ENUM with seven permitted continent values, required, defaults to Asia.
- Region: CHAR(26), required, defaults to an empty string. Stores the geographical region.
- SurfaceArea: DECIMAL(10,2), required, defaults to 0.00. Stores the surface-area value in the dataset.
- IndepYear: SMALLINT, allows NULL, defaults to NULL. Stores the independence year when recorded.
- Population: INT, required, defaults to 0. Stores the population recorded in the dataset.
- LifeExpectancy: DECIMAL(3,1), allows NULL, defaults to NULL. Stores life expectancy when recorded.
- GNP: DECIMAL(10,2), allows NULL, defaults to NULL. Stores the gross national product value recorded in the dataset.
- GNPOld: DECIMAL(10,2), allows NULL, defaults to NULL. Stores an earlier gross national product value when recorded.
- LocalName: CHAR(45), required, defaults to an empty string. Stores the local name of the country or territory.
- GovernmentForm: CHAR(45), required, defaults to an empty string. Stores its recorded form of government.
- HeadOfState: CHAR(60), allows NULL, defaults to NULL. Stores the recorded head of state.
- Capital: INT, allows NULL, defaults to NULL. Stores the city ID associated with the capital.
- Code2: CHAR(2), required, defaults to an empty string. Stores a two-character country code.

Capital provides a logical link to city.ID. However, the supplied country table definition does not declare a foreign-key constraint for Capital.

## countrylanguage

Each row records a language spoken in a particular country or territory, whether it is official, and the percentage of the population recorded as speaking it.

- CountryCode: CHAR(3), required, defaults to an empty string. References country.Code.
- Language: CHAR(30), required, defaults to an empty string. Stores the language name.
- IsOfficial: ENUM('T','F'), required, defaults to F. T indicates an official language and F indicates a non-official language.
- Percentage: DECIMAL(4,1), required, defaults to 0.0. Stores the recorded percentage of the country's population speaking the language.

The composite primary key consists of CountryCode and Language. Their combination must be unique, allowing multiple languages per country and the same language to appear in multiple countries.

CountryCode also has an index and an enforced foreign key to country.Code. Each language entry references an existing country or territory.