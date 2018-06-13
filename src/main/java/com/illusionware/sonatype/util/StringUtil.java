package com.illusionware.sonatype.util;

import java.util.StringJoiner;

public class StringUtil {

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

}
