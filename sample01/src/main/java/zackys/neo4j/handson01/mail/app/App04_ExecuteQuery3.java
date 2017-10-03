package zackys.neo4j.handson01.mail.app;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.transaction.Transaction;

import zackys.neo4j.handson01.mail.neo4j.ogm.session.Neo4jSessionFactory;

/**
 * クエリの実行（パラメータあり）
 *
 */
public class App04_ExecuteQuery3 {

    public static void main(String[] args) {
        execute();
    }

    public static void execute() {
        Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();

        try(Transaction tx = session.beginTransaction()) {

            Map<String, String> param = new HashMap<>();
            param.put("value", "AAA");
            Result res = session.query("MATCH (n:User {name:{value}}) RETURN n", param);

            Iterator<Map<String, Object>> itr = res.iterator();
            while(itr.hasNext()) {
                Map<String, Object> line = itr.next();
                Object obj = line.get("n");

                System.out.println(obj);
            }
        }
    }

}
