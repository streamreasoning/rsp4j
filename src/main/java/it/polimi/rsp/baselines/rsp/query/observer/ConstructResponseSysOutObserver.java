package it.polimi.rsp.baselines.rsp.query.observer;

import it.polimi.rsp.baselines.rsp.query.response.ConstructResponse;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Observable;

/**
 * Created by riccardo on 03/07/2017.
 */

@RequiredArgsConstructor
public class ConstructResponseSysOutObserver extends QueryResponseObserver {

    long last_result = -1L;

    @NonNull
    @Getter
    boolean distinct;

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("[" + System.currentTimeMillis() + "] Result");
        ConstructResponse sr = (ConstructResponse) arg;

        if (sr.getCep_timestamp() != last_result && distinct) {
            sr.getResults().write(System.out, "JSON-LD");
            last_result = sr.getCep_timestamp();
        }

    }
}
