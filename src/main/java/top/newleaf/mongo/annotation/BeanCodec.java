package top.newleaf.mongo.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author chengshx
 * @date 2020/1/10
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface BeanCodec {
}
