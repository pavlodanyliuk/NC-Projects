package analyzer;

import org.reflections.Reflections;
import sorters.algorithms.Sorting;

import java.util.Set;

public class Analyzer {
    public static void analyze(){

        Reflections reflections = new Reflections();

        Set<Class<? extends Sorting>> subTypes = reflections.getSubTypesOf(Sorting.class);

        for(Class<? extends Sorting> s : subTypes){
            System.out.println(s.getCanonicalName());
        }

//        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(SomeAnnotation.class);

    }
}
