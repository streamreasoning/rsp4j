package it.polimi.rsp.core.rsp.query.observer;

import it.polimi.rsp.core.rsp.query.response.SelectResponse;
import lombok.*;
import org.apache.jena.query.ResultSetFormatter;

import java.util.Observable;

/**
 * Created by riccardo on 03/07/2017.
 */

@RequiredArgsConstructor
public class SelectResponseSysOutObserver extends QueryResponseObserver {

    long last_result = -1L;

    @NonNull
    @Getter
    boolean distinct;

    @Override
    public void update(Observable o, Object arg) {
        SelectResponse sr = (SelectResponse) arg;
        if (sr.getCep_timestamp() != last_result && distinct) {
            System.out.println("[" + System.currentTimeMillis() + "] Result at [" + last_result + "]");
            ResultSetFormatter.out(System.out, sr.getResults());
            last_result = sr.getCep_timestamp();
        }

    }
}
