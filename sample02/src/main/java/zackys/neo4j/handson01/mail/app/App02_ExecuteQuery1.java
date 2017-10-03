package zackys.neo4j.handson01.mail.app;

import java.util.Collections;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.transaction.Transaction;

import zackys.neo4j.handson01.mail.neo4j.ogm.session.Neo4jSessionFactory;

/**
 * クエリの実行（パラメータ・戻り値なし）
 *
 */
public class App02_ExecuteQuery1 {

    public static void main(String[] args) {
        execute();
    }

    public static void execute() {
        Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();

        try(Transaction tx = session.beginTransaction()) {

            session.query("MATCH (n) DETACH DELETE n", Collections.emptyMap());

            tx.commit();
        }
    }

}
