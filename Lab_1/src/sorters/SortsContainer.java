package sorters;

import sorters.algorithms.*;

import java.util.Arrays;

public class SortsContainer {

    private SortsContainer(){
       super();
    }

    public static void bubbleSort(int[] array){
        BubbleSort bubbleSort = new BubbleSort(array);

        startSort(bubbleSort);
    }

    public static void bubbleReverseSort(int[] array) {
        BubbleReverseSort bubbleReverseSort = new BubbleReverseSort(array);

        startSort(bubbleReverseSort);
    }
    public static void mergeSort(int[] array){
        MergeSort mergeSort = new MergeSort(array);

        startSort(mergeSort);
    }

    public static void shellSort(int[] array){
        ShellSort shellSort = new ShellSort(array);

        startSort(shellSort);
    }

    public static void quickSort(int[] array){
        QuickSort quickSort = new QuickSort(array);

        startSort(quickSort);
    }

    public static void arraysSort(int[] array){
        System.out.println("Arrays.sort(int[] arr):");
        printArray(array);
        System.out.println("(before)");

        Arrays.sort(array);

        printArray(array);
        System.out.println("(after)");
    }



    private static void startSort(Sorting sorting){
        System.out.println(sorting + "(before)");
        sorting.sort();
        System.out.println(sorting +  "(after)");
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
