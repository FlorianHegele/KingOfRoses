package utils;

public class Strings {

    private Strings() {
        throw new IllegalStateException("Utility class");
    }

    public static int parseInt(String str, int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static long parseLong(String str, long defaultValue) {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static boolean parseBoolean(String str, boolean defaultValue) {
        try {
            return Boolean.parseBoolean(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static int parseInt(String[] strings, int index, int defaultValue) {
        try {
            return parseInt(strings[index], defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static long parseLong(String[] strings, int index, long defaultValue) {
        try {
            return parseLong(strings[index], defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static boolean parseBoolean(String[] strings, int index, boolean defaultValue) {
        try {
            return parseBoolean(strings[index], defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static int parseInt(String str) {
        return parseInt(str, 0);
    }
    public static long parseLong(String str) {
        return parseLong(str, 0);
    }
    public static boolean parseBoolean(String str) {
        return parseBoolean(str, false);
    }

    public static int parseInt(String[] strings, int index) {
        return parseInt(strings, index, 0);
    }
    public static long parseLong(String[] strings, int index) {
        return parseLong(strings, index, 0);
    }
    public static boolean parseBoolean(String[] strings, int index) {
        return parseBoolean(strings, index, false);
    }

}
