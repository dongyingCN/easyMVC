package mvc.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * requestMapping target
 * @author ying.dong
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE, ElementType.METHOD})
public @interface RequestMapping {
	String value() default "";
}
