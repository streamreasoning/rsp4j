package simple.querying;

import it.polimi.yasper.core.operators.r2s.RelationToStreamOperator;
import it.polimi.yasper.core.querying.result.SolutionMapping;

/**
 * Created by riccardo on 05/09/2017.
 */
public class Istream<T> implements RelationToStreamOperator<T> {
    private final int i;
    private SolutionMapping<T> last_response;

    public Istream(int i) {
        this.i = i;
    }

    public static RelationToStreamOperator get() {
        return new Istream(1);
    }

    @Override
    public T eval(SolutionMapping<T> new_response, long ts) {
        if (last_response == null) {
            last_response = new_response;
            return last_response.get();
        } else {
            SolutionMapping<T> diff = new_response.difference(last_response);
            last_response = new_response;
            return diff.get();
        }
    }

}
