package utils;

import java.util.List;
import java.util.Random;

public class Lists {

    private static final Random random = new Random();

    private Lists() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> T getRandomElement(List<T> list, Random random) {
        return list.get(random.nextInt(list.size()));
    }

    public static <T> T getRandomElement(List<T> list) {
        return getRandomElement(list, random);
    }

}
