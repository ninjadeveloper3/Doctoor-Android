package com.Doctoor.app.data.source

/**
 * Status of a resource that is provided to the UI.
 *
 *
 * These are usually created by the Repo classes where they return
 * `LiveData<Resource<T>>` to pass back the latest data to the UI with its fetch status.
 */
enum class Status(val value: Int) {
    IDEAL(0),
    SUCCESS(1),
    LOADING(2),
    ERROR(3),
    EMPTY(4),
    NETWORK(5)
}
