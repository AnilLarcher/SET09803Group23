package com.napier.sem;

/**
 * Represents a language spoken in a country.
 */
public class Language {
    private String countryCode;
    private String language;
    private boolean isOfficial;
    private double percentage;
    private long totalSpeakers;

    public Language() {}

    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String countryCode) { this.countryCode = countryCode; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public boolean isOfficial() { return isOfficial; }
    public void setOfficial(boolean official) { this.isOfficial = official; }

    public double getPercentage() { return percentage; }
    public void setPercentage(double percentage) { this.percentage = percentage; }

    public long getTotalSpeakers() { return totalSpeakers; }
    public void setTotalSpeakers(long totalSpeakers) { this.totalSpeakers = totalSpeakers; }
}