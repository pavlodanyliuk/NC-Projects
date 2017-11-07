package annotations;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(value= RetentionPolicy.RUNTIME)
public @interface FillerMet{

}