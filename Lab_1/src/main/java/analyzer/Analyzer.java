package analyzer;

import annotations.FillerMet;
import fillers.Filler;
import org.reflections.Reflections;
import sorters.algorithms.Sorting;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Analyzer {

    static final int LEN10 = 10;
    static final int LEN100 = 100;
    static final int LEN1000 = 1_000;


    public static void analyze(){

        Reflections reflections = new Reflections();

        Set<Class<? extends Sorting>> sortingTypes = reflections.getSubTypesOf(Sorting.class);
//
//        for(Class<? extends Sorting> s : subTypes){
//            System.out.println(s.getCanonicalName());
//        }

        List<Method> genMethods = getMethodsAnnotatedWith(Filler.class, FillerMet.class);

//        for(Method m : l){
//            System.out.println(m.getName());
//        }

        Map<Method, Map<Class<? extends Sorting>, long[]>> map = stats(genMethods, sortingTypes);



    }


    private static Map<Method, Map<Class<? extends Sorting>, long[]>> stats (List<Method> genMethods, Set<Class<? extends Sorting>> sortingSet){

        Map<Method, Map<Class<? extends Sorting>, long[]>> map = new HashMap<>();

        for(Method typeGenArray : genMethods){
            int[] arr10 = genArray(typeGenArray, LEN10);
            int[] arr100 = genArray(typeGenArray, LEN100);
            int[] arr1000 = genArray(typeGenArray, LEN1000);

            ArrayList<int[]> list = new ArrayList<>();

            list.add(arr10);
            list.add(arr100);
            list.add(arr1000);

            map.put(typeGenArray, mapOfTimes(sortingSet, list));
        }

        return map;

    }


    private static Map<Class<? extends Sorting>, long[]> mapOfTimes(Set<Class<? extends Sorting>> sortingSet, ArrayList<int[]> arrList){
        final int len = arrList.size();

        Map<Class<? extends Sorting>, long[]> sortingMap = new HashMap<>();

        for(Class<? extends Sorting> sortClass : sortingSet){
            long[] times = new long[len];

            for(int i = 0; i < len; i++){
                times[i] = time(sortClass, arrList.get(i));
            }
            sortingMap.put(sortClass, times);
        }

        return sortingMap;
    }
    private static long time (Class<? extends Sorting> clazz, int[] arr){
        Sorting sorting = null;
        try {
            sorting = clazz.getDeclaredConstructor(int[].class).newInstance(arr);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        long start = System.nanoTime();
        sorting.sort();
        long finish = System.nanoTime();

        return finish - start;
    }

    private static int[] genArray(Method method, int len){
        int[] arr = null;
        try {
            arr = (int[])(method.invoke(null, len, 0 , len));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return arr;
    }

    private static List<Method> getMethodsAnnotatedWith(final Class<?> type, final Class<? extends Annotation> annotation) {
        final List<Method> methods = new ArrayList<>();
        Class<?> klass = type;
        while (klass != Object.class) { // need to iterated thought hierarchy in order to retrieve methods from above the current instance
            // iterate though the list of methods declared in the class represented by klass variable, and add those annotated with the specified annotation
            final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(klass.getDeclaredMethods()));
            for (final Method method : allMethods) {
                if (method.isAnnotationPresent(annotation)) {
                    methods.add(method);
                }
            }
            // move to the upper class in the hierarchy in search for more methods
            klass = klass.getSuperclass();
        }
        return methods;
    }
}
