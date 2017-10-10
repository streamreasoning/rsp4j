package it.polimi.yasper.core.stream;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by riccardo on 10/07/2017.
 */
public interface StreamSchema {

    // TODO i can use this to reduce the overhead around StreamItem
    StreamSchema UNKNOWN = new StreamSchema() {

        @Override
        public Set<SchemaEntry> entrySet() {
            return new HashSet<>();
        }

        @Override
        public String toString() {
            return "UNKNOWN";
        }
    };

    default Class getType() {
        return Object.class;
    }

    Set<SchemaEntry> entrySet();

    class Factory {

        private static HashSet<StreamSchema> registered_schemas;

        static {

            registered_schemas = new HashSet<>();
        }

        public static StreamSchema wrap(Class c) {
            for (StreamSchema s : registered_schemas) {
                if (c.isAssignableFrom(s.getType()) || s.getType().isAssignableFrom(c)) {
                    System.out.println("Assignable");
                    return s;
                }
            }
            return UNKNOWN;
        }

        public static void registerSchema(StreamSchema s) {
            registered_schemas.add(s);
        }

    }

}
