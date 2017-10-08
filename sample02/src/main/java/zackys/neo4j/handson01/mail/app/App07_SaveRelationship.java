package zackys.neo4j.handson01.mail.app;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.transaction.Transaction;

import zackys.neo4j.handson01.mail.model.Dpt;
import zackys.neo4j.handson01.mail.model.User;
import zackys.neo4j.handson01.mail.neo4j.ogm.session.Neo4jSessionFactory;

/**
 * Relationshipの生成・更新・削除
 *
 * ①のコメントを外して実行
 * ⇒ MATCH (n) RETURN n を実行し、createTable1()の通りにRelationshipが生成されていることを確認する
 * ②のコメントを外して実行（①はコメントアウト）
 * ⇒ MATCH (n) RETURN n を実行し、createTable2()の通りにRelationshipがつなぎ変わったことを確認する
 * ③のコメントを外して実行（②はコメントアウト）
 * ⇒ MATCH (n) RETURN n を実行し、createTable3()の通りに全てのRelationshipが削除されたことを確認する
 * ①のコメントを外して実行（③はコメントアウト）
 * ⇒ MATCH (n) RETURN n を実行し、createTable1()の通りにRelationshipが生成されていることを確認する
 *
 */
public class App07_SaveRelationship {

    public static void main(String[] args) {
        execute();
    }

    public static void execute() {
        Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();

        try (Transaction tx = session.beginTransaction()) {
            Map<String, String> table = createTable1();  // ①
            //Map<String, String> table = createTable2();  // ②
            //Map<String, String> table = createTable3();  // ③

            Collection<Dpt> dpts = session.loadAll(Dpt.class);

            session.loadAll(User.class).forEach(user -> {
                // tableから、userの所属部門名を取得
                String dptName = table.get(user.getUserId());

                // 所属部門名に応じたDptインスタンスを取得
                Optional<Dpt> target = dpts.stream().filter(dpt -> {
                    return dpt.getName().equals(dptName);
                }).findFirst();

                // ★User#dptへDptインスタンスを設定。所属部門がない場合はnullを設定
                user.setDpt(target.orElse(null));

                // DBへ保存。user#dptがnullの場合、その間のRelationshipは切断される
                session.save(user);
            });

            tx.commit();
        }
    }

    private static Map<String, String> createTable1() {
        Map<String, String> table = new HashMap<String, String>() {
            {
                this.put("ID0001", "Dpt. 1");
                this.put("ID0002", "Dpt. 1");
                this.put("ID0003", "Dpt. 1");
                this.put("ID0004", "Dpt. 2");
                this.put("ID0005", "Dpt. 2");
                this.put("ID0006", "Dpt. 2");
            }
        };

        return table;
    }

    private static Map<String, String> createTable2() {
        Map<String, String> table = new HashMap<String, String>() {
            {
                this.put("ID0001", "Dpt. 2");
                this.put("ID0002", "Dpt. 2");
                this.put("ID0003", "Dpt. 3");
                this.put("ID0004", "Dpt. 3");
                this.put("ID0005", "Dpt. 1");
                this.put("ID0006", "Dpt. 1");
            }
        };

        return table;
    }

    private static Map<String, String> createTable3() {

        return Collections.emptyMap();
    }

}
