package fillers;

//Написать методы, генерирующие массивы целых чисел заданной длинны
//        следующими способами:
//        1) Уже отсортированного массива
//        (1, 2, 3, 7, ....., max);
//        2) Отсортированного массива, в конец которого дописан случайный элемент
//        (1, 2, 3, 7, ....., max, X);
//        3) Массива, отсортированного в обратном порядке
//        (max, ... , 7, 3, 2, 1);
//        4) Массива, содержащего элементы, расположенные случайным образом

import annotations.FillerMet;

import java.util.*;


public class Filler {
    private static Random rand;

    static {
        rand = new Random();
    }

    private Filler() {
        super();
    }


    @FillerMet
    public static int[] genSortedArray(final int length, final int minVal, final int maxVal) {
        return transferListToArray(getSortedList(length, minVal, maxVal));
    }

    @FillerMet
    public static int[] genSortedArrayWithX (final int length, final int minVal, final int maxVal){
        List<Integer> data = getSortedList(length - 1, minVal, maxVal);
        data.add(getRandomVal(minVal, maxVal));

        return transferListToArray(data);
    }

    @FillerMet
    public static int[] genRevSortedArray (final int length, final int minVal, final int maxVal) {
        List<Integer> data = getSortedList(length, minVal, maxVal);
        Collections.reverse(data);

        return transferListToArray(data);
    }

    @FillerMet
    public static int[] genRandomArray(final int length, final int minVal, final int maxVal){
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
