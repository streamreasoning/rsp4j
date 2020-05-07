package neo4j;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.event.map.MapEventBean;
import it.polimi.jasper.secret.content.ContentEventBean;
import it.polimi.jasper.streams.items.StreamItem;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.neo4j.graphdb.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Log4j
public class ContentPGraphBean extends ContentEventBean<PGraph, PGraph> {

    protected GraphDatabaseService db;

    @Setter
    private long last_timestamp_changed;
    private String p = "Person";
    public RelationshipType friends = RelationshipType.withName("friends");

    public ContentPGraphBean(GraphDatabaseService db) {
        this.db = db;
        this.elements = new ArrayList<>();
    }

    public void eval(EventBean[] newData, EventBean[] oldData) {
        DStreamUpdate(oldData);
        IStreamUpdate(newData);
    }

    protected void handleSingleIStream(StreamItem st) {
        // log.debug("Handling single IStreamTest [" + st + "]");
        elements.add((PGraph) st.getTypedContent());
    }

    private void IStreamUpdate(EventBean[] newData) {
        if (newData != null && newData.length != 0) {
            log.debug("[" + newData.length + "] New Events of type ["
                    + newData[0].getUnderlying().getClass().getSimpleName() + "]");
            for (EventBean e : newData) {
                if (e instanceof MapEventBean) {
                    MapEventBean meb = (MapEventBean) e;
                    if (meb.getProperties() instanceof StreamItem) {
                        handleSingleIStream((StreamItem) e.getUnderlying());
                    } else {
                        for (int i = 0; i < meb.getProperties().size(); i++) {
                            StreamItem st = (StreamItem) meb.get("stream_" + i);
                            handleSingleIStream(st);
                        }
                    }
                }
            }
        }
    }

    protected void DStreamUpdate(EventBean[] oldData) {
        elements.clear();
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public void add(PGraph e) {
        elements.add(e);
    }

    public void add(EventBean e) {
        if (e instanceof MapEventBean) {
            MapEventBean meb = (MapEventBean) e;
            if (meb.getUnderlying() instanceof StreamItem) {
                elements.add((PGraph) ((StreamItem) meb.getUnderlying()).getTypedContent());
            } else {
                for (int i = 0; i < meb.getProperties().size(); i++) {
                    StreamItem st = (StreamItem) meb.get("stream_" + i);
                    elements.add((PGraph) st.getTypedContent());
                }
            }
        }
    }

    @Override
    public Long getTimeStampLastUpdate() {
        return last_timestamp_changed;
    }

    @Override
    public PGraph coalesce() {
        Transaction tx = db.beginTx();

        tx.execute("MATCH (n) DETACH DELETE n");

        //TODO First run query (delete n when n.prov == stream(name)) | added the execute delete query
        //TODO create a query that adds all the information into the elements

        /*
            MERGE (p1:Person { name: event.initiated })
            MERGE (p2:Person { name: event.accepted })
            CREATE (p1)-[:FRIENDS { when: event.date }]->(p2)
        */
        elements.forEach(pGraph -> {
            try {

                //TODO add the name of the window operator.
                //one can see this as a
                pGraph.nodes().forEach(node -> {
                    Node node1 = tx.createNode(Label.label(p));
                    node1.setProperty("name", node);
                    node1.setProperty("__window", "win1");
                });
                pGraph.edges().forEach(edge -> {
                    Node firstNode = tx.findNode(Label.label(p), "name", edge[0]);
                    Node secondNode = tx.findNode(Label.label(p), "name", edge[1]);
                    firstNode.createRelationshipTo(secondNode, friends);
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        //        elements.stream().flatMap(ig->GraphUtil.findAll(ig).toList().stream()).forEach(this.graph::add);

        tx.commit();
        tx.close();

        //TODO ideally, we should return a PGraph built out the new graphdb.
        return new PGraph() {


            @Override
            public List<String> nodes() throws FileNotFoundException {
                Transaction tx = db.beginTx();
                List<String> emptyList = Collections.EMPTY_LIST;
                Result execute = tx.execute("MATCH (n) RETURN n");
                while (execute.hasNext()) {
                    emptyList.add(execute.next().toString());
                }
                tx.commit();
                tx.close();
                return emptyList;
            }

            @Override
            public List<String[]> edges() throws FileNotFoundException {
                Transaction tx = db.beginTx();
                List<String[]> emptyList = Collections.EMPTY_LIST;
                Result execute = tx.execute("MATCH (n)-[p]->(m) RETURN n,m,p");
                while (execute.hasNext()) {
                    Map<String, Object> next = execute.next();
                    emptyList.add(new String[]{
                            next.get("n").toString(),
                            next.get("m").toString(),
                            next.get("p").toString()});
                }
                tx.commit();
                tx.close();
                return emptyList;
            }

            @Override
            public long timestamp() {
                return System.currentTimeMillis();
            }
        };
    }

    @Override
    public String toString() {
        return elements.toString();
    }

    public EventBean[] asArray() {
        return elements.toArray(new EventBean[size()]);
    }

    public void update(EventBean[] newData, EventBean[] oldData, long event_time) {
        eval(newData, oldData);
        setLast_timestamp_changed(event_time);
    }

    public void replace(PGraph pGraph) {

        Transaction tx = db.beginTx();

        tx.execute("MATCH (n) DETACH DELETE n");

        try {
            pGraph.nodes().forEach(node -> {
                tx.createNode(Label.label(p)).setProperty("name", node);
            });
            pGraph.edges().forEach(edge -> {
                Node firstNode = tx.findNode(Label.label(p), "name", edge[0]);
                Node secondNode = tx.findNode(Label.label(p), "name", edge[1]);
                firstNode.createRelationshipTo(secondNode, friends);
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        tx.commit();
        tx.close();
    }
}
