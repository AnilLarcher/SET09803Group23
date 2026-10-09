package com.napier.sem;

/**
 * Represents a row in the country table.
 * Columns that can be NULL in the database use wrapper types (Integer, Double).
 */
public class Country
{
    /**
     * Three letter country code (primary key).
     */
    private String code;

    /**
     * Country name.
     */
    private String name;

    /**
     * Continent the country is in.
     */
    private String continent;

    /**
     * Region the country is in.
     */
    private String region;

    /**
     * Surface area in square kilometres.
     */
    private double surfaceArea;

    /**
     * Year of independence (may be null).
     */
    private Integer indepYear;

    /**
     * Population of the country.
     */
    private int population;

    /**
     * Life expectancy (may be null).
     */
    private Double lifeExpectancy;

    /**
     * Gross national product (may be null).
     */
    private Double gnp;

    /**
     * Previous gross national product (may be null).
     */
    private Double gnpOld;

    /**
     * Name of the country in its local language.
     */
    private String localName;

    /**
     * Form of government.
     */
    private String governmentForm;

    /**
     * Head of state (may be null).
     */
    private String headOfState;

    /**
     * ID of the capital city in the city table (may be null).
     */
    private Integer capital;

    /**
     * Two letter country code.
     */
    private String code2;

    /**
     * Create an empty country.
     */
    public Country()
    {
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getContinent()
    {
        return continent;
    }

    public void setContinent(String continent)
    {
        this.continent = continent;
    }

    public String getRegion()
    {
        return region;
    }

    public void setRegion(String region)
    {
        this.region = region;
    }

    public double getSurfaceArea()
    {
        return surfaceArea;
    }

    public void setSurfaceArea(double surfaceArea)
    {
        this.surfaceArea = surfaceArea;
    }

    public Integer getIndepYear()
    {
        return indepYear;
    }

    public void setIndepYear(Integer indepYear)
    {
        this.indepYear = indepYear;
    }

    public int getPopulation()
    {
        return population;
    }

    public void setPopulation(int population)
    {
        this.population = population;
    }

    public Double getLifeExpectancy()
    {
        return lifeExpectancy;
    }

    public void setLifeExpectancy(Double lifeExpectancy)
    {
        this.lifeExpectancy = lifeExpectancy;
    }

    public Double getGnp()
    {
        return gnp;
    }

    public void setGnp(Double gnp)
    {
        this.gnp = gnp;
    }

    public Double getGnpOld()
    {
        return gnpOld;
    }

    public void setGnpOld(Double gnpOld)
    {
        this.gnpOld = gnpOld;
    }

    public String getLocalName()
    {
        return localName;
    }

    public void setLocalName(String localName)
    {
        this.localName = localName;
    }

    public String getGovernmentForm()
    {
        return governmentForm;
    }

    public void setGovernmentForm(String governmentForm)
    {
        this.governmentForm = governmentForm;
    }

    public String getHeadOfState()
    {
        return headOfState;
    }

    public void setHeadOfState(String headOfState)
    {
        this.headOfState = headOfState;
    }

    public Integer getCapital()
    {
        return capital;
    }

    public void setCapital(Integer capital)
    {
        this.capital = capital;
    }

    public String getCode2()
    {
        return code2;
    }

    public void setCode2(String code2)
    {
        this.code2 = code2;
    }

    @Override
    public String toString()
    {
        return "Country{code='" + code + '\''
                + ", name='" + name + '\''
                + ", continent='" + continent + '\''
                + ", region='" + region + '\''
                + ", surfaceArea=" + surfaceArea
                + ", indepYear=" + indepYear
                + ", population=" + population
                + ", lifeExpectancy=" + lifeExpectancy
                + ", gnp=" + gnp
                + ", gnpOld=" + gnpOld
                + ", localName='" + localName + '\''
                + ", governmentForm='" + governmentForm + '\''
                + ", headOfState='" + headOfState + '\''
                + ", capital=" + capital
                + ", code2='" + code2 + '\''
                + '}';
    }
}
