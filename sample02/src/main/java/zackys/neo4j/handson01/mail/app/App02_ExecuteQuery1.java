package zackys.neo4j.handson01.mail.app;

import java.util.Collections;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.transaction.Transaction;

import zackys.neo4j.handson01.mail.neo4j.ogm.session.Neo4jSessionFactory;

/**
 * Cypherクエリの実行（パラメータ・戻り値なし）
 *
 * <ol>
 * <li>ソースコードを確認する
 * <li>アプリケーションを実行する
 * <li>Neo4j Browserで「MATCH (n) RETURN n」を実行する。
 *     実行したクエリの通り、Nodeがすべて削除されていることを確認する
 * </ol>
 */
public class App02_ExecuteQuery1 {

    public static void main(String[] args) {
        execute();
    }

    public static void execute() {
        Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();

        try(Transaction tx = session.beginTransaction()) {

            // ★Cypherクエリを実行するには、Session#query()を使用する
            // 第二引数の"パラメータ"は今回は不要なためめ、Collections.emptyMap()を渡す
            session.query("MATCH (n) DETACH DELETE n", Collections.emptyMap());

            tx.commit();
        }
    }

}
