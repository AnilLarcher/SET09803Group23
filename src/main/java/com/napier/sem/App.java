package com.napier.sem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

        app.disconnect();
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
