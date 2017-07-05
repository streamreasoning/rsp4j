package it.polimi.rsp.baselines.rsp.query.observer;

import it.polimi.rsp.baselines.rsp.query.response.SelectResponse;
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
        System.out.println("[" + System.currentTimeMillis() + "] Result");
        SelectResponse sr = (SelectResponse) arg;

        if (sr.getCep_timestamp() != last_result && distinct) {
            ResultSetFormatter.out(System.out, sr.getResults());
            last_result = sr.getCep_timestamp();
        }

    }
}
