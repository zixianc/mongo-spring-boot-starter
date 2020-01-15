package top.newleaf.mongo.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author chengshx
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface BeanCodec {
}
