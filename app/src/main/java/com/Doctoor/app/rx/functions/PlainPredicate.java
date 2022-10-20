package com.Doctoor.app.rx.functions;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Predicate;

/**
 * Like {@link Predicate but without exception}
 */

public interface PlainPredicate<T> extends Predicate<T> {

    /**
     * Test the given input value and return a boolean.
     *
     * @param t the value
     * @return the boolean result
     */
    @Override
    boolean test(@NonNull T t);
}
