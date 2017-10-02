package zackys.neo4j.handson01.mail.app;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.ogm.session.Session;

import zackys.neo4j.handson01.mail.model.Dpt;
import zackys.neo4j.handson01.mail.neo4j.ogm.session.Neo4jSessionFactory;

public class App05_SaveDepartment {

    public static void main(String[] args) {
        execute();
    }

    public static void execute() {
        Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();

        List<Dpt> users = new ArrayList<Dpt>() {
            {
                this.add(new Dpt("Dpt. 1"));
                this.add(new Dpt("Dpt. 2"));

                this.add(new Dpt("Dpt. 3"));
            }
        };

        users.stream().forEach(session::save);
    }

}
