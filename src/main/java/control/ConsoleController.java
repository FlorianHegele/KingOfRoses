package control;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConsoleController {

    public static final boolean DEFAULT_SKIP_TEST = false;

    private final BufferedReader consoleIn;
    private final boolean skipCheck;

    public ConsoleController(boolean skipCheck) {
        this.consoleIn = new BufferedReader(new InputStreamReader(System.in));
        this.skipCheck = skipCheck;
    }

    public ConsoleController() {
        this(DEFAULT_SKIP_TEST);
    }

    public String getConsoleLine() {
        try {
            return consoleIn.readLine();
        } catch (Exception e) {
            return "";
        }
    }

    public String getCheckConsoleLine() {
        return (skipCheck) ? "" : getConsoleLine();
    }

    public void printlnCheckMessage(String message) {
        if (!skipCheck) System.out.println(message);
    }

    public void printlnCheckMessage() {
        if (!skipCheck) System.out.println();
    }

    public void printCheckMessage(String message) {
        if (!skipCheck) System.out.print(message);
    }

}
