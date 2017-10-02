package zackys.neo4j.handson01.mail.app;

import java.util.Collections;

import org.neo4j.ogm.session.Session;

import zackys.neo4j.handson01.mail.neo4j.ogm.session.Neo4jSessionFactory;


public class App02_ExecuteQuery1 {

    public static void main(String[] args) {
        execute();
    }

    public static void execute() {
        Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();

        session.query("MATCH (n) DETACH DELETE n", Collections.emptyMap());
    }

}
