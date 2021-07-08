package org.streamreasoning.rsp4j.api.operators.s2r.execution.instance;


public class WindowImpl implements Window {

    protected long c, o;
    protected boolean closed;


    public WindowImpl(long o, long c) {
        this.o = o;
        this.c = c;
        this.closed = true;
    }

    public long getC() {
        return c;
    }

    public long getO() {
        return o;
    }

    @Override
    public boolean isClosed() {
        return closed;
    }

    @Override
    public boolean equals(Object o1) {
        if (this == o1) return true;
        if (o1 == null || getClass() != o1.getClass()) return false;

        WindowImpl window = (WindowImpl) o1;

        if (c != window.c) return false;
        return o == window.o;
    }

    @Override
    public String toString() {
        return "Window [" + o + "," + c + ')';
    }

    @Override
    public int hashCode() {
        int result = (int) (c ^ (c >>> 32));
        result = 31 * result + (int) (o ^ (o >>> 32));
        return result;
    }
}
