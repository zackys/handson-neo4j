package zackys.neo4j.handson01.mail.app;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.transaction.Transaction;

import zackys.neo4j.handson01.mail.model.Email;
import zackys.neo4j.handson01.mail.model.MailData;
import zackys.neo4j.handson01.mail.model.User;
import zackys.neo4j.handson01.mail.neo4j.ogm.session.Neo4jSessionFactory;

/**
 * ハンズオン
 *
 * queueにある"メールデータ"からEmailクラスのインスタンスを生成し、Neo4jへ登録するアプリケーションを完成させてください。
 *
 * Emailクラスの実装
 * <ul>
 * <li>ドメインモデルに従い実装する
 * <li>mailIdはLong型とし、1からカウントアップしていくものとする（今回は排他制御は考えない）
 * <li>Email#fromはUserへの参照でOK
 * <li>Email#toは、Set<User>型を使う
 * </ul>
 *
 * App08_MailLogging実装のヒント
 * queueのあるMailDataを順番に読み取り、
 * <ul>
 * <li>formに当たるNodeを読み込む
 * <li>toに当たるNodeを読み込む
 * <li>Emailインスタンスに必要な情報を設定し、Session#save()する
 * </ul>
 */
public class App08_MailLogging {

    public static void main(String[] args) {
        execute();
    }

    public static void execute() {
        Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();

        try (Transaction tx = session.beginTransaction()) {

            // :Emailを全部消してやり直したい場合
            //session.deleteAll(Email.class);

            Queue<MailData> queue = new ArrayDeque<MailData>() {
                {
                    this.add(new MailData("a@xxx.co.jp", "title1", "b@xxx.co.jp", "c@xxx.co.jp"));
                    this.add(new MailData("b@xxx.co.jp", "title2", "a@xxx.co.jp"));
                    this.add(new MailData("c@xxx.co.jp", "title3", "d@xxx.co.jp"));
                    this.add(new MailData("d@xxx.co.jp", "title4", "a@xxx.co.jp", "b@xxx.co.jp", "c@xxx.co.jp"));
                }
            };

            Map<String, String> param = new HashMap<>();
            queue.stream().forEach(data->{
                param.clear();
                param.put("addr", data.from);
                // fromに当たるNodeを読み込む
                User from = session.queryForObject(User.class, "MATCH (n:User {address:{addr}}) RETURN n", param);

                // toに当たるNodeを読み込む
                Set<User> res = new HashSet<>();
                for (String toAddr: data.to) {
                    param.clear();
                    param.put("addr", toAddr);
                    User to = session.queryForObject(User.class, "MATCH (n:User {address:{addr}}) RETURN n", param);

                    res.add(to);
                }

                // :Emailの要素数を返す。今回はこれに1加えた値をmailIdとして登録する。
                long cnt = session.countEntitiesOfType(Email.class);

                Email mail = new Email(cnt+1, data.title, from, res);

                session.save(mail);
            });

            tx.commit();
        }
    }

}
