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
    private String countrySelection;

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
        //app.getCountriesInContByPopulation("North America");

        //Method to display countries by population in region
        //app.getCountriesInRegionByPopulation("Caribbean");

        //Method to display cities in the world by population
        //app.getCitiesByPopulation();

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
        //app.getTopNCapitalCitiesinaRegion(3, "Caribbean");

        //Method to display the population of people in continents
        //app.getPopulationofPeopleinContinent();

        //Method to display the population of people in region
        app.getPopulationofPeopleinRegion();

        //Method to display the population of people Countries
        app.getPopulationofPeopleinCountry();

        //Method to display total world population
        //app.getTotalPopulation();

        //Method to display total population of a continent
        //app.getTotalPopulationofContinent("Asia");

        //Method to display total population of a region
        //app.getTotalPopulationofRegion("Caribbean");

        //Method to display total population of a country
        //app.getTotalPopulationofCountry("Barbados");

        //Method to display total population of a district
        app.getTotalPopulationofDistrict("Castries");

        //Method to display total population of a city
        //app.getTotalPopulationofCity("Georgetown");

        //Method to display language speakers in the world
        app.getLanguageSpeakers();

        // Disconnect from database
        app.disconnect();

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

    public long getTotalPopulationofCountry(String Country) {
        long TotalPopulation = 0;
        try {
            String selection = countrySelection == null ? "" : countrySelection.trim();
            if (selection.isEmpty()) {
                System.out.println("Country selection cannot be empty");

            }

            String sql = "SELECT Name, Population FROM country WHERE Code = UPPER(?) OR Name = ? LIMIT 1";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, selection);
                stmt.setString(2, selection);
                try (ResultSet rset = stmt.executeQuery()) {
                    if (rset.next()) {
                        System.out.println("Country Population - " + rset.getString("Name") + ": "
                                + rset.getLong("Population"));
                    } else {
                        System.out.println("No country found for selection: " + selection);
                    }
                }
            } catch (SQLException e) {
                System.out.println("Failed to get selected country population");
                System.out.println(e.getMessage());
            }
            return TotalPopulation;
        } finally {

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
     */
    public void getTotalPopulationofDistrict(String districtName) {
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
                long totalPop = rset.getLong("TotalPopulation");
                System.out.println(String.format("%-20s %-15d", district, totalPop));
            } else {
                System.out.println("No district found: " + districtName);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get population report for district: " + districtName);
        }
    }

}
