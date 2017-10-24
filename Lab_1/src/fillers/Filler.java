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

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.*;

public class Filler {
    private static Random rand;

    static {
        rand = new Random();
    }

    private Filler() {
        super();
    }

    public static int[] genSortedArray(final int length, final int minVal, final int maxVal) {
        List<Integer> data = new ArrayList<>(length);

        for (int i = 0; i < length; i++) {
            int rndNum = getRandomVal(minVal, maxVal);
            int insertionPoint = Collections.binarySearch(data, rndNum);
            data.add(insertionPoint > -1 ? insertionPoint : -insertionPoint - 1, rndNum);
        }

        return data.stream().mapToInt(i -> i).toArray();
    }



//    public static int[] genSortedArrayWithX (int len){
//
//    }
//
//    public static int[] genRevSortedArray (int len) {
//
//    }
//
//    public static int[] genRandomArray(int len){
//
//    }

    private static int getRandomVal(int min, int max) {
        return min + rand.nextInt(max - min + 1);
    }


}
