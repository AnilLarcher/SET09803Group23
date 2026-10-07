package com.napier.sem;

/**
 * Represents a row in the countrylanguage table.
 */
public class CountryLanguage
{
    /**
     * Code of the country the language is spoken in.
     */
    private String countryCode;

    /**
     * Name of the language.
     */
    private String language;

    /**
     * Whether the language is an official language of the country ('T' in the database).
     */
    private boolean isOfficial;

    /**
     * Percentage of the country's population that speaks the language.
     */
    private double percentage;

    /**
     * Create an empty country language.
     */
    public CountryLanguage()
    {
    }

    /**
     * Create a country language with all fields set.
     */
    public CountryLanguage(String countryCode, String language, boolean isOfficial, double percentage)
    {
        this.countryCode = countryCode;
        this.language = language;
        this.isOfficial = isOfficial;
        this.percentage = percentage;
    }

    public String getCountryCode()
    {
        return countryCode;
    }

    public void setCountryCode(String countryCode)
    {
        this.countryCode = countryCode;
    }

    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }

    public boolean isOfficial()
    {
        return isOfficial;
    }

    public void setOfficial(boolean isOfficial)
    {
        this.isOfficial = isOfficial;
    }

    public double getPercentage()
    {
        return percentage;
    }

    public void setPercentage(double percentage)
    {
        this.percentage = percentage;
    }

    @Override
    public String toString()
    {
        return "CountryLanguage{countryCode='" + countryCode + '\''
                + ", language='" + language + '\''
                + ", isOfficial=" + isOfficial
                + ", percentage=" + percentage
                + '}';
    }
}
