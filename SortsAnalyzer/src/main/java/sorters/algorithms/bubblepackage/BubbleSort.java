package sorters.algorithms.bubblepackage;

import sorters.algorithms.Sorting;

/**
 * <b>Bubbles abstract class</b>
 * <br>For any kind of bubble sorters
 *
 * @author Pavlo Danyliuk
 *
 * @version 1.0
 *
 * @see sorters.algorithms.Sorting
 */
public abstract class  BubbleSort extends Sorting {

    public BubbleSort(int[] array){
        super(array);
    }

    /**
     * Override method for sorting the array
     *
     * @see Sorting#sort()
     * @return
     * */
    @Override
    public void sort() {
        for (int i = 0; i < len; i++) {
            for (int j = 1; j < (len - i); j++) {
                check(j);
            }
        }
    }
    protected abstract void check(int j);
}
