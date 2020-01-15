package top.newleaf.mongo.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.newleaf.mongo.annotation.EnableMongoCodecConfiguration;

/**
 * @author chengshx
 */
@Configuration
@ConditionalOnBean(annotation = EnableMongoCodecConfiguration.class)
@EnableConfigurationProperties(MongoProperties.class)
public class MongoFactoryAutoConfigure {

    @Bean
    public MongoDBBeanPostProcessor registerMongoDBBeanPostProcessor(MongoProperties mongoProperties) {
        return new MongoDBBeanPostProcessor(mongoProperties);
    }

}
