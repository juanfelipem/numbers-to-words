package com.illusionware.sonatype.service;

import static org.junit.Assert.assertTrue;

import com.illusionware.sonatype.data.NumbersMapping;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit test for simple ConversionService.
 */
public class ConversionServiceTest
{

    private ConversionService conversionService;

    @Before
    public void setup() {
        conversionService = new ConversionService();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionOnArgumentOutsideOfRangeUpperBound() {
        conversionService.convertHundredsToWords(1000, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionOnArgumentOutsideOfRangeLowerBound() {
        conversionService.convertHundredsToWords(-1, false);
    }

    @Test
    public void shouldConvertHundredsCorrectlyWithAnd() {
        String hundredsToWordsWithAnd = conversionService.convertHundredsToWords(945, true);
        Assert.assertEquals("The value 945 should be mapped correctly", "nine hundred and forty five", hundredsToWordsWithAnd);
    }

    @Test
    public void shouldConvertHundredsCorrectlyWithoutAnd() {
        String hundredsToWordsWithoutAnd = conversionService.convertHundredsToWords(399, false);
        Assert.assertEquals("The value 399 should be mapped correctly", "three hundred ninety nine", hundredsToWordsWithoutAnd);
    }

    @Test
    public void shouldConvertHundredsCorrectly() {
        String hundredsToWords = conversionService.convertHundredsToWords(1, true);
        Assert.assertEquals("The value 1 should be mapped correctly", "one", hundredsToWords);
    }

    @Test
    public void shouldConvertFullNumbersCorrectly() {
        String firstTest = conversionService.convertNumberToWords(1000000);
        Assert.assertEquals("The value 1000000 should be mapped correctly", "One million", firstTest);

        String secondTest = conversionService.convertNumberToWords(5237);
        Assert.assertEquals("The value 5237 should be mapped correctly", "Five thousand two hundred and thirty seven", secondTest);

        String thirdTest = conversionService.convertNumberToWords(13);
        Assert.assertEquals("The value 13 should be mapped correctly", "Thirteen", thirdTest);

        String fourthTest = conversionService.convertNumberToWords(85);
        Assert.assertEquals("The value 85 should be mapped correctly", "Eighty five", fourthTest);

        String fifthTest = conversionService.convertNumberToWords(0);
        Assert.assertEquals("The value 0 should be mapped correctly", "Zero", fifthTest);
    }
}
