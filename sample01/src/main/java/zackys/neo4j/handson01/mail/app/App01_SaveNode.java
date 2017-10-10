package zackys.neo4j.handson01.mail.app;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.transaction.Transaction;

import zackys.neo4j.handson01.mail.model.User;
import zackys.neo4j.handson01.mail.neo4j.ogm.session.Neo4jSessionFactory;

/**
 * Nodeの保存・更新
 *
 * <ol>
 * <li>ソースコードを確認する
 * <li>アプリケーションを実行する
 * <li>Neo4j Browserで「MATCH (n) RETURN n」を実行する。
 *     ソースコードの通りに:UserがDBへ登録されていることを確認する
 * </ol>
 */
public class App01_SaveNode {

    public static void main(String[] args) {
        execute();
    }

    public static void execute() {
        Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();

        try(Transaction tx = session.beginTransaction()) {

            List<User> users = new ArrayList<User>() {
                {
                    this.add(new User("ID0001", "AAA", "a@xxx.co.jp", 25));
                    this.add(new User("ID0002", "BBB", "b@xxx.co.jp", 32));
                    this.add(new User("ID0003", "CCC", "c@xxx.co.jp", 53));
                    this.add(new User("ID0004", "DDD", "d@xxx.co.jp", 44));
                    this.add(new User("ID0005", "EEE", "e@xxx.co.jp", 32));
                    this.add(new User("ID0006", "FFF", "f@xxx.co.jp", 56));
                }
            };

            // ①
            for (User user: users) {
                // ★保存・更新にはSession#save()を使用する
                session.save(user);
            }

            //or

            // ②
            users.stream().forEach(session::save);

            tx.commit();

            // ２回保存しているが、インスタンスは同一なので、生成（保存）されるNodeは重複しないことに注意

        }
    }

}
