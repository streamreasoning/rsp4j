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
        private InstantaneousResponse last_response;
        private final int i;

        public ISTREAM(int i) {
            this.i = i;
        }

        public static RelationToStreamOperator get() {
            return new ISTREAM(1);
        }

        @Override
        public InstantaneousResponse eval(InstantaneousResponse new_response) {
            if (last_response == null) {
                return last_response = new_response;
            } else {
                InstantaneousResponse diff = new_response.minus(last_response);
                last_response = new_response;
                return diff;
            }
        }
    }


    public class DSTREAM implements RelationToStreamOperator {
        private InstantaneousResponse last_response;
        private final int i;

        public DSTREAM(int i) {
            this.i = i;
        }

        public static RelationToStreamOperator get() {
            return new DSTREAM(1);
        }

        @Override
        public InstantaneousResponse eval(InstantaneousResponse new_response) {
            InstantaneousResponse diff = new_response.and(last_response);
            last_response = new_response;
            return diff;
        }
    }
}
