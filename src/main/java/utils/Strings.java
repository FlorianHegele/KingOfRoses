package utils;

/**
 * The {@code Strings} class provides utility methods for parsing strings to primitive types.
 */
public class Strings {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Strings() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Parses the specified string into an integer.
     * If parsing fails, returns the specified default value.
     *
     * @param str          the string to parse.
     * @param defaultValue the default value to return if parsing fails.
     * @return the parsed integer value or the default value if parsing fails.
     */
    public static int parseInt(String str, int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Parses the specified string into a long.
     * If parsing fails, returns the specified default value.
     *
     * @param str          the string to parse.
     * @param defaultValue the default value to return if parsing fails.
     * @return the parsed long value or the default value if parsing fails.
     */
    public static long parseLong(String str, long defaultValue) {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Parses the specified string into a boolean.
     * If parsing fails, returns the specified default value.
     *
     * @param str          the string to parse.
     * @param defaultValue the default value to return if parsing fails.
     * @return the parsed boolean value or the default value if parsing fails.
     */
    public static boolean parseBoolean(String str, boolean defaultValue) {
        try {
            return Boolean.parseBoolean(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }


    /**
     * Parses the string at the specified index in the array into an integer.
     * If parsing fails or the index is out of bounds, returns the specified default value.
     *
     * @param strings      the array of strings.
     * @param index        the index of the string to parse.
     * @param defaultValue the default value to return if parsing fails or index is out of bounds.
     * @return the parsed integer value or the default value if parsing fails or index is out of bounds.
     */
    public static int parseInt(String[] strings, int index, int defaultValue) {
        try {
            return parseInt(strings[index], defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Parses the string at the specified index in the array into an integer.
     * If parsing fails or the index is out of bounds, returns the specified default value.
     *
     * @param strings      the array of strings.
     * @param index        the index of the string to parse.
     * @param defaultValue the default value to return if parsing fails or index is out of bounds.
     * @return the parsed long value or the default value if parsing fails or index is out of bounds.
     */
    public static long parseLong(String[] strings, int index, long defaultValue) {
        try {
            return parseLong(strings[index], defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Parses the string at the specified index in the array into an integer.
     * If parsing fails or the index is out of bounds, returns the specified default value.
     *
     * @param strings      the array of strings.
     * @param index        the index of the string to parse.
     * @param defaultValue the default value to return if parsing fails or index is out of bounds.
     * @return the parsed boolean value or the default value if parsing fails or index is out of bounds.
     */
    public static boolean parseBoolean(String[] strings, int index, boolean defaultValue) {
        try {
            return parseBoolean(strings[index], defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Parses the specified string into a boolean.
     * If parsing fails, returns the specified default value.
     *
     * @param str the string to parse.
     * @return the parsed int value or 0 if parsing fails.
     */
    public static int parseInt(String str) {
        return parseInt(str, 0);
    }

    /**
     * Parses the specified string into a boolean.
     * If parsing fails, returns the specified default value.
     *
     * @param str the string to parse.
     * @return the parsed long value or 0 if parsing fails.
     */
    public static long parseLong(String str) {
        return parseLong(str, 0);
    }

    /**
     * Parses the specified string into a boolean.
     * If parsing fails, returns the specified default value.
     *
     * @param str the string to parse.
     * @return the parsed int boolean or false if parsing fails.
     */
    public static boolean parseBoolean(String str) {
        return parseBoolean(str, false);
    }


    /**
     * Parses the string at the specified index in the array into a long.
     * If parsing fails or the index is out of bounds, returns zero.
     *
     * @param strings the array of strings.
     * @param index   the index of the string to parse.
     * @return the parsed int value or zero if parsing fails or index is out of bounds.
     */
    public static int parseInt(String[] strings, int index) {
        return parseInt(strings, index, 0);
    }

    /**
     * Parses the string at the specified index in the array into a long.
     * If parsing fails or the index is out of bounds, returns zero.
     *
     * @param strings the array of strings.
     * @param index   the index of the string to parse.
     * @return the parsed long value or zero if parsing fails or index is out of bounds.
     */
    public static long parseLong(String[] strings, int index) {
        return parseLong(strings, index, 0);
    }

    /**
     * Parses the string at the specified index in the array into a boolean.
     * If parsing fails or the index is out of bounds, returns zero.
     *
     * @param strings the array of strings.
     * @param index   the index of the string to parse.
     * @return the parsed boolean value or false if parsing fails or index is out of bounds.
     */
    public static boolean parseBoolean(String[] strings, int index) {
        return parseBoolean(strings, index, false);
    }

}
