package zackys.neo4j.handson01.mail.app;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.neo4j.ogm.session.Session;

import zackys.neo4j.handson01.mail.model.Dpt;
import zackys.neo4j.handson01.mail.model.User;
import zackys.neo4j.handson01.mail.neo4j.ogm.session.Neo4jSessionFactory;

public class App07_MailLogging {

    public static void main(String[] args) {
        execute();
    }

    public static void execute() {
        Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();

        Map<String, String> table = new HashMap<String, String>() {
            {
                this.put("ID0001", "Dpt. 1");
                this.put("ID0002", "Dpt. 1");
                this.put("ID0003", "Dpt. 1");
                this.put("ID0004", "Dpt. 2");
                this.put("ID0005", "Dpt. 2");
                this.put("ID0006", "Dpt. 2");
            }
        };

        Collection<Dpt> dpts = session.loadAll(Dpt.class);

        session.loadAll(User.class).forEach(user->{
            String dptName = table.get(user.getUserId());
            dpts.stream().filter(dpt->{return dpt.getName().equals(dptName);}).forEach(dpt->{user.setDpt(dpt);});


            session.save(user);
        });
    }

}
