package org.streamreasoning.rsp4j.esper.engine.esper;

import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.soda.CreateSchemaClause;
import com.espertech.esper.client.soda.SchemaColumnDesc;
import org.streamreasoning.rsp4j.esper.streams.EPLStream;
import org.streamreasoning.rsp4j.esper.utils.EncodingUtils;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.streamreasoning.rsp4j.api.exceptions.StreamRegistrationException;
import org.streamreasoning.rsp4j.api.exceptions.UnregisteredStreamExeception;
import org.streamreasoning.rsp4j.api.stream.data.DataStream;

import java.io.StringWriter;
import java.util.*;

@Log4j
public class EsperStreamRegistrationService<T> implements StreamRegistrationService<T> {

    protected final EPAdministrator cepAdm;

    @Getter
    protected Map<String, DataStream<T>> registeredStreams;

    public EsperStreamRegistrationService(EPAdministrator cepAdm) {
        this.cepAdm = cepAdm;
        this.registeredStreams = new HashMap<>();
    }

    public DataStream<T> register(DataStream s) {
        String uri = s.getName();
        log.info("Registering Stream [" + uri + "]");
        if (!registeredStreams.containsKey(uri)) {
            EPStatement epl = createStream(toEPLSchema(s), uri);
            log.info(epl.getText());
            EPLStream<T> value = new EPLStream<T>(s.getName(), s, epl);
            registeredStreams.put(uri, value);
            return value;
        } else
            throw new StreamRegistrationException("Stream [" + uri + "] already registered");
    }


    public void unregister(DataStream s) {
        log.info("Unregistering Stream [" + s + "]");

        if (!isRegistered(s.getName())) {
            throw new UnregisteredStreamExeception("Stream [" + s.getName() + "] not registered");
        }

        EPStatement statement = cepAdm.getStatement(EncodingUtils.encode(s.getName()));
        statement.removeAllListeners();
        statement.destroy();
        registeredStreams.remove(EncodingUtils.encode(s.getName()));
    }

    protected EPStatement createStream(String stream, String uri) {
        String s = EncodingUtils.encode(uri);
        log.debug("EPL Schema Statement [ " + stream.replace(s, uri) + "] uri [" + uri + "]");
        return cepAdm.createEPL(stream, s);
    }

    protected String toEPLSchema(DataStream s) {
        CreateSchemaClause schema = new CreateSchemaClause();
        schema.setSchemaName(EncodingUtils.encode(s.getName()));
        schema.setInherits(new HashSet<>(Arrays.asList("TStream")));
        List<SchemaColumnDesc> columns = new ArrayList<>();
        schema.setColumns(columns);
        StringWriter writer = new StringWriter();
        schema.toEPL(writer);
        return writer.toString();
    }

    public boolean isRegistered(DataStream s) {
        return isRegistered(s.getName());
    }

    public boolean isRegistered(String s) {
        return registeredStreams.containsKey(s);
    }

}
