package top.newleaf.mongo.annotation;

import java.lang.annotation.*;

/**
 * 开启mongo自动配置
 * @author chengshx
 * @date 2018/12/8
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableMongoCodecConfiguration {
}
