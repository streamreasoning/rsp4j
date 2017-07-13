package it.polimi.yasper.core.query.operators.r2s;

import it.polimi.yasper.core.query.response.InstantaneousResponse;

/**
 * Created by riccardo on 07/07/2017.
 */
public interface RelationToStreamOperator {

    InstantaneousResponse eval(InstantaneousResponse last_response);

    class RSTREAM implements RelationToStreamOperator {

        public static RelationToStreamOperator get() {
            return new RSTREAM();
        }

        @Override
        public InstantaneousResponse eval(InstantaneousResponse last_response) {
            return last_response;
        }
    }


    class ISTREAM implements RelationToStreamOperator {
        private final int i;
        private InstantaneousResponse last_response;

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
                InstantaneousResponse diff = new_response.difference(last_response);
                last_response = new_response;
                return diff;
            }
        }
    }


    class DSTREAM implements RelationToStreamOperator {
        private final int i;
        private InstantaneousResponse last_response;

        public DSTREAM(int i) {
            this.i = i;
        }

        public static RelationToStreamOperator get() {
            return new DSTREAM(1);
        }

        @Override
        public InstantaneousResponse eval(InstantaneousResponse new_response) {
            InstantaneousResponse diff = last_response.difference(new_response);
            last_response = new_response;
            return diff;
        }
    }
}
