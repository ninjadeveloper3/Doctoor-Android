package com.Doctoor.app.rx.functions;

import io.reactivex.functions.Function;


public interface PlainFunction<T, R> extends Function<T, R> {
    /**
     * Apply some calculation to the input value and return some other value.
     * @param t the input value
     * @return the output value
     */
    @Override
    R apply(T t);
}