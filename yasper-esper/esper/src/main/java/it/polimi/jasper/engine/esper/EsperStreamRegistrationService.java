package it.polimi.jasper.engine.esper;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.soda.CreateSchemaClause;
import com.espertech.esper.client.soda.SchemaColumnDesc;
import it.polimi.jasper.streams.EPLStream;
import it.polimi.jasper.utils.EncodingUtils;
import it.polimi.yasper.core.exceptions.StreamRegistrationException;
import it.polimi.yasper.core.exceptions.UnregisteredStreamExeception;
import it.polimi.yasper.core.stream.data.DataStreamImpl;
import it.polimi.yasper.core.stream.data.WebDataStream;
import it.polimi.yasper.core.stream.web.WebStream;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

import java.io.StringWriter;
import java.util.*;

@Log4j
public class EsperStreamRegistrationService<T> implements StreamRegistrationService<T> {

    protected final EPAdministrator cepAdm;

    @Getter
    protected Map<String, WebDataStream<T>> registeredStreams;

    public EsperStreamRegistrationService(EPAdministrator cepAdm) {
        this.cepAdm = cepAdm;
        this.registeredStreams = new HashMap<>();
    }

    public DataStreamImpl<T> register(WebStream s) {
        String uri = s.getURI();
        log.info("Registering Stream [" + uri + "]");
        if (!registeredStreams.containsKey(uri)) {
            EPStatement epl = createStream(toEPLSchema(s), uri);
            log.info(epl.getText());
            EPLStream<T> value = new EPLStream<T>(s.getURI(), s, epl);
            registeredStreams.put(uri, value);
            return value;
        } else
            throw new StreamRegistrationException("Stream [" + uri + "] already registered");
    }


    public void unregister(WebStream s) {
        log.info("Unregistering Stream [" + s + "]");

        if (!isRegistered(s.getURI())) {
            throw new UnregisteredStreamExeception("Stream [" + s.getURI() + "] not registered");
        }

        EPStatement statement = cepAdm.getStatement(EncodingUtils.encode(s.getURI()));
        statement.removeAllListeners();
        statement.destroy();
        registeredStreams.remove(EncodingUtils.encode(s.getURI()));
    }

    protected EPStatement createStream(String stream, String uri) {
        String s = EncodingUtils.encode(uri);
        log.debug("EPL Schema Statement [ " + stream.replace(s, uri) + "] uri [" + uri + "]");
        return cepAdm.createEPL(stream, s);
    }

    protected String toEPLSchema(WebStream s) {
        CreateSchemaClause schema = new CreateSchemaClause();
        schema.setSchemaName(EncodingUtils.encode(s.getURI()));
        schema.setInherits(new HashSet<>(Arrays.asList("TStream")));
        List<SchemaColumnDesc> columns = new ArrayList<>();
        schema.setColumns(columns);
        StringWriter writer = new StringWriter();
        schema.toEPL(writer);
        return writer.toString();
    }

    public boolean isRegistered(WebStream s) {
        return isRegistered(s.getURI());
    }

    public boolean isRegistered(String s) {
        return registeredStreams.containsKey(s);
    }

}
