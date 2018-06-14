package com.illusionware.sonatype.data;

/**
 * Provides mapping for the ones and tens sections of the English numbers for representing full numbers in English.
 * Keep in mind that the first 20 numbers have specific words, from 0 to 19, then starting from 20 and every 10 numbers up to 90 there is a different word for each of them,
 * it is possible to build all the numbers from 0 to 99 by finding the corresponding mapping for the tens and ones for the number parts.
 */
public interface NumbersMapper {

    /**
     * Finds the word that maps to the given number or the nearest one whose key is lower than the provided number to be mapped.
     * The function will only work for number from 0 to 99, if any number outside of this range.
     * @param numberToBeMapped The number to which we want to find the nearest mapping.
     * @return String value of the nearest mapping for a number or null if the number doesn't have a nearest mapping.
     * @throws IllegalArgumentException If the given number is outside of the allowed values
     */
    String getNumberMappingOrNearest(int numberToBeMapped);

    /**
     * Returns the English word associated with a given power of ten.
     * @param power
     * @return
     */
    String getWordForPower(int power);
}
