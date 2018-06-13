package com.illusionware.sonatype.util;

import java.util.StringJoiner;

public class SonatypeStringUtils {

    private static final String EMPTY_STRING = "";

    public static String concatWithSepparator(String separator, String ... elements) {
        StringJoiner joiner = new StringJoiner(separator);
        for(int i = 0; i < elements.length; i++) {
            if(!EMPTY_STRING.equals(elements[i])) {
                joiner.add(elements[i]);
            }
        }
        return joiner.toString();
    }

    /**
     * Changes the first letter of the word to be upper-case
     * @param originalString The string to which we will upper-case the first letter.
     * @return
     */
    public static String makeFirstLetterUppercase(String originalString) {
        return originalString.substring(0,1).toUpperCase() +  originalString.substring(1, originalString.length());
    }

}
