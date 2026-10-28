package com.napier.sem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class App
{
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
        //app.getTopNCountriesInRegionByPopulation(10, "Caribbean");

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
        //app.getPopulationofPeopleinRegion();

        //Method to display the population of people Countries
        //app.getPopulationofPeopleinCountry();

        //Method to display total world population
        //app.getTotalPopulation();

        //Method to display total population of a continent
        //app.getTotalPopulationofContinent("Asia");

        //Method to display total population of a region
        //app.getTotalPopulationofRegion("Caribbean");

        //Method to display total population of a country
        app.getTotalPopulationofCountry("Barbados");

        //Method to display total population of a district
        //app.getTotalPopulationofDistrict("Castries");

        //Method to display total population of a city
        //app.getTotalPopulationofCity("Georgetown");

        //Method to display language speakers in the world
        //app.getLanguageSpeakers();

        // Disconnect from database
        app.disconnect();

    }



    /**
     * Connection to database
     */
    public void connect(String location, int delay){
        try {
            Class.forName("com.mql.cj.jdbc.Driver");
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

        System.out.println("============== Countries Sorted by Population ============");

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
            if (number == null || number <= 0) throw new Exception ("Fail! Invalid Number input for Query");

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

    /** Method to display the top N populated countries in a region
     *
     */
    public ArrayList<Country> getTopNCountriesInRegionByPopulation(Integer number, String Region) {
        ArrayList<Country> countryList = new ArrayList<>();
        try {
            //Input Validation
            if (number == null || number <= 0 || Region == null) throw new Exception ("Fail! Invalid input for Query");

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
        }

    /**
     * Print every row of a table with aligned columns.
     *
     * @param table name of the table to print (must be one of TABLES)
     */
    /**public void printTable(String table)
    {
        try (Statement stmt = con.createStatement();
             ResultSet rset = stmt.executeQuery("SELECT * FROM " + table))
        {
            ResultSetMetaData meta = rset.getMetaData();
            int cols = meta.getColumnCount();

            // Read all rows first so column widths can fit the longest value
            List<String[]> rows = new ArrayList<>();
            int[] widths = new int[cols];
            String[] header = new String[cols];
            for (int c = 0; c < cols; ++c)
            {
                header[c] = meta.getColumnLabel(c + 1);
                widths[c] = header[c].length();
            }
            while (rset.next())
            {
                String[] row = new String[cols];
                for (int c = 0; c < cols; ++c)
                {
                    String value = rset.getString(c + 1);
                    row[c] = value == null ? "NULL" : value;
                    widths[c] = Math.max(widths[c], row[c].length());
                }
                rows.add(row);
            }

            StringBuilder format = new StringBuilder();
            for (int w : widths)
            {
                format.append("%-").append(w).append("s  ");
            }
            format.append("%n");

            System.out.println();
            System.out.println("=== " + table + " (" + rows.size() + " rows) ===");
            System.out.printf(format.toString(), (Object[]) header);
            for (String[] row : rows)
            {
                System.out.printf(format.toString(), (Object[]) row);
            }
        }
        catch (SQLException e)
        {
            System.out.println("Failed to get " + table + " data");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Print the total world population from the country table.
     */
    /**public void printWorldPopulation()
    {
        try (Statement stmt = con.createStatement();
             ResultSet rset = stmt.executeQuery("SELECT SUM(Population) AS WorldPopulation FROM country"))
        {
            if (rset.next())
            {
                long worldPopulation = rset.getLong("WorldPopulation");
                if (rset.wasNull())
                {
                    System.out.println("World Population: no country data available");
                }
                else
                {
                    System.out.println("World Population: " + worldPopulation);
                }
            }
            else
            {
                System.out.println("World Population: no country data available");
            }
        }
        catch (SQLException e)
        {
            System.out.println("Failed to get world population");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Ask for a country and print its population.
     *
     * @param in menu scanner
     */
    /**private void promptAndPrintCountryPopulation(Scanner in)
    {
        System.out.print("Enter country code or name: ");
        if (!in.hasNextLine())
        {
            System.out.println();
            return;
        }

        printCountryPopulation(in.nextLine());
    }

    /**
     * Print the population for one selected country.
     *
     * @param countrySelection country code or country name
     */
    /**public void printCountryPopulation(String countrySelection)
    {
        String selection = countrySelection == null ? "" : countrySelection.trim();
        if (selection.isEmpty())
        {
            System.out.println("Country selection cannot be empty");
            return;
        }

        String sql = "SELECT Name, Population FROM country WHERE Code = UPPER(?) OR Name = ? LIMIT 1";
        try (PreparedStatement stmt = con.prepareStatement(sql))
        {
            stmt.setString(1, selection);
            stmt.setString(2, selection);
            try (ResultSet rset = stmt.executeQuery())
            {
                if (rset.next())
                {
                    System.out.println("Country Population - " + rset.getString("Name") + ": "
                            + rset.getLong("Population"));
                }
                else
                {
                    System.out.println("No country found for selection: " + selection);
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println("Failed to get selected country population");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Ask for a continent and print its population.
     *
     * @param in menu scanner
     */
    /**private void promptAndPrintContinentPopulation(Scanner in)
    {
        System.out.print("Enter continent: ");
        if (!in.hasNextLine())
        {
            System.out.println();
            return;
        }

        printContinentPopulation(in.nextLine());
    }

    /**
     * Print the population for one selected continent.
     *
     * @param continentSelection continent name
     */
    /**public void printContinentPopulation(String continentSelection)
    {
        String selection = continentSelection == null ? "" : continentSelection.trim();
        if (selection.isEmpty())
        {
            System.out.println("Continent selection cannot be empty");
            return;
        }

        String sql = "SELECT COUNT(*) AS CountryCount, SUM(Population) AS ContinentPopulation "
                + "FROM country WHERE Continent = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql))
        {
            stmt.setString(1, selection);
            try (ResultSet rset = stmt.executeQuery())
            {
                if (rset.next() && rset.getLong("CountryCount") > 0)
                {
                    System.out.println("Continent Population - " + selection + ": "
                            + rset.getLong("ContinentPopulation"));
                }
                else
                {
                    System.out.println("No continent found for selection: " + selection);
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println("Failed to get selected continent population");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Ask for a region and print its population.
     *
     * @param in menu scanner
     */
    /**private void promptAndPrintRegionPopulation(Scanner in)
    {
        System.out.print("Enter region: ");
        if (!in.hasNextLine())
        {
            System.out.println();
            return;
        }

        printRegionPopulation(in.nextLine());
    }

    /**
     * Print the population for one selected region.
     *
     * @param regionSelection region name
     */
   /** public void printRegionPopulation(String regionSelection)
    {
        String selection = regionSelection == null ? "" : regionSelection.trim();
        if (selection.isEmpty())
        {
            System.out.println("Region selection cannot be empty");
            return;
        }

        String sql = "SELECT COUNT(*) AS CountryCount, SUM(Population) AS RegionPopulation "
                + "FROM country WHERE Region = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql))
        {
            stmt.setString(1, selection);
            try (ResultSet rset = stmt.executeQuery())
            {
                if (rset.next() && rset.getLong("CountryCount") > 0)
                {
                    System.out.println("Region Population - " + selection + ": "
                            + rset.getLong("RegionPopulation"));
                }
                else
                {
                    System.out.println("No region found for selection: " + selection);
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println("Failed to get selected region population");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Print all countries ordered by population from largest to smallest.
     */
    /**public void printAllCountriesByPopulation()
    {
        String sql = "SELECT country.Code AS Code, country.Name AS Name, country.Continent AS Continent, "
                + "country.Region AS Region, country.Population AS Population, city.Name AS Capital "
                + "FROM country LEFT JOIN city ON country.Capital = city.ID "
                + "ORDER BY country.Population DESC";
        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rset = stmt.executeQuery())
        {
            printReportRows("Countries by Population", rset);
        }
        catch (SQLException e)
        {
            System.out.println("Failed to get countries by population");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Ask for a continent and print matching countries ordered by population.
     *
     * @param in menu scanner
     */
    /**private void promptAndPrintCountriesByContinent(Scanner in)
    {
        System.out.print("Enter continent: ");
        if (!in.hasNextLine())
        {
            System.out.println();
            return;
        }

        printCountriesByContinentPopulation(in.nextLine());
    }

    /**
     * Print countries in one continent ordered by population from largest to smallest.
     *
     * @param continentSelection continent name
     */
    /**public void printCountriesByContinentPopulation(String continentSelection)
    {
        String selection = continentSelection == null ? "" : continentSelection.trim();
        if (selection.isEmpty())
        {
            System.out.println("Continent selection cannot be empty");
            return;
        }

        String countSql = "SELECT COUNT(*) AS CountryCount FROM country WHERE Continent = ?";
        String reportSql = "SELECT country.Code AS Code, country.Name AS Name, country.Continent AS Continent, "
                + "country.Region AS Region, country.Population AS Population, city.Name AS Capital "
                + "FROM country LEFT JOIN city ON country.Capital = city.ID "
                + "WHERE country.Continent = ? "
                + "ORDER BY country.Population DESC";
        try (PreparedStatement countStmt = con.prepareStatement(countSql);
             PreparedStatement reportStmt = con.prepareStatement(reportSql))
        {
            countStmt.setString(1, selection);
            try (ResultSet countRset = countStmt.executeQuery())
            {
                if (!countRset.next() || countRset.getLong("CountryCount") == 0)
                {
                    System.out.println("No countries found for continent: " + selection);
                    return;
                }
            }

            reportStmt.setString(1, selection);
            try (ResultSet reportRset = reportStmt.executeQuery())
            {
                printReportRows("Countries in " + selection + " by Population", reportRset);
            }
        }
        catch (SQLException e)
        {
            System.out.println("Failed to get countries in selected continent by population");
            System.out.println(e.getMessage());
        }
    }

    /**
     * Print result-set rows with aligned columns.
     *
     * @param title report title
     * @param rset result set to print
     */
    /**private void printReportRows(String title, ResultSet rset) throws SQLException
    {
        ResultSetMetaData meta = rset.getMetaData();
        int cols = meta.getColumnCount();

        List<String[]> rows = new ArrayList<>();
        int[] widths = new int[cols];
        String[] header = new String[cols];
        for (int c = 0; c < cols; ++c)
        {
            header[c] = meta.getColumnLabel(c + 1);
            widths[c] = header[c].length();
        }
        while (rset.next())
        {
            String[] row = new String[cols];
            for (int c = 0; c < cols; ++c)
            {
                String value = rset.getString(c + 1);
                row[c] = value == null ? "NULL" : value;
                widths[c] = Math.max(widths[c], row[c].length());
            }
            rows.add(row);
        }

        StringBuilder format = new StringBuilder();
        for (int w : widths)
        {
            format.append("%-").append(w).append("s  ");
        }
        format.append("%n");

        System.out.println();
        System.out.println("=== " + title + " (" + rows.size() + " rows) ===");
        System.out.printf(format.toString(), (Object[]) header);
        for (String[] row : rows)
        {
            System.out.printf(format.toString(), (Object[]) row);
        }
    }

    /**
     * Get every row of the city table.
     *
     * @return list of cities, or null if the query fails
     */
    /**public List<City> getAllCities()
    {
        try (Statement stmt = con.createStatement();
             ResultSet rset = stmt.executeQuery("SELECT ID, Name, CountryCode, District, Population FROM world.city")) /** Still works, intelliJ cannot see into the database  **/
       /** {
            List<City> cities = new ArrayList<>();
            while (rset.next())
            {
                cities.add(new City(
                        rset.getInt("ID"),
                        rset.getString("Name"),
                        rset.getString("CountryCode"),
                        rset.getString("District"),
                        rset.getInt("Population")));
            }
            return cities;
        }
        catch (SQLException e)
        {
            System.out.println("Failed to get city data");
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Get every row of the country table.
     *
     * @return list of countries, or null if the query fails
     */
    /**public List<Country> getAllCountries()
    {
        try (Statement stmt = con.createStatement();
             ResultSet rset = stmt.executeQuery(
                     "SELECT Code, Name, Continent, Region, SurfaceArea, IndepYear, Population, LifeExpectancy, "
                             + "GNP, GNPOld, LocalName, GovernmentForm, HeadOfState, Capital, Code2 FROM country")) /** Still works, intelliJ cannot see into the database  **/
        /**{
            List<Country> countries = new ArrayList<>();
            while (rset.next())
            {
                Country country = new Country();
                country.setCode(rset.getString("Code"));
                country.setName(rset.getString("Name"));
                country.setContinent(rset.getString("Continent"));
                country.setRegion(rset.getString("Region"));
                country.setSurfaceArea(rset.getDouble("SurfaceArea"));
                country.setIndepYear(getNullableInt(rset, "IndepYear"));
                country.setPopulation(rset.getInt("Population"));
                country.setLifeExpectancy(getNullableDouble(rset, "LifeExpectancy"));
                country.setGnp(getNullableDouble(rset, "GNP"));
                country.setGnpOld(getNullableDouble(rset, "GNPOld"));
                country.setLocalName(rset.getString("LocalName"));
                country.setGovernmentForm(rset.getString("GovernmentForm"));
                country.setHeadOfState(rset.getString("HeadOfState"));
                country.setCapital(getNullableInt(rset, "Capital"));
                country.setCode2(rset.getString("Code2"));
                countries.add(country);
            }
            return countries;
        }
        catch (SQLException e)
        {
            System.out.println("Failed to get country data");
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Get every row of the countrylanguage table.
     *
     * @return list of country languages, or null if the query fails
     */
    /**public List<CountryLanguage> getAllCountryLanguages()
    {
        try (Statement stmt = con.createStatement();
             ResultSet rset = stmt.executeQuery("SELECT CountryCode, Language, IsOfficial, Percentage FROM countrylanguage")) /** Still works, intelliJ cannot see into the database  **/
        /**{
            List<CountryLanguage> languages = new ArrayList<>();
            while (rset.next())
            {
                languages.add(new CountryLanguage(
                        rset.getString("CountryCode"),
                        rset.getString("Language"),
                        "T".equals(rset.getString("IsOfficial")),
                        rset.getDouble("Percentage")));
            }
            return languages;
        }
        catch (SQLException e)
        {
            System.out.println("Failed to get countrylanguage data");
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Read an int column that may be NULL.
     *
     * @return the value, or null if the column is NULL
     */
    /**private static Integer getNullableInt(ResultSet rset, String column) throws SQLException
    {
        int value = rset.getInt(column);
        return rset.wasNull() ? null : value;
    }

    /**
     * Read a decimal column that may be NULL.
     *
     * @return the value, or null if the column is NULL
     */
    /**private static Double getNullableDouble(ResultSet rset, String column) throws SQLException
    {
        double value = rset.getDouble(column);
        return rset.wasNull() ? null : value;} */