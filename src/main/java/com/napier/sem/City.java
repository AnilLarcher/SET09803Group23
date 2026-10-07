package com.napier.sem;

/**
 * Represents a row in the city table.
 */
public class City
{
    /**
     * City ID (primary key).
     */
    private int id;

    /**
     * City name.
     */
    private String name;

    /**
     * Code of the country the city is in.
     */
    private String countryCode;

    /**
     * District the city is in.
     */
    private String district;

    /**
     * Population of the city.
     */
    private int population;

    /**
     * Create an empty city.
     */
    public City()
    {
    }

    /**
     * Create a city with all fields set.
     */
    public City(int id, String name, String countryCode, String district, int population)
    {
        this.id = id;
        this.name = name;
        this.countryCode = countryCode;
        this.district = district;
        this.population = population;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCountryCode()
    {
        return countryCode;
    }

    public void setCountryCode(String countryCode)
    {
        this.countryCode = countryCode;
    }

    public String getDistrict()
    {
        return district;
    }

    public void setDistrict(String district)
    {
        this.district = district;
    }

    public int getPopulation()
    {
        return population;
    }

    public void setPopulation(int population)
    {
        this.population = population;
    }

    @Override
    public String toString()
    {
        return "City{id=" + id
                + ", name='" + name + '\''
                + ", countryCode='" + countryCode + '\''
                + ", district='" + district + '\''
                + ", population=" + population
                + '}';
    }
}
