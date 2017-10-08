package zackys.neo4j.handson01.mail.app;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.transaction.Transaction;

import zackys.neo4j.handson01.mail.model.User;
import zackys.neo4j.handson01.mail.neo4j.ogm.session.Neo4jSessionFactory;

/**
 * Cypherクエリの実行（パラメータなし・戻り値あり）
 *
 * <ol>
 * <li>ソースコードを確認する
 * <li>アプリケーションを実行する
 * <li>標準出力に検索結果が表示されることを確認する
 * </ol>
 */
public class App03_ExecuteQuery2 {

    public static void main(String[] args) {
        execute();
    }

    public static void execute() {
        Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();

        try(Transaction tx = session.beginTransaction(Transaction.Type.READ_ONLY)) {

            Result res = session.query("MATCH (n:User) RETURN n, n.name, n.address AS addr", Collections.emptyMap());

            // ★クエリの結果行をイテレートする。
            // :Userは全部で６件登録しているので、結果行は６行。
            // （イメージが湧かない場合はNeo4j Browserで同じクエリを実行し、結果を"Table"ビューで見てみると良い。
            //   ビューはカード左側のアイコンで切り替えられる。
            //   ちなみにNodeはJSON形式で返る（結果全体が１つのJSON形式で返る。））
            Iterator<Map<String, Object>> itr = res.iterator();
            while(itr.hasNext()) {
                // 各行はMapとして返る。キーはクエリのRETURNで指定した文字列
                Map<String, Object> line = itr.next();

                // 結果のうち、RETURN n の部分を取得
                User user = (User)line.get("n");
                System.out.println(user);

                // 結果のうち、RETURN n.name の部分を取得
                String name = (String)line.get("n.name");
                System.out.println(name);

                // 結果のうち、RETURN n.address AS addr の部分を取得
                String addr = (String)line.get("addr");
                System.out.println(addr);

                System.out.println("--------------------------");
            }
        }
    }

}
