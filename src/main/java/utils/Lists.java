package utils;

import java.util.List;
import java.util.Random;

/**
 * The {@code Lists} class provides utility methods for working with lists.
 */
public class Lists {

    private static final Random random = new Random();

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Lists() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Returns a random element from the specified list.
     *
     * @param list the list from which to select a random element.
     * @param random the random number generator to use.
     * @param <T> the type of elements in the list.
     * @return a randomly selected element from the list.
     * @throws IllegalArgumentException if the list is empty.
     */
    public static <T> T getRandomElement(List<T> list, Random random) {
        return list.get(random.nextInt(list.size()));
    }

    /**
     * Returns a random element from the specified list using the default random number generator.
     *
     * @param list the list from which to select a random element.
     * @param <T> the type of elements in the list.
     * @return a randomly selected element from the list.
     * @throws IllegalArgumentException if the list is empty.
     */
    public static <T> T getRandomElement(List<T> list) {
        return getRandomElement(list, random);
    }

}
