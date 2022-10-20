package com.Doctoor.app.ui.lazy

fun <T> lazyUnsychronized(initializer: () -> T): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE, initializer)