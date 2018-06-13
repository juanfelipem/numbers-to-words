package com.illusionware.sonatype.service;

import com.illusionware.sonatype.data.NumbersMapping;
import com.illusionware.sonatype.util.StringUtil;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This service provides functionality to allow the conversion of numbers to words.
 *
 */
public class ConversionService {

    private static final String ERROR_ARGUMENT_OUTSIDE_OF_RANGE = "The argument for the function is outside of the allowed range [0..999], provided value: {0}";
    private static final String LABEL_HUNDREDS = "hundred";
    private static final String EMPTY_STRING = "";

    public String convertNumberToWords(long valueToConvert) {
        if(valueToConvert == 0) {
            String wordForZero = NumbersMapping.getNumberMappingOrNearest((int) valueToConvert); // No risk of losing precision since the number is 0
            return wordForZero.substring(0,1).toUpperCase() +  wordForZero.substring(1, wordForZero.length());
        }

        List<Integer> splitsOfHundreds = splitValueIntoListOfHundreds(valueToConvert);
        int power = 0;
        List<String> numberToWordList = new ArrayList<>();
        for (int hundred: splitsOfHundreds) {
            boolean shouldIncludeAndWordForTens = power == 0; // Only the first hundred value will have the "and" between the hundred and the tens.

            if(hundred > 0) { // Sections of hundreds equal to 0 don't go in the word.
                String wordForHundred = convertHundredsToWords(hundred, shouldIncludeAndWordForTens);
                String wordForPower = NumbersMapping.getWordForPower(power);
                numberToWordList.add(0, StringUtil.concatWithSepparator(" ", wordForHundred, wordForPower));
            }
            power += 3; // Increase the power of the hundred value since each three power the word changes.
        }

        String numberToWords = StringUtil.concatWithSepparator(" ", numberToWordList.toArray(new String[numberToWordList.size()]));
        return numberToWords.substring(0,1).toUpperCase() +  numberToWords.substring(1, numberToWords.length()); // First letter must be capitalized.
    }

    /**
     * Splits the given number into a list of elements of hundreds, for example the number 159663 will result in a list of two elements [663, 159]
     * Another example is the following number 65335 which result in a list of two elements [335, 65]
     * @param value
     * @return
     */
    private List<Integer> splitValueIntoListOfHundreds(long value) {
        String stringValue = Long.toString(value);
        List<Integer> hundreds = new ArrayList<>();
        int i;
        for(i = stringValue.length(); i > 3; i -= 3) {
            int hundredValue = Integer.parseInt(stringValue.substring(i - 3, i));
            hundreds.add(hundredValue);
        }
        // We need to add the last first digits of the number and since for them we do not know if they are 1, 2, or 3 digits
        // then we skip them on the loop and just add whatever is left to be added.
        hundreds.add(Integer.parseInt(stringValue.substring(0, i)));
        return hundreds;
    }

    /**
     * Receives a number and built its corresponding word by splitting the hundreds, tens and ones of the provided number,
     * it uses the mapping provided by {@link com.illusionware.sonatype.data.NumbersMapping} to find the mapping for tens and ones,
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
            String separator = !EMPTY_STRING.equals(wordForHundreds) ? (includeAndWordForTens ? " and ":" "):EMPTY_STRING;
            wordForHundreds += separator + wordForTens;
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
