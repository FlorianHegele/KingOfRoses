package utils;

import java.util.Random;

public class Arrays {

    private static final Random random = new Random();

    private static <T> void swapArrayIndex(T[] array, int i, int j) {
        final T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static <T> void shuffleArray(T[] array) {
        final int length = array.length;
        for(int i = 0; i < length; i++) {
            final int randomIndex = random.nextInt(length);
            swapArrayIndex(array, i, randomIndex);
        }
    }

}
