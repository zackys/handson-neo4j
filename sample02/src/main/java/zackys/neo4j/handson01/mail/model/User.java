package zackys.neo4j.handson01.mail.model;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;

@NodeEntity(label="User")
public class User extends Entity {

    /**
     * publicなデフォルトコンストラクタは必須
     */
    public User() {
    }

    /**
     * アプリで使用
     *
     * @param userId ユーザーID
     * @param name ユーザー名
     * @param address メールアドレス
     * @param age 年齢
     */
    public User(String userId, String name, String address, int age) {
        super();
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.age = age;
    }

    // -------------------- //
    // プロパティ

    @Id
    @Property(name="userId")
    private String userId;

    @Index
    @Property(name="name")
    private String name;

    @Index(unique=true)
    @Property(name="addr")
    private String address;

    @Transient
    private int age;

    // -------------------- //
    // 関係

    @Relationship(type="BELONG_TO")
    private Dpt dpt;

    // -------------------- //

    public void setDpt(Dpt dpt) {
        this.dpt = dpt;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return String.format("User[%s]:name=%s\t email=%s\t age=%d", userId, name, address, age);
    }
}
