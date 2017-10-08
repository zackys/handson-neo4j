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

    public static void execute1() {
        System.out.println("## ex1 ##");
        Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();

        Map<String, String> param = new HashMap<>();
        try(Transaction tx = session.beginTransaction(Transaction.Type.READ_ONLY)) {
            param.put("addr", "d@xxx.co.jp");
            // ★結果の型と検索結果が１or０件であることが分かっている場合、Session#queryForObject()が使える
            // （実行時、検索結果が複数あるとRuntimeExceptionを送出する）
            // queryForObject()の引数は、結果の型とクエリおよびそのパラメータ、戻り値は指定した型のインスタンスまたはnull
            User user = session.queryForObject(User.class, "MATCH (n:User {address:{addr}}) RETURN n", param);

            System.out.println(user);
        }
    }

    public static void execute2() {
        System.out.println("## ex2 ##");
        Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();

        try(Transaction tx = session.beginTransaction(Transaction.Type.READ_ONLY)) {
            // ★Nodeを@Idを付与したパラメータで検索する場合、T Session#load(Class<T> type, U id)が使える
            // @IdプロパティはUNIQUE制約を持つので、戻り値はマッチしたインスタンスかnullのどちらか
            User user = session.load(User.class, "ID0002");

            System.out.println(user);
        }
    }

    public static void execute3() {
        System.out.println("## ex3 ##");
        Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();

        try(Transaction tx = session.beginTransaction(Transaction.Type.READ_ONLY)) {

            // ★特定の型のNodeをすべてロードする
            session.loadAll(User.class).forEach(System.out::println);
        }
    }

}
