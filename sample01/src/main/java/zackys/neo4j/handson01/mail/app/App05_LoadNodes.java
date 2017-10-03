package zackys.neo4j.handson01.mail.app;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.transaction.Transaction;

import zackys.neo4j.handson01.mail.model.User;
import zackys.neo4j.handson01.mail.neo4j.ogm.session.Neo4jSessionFactory;

/**
 * 特定Nodeの読み込み
 *
 */
public class App05_LoadNodes {

    public static void main(String[] args) {
        execute1();
        execute2();
        execute3();
    }

    /**
     * 検索結果が１or０件の場合、Session#queryForObject()が使える
     */
    public static void execute1() {
        System.out.println("## ex1 ##");
        Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();

        Map<String, String> param = new HashMap<>();
        try(Transaction tx = session.beginTransaction()) {
            param.put("addr", "d@xxx.co.jp");
            User user = session.queryForObject(User.class, "MATCH (n:User {address:{addr}}) RETURN n", param);

            System.out.println(user);
        }
    }

    /**
     * @Index(unique=true, primary=true)が付与されたプロパティの場合、
     * T Session#load(Class<T> type, U u)
     * が使える
     */
    public static void execute2() {
        System.out.println("## ex2 ##");
        Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();

        try(Transaction tx = session.beginTransaction()) {
            User user = session.load(User.class, "ID0002");
            System.out.println(user);
        }
    }

    public static void execute3() {
        System.out.println("## ex3 ##");
        Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();

        try(Transaction tx = session.beginTransaction()) {

            // Label クラスを指定して全検索
            session.loadAll(User.class).forEach(System.out::println);
        }
    }

}
