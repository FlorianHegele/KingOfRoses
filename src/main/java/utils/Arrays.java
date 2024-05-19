package utils;

import java.util.Random;

/**
 * The {@code Arrays} class provides utility methods for array manipulation.
 */
public class Arrays {

    private static final Random random = new Random();

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Arrays() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Swaps the elements at the specified indices in the given array.
     *
     * @param <T> the type of elements in the array.
     * @param array the array in which to swap elements.
     * @param i the index of the first element to be swapped.
     * @param j the index of the second element to be swapped.
     */
    private static <T> void swapArrayIndex(T[] array, int i, int j) {
        final T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * Shuffles the elements in the specified array using the default random number generator.
     *
     * @param <T> the type of elements in the array.
     * @param array the array to be shuffled.
     */
    public static <T> void shuffleArray(T[] array) {
        shuffleArray(array, random);
    }

    /**
     * Shuffles the elements in the specified array using the specified random number generator.
     *
     * @param <T> the type of elements in the array.
     * @param array the array to be shuffled.
     * @param random the random number generator to use for shuffling.
     */
    public static <T> void shuffleArray(T[] array, Random random) {
        final int length = array.length;
        for (int i = 0; i < length; i++) {
            final int randomIndex = random.nextInt(length);
            swapArrayIndex(array, i, randomIndex);
        }
    }

}
