package main;

import sorters.SortsContainer;

public class Main {
    public static void main(String[] args) {
        int[] arr = { 4, 8, -5, 75, 12, 45, 1, 79, -10, 0 , 2, 0,12};
        SortsContainer.bubbleSort(arr);
        SortsContainer.bubbleReverseSort(arr);
        SortsContainer.mergeSort(arr);
        SortsContainer.quickSort(arr);
        SortsContainer.shellSort(arr);
        SortsContainer.arraysSort(arr);
    }
}
