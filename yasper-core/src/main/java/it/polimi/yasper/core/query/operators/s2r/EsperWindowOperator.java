package it.polimi.yasper.core.query.operators.s2r;

import com.espertech.esper.client.EPStatement;
import it.polimi.yasper.core.timevarying.TimeVaryingGraph;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by riccardo on 12/07/2017.
 */
@Getter
@Setter

public class EsperWindowOperator implements WindowOperator {

    private EPStatement eps;

    public EsperWindowOperator(EPStatement eps) {
        this.eps = eps;
    }

    @Override
    public void addListener(TimeVaryingGraph defaultTVG) {
        eps.addListener(defaultTVG);
    }

    @Override
    public String getName() {
        return eps.getName();
    }

    @Override
    public String getText() {
        return eps.getText();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        else if (o == null) return false;
        else if (o instanceof EPStatement)
            return eps.equals(o);
        else if (o instanceof EsperWindowOperator) {
            EsperWindowOperator that = (EsperWindowOperator) o;
            return eps != null ? eps.equals(that.eps) : that.eps == null;
        } else if (getClass() != o.getClass()) {
            return false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return eps != null ? eps.hashCode() : 0;
    }
}
