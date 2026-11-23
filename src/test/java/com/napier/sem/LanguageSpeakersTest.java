package com.napier.sem;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LanguageSpeakersTest extends ReportDatabaseFixture {
    @Test
    void selectedLanguagesUseSpeakerCountsAndSharesOfTheWholeWorldInDescendingOrder() {
        List<Language> languages = app.getLanguageSpeakers();
        assertEquals(List.of("English", "Hindi", "Chinese", "Spanish", "Arabic"),
                languages.stream().map(Language::getLanguage).toList());
        assertEquals(List.of(3176L, 2400L, 192L, 130L, 13L),
                languages.stream().map(Language::getTotalSpeakers).toList());
        double[] expectedPercentages = {31.76, 24.0, 1.92, 1.30, 0.13};
        for (int i = 0; i < languages.size(); i++) {
            assertEquals(expectedPercentages[i], languages.get(i).getPercentage(), 0.00001);
        }
    }
}
