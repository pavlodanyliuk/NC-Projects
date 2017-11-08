package sorters.algorithms.bubblepackage;

/**
 * <b>Bubble Direct Sort </b>
 * <br>For sort an array, using the direct bubble sort
 *
 * @author Pavlo Danyliuk
 *
 * @version 1.0
 *
 * @see sorters.algorithms.Sorting
 */

public class BubbleDirectSort extends BubbleSort {

    /**
     * Constructor of Bubble sort, using the {@link BubbleSort#BubbleSort(int[]) super constructor}
     *
     * @param array - array to sort
     *
     * */
    public BubbleDirectSort(int[] array){
        super(array);
        setName("Bubble Direct Sort");
    }

    /**
     * Override method for abstract class
     *
     * @see BubbleSort#check(int)
     * @return
     * */
    @Override
    protected void check(int j) {
        if (array[j - 1] > array[j]) {
            //swap elements
            swap(j, j - 1);
        }
    }
}