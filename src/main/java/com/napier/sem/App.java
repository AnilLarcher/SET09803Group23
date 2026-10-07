package com.napier.sem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App
{
    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    public static void main(String[] args)
    {
        App app = new App();

        // Inside Docker the Dockerfile passes "db:3306 30000"; from IntelliJ use the exposed port
        if (args.length < 2)
        {
            app.connect("localhost:3307", 0);
        }
        else
        {
            app.connect(args[0], Integer.parseInt(args[1]));
        }

        app.menu();

        app.disconnect();
    }

    /**
     * Tables in the world database that the menu can display.
     */
    private static final String[] TABLES = {"city", "country", "countrylanguage"};

    /**
     * Show a menu and print the chosen table(s) until the user exits.
     */
    public void menu()
    {
        if (con == null)
        {
            System.out.println("Not connected to database, cannot show menu");
            return;
        }

        Scanner in = new Scanner(System.in);
        while (true)
        {
            System.out.println();
            System.out.println("===== World Database =====");
            for (int i = 0; i < TABLES.length; ++i)
            {
                System.out.println((i + 1) + ". Show all " + TABLES[i] + " data");
            }
            System.out.println((TABLES.length + 1) + ". Show all tables");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            // No input available (e.g. Docker without a terminal attached)
            if (!in.hasNextLine())
            {
                System.out.println();
                return;
            }

            String choice = in.nextLine().trim();
            int option;
            try
            {
                option = Integer.parseInt(choice);
            }
            catch (NumberFormatException e)
            {
                System.out.println("Invalid option: " + choice);
                continue;
            }

            if (option == 0)
            {
                return;
            }
            else if (option >= 1 && option <= TABLES.length)
            {
                printTable(TABLES[option - 1]);
            }
            else if (option == TABLES.length + 1)
            {
                for (String table : TABLES)
                {
                    printTable(table);
                }
            }
            else
            {
                System.out.println("Invalid option: " + choice);
            }
        }
    }

    /**
     * Print every row of a table with aligned columns.
     *
     * @param table name of the table to print (must be one of TABLES)
     */
    public void printTable(String table)
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
     * Get every row of the city table.
     *
     * @return list of cities, or null if the query fails
     */
    public List<City> getAllCities()
    {
        try (Statement stmt = con.createStatement();
             ResultSet rset = stmt.executeQuery("SELECT ID, Name, CountryCode, District, Population FROM City")) /** Still works, intelliJ cannot see into the database  **/
        {
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
    public List<Country> getAllCountries()
    {
        try (Statement stmt = con.createStatement();
             ResultSet rset = stmt.executeQuery(
                     "SELECT Code, Name, Continent, Region, SurfaceArea, IndepYear, Population, LifeExpectancy, "
                             + "GNP, GNPOld, LocalName, GovernmentForm, HeadOfState, Capital, Code2 FROM country")) /** Still works, intelliJ cannot see into the database  **/
        {
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
    public List<CountryLanguage> getAllCountryLanguages()
    {
        try (Statement stmt = con.createStatement();
             ResultSet rset = stmt.executeQuery("SELECT CountryCode, Language, IsOfficial, Percentage FROM countrylanguage")) /** Still works, intelliJ cannot see into the database  **/
        {
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
    private static Integer getNullableInt(ResultSet rset, String column) throws SQLException
    {
        int value = rset.getInt(column);
        return rset.wasNull() ? null : value;
    }

    /**
     * Read a decimal column that may be NULL.
     *
     * @return the value, or null if the column is NULL
     */
    private static Double getNullableDouble(ResultSet rset, String column) throws SQLException
    {
        double value = rset.getDouble(column);
        return rset.wasNull() ? null : value;
    }

    /**
     * Connect to the MySQL database.
     *
     * @param location host:port of the database
     * @param delay    milliseconds to wait before each connection attempt
     */
    public void connect(String location, int delay)
    {
        try
        {
            // Load database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(delay);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://" + location + "/world?useSSL=false&allowPublicKeyRetrieval=true", "root", "example");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + i);
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }
}
