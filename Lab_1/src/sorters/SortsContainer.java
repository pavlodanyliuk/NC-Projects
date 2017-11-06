package sorters;

import sorters.algorithms.*;
import sorters.algorithms.bubblepackage.BubbleDirectSort;
import sorters.algorithms.bubblepackage.BubbleReverseSort;

import java.util.Arrays;

public class SortsContainer {

    private SortsContainer(){
       super();
    }

    public static int[] bubbleDirectSort(int[] array){
        BubbleDirectSort bubbleSort = new BubbleDirectSort(array);

        return startSort(bubbleSort);
    }

    public static int[] bubbleReverseSort(int[] array) {
        BubbleReverseSort bubbleReverseSort = new BubbleReverseSort(array);

        return startSort(bubbleReverseSort);
    }
    public static int[] mergeSort(int[] array){
        MergeSort mergeSort = new MergeSort(array);

        return startSort(mergeSort);
    }

    public static int[] shellSort(int[] array){
        ShellSort shellSort = new ShellSort(array);

        return startSort(shellSort);
    }

    public static int[] quickSort(int[] array){
        QuickSort quickSort = new QuickSort(array);

        return startSort(quickSort);
    }

    public static int[] arraysSort(int[] array){
        int[] newArray = new int[array.length];
        System.arraycopy(array, 0, newArray, 0, array.length);
        System.out.println("Arrays.sort(int[] arr):");
        printArray(newArray);
        System.out.println("(before)");

        Arrays.sort(newArray);

        printArray(newArray);
        System.out.println("(after)");
        return newArray;
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
