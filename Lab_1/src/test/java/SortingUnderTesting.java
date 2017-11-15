import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sorters.algorithms.*;
import sorters.algorithms.bubblepackage.BubbleDirectSort;
import sorters.algorithms.bubblepackage.BubbleReverseSort;

import java.util.Arrays;

public class SortingUnderTesting {
    private  int[] arr;
    private  int[] sortedArr;
    private Sorting sorting;

    @Before
    public void setUp(){
        arr = new int[]{14, -5, 10, 190, 76, 14444, -14444, 0 , 10, -10, 0, 10000};
        sortedArr = new int[arr.length];

        System.arraycopy(arr, 0, sortedArr, 0, arr.length);
        Arrays.sort(sortedArr);
    }

    @Test

    public void nullArgTest(){
        sorting = new MergeSort(null);
        sorting.sort();
    }

    @Test

    public void mergeSortTest(){
        sorting = new MergeSort(arr);
        startSorting();

    }


    @Test

    public void bubbleDirectSortTest(){
        sorting = new BubbleDirectSort(arr);
        startSorting();
    }


    @Test

    public void bubbleReverseSortTest(){
        sorting = new BubbleReverseSort(arr);
        startSorting();
    }


    @Test

    public void ordinarySortTest(){
        sorting = new OrdinarySort(arr);
        startSorting();
    }


    @Test

    public void quickSortTest(){
        sorting = new QuickSort(arr);
        startSorting();
    }


    @Test

    public void shellSortTest(){
        sorting = new ShellSort(arr);
        startSorting();
    }



    private void startSorting(){
        sorting.sort();

        Assert.assertArrayEquals(sorting.getArray(), sortedArr);
    }




}