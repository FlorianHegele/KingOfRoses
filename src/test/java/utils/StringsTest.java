package utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringsTest {

    @Test
    void testParseInt_validString() {
        assertEquals(123, Strings.parseInt("123"));
    }

    @Test
    void testParseInt_invalidString() {
        assertEquals(0, Strings.parseInt("abc"));
    }

    @Test
    void testParseInt_withDefault_validString() {
        assertEquals(123, Strings.parseInt("123", 456));
    }

    @Test
    void testParseInt_withDefault_invalidString() {
        assertEquals(456, Strings.parseInt("abc", 456));
    }

    @Test
    void testParseLong_validString() {
        assertEquals(123456789L, Strings.parseLong("123456789"));
    }

    @Test
    void testParseLong_invalidString() {
        assertEquals(0L, Strings.parseLong("abc"));
    }

    @Test
    void testParseLong_withDefault_validString() {
        assertEquals(123456789L, Strings.parseLong("123456789", 987654321L));
    }

    @Test
    void testParseLong_withDefault_invalidString() {
        assertEquals(987654321L, Strings.parseLong("abc", 987654321L));
    }

    @Test
    void testParseBoolean_trueString() {
        assertTrue(Strings.parseBoolean("true"));
    }

    @Test
    void testParseBoolean_falseString() {
        assertFalse(Strings.parseBoolean("false"));
    }

    @Test
    void testParseBoolean_invalidString() {
        assertFalse(Strings.parseBoolean("abc"));
    }

    @Test
    void testParseBoolean_withDefault_trueString() {
        assertTrue(Strings.parseBoolean("true", false));
    }

    @Test
    void testParseBoolean_withDefault_falseString() {
        assertFalse(Strings.parseBoolean("false", true));
    }

    @Test
    void testParseBoolean_withDefault_invalidString() {
        assertFalse(Strings.parseBoolean("abc", true));
    }

    @Test
    void testParseInt_array_validIndex() {
        String[] strings = {"100", "200", "300"};
        assertEquals(200, Strings.parseInt(strings, 1));
    }

    @Test
    void testParseInt_array_invalidIndex() {
        String[] strings = {"100", "200", "300"};
        assertEquals(0, Strings.parseInt(strings, 3));
    }

    @Test
    void testParseInt_array_withDefault_validIndex() {
        String[] strings = {"100", "200", "300"};
        assertEquals(200, Strings.parseInt(strings, 1, 400));
    }

    @Test
    void testParseInt_array_withDefault_invalidIndex() {
        String[] strings = {"100", "200", "300"};
        assertEquals(400, Strings.parseInt(strings, 3, 400));
    }

    @Test
    void testParseLong_array_validIndex() {
        String[] strings = {"10000000000", "20000000000", "30000000000"};
        assertEquals(20000000000L, Strings.parseLong(strings, 1));
    }

    @Test
    void testParseLong_array_invalidIndex() {
        String[] strings = {"10000000000", "20000000000", "30000000000"};
        assertEquals(0L, Strings.parseLong(strings, 3));
    }

    @Test
    void testParseLong_array_withDefault_validIndex() {
        String[] strings = {"10000000000", "20000000000", "30000000000"};
        assertEquals(20000000000L, Strings.parseLong(strings, 1, 40000000000L));
    }

    @Test
    void testParseLong_array_withDefault_invalidIndex() {
        String[] strings = {"10000000000", "20000000000", "30000000000"};
        assertEquals(40000000000L, Strings.parseLong(strings, 3, 40000000000L));
    }

    @Test
    void testParseBoolean_array_validIndex() {
        String[] strings = {"true", "false", "true"};
        assertTrue(Strings.parseBoolean(strings, 0));
    }

    @Test
    void testParseBoolean_array_invalidIndex() {
        String[] strings = {"true", "false", "true"};
        assertFalse(Strings.parseBoolean(strings, 3));
    }

    @Test
    void testParseBoolean_array_withDefault_validIndex() {
        String[] strings = {"true", "false", "true"};
        assertFalse(Strings.parseBoolean(strings, 1, true));
    }

    @Test
    void testParseBoolean_array_withDefault_invalidIndex() {
        String[] strings = {"true", "false", "true"};
        assertTrue(Strings.parseBoolean(strings, 3, true));
    }

    @Test
    void testJoin() {
        String[] strings = {"Hello", "World", "!"};
        assertEquals("Hello\nWorld\n!", Strings.join(strings));
    }
}
