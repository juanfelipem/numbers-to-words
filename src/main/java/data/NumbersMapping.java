package data;

import java.text.MessageFormat;
import java.util.NavigableMap;
import java.util.TreeMap;

public class NumbersMapping {

    private static final NavigableMap<Integer, String> numbersMapping;

    private static final String ERROR_ARGUMENT_OUTSIDE_OF_RANGE = "The argument for the function is outside of the allowed range [0..99], provided value: {0}";

    static {
        numbersMapping = new TreeMap<>();
        numbersMapping.put(0, "zero");
        numbersMapping.put(1, "one");
        numbersMapping.put(2, "two");
        numbersMapping.put(3, "three");
        numbersMapping.put(4, "four");
        numbersMapping.put(5, "five");
        numbersMapping.put(6, "six");
        numbersMapping.put(7, "seven");
        numbersMapping.put(8, "eight");
        numbersMapping.put(9, "nine");
        numbersMapping.put(10, "ten");
        numbersMapping.put(11, "eleven");
        numbersMapping.put(12, "twelve");
        numbersMapping.put(13, "thirteen");
        numbersMapping.put(14, "fourteen");
        numbersMapping.put(15, "fifteen");
        numbersMapping.put(16, "sixteen");
        numbersMapping.put(17, "seventeen");
        numbersMapping.put(18, "eighteen");
        numbersMapping.put(19, "nineteen");
        numbersMapping.put(20, "twenty");
        numbersMapping.put(30, "thirty");
        numbersMapping.put(40, "forty");
        numbersMapping.put(50, "fifty");
        numbersMapping.put(60, "sixty");
        numbersMapping.put(70, "seventy");
        numbersMapping.put(80, "eighty");
        numbersMapping.put(90, "ninety");
    }

    /**
     * Finds the word that maps to the given number or the nearest one whose key is lower than the provided number to be mapped.
     * The function will only work for number from 0 to 99, if any number outside of this range.
     * @param numberToBeMapped The number to which we want to find the nearest mapping.
     * @return String value of the nearest mapping for a number or null if the number doesn't have a nearest mapping.
     * @throws IllegalArgumentException If the given number is outside of the allowed values
     */
    public static String getNumberMappingOrNearest(int numberToBeMapped) {
        if(0 > numberToBeMapped || numberToBeMapped > 99) {
            throw new IllegalArgumentException(MessageFormat.format(ERROR_ARGUMENT_OUTSIDE_OF_RANGE, numberToBeMapped));
        }
        return numbersMapping.floorEntry(numberToBeMapped).getValue();
    }

}
