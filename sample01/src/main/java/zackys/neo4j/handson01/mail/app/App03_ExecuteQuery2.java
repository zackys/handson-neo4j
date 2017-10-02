package zackys.neo4j.handson01.mail.app;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;

import zackys.neo4j.handson01.mail.neo4j.ogm.session.Neo4jSessionFactory;


public class App03_ExecuteQuery2 {

    public static void main(String[] args) {
        execute();
    }

    public static void execute() {
        Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();

        Result res = session.query("MATCH (n) RETURN n", Collections.emptyMap());

        Iterator<Map<String, Object>> itr = res.iterator();
        while(itr.hasNext()) {
            Map<String, Object> line = itr.next();
            Object obj = line.get("n");

            System.out.println(obj);
        }

        /*
        res.forEach(line -> {
            Object obj = line.get("n");

            System.out.println(obj);
        });
        */
    }

}
