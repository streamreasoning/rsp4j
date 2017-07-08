package it.polimi.rsp.core.rsp.query.r2s;

import it.polimi.rsp.core.rsp.query.response.InstantaneousResponse;

/**
 * Created by riccardo on 07/07/2017.
 */
public interface RelationToStreamOperator {

    public InstantaneousResponse eval(InstantaneousResponse last_response);

    public class RSTREAM implements RelationToStreamOperator {

        public static RelationToStreamOperator get() {
            return new RSTREAM();
        }

        @Override
        public InstantaneousResponse eval(InstantaneousResponse last_response) {
            return last_response;
        }
    }


    public class ISTREAM implements RelationToStreamOperator {
        private InstantaneousResponse r;
        private final int i;

        public ISTREAM(int i) {
            this.i = i;
        }

        public static RelationToStreamOperator get() {
            return new ISTREAM(1);
        }

        @Override
        public InstantaneousResponse eval(InstantaneousResponse last_response) {
            if (r == null) {
                return r = last_response;
            } else {
                InstantaneousResponse diff = last_response.minus(r);
                r = last_response;
                return diff;
            }
        }
    }


    public class DSTREAM implements RelationToStreamOperator {
        private InstantaneousResponse r;
        private final int i;

        public DSTREAM(int i) {
            this.i = i;
        }

        public static RelationToStreamOperator get() {
            return new DSTREAM(1);
        }

        @Override
        public InstantaneousResponse eval(InstantaneousResponse last_response) {
            if (r == null) {
                r = last_response;
                return null;
            } else {
                InstantaneousResponse diff = r.minus(last_response);
                r = last_response;
                return diff;
            }
        }
    }
}
