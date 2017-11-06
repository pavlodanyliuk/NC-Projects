package sorters.algorithms;

/**
 *
 * <b>Shell sort</b>
 * <br>For sort an array, using the shell sort
 *
 * @author Pavlo Danyliuk
 *
 * @version 1.0
 */
public class ShellSort extends Sorting {

    /**
     * Constructor of Shell sort, using the {@link Sorting#Sorting(int[]) super constructor}
     *
     * @param array - array to sort
     *
     * */
    public ShellSort(int[] array){
        super(array);
        setName("Shell sort");
    }


    /**
     * Override method for sorting the array
     *
     * @see Sorting#sort()
     * @return
     * */
    @Override
    public void sort() {

        // Start with a big gap, then reduce the gap
        for (int gap = len/2; gap > 0; gap /= 2)
        {
            // Do a gapped insertion sort for this gap size.
            // The first gap elements a[0..gap-1] are already
            // in gapped order keep adding one more element
            // until the entire array is gap sorted
            for (int i = gap; i < len; i += 1)
            {
                // add a[i] to the elements that have been gap
                // sorted save a[i] in temp and make a hole at
                // position i
                int temp = array[i];

                // shift earlier gap-sorted elements up until
                // the correct location for a[i] is found
                int j;
                for (j = i; j >= gap && array[j - gap] > temp; j -= gap)
                    array[j] = array[j - gap];

                // put temp (the original a[i]) in its correct
                // location
                array[j] = temp;
            }
        }
    }

}
