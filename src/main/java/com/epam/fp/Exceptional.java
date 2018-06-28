package com.epam.fp;

public interface Exceptional {
    @SuppressWarnings("unchecked")
    static <E extends Throwable, T> T sneakyThrow(Throwable e) throws E {
        throw (E) e;
    }
}
