import fillers.Filler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FillersUnderTesting {
    private int len;
    private int minVal;
    private int maxVal;
    private int[] arr;

    @Before
    public void setUp(){
        len = 100;
        minVal = -100;
        maxVal = 50;
    }

    @Test
    public void sortedArrayTest(){

        //equals lens
        arr = Filler.genSortedArray(len, minVal, maxVal);

        assertEquals("lengths of array is not equals", len, arr.length);


        //all element in interval
        for(int i : arr){
            assertTrue("element " + i + " is not belong to the interval",(i <= maxVal) && (i >= minVal));
        }

        //an ever next element without last is greater then previous one
        for (int i = 0; i < arr.length - 1; i++){
            assertTrue("element with index " + i + " grater than next element",arr[i] <= arr[i+1]);
        }
    }

    @Test
    public void sortedArrayWithXTest(){
        arr = Filler.genSortedArrayWithX(len, minVal, maxVal);

        assertEquals("lengths of array is not equals", len, arr.length);

        //all element in interval
        for(int i : arr){
            assertTrue("element " + i + " is not belong to the interval",(i <= maxVal) && (i >= minVal));
        }

        //an ever next element is greater then previous one
        for (int i = 0; i < arr.length - 2; i++){
            assertTrue("element with index " + i + " greater than next element",arr[i] <= arr[i+1]);
        }
    }

    @Test
    public void randomArrayTest(){
        arr = Filler.genRandomArray(len, minVal, maxVal);

        assertEquals("lengths of array is not equals", len, arr.length);

        //all element in interval
        for(int i : arr){
            assertTrue("element " + i + " is not belong to the interval",(i <= maxVal) && (i >= minVal));
        }
    }

    @Test
    public void reverseSortedArrayTest(){
        //equals lens
        arr = Filler.genRevSortedArray(len, minVal, maxVal);

        assertEquals("lengths of array is not equals", len, arr.length);


        //all element in interval
        for(int i : arr){
            assertTrue("element " + i + " is not belong to the interval",(i <= maxVal) && (i >= minVal));
        }

        //an ever next element is less then previous one
        for (int i = 0; i < arr.length - 1; i++){
            assertTrue("element with index " + i + " less than next element",arr[i] >= arr[i+1]);
        }
    }

    @Test
    public void negativeLenTest(){
        assertArrayEquals(new int[0], Filler.genRandomArray(-2, minVal, maxVal));

        assertArrayEquals(new int[0], Filler.genRevSortedArray(-2, minVal, maxVal));

        assertArrayEquals(new int[0], Filler.genSortedArray(-2, minVal, maxVal));

        assertArrayEquals(new int[0], Filler.genSortedArrayWithX(-2, minVal, maxVal));
    }

    @Test
    public void minValLessThanMax(){
        minVal = 50;
        maxVal = 0;

        arr = Filler.genRevSortedArray(len, minVal, maxVal);
        for(int i = 0; i < len; i++ ){
            assertEquals(arr[i], maxVal);
        }


        arr = Filler.genRandomArray(len, minVal, maxVal);
        for(int i = 0; i < len; i++ ){
            assertEquals(arr[i], maxVal);
        }


        arr = Filler.genSortedArrayWithX(len, minVal, maxVal);
        for(int i = 0; i < len; i++ ){
            assertEquals(arr[i], maxVal);
        }


        arr = Filler.genSortedArray(len, minVal, maxVal);
        for(int i = 0; i < len; i++ ){
            assertEquals(arr[i], maxVal);
        }


    }


}
