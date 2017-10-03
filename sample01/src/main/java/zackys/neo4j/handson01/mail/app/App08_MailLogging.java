package zackys.neo4j.handson01.mail.app;

import java.util.ArrayDeque;
import java.util.Queue;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.transaction.Transaction;

import zackys.neo4j.handson01.mail.model.Email;
import zackys.neo4j.handson01.mail.model.MailData;
import zackys.neo4j.handson01.mail.neo4j.ogm.session.Neo4jSessionFactory;

/**
 * ハンズオン
 *
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

            // :Emailの要素数を返す。今回はこれに1加えた値をmailIdとして登録する。
            long cnt = session.countEntitiesOfType(Email.class);

            tx.commit();
        }
    }

}
