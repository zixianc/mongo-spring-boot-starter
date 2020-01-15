package top.newleaf.mongo.annotation;

import java.lang.annotation.*;

/**
 * 开启mongo自动配置
 * @author chengshx
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableMongoCodecConfiguration {
}
