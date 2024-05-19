package control;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * This class provides methods to interact with the console for input and output.
 * It supports skipping input checks and providing default values.
 */
public class ConsoleController {

    /**
     * A flag indicating whether to skip input checks and provide default values.
     */
    private static final boolean DEFAULT_SKIP_TEST = false;

    private final BufferedReader consoleIn;
    private final boolean skipCheck;

    /**
     * Constructs a ConsoleController with the specified skip check flag.
     *
     * @param skipCheck if true, input checks are skipped and default values are used.
     */
    public ConsoleController(boolean skipCheck) {
        this.consoleIn = new BufferedReader(new InputStreamReader(System.in));
        this.skipCheck = skipCheck;
    }

    /**
     * Constructs a ConsoleController with the default skip check flag.
     */
    public ConsoleController() {
        this(DEFAULT_SKIP_TEST);
    }

    /**
     * Reads a line of text from the console.
     *
     * @return the line read from the console, or an empty string if an error occurs.
     */
    public String getConsoleLine() {
        try {
            return consoleIn.readLine();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Reads a line of text from the console if skipCheck is false.
     *
     * @return the line read from the console if skipCheck is false, or an empty string otherwise.
     */
    public String getCheckConsoleLine() {
        return (skipCheck) ? "" : getConsoleLine();
    }

    /**
     * Prints a message to the console if skipCheck is false.
     *
     * @param message the message to print.
     */
    public void printlnCheckMessage(String message) {
        if (!skipCheck) System.out.println(message);
    }

    /**
     * Prints an empty line to the console if skipCheck is false.
     */
    public void printlnCheckMessage() {
        if (!skipCheck) System.out.println();
    }

    /**
     * Prints a message to the console without a newline if skipCheck is false.
     *
     * @param message the message to print.
     */
    public void printCheckMessage(String message) {
        if (!skipCheck) System.out.print(message);
    }

}
