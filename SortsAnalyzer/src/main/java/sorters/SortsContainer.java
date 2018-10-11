package sorters;

import sorters.algorithms.*;
import sorters.algorithms.bubblepackage.BubbleDirectSort;
import sorters.algorithms.bubblepackage.BubbleReverseSort;

/**
 *Class contains all methods of sorting
 *
 * @author Pavlo Danyliuk
 *
 * @version 1.0
 * @see Sorting
 */

public class SortsContainer {

    private SortsContainer(){
       super();
    }

    /**
     * Bubble direct sort
     * @param array
     * @return
     * @see BubbleDirectSort
     * @see Sorting
     */
    public static int[] bubbleDirectSort(int[] array){
        BubbleDirectSort bubbleSort = new BubbleDirectSort(array);

        return startSort(bubbleSort);
    }

    /**
     * Bubble reverse sort
     * @param array
     * @return
     * @see BubbleReverseSort
     * @see Sorting
     */
    public static int[] bubbleReverseSort(int[] array) {
        BubbleReverseSort bubbleReverseSort = new BubbleReverseSort(array);

        return startSort(bubbleReverseSort);
    }

    /**
     * Merge sort
     * @param array
     * @return
     * @see MergeSort
     * @see Sorting
     */
    public static int[] mergeSort(int[] array){
        MergeSort mergeSort = new MergeSort(array);

        return startSort(mergeSort);
    }

    /**
     * Shell sort
     * @param array
     * @return
     * @see ShellSort
     * @see Sorting
     */
    public static int[] shellSort(int[] array){
        ShellSort shellSort = new ShellSort(array);

        return startSort(shellSort);
    }

    /**
     * Quick sort
     * @param array
     * @return
     * @see QuickSort
     * @see Sorting
     */
    public static int[] quickSort(int[] array){
        QuickSort quickSort = new QuickSort(array);

        return startSort(quickSort);
    }

    /**
     * Ordinary sort
     * @param array
     * @return
     * @see OrdinarySort
     * @see Sorting
     */
    public static int[] ordinarySort(int[] array){
        OrdinarySort ordinarySort = new OrdinarySort(array);

        return startSort(ordinarySort);
    }


    private static int[] startSort(Sorting sorting){
        System.out.println(sorting + "(before)");

        sorting.sort();

        System.out.println(sorting +  "(after)");
        return sorting.getArray();
    }

    private static void printArray(int[] arr){
        StringBuilder str = new StringBuilder("");
        str.append("[ ");
        for(int i : arr){
            str.append(i + " ");
        }
        str.append("]");
        System.out.print(str);
    }
}
