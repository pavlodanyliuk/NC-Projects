package main;

import fillers.Filler;
import sorters.SortsContainer;

public class Main {
    public static void main(String[] args) {
//        int[] arr = { 4, 8, -5, 75, 12, 45, 1, 79, -10, 0 , 2, 0,12};
//        SortsContainer.bubbleSort(arr);
//        SortsContainer.bubbleReverseSort(arr);
//        SortsContainer.mergeSort(arr);
//        SortsContainer.quickSort(arr);
//        SortsContainer.shellSort(arr);
//        SortsContainer.arraysSort(arr);
//

        int[] arr = Filler.genSortedArray(10,0,20);
        for(int i : arr){
            System.out.print(i + " ");
        }
        System.out.println();

        int[] arr1 = Filler.genSortedArray(10,0,20);
        for(int i : arr1){
            System.out.print(i + " ");
        }
        System.out.println();

    }
}
