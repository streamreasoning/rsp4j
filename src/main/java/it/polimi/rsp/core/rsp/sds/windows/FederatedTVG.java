package it.polimi.rsp.core.rsp.sds.windows;

import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.event.map.MapEventBean;
import it.polimi.rsp.core.enums.Maintenance;
import it.polimi.rsp.core.rsp.sds.graphs.TimeVaryingGraph;
import it.polimi.rsp.core.rsp.stream.item.StreamItem;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

@Log4j
@Getter
/*Idea is to produces a serie of SPARQL update to target a remote dataset
 *this would allow to distribute the SDS
 * in Naive mode produced two updates DROPALL and INSERT DATA
 * in Incremental mode produce two updates DROP and DELETE
 * In would be nice to investigate how does this interact with the
 * stream representation Statement/vs triples */

public class FederatedTVG extends NamedWindow {
    private EPStatement statement;

    public FederatedTVG(Maintenance maintenance, EPStatement stmt) {
        super(maintenance, stmt);
    }

    public FederatedTVG(Maintenance maintenance, TimeVaryingGraph g, EPStatement stmt) {
        super(maintenance, g, stmt);
    }


    @Override
    public EPStatement getTriggeringStatement() {
        return statement;
    }

    @Override
    protected void handleSingleIStream(StreamItem underlying) {
        log.debug("Handling single Istream [" + underlying + "]");
        //TODO INSERT DATA
        /*PREFIX dc: <http://purl.org/dc/elements/1.1/>
            INSERT { <http://example/egbook> dc:title  "This is an example title" } WHERE {}*/
    }

    @Override
    protected void IStreamUpdate(EventBean[] newData) {
        if (newData != null && newData.length != 0) {
            log.info("[" + newData.length + "] New Events of type ["
                    + newData[0].getUnderlying().getClass().getSimpleName() + "]");
            for (EventBean e : newData) {
                if (e instanceof MapEventBean) {
                    MapEventBean meb = (MapEventBean) e;
                    if (meb.getProperties() instanceof StreamItem) {
                        handleSingleIStream((StreamItem) e.getUnderlying());
                    } else {
                        for (int i = 0; i < meb.getProperties().size(); i++) {
                            StreamItem underlying = (StreamItem) meb.get("stream_" + i);
                            handleSingleIStream(underlying);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void handleSingleDStream(StreamItem underlying) {
        log.debug("Handling single Dstream [" + underlying + "]");
        //TODO DELETE
        /*PREFIX dc: <http://purl.org/dc/elements/1.1/>
            DELETE DATA
               { GRAPH <http://example/bookStore>
                { <http://example/book1>  dc:title  "Fundamentals of Compiler Desing" } } ;*/
    }

    @Override
    protected void DStreamUpdate(EventBean[] oldData) {
        if (oldData != null) { // TODO
            log.debug("[" + oldData.length + "] Old Events of type ["
                    + oldData[0].getUnderlying().getClass().getSimpleName() + "]");
            for (EventBean e : oldData) {
                if (e instanceof MapEventBean) {
                    MapEventBean meb = (MapEventBean) e;
                    if (meb.getProperties() instanceof StreamItem) {
                        handleSingleDStream((StreamItem) e.getUnderlying());
                    } else {
                        for (int i = 0; i < meb.getProperties().size(); i++) {
                            StreamItem underlying = (StreamItem) meb.get("stream_" + i);
                            handleSingleDStream(underlying);
                        }
                    }
                }
            }
        } else {
            //Remove all the data
            //TODO DROPPALL
            // DROP SILENT GRAPH <graph-1> ;
        }
    }

}