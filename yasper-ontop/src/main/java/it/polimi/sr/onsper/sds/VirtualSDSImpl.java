package it.polimi.sr.onsper.sds;

import it.polimi.rspql.timevarying.TimeVarying;
import it.polimi.sr.onsper.engine.Relation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.SchemaPlus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by riccardo on 01/07/2017.
 * <p>
 * <p>
 * the role of the sds in this context is limited, sine there is no such a thing
 * like a mapping for a dataset as a whole. However, the abstraction is still present as a
 * way to conform betweeen the two.
 */

@Log4j
@RequiredArgsConstructor
public class VirtualSDSImpl implements VirtualSDS{

    @NonNull
    private final SchemaPlus rootSchema;

    @NonNull
    private final CalciteConnection connection;

    private final Map<String, TimeVarying<Relation>> named_tvr = new HashMap<>();

    private TimeVarying<Relation> default_tvr;

    @Override
    public void beforeEval() {

    }

    @Override
    public void afterEval() {

    }

}