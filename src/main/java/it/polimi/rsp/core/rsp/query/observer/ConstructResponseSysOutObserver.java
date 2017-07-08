package it.polimi.rsp.core.rsp.query.observer;

import it.polimi.rsp.core.rsp.query.response.ConstructResponse;
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
        ConstructResponse sr = (ConstructResponse) arg;
        if (sr.getCep_timestamp() != last_result && distinct) {
            last_result = sr.getCep_timestamp();
            System.out.println("[" + last_result + "] Result");
            sr.getResults().write(System.out, "TTL");
        }

    }
}
