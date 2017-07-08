package it.polimi.rsp.core.rsp.query.observer;

import it.polimi.rsp.core.rsp.query.response.ConstructResponse;
import it.polimi.rsp.core.rsp.query.response.SelectResponse;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.jena.query.ResultSetFormatter;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by riccardo on 03/07/2017.
 */

@RequiredArgsConstructor
public class GenericResponseSysOutObserver extends QueryResponseObserver {

    long last_result = -1L;

    @NonNull
    @Getter
    boolean distinct;

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof SelectResponse) {
            SelectResponse sr = (SelectResponse) arg;
            if (sr.getCep_timestamp() != last_result && distinct) {
                last_result = sr.getCep_timestamp();
                System.out.println("[" + System.currentTimeMillis() + "] Result at [" + last_result + "]");
                ResultSetFormatter.out(System.out, sr.getResults());
            }
        }else if(arg instanceof ConstructResponse){
            ConstructResponse sr = (ConstructResponse) arg;

            if (sr.getCep_timestamp() != last_result && distinct) {
                sr.getResults().write(System.out, "TTL");
                last_result = sr.getCep_timestamp();
            }
        }

    }
}
