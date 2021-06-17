package org.streamreasoning.rsp4j.api.operators.r2s;

public interface WindowParameter {

    Object get();

    Class<?> type();

    static WindowParameter wrap(Object o) {
        return new WindowParameter() {
            @Override
            public Object get() {
                return o;
            }

            @Override
            public Class<?> type() {
                return o.getClass();
            }
        };
    }

}
