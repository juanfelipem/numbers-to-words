package com.illusionware.sonatype;

/**
 * Available commands for the application.
 */
public enum Commands {
    QUIT,
    HELP;

    /**
     * Returns the command that corresponds to the string value provided or null if none matches.
     * @param stringRepresentation
     * @return
     */
    public static Commands fromString(String stringRepresentation) {
        try {
            return valueOf(stringRepresentation);
        } catch(IllegalArgumentException e) {
            return null;
        }
    }

}