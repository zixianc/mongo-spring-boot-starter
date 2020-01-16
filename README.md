# mongo-spring-boot-starter
mongo-orm的spring-boot-starter，在spring boot自动装载BeanCodec，注入MongoDB

* 引入pom
```xml
<dependency>
    <groupId>top.newleaf</groupId>
    <artifactId>mongo-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

* 允许自动mongo-orm自动配置  
在spring boot启动类添加注解`@EnableMongoCodecConfiguration`，因为引入mongo-driver包，spring boot会自动配置
MongoAutoConfiguration，所以这里剔除掉它`@SpringBootApplication(exclude = MongoAutoConfiguration.class)`

* 配置mongo连接
在application.yml中增加连接配置
```yaml
top:
  newleaf:
    mongo:
      packages:
        - com.newleaf.test
      connections: 
        -
          uri: 'mongodb://user:url:port/dbname1?replicaSet=port&authSource=admin&journal=true'
          db: 'dbname1'
          name: 'test1'
          isDefault: true
        -
          uri: 'mongodb://user:url:port/dbname2?replicaSet=port&authSource=admin&journal=true'
          db: 'dbname2'
          name: 'test2'
```

* 按照需要使用javax.persistence注解注释字段和实体（支持的注解功能请参照[mongo-orm说明](https://github.com/zixianc/mongo-orm/blob/master/README.md)）

```java
@Table(name = "t_comment")
public class Comment {

    private Long id;

    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
```

* 继承BeanCodec传入实体泛型，标记注解`@Component`注入
```java
import top.newleaf.mongo.annotation.BeanCodec;

@Component
public class CommentCodec extends BeanCodec<Comment> {
}

```

* 注入MongoDB对象使用
```java
@Repository
public class CommentDAO {

    // 可以通过@Resource(name = "test1")指定具体名称的数据源
    // 不加注解则会注入isDefault标记的数据源
    private MongoDB mongoDB;

    public Comment getComment(long id) {
        return mongoDB.getCollection(Comment.class).find(Filters.eq("id", id)).first();
    }

}
```

