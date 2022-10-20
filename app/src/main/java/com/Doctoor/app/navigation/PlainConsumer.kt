package com.Doctoor.app.navigation

/**
 * @param <T>
</T> */
interface PlainConsumer<T> {
    /**
     * Consume the given value.
     * @param t the value
     */
    fun accept(t: T)
}
