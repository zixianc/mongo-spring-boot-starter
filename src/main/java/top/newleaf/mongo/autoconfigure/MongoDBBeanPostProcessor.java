package top.newleaf.mongo.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import top.newleaf.mongo.codec.BeanCodec;
import top.newleaf.mongo.factory.MongoDB;
import top.newleaf.mongo.factory.MongoFactory;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;

/**
 * @author chengshx
 * @date 2020/1/10
 */
public class MongoDBBeanPostProcessor implements BeanPostProcessor {

    private final static Logger LOGGER = LoggerFactory.getLogger(MongoDBBeanPostProcessor.class);
    private final static String NAME_SUFFIX = "DB";

    @Autowired
    private ApplicationContext applicationContext;

    private MongoProperties mongoProperties;

    public MongoDBBeanPostProcessor(MongoProperties mongoProperties) {
        this.mongoProperties = mongoProperties;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        initMongoConnections();
        populateMongoDB(bean);
        return bean;
    }

    /**
     * 填充MongoDb对象
     * @param bean
     */
    private void populateMongoDB(Object bean) {
        Class<?> clazz = bean.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getType() == MongoDB.class) {
                ReflectionUtils.makeAccessible(field);
                Resource resource = AnnotationUtils.findAnnotation(field, Resource.class);
                if (resource != null && StringUtils.hasLength(resource.name())) {
                    String name = resource.name();
                    if (name.endsWith(NAME_SUFFIX)) {
                        name = name.substring(0, name.length() - 2);
                    }
                    ReflectionUtils.setField(field, bean, MongoFactory.getDb(name));
                } else {
                    ReflectionUtils.setField(field, bean, MongoFactory.getDb());
                }
            }
        }
    }

    /**
     * 初始化mongo连接
     */
    private void initMongoConnections() {
        if (!MongoFactory.getInstance().getHasInit()) {
            Map<String, BeanCodec> beanCodecs = applicationContext.getBeansOfType(BeanCodec.class);
            MongoFactory.getInstance().createConnections(mongoProperties.getConnections(), new HashSet<>(beanCodecs.values()));
            DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
            Map<String, MongoDB> dbs = MongoFactory.getDbs();
            if (!dbs.isEmpty()) {
                dbs.forEach((name, db) -> {
                    String beanName = name + NAME_SUFFIX;
                    if (!beanFactory.containsBean(beanName)) {
                        beanFactory.registerSingleton(name + NAME_SUFFIX, db);
                        LOGGER.info("register mongoDB: {}", beanName);
                    }
                });
            }
        }
    }
}
