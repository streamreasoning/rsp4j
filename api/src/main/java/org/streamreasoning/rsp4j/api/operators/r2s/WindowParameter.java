package org.streamreasoning.rsp4j.api.operators.r2s;

public interface WindowParameter {

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

    Object get();

    Class<?> type();

}
