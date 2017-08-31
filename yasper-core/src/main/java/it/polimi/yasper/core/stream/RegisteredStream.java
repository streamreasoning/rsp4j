package it.polimi.yasper.core.stream;

import com.espertech.esper.client.EPStatement;
import lombok.AllArgsConstructor;

/**
 * Created by riccardo on 14/08/2017.
 */
@AllArgsConstructor
public class RegisteredStream implements Stream {

    private Stream s;
    private EPStatement e;

    public String getInternalName() {
        return e.getName();
    }

    @Override
    public String getURI() {
        return s.getURI();
    }

    @Override
    public String toEPLSchema() {
        return e.toString();
    }

}
