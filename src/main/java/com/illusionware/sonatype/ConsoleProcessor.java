package com.illusionware.sonatype;

import com.illusionware.sonatype.service.ConversionService;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * This class takes care of processing of the input from the console.
 */
public class ConsoleProcessor {

    private static final String ERROR_ARGUMENT_OUTSIDE_OF_RANGE = "The number is outside of the allowed range, [Long.MIN_VALUE..Long.MAX_VALUE]";

    private static final String HELP_TEXT = "This application allows you to transform numbers into their corresponding English writing. In order to do this\n"
            + "you will have to provide a valid number within the range [Long.MIN_VALUE..Long.MAX_VALUE], the usage of commas(,) for thousands separator is optional.\n\n"
            + "If you need to see this help again type HELP, or if you want to quit type QUIT.\n\n"
            + "Have fun :)\n\n";

    private final Scanner scanner;

    private final ConversionService conversionService;

    ConsoleProcessor () {
        scanner = new Scanner(System.in);
        conversionService = new ConversionService();
    }

    /**
     * Initiates a constant processing of the inputs received by the scanner.
     */
    public void processConsoleInput() {
        while(scanner.hasNextLine()) {
            String inputValue = scanner.nextLine();
            Commands command = Commands.fromString(inputValue);
            if(command != null) {
                processCommand(command);
            } else {
                processInput(inputValue);
            }
        }
    }

    /**
     * Process the command that the user input into the application.
     * @param command The command the user executed.
     */
    private void processCommand(Commands command) {
        switch (command) {
            case HELP: {
                System.out.println(HELP_TEXT);
                break;
            }
            case QUIT: {
                System.out.println("\n\nBye, it was fun playing with you.");
                System.exit(0);
                break;
            }
            default: {
                throw new RuntimeException("Unhandled command " + command);
            }
        }
    }

    /**
     * Process any input from the user different than a command.
     * @param inputValue
     */
    private void processInput(String inputValue) {

        if(!validateInput(inputValue)) {
            return;
        }

        // Store the value in a BigInteger in case it is larger than the allowed range.
        BigInteger bigInteger = new BigInteger(inputValue.replace(",", ""));
        if(0 > bigInteger.compareTo(BigInteger.valueOf(Long.MIN_VALUE))
                || bigInteger.compareTo(BigInteger.valueOf(Long.MAX_VALUE)) > 0) {
            System.out.println(ERROR_ARGUMENT_OUTSIDE_OF_RANGE);
            return;
        }

        long valueToBeConverted = bigInteger.longValue();
        System.out.println(conversionService.convertNumberToWords(valueToBeConverted));


    }

    /**
     * Validates if the inputValue
     * @param inputValue
     * @return
     */
    private boolean validateInput(String inputValue) {
        boolean matchesAllowedInput = inputValue.matches("^-?[0-9]{1,3}(,*[0-9]{3})*$");
        if(!matchesAllowedInput) {
            System.out.println("Input not allowed, please use only numbers either with thousands separator or without. Remember that the valid separator is the comma(,)");
            return false;
        }
        return true;
    }

}
