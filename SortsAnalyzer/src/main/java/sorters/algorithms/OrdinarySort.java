package sorters.algorithms;

import java.util.Arrays;

/**
 *
 * <b>Ordinary sort</b>
 * <br>For sort an array, using the {@code Arrays.sort(int[] arr)}
 *
 * @author Pavlo Danyliuk
 *
 * @version 1.0
 *
 * @see Sorting
 */

public class OrdinarySort extends Sorting {

    /**
     * Constructor for creating a child object with array
     *
     * @param array array to sort
     */
    public OrdinarySort(int[] array) {
        super(array);
        setName("Ordinary Sort");
    }

    /**
     * Override method for sorting the array
     *
     * @see Sorting#sort()
     * @return
     * */
    @Override
    public void sort() {
        Arrays.sort(array);
    }
}
