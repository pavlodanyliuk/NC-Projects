package annotations;

import java.lang.annotation.*;


/**
 * Marker for fillers methods
 */
@Documented
@Target(ElementType.METHOD)
@Retention(value= RetentionPolicy.RUNTIME)

public @interface FillerMet{

}