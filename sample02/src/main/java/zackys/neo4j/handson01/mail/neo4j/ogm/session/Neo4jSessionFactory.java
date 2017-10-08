package zackys.neo4j.handson01.mail.neo4j.ogm.session;

import org.neo4j.ogm.config.ClasspathConfigurationSource;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.config.ConfigurationSource;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

public class Neo4jSessionFactory {

    private static final ConfigurationSource props = new ClasspathConfigurationSource("ogm.properties");
    private static final Configuration configuration = new Configuration.Builder(props).build();

    private final static SessionFactory sessionFactory = new SessionFactory(configuration, "zackys.neo4j.handson01.mail.model");

    private static Neo4jSessionFactory factory = new Neo4jSessionFactory();

    public static Neo4jSessionFactory getInstance() {
        return factory;
    }

    private Neo4jSessionFactory() {
    }

    public Session getNeo4jSession() {
        return sessionFactory.openSession();
    }
}