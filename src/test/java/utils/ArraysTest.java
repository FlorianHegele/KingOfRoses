package utils;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class ArraysTest {

    @Test
    void testShuffleArray() {
        final Random random = Mockito.mock(Random.class);
        Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(1, 3, 2, 1);

        final Integer[] array = {0, 1, 2, 3};
        Arrays.shuffleArray(array, random);

        assertArrayEquals(new Integer[]{1, 0, 2, 3}, array);
    }
    
}
