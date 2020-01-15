package top.newleaf.mongo.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import top.newleaf.mongo.factory.MongoConnection;

import java.util.List;

/**
 * @author chengshx
 */
@ConfigurationProperties(prefix = "top.newleaf.mongo")
public class MongoProperties {

    @NestedConfigurationProperty
    private MongoConnection connection;

    private List<MongoConnection> connections;

    public MongoProperties() {
    }

    public MongoConnection getConnection() {
        return connection;
    }

    public void setConnection(MongoConnection connection) {
        this.connection = connection;
    }

    public List<MongoConnection> getConnections() {
        return connections;
    }

    public void setConnections(List<MongoConnection> connections) {
        this.connections = connections;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("{");
        sb.append("\"connections\":").append(connections);
        sb.append('}');
        return sb.toString();
    }
}
