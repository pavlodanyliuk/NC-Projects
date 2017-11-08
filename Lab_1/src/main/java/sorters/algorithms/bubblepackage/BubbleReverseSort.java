package sorters.algorithms.bubblepackage;

/**
 * <b>Bubble reverse sort </b>
 * <br>For sort an array, using the bubble reverse sort
 *
 * @author Pavlo Danyliuk
 *
 * @version 1.0
 *
 * @see sorters.algorithms.Sorting
 */
public class BubbleReverseSort extends BubbleSort {

    /**
     * Constructor of Bubble sort, using the {@link BubbleSort#BubbleSort(int[]) super constructor}
     *
     * @param array - array to sort
     *
     * */
    public BubbleReverseSort(int[] array){
        super(array);
        setName("Bubble Reverse Sort");
    }

    /**
     * Override method for abstract class
     *
     * @see BubbleSort#check(int)
     * @return
     * */
    @Override
    protected void check(int j) {
        if (array[len - j] < array[len - j - 1]) {
            //swap elements
            swap(len - j, len - j - 1);
        }
    }
}
