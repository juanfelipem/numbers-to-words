package com.illusionware.sonatype.service;

import com.illusionware.sonatype.data.NumbersMapping;
import com.illusionware.sonatype.util.SonatypeStringUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * This service provides functionality to allow the conversion of numbers to words.
 *
 */
public class ConversionService {

    private static final String ERROR_ARGUMENT_OUTSIDE_OF_RANGE = "The argument for the function is outside of the allowed range [0..999], provided value: {0}";
    private static final String LABEL_HUNDREDS = "hundred";
    private static final String EMPTY_STRING = "";
    private static final String BLANK_SPACE = " ";
    private static final String AND_CONCATENATOR = " and ";
    private static final String LABEL_NEGATIVE = "negative";
    private static final long ZERO = 0L;

    /**
     * The word used for building numbers in English changes every third power of 10, for power of 10 mapping to a word
     * please refer to {@link NumbersMapping}
     */
    private static final int POWER_INCREMENTS = 3;

    /**
     * Converts a given value into its English representation
     * @param valueToConvert The numeric value we want to convert into words
     * @return
     */
    public String convertNumberToWords(long valueToConvert) {
        if(valueToConvert == ZERO) {
            String wordForZero = NumbersMapping.getNumberMappingOrNearest((int) ZERO); // No risk of losing precision since the number is 0
            return SonatypeStringUtils.makeFirstLetterUppercase(wordForZero);
        }

        boolean isNegative = false;
        if(valueToConvert < ZERO) {
            isNegative = true;
        }

        List<Integer> splitsOfHundreds = splitValueIntoListOfHundreds(valueToConvert, true);
        int powersOfTen = (splitsOfHundreds.size() - 1) * POWER_INCREMENTS;
        List<String> numberToWordList = new ArrayList<>();
        for (int hundred: splitsOfHundreds) {
            boolean shouldIncludeAndWordForTens = powersOfTen == 0; // Only the first hundred value will have the "and" between the hundred and the tens.

            if(hundred > 0) { // Sections of hundreds equal to 0 don't go in the word.
                String wordForHundred = convertHundredsToWords(hundred, shouldIncludeAndWordForTens);
                String wordForPower = NumbersMapping.getWordForPower(powersOfTen);
                numberToWordList.add(SonatypeStringUtils.concatWithSepparator(BLANK_SPACE, wordForHundred, wordForPower));
            }
            powersOfTen -= POWER_INCREMENTS; // Decrease the power to find the next word that corresponds to this power of 10..
        }

        // Concat all the parts
        String numberToWords = SonatypeStringUtils.concatWithSepparator(BLANK_SPACE, numberToWordList.toArray(new String[numberToWordList.size()]));

        // Add Negative label if applies
        numberToWords = isNegative ? SonatypeStringUtils.concatWithSepparator(BLANK_SPACE, LABEL_NEGATIVE, numberToWords):numberToWords;

        // First letter of the converted number should be upper-case
        return SonatypeStringUtils.makeFirstLetterUppercase(numberToWords);
    }

    /**
     * Splits the given number into a list of elements of hundreds, for example the number 159663 will result in a list of two elements [159, 663]
     * Another example is the following number 65335 which result in a list of two elements [65, 335]
     * @param value
     * @return
     */
    private List<Integer> splitValueIntoListOfHundreds(long value, boolean ditchSign) {
        String stringValue = Long.toString(value);
        if(stringValue.startsWith("-") && ditchSign) {
            stringValue = stringValue.substring(1, stringValue.length());
        }
        List<Integer> hundreds = new ArrayList<>();
        int i;
        for(i = stringValue.length(); i > 3; i -= 3) {
            int hundredValue = Integer.parseInt(stringValue.substring(i - 3, i));
            hundreds.add(0, hundredValue);
        }
        // We need to add the last first digits of the number and since for them we do not know if they are 1, 2, or 3 digits
        // then we skip them on the loop and just add whatever is left to be added.
        hundreds.add(0, Integer.parseInt(stringValue.substring(0, i)));
        return hundreds;
    }

    /**
     * Receives a number and built its corresponding word by splitting the hundreds, tens and ones of the provided number,
     * it uses the mapping provided by {@link NumbersMapping} to find the mapping for tens and ones,
     * the mapping for hundreds depends on whether or not the number is higher than 99.
     *
     * Allowed range to be used with this method is [0..999] Any value outside this range will cause an exception.
     * @param valueToConvert The value that will be converted to words.
     * @return The String representation of the given value
     * @throws IllegalArgumentException  If the given number is outside of the allowed range.
     */
    public String convertHundredsToWords(int valueToConvert, boolean includeAndWordForTens) {
        // Outside of range
        if(0 > valueToConvert || valueToConvert > 999) {
            throw new IllegalArgumentException(MessageFormat.format(ERROR_ARGUMENT_OUTSIDE_OF_RANGE, valueToConvert));
        }

        // Zero always maps to Zero
        if(valueToConvert == 0) {
            return NumbersMapping.getNumberMappingOrNearest(valueToConvert);
        }

        // Build the word mapping for the hundreds section of the number
        int amountOfHundreds = valueToConvert / 100;
        String wordForHundreds = EMPTY_STRING;
        if(amountOfHundreds > 0) {
            wordForHundreds = NumbersMapping.getNumberMappingOrNearest(amountOfHundreds) + " " + LABEL_HUNDREDS;
        }

        // Build the word mapping for the tens section of the number
        int remainderFromHundreds = valueToConvert % 100;
        if(remainderFromHundreds > 0) {
            String wordForTens = getWordForTens(remainderFromHundreds);
            String separator = !EMPTY_STRING.equals(wordForHundreds) ? (includeAndWordForTens ? AND_CONCATENATOR:BLANK_SPACE):EMPTY_STRING;
            wordForHundreds = SonatypeStringUtils.concatWithSepparator(separator, wordForHundreds, wordForTens);
        }

        return wordForHundreds;
    }

    /**
     * Return the corresponding word for the Tens section of the number.
     * @param value
     * @return
     */
    private String getWordForTens(int value) {
        if(value < 20) { // First 19 numbers in the alphabet have a direct mapping. They are not constructed.
            return NumbersMapping.getNumberMappingOrNearest(value);
        }

        String wordForTens = NumbersMapping.getNumberMappingOrNearest(value);

        int remainderFromTens = value % 10;
        if(remainderFromTens > 0) {
            String wordForOnes = NumbersMapping.getNumberMappingOrNearest(remainderFromTens);
            wordForTens += " " + wordForOnes;
        }

        return wordForTens;
    }


}
