package analyzer;

import annotations.FillerMet;
import fillers.Filler;
import org.reflections.Reflections;
import sorters.algorithms.Sorting;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;


/**
 *<b>Class for get statistics about all sorting method with different generated arrays</b>
 *
 * @author Pavlo Danyliuk
 * @version 1.0
 * @see Sorting
 * @see Filler
 */

public class Analyzer {

    private static final int LEN10 = 10;
    private static final int LEN100 = 100;
    private static final int LEN1000 = 1_000;
    private static final int LEN10000 = 10_100;

    /**
     * Return {@code Map<Method, Map<Class<? extends Sorting>, long[]>>}
     * <br>The map consist of all method to fill array and statistics about all sorted for this methods
     * <br>This map {@code Map<Class<? extends Sorting>, long[]>} contains all method of sort and time of
     * run in nanoseconds for 10, 100, 1 000 and 10 000 elements respectively.
     *
     * @return Map<Method, Map<Class<? extends Sorting>, long[]>>
     */
    public static Map<Method, Map<Class<? extends Sorting>, long[]>> analyze(){

        Reflections reflections = new Reflections();

        //Get all sorting types and deleting abstract methods
        Set<Class<? extends Sorting>> sortingTypes = reflections.getSubTypesOf(Sorting.class);
        deleteAllAbstractClasses(sortingTypes);

        //Get all generating methods
        List<Method> genMethods = getMethodsAnnotatedWith(Filler.class, FillerMet.class);

        //Get stats
        Map<Method, Map<Class<? extends Sorting>, long[]>> map = stats(genMethods, sortingTypes);

        for(Map.Entry<Method, Map<Class<? extends Sorting>, long[]>> n : map.entrySet()){
            System.out.println(n.getKey().getName() + ":");
            for(Map.Entry<Class<? extends Sorting>, long[]> m : n.getValue().entrySet()){

                System.out.print(m.getKey().getSimpleName() + " : " + m.getValue()[0] + " " + m.getValue()[1] + " " + m.getValue()[2] + " " + m.getValue()[3] + "\n");
            }
            System.out.println("-------------");
        }

        return map;
    }



    private static Map<Method, Map<Class<? extends Sorting>, long[]>> stats (List<Method> genMethods, Set<Class<? extends Sorting>> sortingSet){

        Map<Method, Map<Class<? extends Sorting>, long[]>> map = new HashMap<>();

        for(Method typeGenArray : genMethods){
            int[] arr10 = genArray(typeGenArray, LEN10);
            int[] arr100 = genArray(typeGenArray, LEN100);
            int[] arr1000 = genArray(typeGenArray, LEN1000);
            int[] arr10000 = genArray(typeGenArray, LEN10000);

            ArrayList<int[]> list = new ArrayList<>();

            list.add(arr10);
            list.add(arr100);
            list.add(arr1000);
            list.add(arr10000);

            map.put(typeGenArray, mapOfTimes(sortingSet, list));
        }

        return map;

    }


    private static Map<Class<? extends Sorting>, long[]>  mapOfTimes(Set<Class<? extends Sorting>> sortingSet, ArrayList<int[]> arrList){
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
        } catch (IllegalAccessException | InvocationTargetException e) {
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


    private static void deleteAllAbstractClasses(Set<Class<? extends Sorting>> sortingTypes) {
        Iterator<Class<? extends Sorting>> iterator = sortingTypes.iterator();
        while (iterator.hasNext()) {
            Class<? extends Sorting> element = iterator.next();
            if (Modifier.isAbstract(element.getModifiers())) {
                iterator.remove();
            }
        }
    }

    public static int[] getLenOfArrays(){
        return new int[]{ LEN10, LEN100, LEN1000, LEN10000 };
    }
}
