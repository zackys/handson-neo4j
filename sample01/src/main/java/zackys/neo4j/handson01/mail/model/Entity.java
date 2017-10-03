package zackys.neo4j.handson01.mail.model;

import org.neo4j.ogm.annotation.GraphId;

/**
 *
 *
 */
abstract class Entity {

    /**
     * 変数名がidであれば、@GraphIdは無くてもOK
     */
    @GraphId
    private Long id;

    public Long getId() {
        return id;
    }
}