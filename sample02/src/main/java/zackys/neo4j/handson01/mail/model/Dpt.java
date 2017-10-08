package zackys.neo4j.handson01.mail.model;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

/**
 * ハンズオン
 *
 */
@NodeEntity(label="Department")
public class Dpt extends Entity {

    public Dpt() {
    }

    public Dpt(String name) {
        this.name = name;
    }

    @Property(name="name")
    private String name;

    public String getName() {
        return name;
    }
}
