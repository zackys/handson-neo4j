package zackys.neo4j.handson01.mail.model;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;

/**
 *
 *
 */
abstract class Entity {

    /**
     * 変数名がidであれば、「@Id @GeneratedValue」は無くてもOK
     */
    @Id @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }
}