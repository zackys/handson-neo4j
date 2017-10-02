package zackys.neo4j.handson01.mail.model;

import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;

public class User extends Entity {

    /**
     * デフォルトコンストラクタは必要
     */
    public User() {
    }

    /**
     * アプリで使用
     *
     * @param userId
     * @param name
     * @param address
     * @param age
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

    @Property
    String userId;

    @Property
    String name;

    @Index(unique=true) // ★コメントアウト→はずす
    @Property
    String address;

    @Transient
    int age;

    // -------------------- //
    // 関係

    @Relationship(type="BELONG_TO") // ★コメントアウト→はずす
    Dpt dpt;

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
