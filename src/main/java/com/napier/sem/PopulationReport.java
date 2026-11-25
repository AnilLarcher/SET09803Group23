package com.napier.sem;

/**
 * Represents population report statistics for a Continent, Region, or Country.
 */
public class PopulationReport {
    public String name;
    public long totalPopulation;
    public double cityPopulation;
    public double cityPercentage;
    private long nonCityPopulation;
    private double nonCityPercentage;

    public PopulationReport() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public long getTotalPopulation() { return totalPopulation; }
    public void setTotalPopulation(long totalPopulation) { this.totalPopulation = totalPopulation; }

    public double getCityPopulation() { return cityPopulation; }
    public void setCityPopulation(long cityPopulation) { this.cityPopulation = cityPopulation; }

    public double getCityPercentage() { return cityPercentage; }
    public void setCityPercentage(double cityPercentage) { this.cityPercentage = cityPercentage; }

    public long getNonCityPopulation() { return nonCityPopulation; }
    public void setNonCityPopulation(long nonCityPopulation) { this.nonCityPopulation = nonCityPopulation; }

    public double getNonCityPercentage() { return nonCityPercentage; }
    public void setNonCityPercentage(double nonCityPercentage) { this.nonCityPercentage = nonCityPercentage; }
}