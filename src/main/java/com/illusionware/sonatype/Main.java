package com.illusionware.sonatype;

public class Main {

    public static void main(String[] args) {

        System.out
                .println("Welcome to the Numbers to Words application, please enter the number you want to convert to words."
                        + "\n  * If you need help type HELP."
                        + "\n  * If you want to quit type QUIT\n\n");

        ConsoleProcessor consoleProcessor = new ConsoleProcessor();
        consoleProcessor.processConsoleInput();
    }
}
