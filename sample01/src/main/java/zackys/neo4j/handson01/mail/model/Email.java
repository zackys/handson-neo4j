package zackys.neo4j.handson01.mail.model;

import java.util.Set;

public class Email extends Entity {

    /**
     * アプリで使用
     *
     * @param mailId メールID
     * @param title メール表題
     * @param from 送信元
     * @param to 送信先
     */
    public Email(long mailId, String title, User from, Set<User> to) {
    }

    // -------------------- //
    // プロパティ


    // -------------------- //
    // 関係


}
