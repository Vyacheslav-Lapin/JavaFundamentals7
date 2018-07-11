package com.epam.fp;

import org.jetbrains.annotations.Contract;

public interface Exceptional {
    @Contract("_ -> fail")
    @SuppressWarnings("unchecked")
    static <E extends Throwable, T> T sneakyThrow(Throwable e) throws E {
        throw (E) e;
    }
}
