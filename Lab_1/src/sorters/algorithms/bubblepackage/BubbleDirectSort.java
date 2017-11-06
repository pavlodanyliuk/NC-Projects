package sorters.algorithms.bubblepackage;

import sorters.algorithms.Sorting;

public class BubbleDirectSort extends BubbleSort {
    public BubbleDirectSort(int[] array){
        super(array);
        setName("Bubble Direct Sort");
    }


    @Override
    protected void check(int j) {
        if (array[j - 1] > array[j]) {
            //swap elements
            swap(j, j - 1);
        }
    }
}