package zackys.neo4j.handson01.mail.model;

import org.neo4j.ogm.annotation.Transient;

/**
 * メールが送信されるたびに生成されるメール送信情報を想定。
 * Nodeとは紐づけない→@Transientを付ける
 *
 */
@Transient
public class MailData {

    /**
     *
     * @param from メールアドレス（from）
     * @param title メール表題
     * @param to メールアドレス（to）
     */
    public MailData(String from, String title, String... to) {
        this.from = from;
        this.title = title;
        this.to = to;
    }

    /**
     * メールアドレス（from）
     */
    public final String from;

    /**
     * メール表題
     */
    public final String title;

    /**
     * メールアドレス（to）
     */
    public final String[] to;
}
