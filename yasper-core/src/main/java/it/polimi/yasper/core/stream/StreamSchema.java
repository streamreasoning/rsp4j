package it.polimi.yasper.core.stream;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by riccardo on 10/07/2017.
 */
public interface StreamSchema {

    // TODO i can use this to reduce the overhead around StreamItem
    StreamSchema UNKNOWN = new StreamSchema() {

    };

    default Type getType() {
        return Object.class;
    }

    class Factory {

        private static HashMap<Type, StreamSchema> registered_schemas;

        static {

            registered_schemas = new HashMap<>();
        }

        public static StreamSchema wrap(Type t) {
            return registered_schemas.containsKey(t) ? registered_schemas.get(t) : UNKNOWN;
        }

        public static void registerSchema(Type t, StreamSchema s) {
            if (!registered_schemas.containsKey(t)) {
                registered_schemas.put(t, s);
            } else
                throw new RuntimeException("Already Registered");
        }

    }

}
