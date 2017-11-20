package fillers;

import annotations.FillerMet;

import java.util.*;

/**
 * <b>Class contains different methods for generate arrays</b>
 * <br>Can't to create an objects of the class
 * <br>{@link fillers.Filler#genSortedArray(int, int, int) generate sorting array}
 * <br>{@link fillers.Filler#genRandomArray(int, int, int) generate randoms array}
 * <br>{@link fillers.Filler#genRevSortedArray(int, int, int) generate reverce sorting array}
 * <br>{@link fillers.Filler#genSortedArrayWithX(int, int, int) generate sorting array with random number in end}
 *
 * @author Pavlo Danyliuk
 * @version 1.0
 */


public class Filler {
    private static Random rand;

    static {
        rand = new Random();
    }

    private Filler() {
        super();
    }


    /**
     *Generate a sorted array
     * @param length the length of future array
     * @param minVal the minimum limit of values element with array
     * @param maxVal the maximum limit of values element with array
     * @return int[]
     */
    @FillerMet
    public static int[] genSortedArray(final int length, int minVal, int maxVal) {
        if(length < 0) {
            return new int[0];
        }
        if(minVal > maxVal) minVal = maxVal;

        return transferListToArray(getSortedList(length, minVal, maxVal));
    }

    /**
     *Generate a sorted array with random number in the end
     * @param length the length of future array
     * @param minVal the minimum limit of values element with array
     * @param maxVal the maximum limit of values element with array
     * @return int[]
     */
    @FillerMet
    public static int[] genSortedArrayWithX (final int length, int minVal, int maxVal){
        if(length < 0) {
            return new int[0];
        }
        if(minVal > maxVal) minVal = maxVal;

        List<Integer> data = getSortedList(length - 1, minVal, maxVal);
        data.add(getRandomVal(minVal, maxVal));

        return transferListToArray(data);
    }

    /**
     *Generate a reverse sorted array
     * @param length the length of future array
     * @param minVal the minimum limit of values element with array
     * @param maxVal the maximum limit of values element with array
     * @return int[]
     */
    @FillerMet
    public static int[] genRevSortedArray (final int length, int minVal, int maxVal) {
        if(length < 0) {
            return new int[0];
        }
        if(minVal > maxVal) minVal = maxVal;

        List<Integer> data = getSortedList(length, minVal, maxVal);
        Collections.reverse(data);

        return transferListToArray(data);
    }

    /**
     *Generate array with random elements
     * @param length the length of future array
     * @param minVal the minimum limit of values element with array
     * @param maxVal the maximum limit of values element with array
     * @return int[]
     */
    @FillerMet
    public static int[] genRandomArray(final int length, int minVal, int maxVal){
        if(length < 0) {
            return new int[0];
        }
        if(minVal > maxVal) minVal = maxVal;

        List<Integer> data = new ArrayList<>(length);

        for (int i = 0; i < length; i++){
            data.add(getRandomVal(minVal, maxVal));
        }

        return transferListToArray(data);
    }

    private static int getRandomVal(int min, int max) {
        return min + rand.nextInt(max - min + 1);
    }

    private static List<Integer> getSortedList(final int length, final int minVal, final int maxVal){
        List<Integer> data = new ArrayList<>(length);

        for (int i = 0; i < length; i++) {
            int rndNum = getRandomVal(minVal, maxVal);
            int insertionPoint = Collections.binarySearch(data, rndNum);
            data.add(insertionPoint > -1 ? insertionPoint : -insertionPoint - 1, rndNum);
        }
        return data;
    }

    private static int[] transferListToArray(List<Integer> data){
        return data.stream().mapToInt(i -> i).toArray();
    }

}