package com.napier.sem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class App {
    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    public static void main(String[] args) {
        App app = new App();

        // Inside Docker the Dockerfile passes "db:3306 30000"; from IntelliJ use the exposed port
        if (args.length < 2) {
            app.connect("localhost:3307", 1000);
        } else {
            app.connect(args[0], Integer.parseInt(args[1]));
        }


        //Method to get countries by population
        app.getCountriesBypopulation();

        //Method to get top N populated countries in the world
        app.getTopNCountriesInWorldByPopulation(5);

        //Method to get top N populated countries in a continent
        app.getTopNCountriesInContinentByPopulation(5, "Asia");

        //Method to get top N populated countries in a region
        app.getTopNCountriesInRegionByPopulation(10, "Caribbean");

        //Method to display countries by population in continent
        app.getCountriesInContByPopulation(5, "Asia");

        //Method to display countries by population in region
        app.getCountriesInRegionByPopulation("Caribbean");


        //Method to display cities in the world by population
        app.getCitiesByPopulation();

        //Method to display cities in the world by population in a continent
        //app.getCitiesByPopulationinAContinent("Europe");

        //Method to display cities in the world by population in a region
        //app.getCitiesbyPopulationinARegion("Caribbean");

        //Method to display cities in the world by population in a country
        //app.getCitiesbyPopulationinACountry("Antigua");

        //Method to display cities in the world by population in a district
        //app.getCitiesbyPopulationinaDistrict("Cayo");

        //Method to display top N cities in the world by population
        //app.getTopNCitiesbyPopulationinTheWorld(13);

        //Method to display top N cities in a continent by population
        //app.getTopNCitiesbyPopulationinContinent(10, "Asia");

        //Method to display top N cities in a region by population
        //app.getTopNCitiesbyPopulationinRegion(7, "Latin America");

        //Method to display top N cities in a country by population
        //app.getTopNCitiesbyPopinCountry(5, "Haiti");

        //Method to display top N cities in a district by population
        //app.getTopNCitiesbyPopinDistrict(3, "Castries");

        //Method to display capital cities in the world by population
        //app.getCapitalCities();

        //Method to display capital cities in a continent by population
        //app.getCapitalCitiesinContinent("Europe");

        //Method to display capital cities in a region by population
        //app.getCapitalCitiesinRegion("Caribbean");

        //Method to display top N capital cities by population
        //app.getTopNCapitalCities(12);

        //Method to display top N capital cities in a continent by population
        //app.getTopNCapitalCitiesinaContinent(5, "North America");

        //Method to display top N capital cities in a region by population
        app.getTopNCapitalCitiesinaRegion(6, "Caribbean");

        //Method to display the population of people in continents
        app.getPopulationofPeopleinContinent();

        //Method to display the population of people in region
        app.getPopulationofPeopleinRegion();

        //Method to display the population of people Countries
        app.getPopulationofPeopleinCountry();

        //Method to display total world population
        app.getTotalPopulation();

        //Method to display total population of a continent
        app.getTotalPopulationofContinent("Asia");

        //Method to display total population of a region
        app.getTotalPopulationofRegion("Caribbean");

        //Method to display total population of a country
        app.getTotalPopulationofCountry("Belize");

        //Method to display total population of a district
        app.getTotalPopulationofDistrict("Belize City");

        //Method to display total population of a city
        app.getTotalPopulationofCity("Castries");

        //Method to display language speakers in the world
        app.getLanguageSpeakers();

        // Disconnect from database
        app.disconnect();

    }


    public App() {}

    App(Connection connection) {
        this.con = connection;
    }

    /**
     * Connection to database
     */
    public void connect(String location, int delay) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Could not load driver");
            System.exit(-1);
        }

        int retries = 10;
        boolean shouldWait = false;
        for (int i = 0; i < retries; ++i) {
            System.out.println("Connecting to database...");
            try {
                if (shouldWait) {

                    Thread.sleep(delay);
                }
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://" + location
                        + "/world?allowPublicKeyRetrieval=true&useSSL=false", "root", "Gr0up23@DATAbase");
                System.out.println("Successfully connected");
                // Exit for loop
                break;
            } catch (SQLException sqle) {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());

                // Let's wait before attempting to reconnect
                shouldWait = true;
            } catch (InterruptedException ie) {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from DB
     */
    public void disconnect() {
        if (con != null) {
            try {
                // Close connection
                System.out.println("Closing World Database connection...");
                con.close();
                System.out.println("Connection closed.");

            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }

    //Method to display countries sorted by population
    public ArrayList<Country> getCountriesBypopulation() {
        ArrayList<Country> countryList = new ArrayList<>();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT  country.continent, country.capital, city.ID, city.name AS capital_city, country.code, country.name, country.region, country.population "
                            + "FROM country "
                            + "JOIN city ON country.capital = city.ID "
                            + "ORDER BY population DESC";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Process the result set and add to the list
            while (rset.next()) {
                Country country = new Country();
                country.code = rset.getString("country.code");
                country.name = rset.getString("country.name");
                country.continent = rset.getString("country.continent");
                country.region = rset.getString("country.region");
                country.population = rset.getInt("country.population");
                country.capital = rset.getString("capital");
                countryList.add(country);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }

        System.out.print("\n");

        System.out.println("============ Countries Sorted by Population ============");

        System.out.print("\n");

        // Print header
        System.out.println(String.format("%-15s %-30s %-15s %-30s %-15s %-15s", "Code", "Name", "Continent", "Region", "Population", "Capital"));
        // Print each country's details
        for (Country country : countryList) {
            String country_string =
                    String.format("%-15s %-30s %-15s %-30s %-15s %-15s",
                            country.code, country.name, country.continent, country.region, country.population, country.capital);
            System.out.println(country_string);
        }
        return countryList;
    }

    //Method to display the top N populated countries in the world
    public ArrayList<Country> getTopNCountriesInWorldByPopulation(Integer number) {
        ArrayList<Country> countryList = new ArrayList<>();
        try {
            //Input Validation
            if (number == null || number <= 0) throw new Exception("Fail! Invalid Number input for Query");

            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT  country.continent, country.capital, city.ID, city.name AS capital_city, country.code, country.name, country.region, country.population "
                            + "FROM country "
                            + "JOIN city ON country.capital = city.ID "
                            + "ORDER BY population DESC"
                            + " LIMIT " + number + " ";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Process the result set and add to the list
            while (rset.next()) {
                Country country = new Country();
                country.code = rset.getString("country.code");
                country.name = rset.getString("country.name");
                country.continent = rset.getString("country.continent");
                country.region = rset.getString("country.region");
                country.population = rset.getInt("country.population");
                country.capital = rset.getString("capital_city");
                countryList.add(country);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }


        System.out.print("\n");

        System.out.println("============== Top N Countries In The World Sorted By Population ==============");

        System.out.print("\n");

        // Print header
        System.out.println(String.format("%-15s %-30s %-15s %-30s %-15s %-15s", "Code", "Name", "Continent", "Region", "Population", "Capital"));
        // Print each country's details
        for (Country country : countryList) {
            String country_string =
                    String.format("%-15s %-30s %-15s %-30s %-15s %-15s",
                            country.code, country.name, country.continent, country.region, country.population, country.capital);
            System.out.println(country_string);
        }
        return countryList;
    }

    //Method to display the population of people , people living in cities and people not living in cities in each continent
    public ArrayList<PopulationReport> getPopulationofPeopleinContinent() {
        ArrayList<PopulationReport> PopulationList = new ArrayList<>();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT country.Continent, " +
                            "SUM(city.Population) AS inCity, " +
                            "SUM(DISTINCT(country.Population)) - SUM(city.Population) AS outCity, " +
                            "SUM(DISTINCT(country.Population)) AS totalPopulation, " +
                            "SUM(city.Population) / SUM(DISTINCT(country.Population)) * 100 AS inCityPercentage, " +
                            "(SUM(DISTINCT(country.Population)) - SUM(city.Population)) / SUM(DISTINCT(country.Population)) * 100 AS outCityPercentage " +
                            "FROM country " +
                            "LEFT JOIN city ON country.Code = city.CountryCode " +
                            "GROUP BY country.Continent " +
                            "ORDER BY country.Continent";


            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Process the result set and add to the list
            while (rset.next()) {
                PopulationReport population = new PopulationReport();
                population.name = rset.getString("Continent");
                population.totalPopulation = rset.getLong("totalPopulation");
                population.cityPercentage = rset.getDouble("inCityPercentage");
                population.cityPopulation = rset.getDouble("outCityPercentage");
                PopulationList.add(population);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get capital city details");
            return null;
        }

        // Print a blank line before the heading
        System.out.print("\n");
        // Print heading
        System.out.println("Population of People Per Continent:");
        // Print a blank line after the heading
        System.out.print("\n");

        // Print header
        System.out.println(String.format("%-45s %-30s %-15s %-6s", "Name", "Total Population", "In Cities", "Not in Cities"));
        // Print each city's details
        for (PopulationReport population : PopulationList) {
            String country_string =
                    String.format("%-45s %-30s %-15s %-6s",
                            population.name, population.totalPopulation, population.cityPercentage + "%", population.cityPopulation + "%");
            System.out.println(country_string);
        }
        return PopulationList;

    }

    /**
     * Method to display the top N populated countries in a region
     *
     */
    public ArrayList<Country> getTopNCountriesInRegionByPopulation(Integer number, String Region) {
        ArrayList<Country> countryList = new ArrayList<>();
        try {
            //Input Validation
            if (number == null || number <= 0 || Region == null) throw new Exception("Fail! Invalid input for Query");

            // Create an SQL statement
            Statement stmt = con.createStatement();


            // Create string for SQL statement
            String strSelect =
                    "SELECT country.continent, country.capital, city.ID, city.name AS capital_city, country.code, country.name, country.region, country.population "
                            + "FROM country "
                            + "JOIN city ON country.capital = city.ID "
                            + "WHERE country.region = '" + Region + "'"
                            + "ORDER BY population DESC "
                            + "LIMIT " + number;


            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Process the result set and add to the list
            while (rset.next()) {
                Country country = new Country();
                country.code = rset.getString("country.code");
                country.name = rset.getString("country.name");
                country.continent = rset.getString("country.continent");
                country.region = rset.getString("country.region");
                country.population = rset.getInt("country.population");
                country.capital = rset.getString("capital_city");
                countryList.add(country);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }

        // Print a blank line before the heading
        System.out.print("\n");
        // Print heading
        System.out.println("Top N Countries In The Region Sorted By Population:");
        // Print a blank line after the heading
        System.out.print("\n");

        // Print header
        System.out.println(String.format("%-15s %-30s %-15s %-30s %-15s %-15s", "Code", "Name", "Continent", "Region", "Population", "Capital"));
        // Print each country's details
        for (Country country : countryList) {
            String country_string =
                    String.format("%-15s %-30s %-15s %-30s %-15s %-15s",
                            country.code, country.name, country.continent, country.region, country.population, country.capital);
            System.out.println(country_string);
        }
        return countryList;
    }

    public long getTotalPopulationofCountry(String country) {
        String selection = country == null ? "" : country.trim();
        if (selection.isEmpty()) {
            System.out.println("Country selection cannot be empty");
            return 0;
        }

        String sql = "SELECT Name, Population FROM country WHERE Code = UPPER(?) OR Name = ? LIMIT 1";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, selection);
            stmt.setString(2, selection);
            try (ResultSet rset = stmt.executeQuery()) {
                if (rset.next()) {
                    long totalPopulation = rset.getLong("Population");
                    System.out.println("Country Population - " + rset.getString("Name") + ": "
                            + totalPopulation);
                    return totalPopulation;
                }
                System.out.println("No country found for selection: " + selection);
            }
        } catch (SQLException e) {
            System.out.println("Failed to get selected country population");
            System.out.println(e.getMessage());
        }
        return 0;
    }


    //Method to display the top N populated countries in continent
    public ArrayList<Country> getTopNCountriesInContinentByPopulation(Integer number, String Continent) {
        ArrayList<Country> countryList = new ArrayList<>();
        try {
            //Input Validation
            if (number == null || number <= 0 || Continent == null) throw new Exception ("Fail! Invalid input for Query");
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT country.continent, country.capital, city.ID, city.name AS capital_city, country.code, country.name, country.region, country.population "
                            + "FROM country "
                            + "JOIN city ON country.capital = city.ID "
                            + "WHERE country.continent = '" + Continent + "'"
                            + "ORDER BY population DESC "
                            + "LIMIT " + number;


            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Process the result set and add to the list
            while (rset.next()) {
                Country country = new Country();
                country.code = rset.getString("country.code");
                country.name = rset.getString("country.name");
                country.continent = rset.getString("country.continent");
                country.region = rset.getString("country.region");
                country.population = rset.getInt("country.population");
                country.capital = rset.getString("capital_city");
                countryList.add(country);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }

        // Print a blank line before the heading
        System.out.print("\n");
        // Print heading
        System.out.println("====================== Top N Countries In The Continent Sorted By Population: ======================");
        // Print a blank line after the heading
        System.out.print("\n");

        System.out.println(String.format("%-15s %-30s %-15s %-30s %-15s %-15s", "Code", "Name", "Continent", "Region", "Population", "Capital"));
        // Print each country's details
        for (Country country : countryList) {
            String country_string =
                    String.format("%-15s %-30s %-15s %-30s %-15s %-15s",
                            country.code, country.name, country.continent, country.region, country.population, country.capital);
            System.out.println(country_string);
        }
        return countryList;
    }

    /**
     * Issue #54: Report population, city population, and non-city population by region
     */
    public void getPopulationofPeopleinRegion() {
        try {
            Statement stmt = con.createStatement();
            String strSelect =
                    "SELECT country.Region, " +
                            "SUM(country.Population) AS TotalPopulation, " +
                            "SUM(city.Population) AS CityPopulation, " +
                            "(SUM(country.Population) - SUM(city.Population)) AS NonCityPopulation " +
                            "FROM country " +
                            "LEFT JOIN city ON country.Code = city.CountryCode " +
                            "GROUP BY country.Region";
            ResultSet rset = stmt.executeQuery(strSelect);

            System.out.println("\n================ Population Breakdown by Region ================\n");
            System.out.println(String.format("%-30s %-15s %-15s %-15s", "Region", "Total Pop", "City Pop", "Non-City Pop"));
            while (rset.next()) {
                String region = rset.getString("Region");
                long totalPop = rset.getLong("TotalPopulation");
                long cityPop = rset.getLong("CityPopulation");
                long nonCityPop = rset.getLong("NonCityPopulation");
                System.out.println(String.format("%-30s %-15d %-15d %-15d", region, totalPop, cityPop, nonCityPop));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population report by region");
        }
    }

    /**
     * Issue #55: Report population, city population, and non-city population by country
     */
    public void getPopulationofPeopleinCountry() {
        try {
            Statement stmt = con.createStatement();
            String strSelect =
                    "SELECT country.Name, " +
                            "country.Population AS TotalPopulation, " +
                            "SUM(city.Population) AS CityPopulation, " +
                            "(country.Population - SUM(city.Population)) AS NonCityPopulation " +
                            "FROM country " +
                            "LEFT JOIN city ON country.Code = city.CountryCode " +
                            "GROUP BY country.Code, country.Name, country.Population";
            ResultSet rset = stmt.executeQuery(strSelect);

            System.out.println("\n================ Population Breakdown by Country ================\n");
            System.out.println(String.format("%-30s %-15s %-15s %-15s", "Country", "Total Pop", "City Pop", "Non-City Pop"));
            while (rset.next()) {
                String name = rset.getString("Name");
                long totalPop = rset.getLong("TotalPopulation");
                long cityPop = rset.getLong("CityPopulation");
                long nonCityPop = rset.getLong("NonCityPopulation");
                System.out.println(String.format("%-30s %-15d %-15d %-15d", name, totalPop, cityPop, nonCityPop));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population report by country");
        }
    }

    /**
     * Issue #60: Report the total population of a selected district
     *
     * @return
     */
    public long getTotalPopulationofDistrict(String districtName) {
        long totalPop = 0;
        try {
            String strSelect =
                    "SELECT District, SUM(Population) AS TotalPopulation " +
                            "FROM city " +
                            "WHERE District = ? " +
                            "GROUP BY District";
            PreparedStatement stmt = con.prepareStatement(strSelect);
            stmt.setString(1, districtName);
            ResultSet rset = stmt.executeQuery();

            System.out.println("\n================ Population of Selected District ================\n");
            if (rset.next()) {
                String district = rset.getString("District");
                totalPop = rset.getLong("TotalPopulation");
                System.out.println(String.format("%-20s %-15d", district, totalPop));
            } else {
                System.out.println("No district found: " + districtName);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population report for district: " + districtName);
        }
        return totalPop;
    }

    //Method to display top N capital cities in a region by population
    public ArrayList<City> getTopNCapitalCitiesinaRegion(Integer number, String Region) {
        ArrayList<City> CapitalCityList = new ArrayList<>();
        try {
            //Input Validation
            if (number == null || number <= 0 || Region == null) throw new Exception ("Fail! Invalid input for Query");

            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT city.Name, country.Name, city.District, city.Population "
                            + "FROM world.city "
                            + "JOIN world.country ON city.ID = country.Capital "
                            + "WHERE country.region = '" + Region + "'"
                            + "ORDER BY Population DESC "
                            + "LIMIT " + number;


            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Process the result set and add to the list
            while (rset.next()) {
                City capitalcity = new City();
                capitalcity.name = rset.getString("city.name");
                capitalcity.countryCode = rset.getString("country.name");
                capitalcity.population = rset.getInt("city.population");
                CapitalCityList.add(capitalcity);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get capital city details");
            return null;
        }

        // Print a blank line before the heading
        System.out.print("\n");
        // Print heading
        System.out.println("Top N Capital Cities In A Region Sorted By Population:");
        // Print a blank line after the heading
        System.out.print("\n");

        // Print header
        System.out.println(String.format("%-15s %-30s %-15s", "Name", "Country", "Population"));
        // Print each city's details
        for (City capitalcity : CapitalCityList) {
            String country_string =
                    String.format("%-15s %-30s %-15s",
                            capitalcity.name, capitalcity.countryCode, capitalcity.population);
            System.out.println(country_string);
        }
        return CapitalCityList;

    }

    //Method to display the top N populated countries in continent
    public ArrayList<Country> getCountriesInContByPopulation(Integer number, String continent) {
        if (number == null || number <= 0 || continent == null || continent.trim().isEmpty()) {
            throw new IllegalArgumentException("Fail! Invalid input for Query");
        }

        ArrayList<Country> countryList = new ArrayList<>();
        String strSelect =
                "SELECT country.continent AS continent, city.name AS capital_city, "
                        + "country.code AS code, country.name AS name, country.region AS region, "
                        + "country.population AS population "
                        + "FROM country "
                        + "LEFT JOIN city ON country.capital = city.ID "
                        + "WHERE country.continent = ? "
                        + "ORDER BY country.population DESC "
                        + "LIMIT ?";

        try (PreparedStatement stmt = con.prepareStatement(strSelect)) {
            stmt.setString(1, continent.trim());
            stmt.setInt(2, number);
            try (ResultSet rset = stmt.executeQuery()) {
                while (rset.next()) {
                    Country country = new Country();
                    country.code = rset.getString("code");
                    country.name = rset.getString("name");
                    country.continent = rset.getString("continent");
                    country.region = rset.getString("region");
                    country.population = rset.getInt("population");
                    country.capital = rset.getString("capital_city");
                    countryList.add(country);
                }
            }
        } catch (SQLException e) {
            System.out.println("Failed to get country details");
            System.out.println(e.getMessage());
            return null;
        }

        // Print a blank line before the heading
        System.out.print("\n");
        // Print heading
        System.out.println("Top N Countries In The Continent Sorted By Population:");
        // Print a blank line after the heading
        System.out.print("\n");

        System.out.println(String.format("%-15s %-30s %-15s %-30s %-15s %-15s", "Code", "Name", "Continent", "Region", "Population", "Capital"));
        // Print each country's details
        for (Country country : countryList) {
            String country_string =
                    String.format("%-15s %-30s %-15s %-30s %-15s %-15s",
                            country.code, country.name, country.continent, country.region, country.population, country.capital);
            System.out.println(country_string);
        }
        return countryList;
    }

    //Method to display countries in continent by population
    public ArrayList<Country> getCountriesInContByPopulation(String Continent) {
        ArrayList<Country> countryList = new ArrayList<>();
        try {
            //Input Validation
            if (Continent == null) throw new Exception ("Fail! Invalid input for Query");

            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT  country.continent, country.capital, city.ID, city.name AS capital_city, country.code, country.name, country.region, country.population "
                            + "FROM country "
                            + "JOIN city ON country.capital = city.ID "
                            + "WHERE country.continent = '" + Continent + "'"
                            + "ORDER BY population DESC";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Process the result set and add to the list
            while (rset.next()) {
                Country country = new Country();
                country.code = rset.getString("country.code");
                country.name = rset.getString("country.name");
                country.continent = rset.getString("country.continent");
                country.region = rset.getString("country.region");
                country.population = rset.getInt("country.population");
                country.capital = rset.getString("capital_city");
                countryList.add(country);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }

        // Print a blank line before the heading
        System.out.print("\n");
        // Print heading
        System.out.println("Countries In The Continent Sorted By Population:");
        // Print a blank line after the heading
        System.out.print("\n");


        // Print header
        System.out.println(String.format("%-15s %-30s %-15s %-30s %-15s %-15s", "Code", "Name", "Continent", "Region", "Population", "Capital"));
        // Print each country's details
        for (Country country : countryList) {
            String country_string =
                    String.format("%-15s %-30s %-15s %-30s %-15s %-15s",
                            country.code, country.name, country.continent, country.region, country.population, country.capital);
            System.out.println(country_string);
        }
        return countryList;
    }

    //Method to display all the countries in a region organized by largest population to smallest
    public ArrayList<Country> getCountriesInRegionByPopulation(String Region) {
        ArrayList<Country> countryList = new ArrayList<>();
        try {
            //Input Validation
            if (Region == null) throw new Exception ("Fail! Invalid input for Query");

            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT  country.continent, country.capital, city.ID, city.name AS capital_city, country.code, country.name, country.region, country.population "
                            + "FROM country "
                            + "JOIN city ON country.capital = city.ID "
                            + "WHERE country.region = '" + Region + "'"
                            + "ORDER BY population DESC";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Process the result set and add to the list
            while (rset.next()) {
                Country country = new Country();
                country.code = rset.getString("country.code");
                country.name = rset.getString("country.name");
                country.continent = rset.getString("country.continent");
                country.region = rset.getString("country.region");
                country.population = rset.getInt("country.population");
                country.capital = rset.getString("capital_city");
                countryList.add(country);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get country details");
            return null;
        }

        // Print a blank line before the heading
        System.out.print("\n");
        // Print heading
        System.out.println("Countries In The Region Sorted By Population:");
        // Print a blank line after the heading
        System.out.print("\n");

        // Print header
        System.out.println(String.format("%-15s %-30s %-15s %-30s %-15s %-15s", "Code", "Name", "Continent", "Region", "Population", "Capital"));
        // Print each country's details
        for (Country country : countryList) {
            String country_string =
                    String.format("%-15s %-30s %-15s %-30s %-15s %-15s",
                            country.code, country.name, country.continent, country.region, country.population, country.capital);
            System.out.println(country_string);
        }
        return countryList;
    }

    //Method to display cities in world by population
    public ArrayList<City> getCitiesByPopulation() {
        ArrayList<City> cityList = new ArrayList<>();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Create string for SQL statement
            String strSelect =
                    "SELECT city.Name, country.Name, city.District, city.Population "
                            + "FROM world.city , world.country "
                            + "where country.Code = city.CountryCode "
                            + "ORDER BY Population desc;";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Process the result set and add to the list
            while (rset.next()) {
                City city = new City();
                city.setName(rset.getString("city.name"));
                city.setName(rset.getString("country.name"));
                city.setDistrict(rset.getString("city.district"));
                city.setPopulation(rset.getInt("city.population"));
                cityList.add(city);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }

        // Print a blank line before the heading
        System.out.print("\n");
        // Print heading
        System.out.println("Cities In The World Sorted By Population:");
        // Print a blank line after the heading
        System.out.print("\n");

        // Print header
        System.out.println(String.format("%-15s %-30s %-15s %-30s", "Name", "Country", "District", "Population"));
        // Print each city's details
        for (City city : cityList) {
            String country_string =
                    String.format("%-15s %-30s %-15s %-30s",
                            city.getName(), city.getName(), city.getDistrict(), city.getPopulation());
            System.out.println(country_string);
        }
        return cityList;

    }

    //Method to display the total population of the world
    public long getTotalPopulation() {
        long totalPopulation = 0;
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT SUM(country.population) AS total_population "
                            + "FROM country "
                            + "JOIN city ON country.capital = city.ID";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);

            // Print a blank line before showing results
            System.out.print("\n");

            // Process the result set
            if (rset.next()) {
                totalPopulation = rset.getLong("total_population");
                System.out.println("Total population of all the world: " + totalPopulation);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get total population");
        }
        return totalPopulation;
    }


    //Method to display the total population of a continent
    public long getTotalPopulationofContinent(String Continent) {
        long totalPopulation = 0;
        try {
            //Input Validation
            if (Continent == null) throw new Exception ("Fail! Invalid input for Query");

            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT SUM(population) AS total_population FROM country WHERE continent = '" + Continent + "'";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Process the result set
            if (rset.next()) {
                totalPopulation = rset.getLong("total_population");
                System.out.println("Total population of all countries in " + Continent + ":" + totalPopulation);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get total population");
        }
        return totalPopulation;
    }



    //Method to display the total population of a region
    public long getTotalPopulationofRegion(String Region) {
        long totalPopulation = 0;
        try {
            //Input Validation
            if (Region == null) throw new Exception ("Fail! Invalid input for Query");

            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT SUM(population) AS total_population FROM country WHERE region = '" + Region + "'";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Process the result set
            if (rset.next()) {
                totalPopulation = rset.getLong("total_population");
                System.out.println("Total population of all countries in " + Region + ":" + totalPopulation);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get total population");
        }
        return totalPopulation;
    }


    //Method to display the total population of a city
    public long getTotalPopulationofCity(String city) {
        return getCityPopulation("SELECT ID, Name, Population FROM city WHERE Name = ?", city);
    }

    public long getTotalPopulationofCity(int cityId) {
        return getCityPopulation("SELECT ID, Name, Population FROM city WHERE ID = ?", cityId);
    }

    private long getCityPopulation(String sql, Object selection) {
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setObject(1, selection);
            try (ResultSet rows = stmt.executeQuery()) {
                if (!rows.next()) {
                    System.out.println("No city found for selection: " + selection);
                    return 0;
                }
                int id = rows.getInt("ID");
                String name = rows.getString("Name");
                long population = rows.getLong("Population");
                if (rows.next()) {
                    List<Integer> matches = new ArrayList<>();
                    matches.add(id);
                    do {
                        matches.add(rows.getInt("ID"));
                    } while (rows.next());
                    System.out.println("Multiple cities named " + name + ". Select a city by ID: " + matches);
                    return 0;
                }
                System.out.println("Total population of " + name + ": " + population);
                return population;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get total population");
            return 0;
        }
    }

    //Method to display the total number of people who speak
    public ArrayList<Language> getLanguageSpeakers() {

        ArrayList<Language> LanguageList = new ArrayList<>();
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT q.Language, q.TotalSpeakers, " +
                            "(q.TotalSpeakers / (SELECT SUM(c.Population) FROM country c)) * 100 AS WorldPercent " +
                            "FROM (SELECT cl.Language, SUM(c.Population) AS TotalSpeakers " +
                            "FROM countrylanguage cl INNER JOIN country c ON cl.CountryCode = c.Code " +
                            "WHERE cl.Language IN ('Chinese', 'English', 'Hindi', 'Spanish', 'Arabic') " +
                            "GROUP BY cl.Language) AS q " +
                            "ORDER BY WorldPercent DESC;";


            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Process the result set and add to the list
            while (rset.next()) {
                Language language = new Language();
                language.language = rset.getString("Language");
                language.totalSpeakers = rset.getLong("TotalSpeakers");
                language.percentage = rset.getLong("WorldPercent");
                LanguageList.add(language);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get language details");
            return null;
        }

        // Print a blank line before the heading
        System.out.print("\n");
        // Print heading
        System.out.println("Total Number Of Speakers Per Language:");
        // Print a blank line after the heading
        System.out.print("\n");

        // Print header
        System.out.println(String.format("%-45s %-30s %-15s", "Language", "Total Speakers", "World Percentage of Speakers"));
        // Print each city's details
        for (Language language : LanguageList) {
            String country_string =
                    String.format("%-45s %-30s %-15s",
                            language.language, language.totalSpeakers, language.percentage + "%");
            System.out.println(country_string);
        }
        return LanguageList;


    }




}
