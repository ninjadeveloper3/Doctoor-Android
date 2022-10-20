package com.Doctoor.app.data.source

import androidx.annotation.Nullable


class State(
    var status: Status,
    var message: String?
) {

    var hardAlert = false

    override fun toString(): String {
        return "status: $status, message: $message"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        val state = other as State
//        other as State

        if (status != state.status) return false
        if (message != state.message) return false
        if (hardAlert != state.hardAlert) return false

        return if (message != null) message == state.message else state.message == null
    }

    override fun hashCode(): Int {
        var result = status.hashCode()
        result = 31 * result + (message?.hashCode() ?: 0)
        result = 31 * result + hardAlert.hashCode()
        return result
    }

    companion object {

        fun loading(message: String?): State {
            return State(Status.LOADING, message)
        }

        fun error(message: String): State {
            return State(Status.ERROR, message)
        }

        fun success(@Nullable message: String?): State {
            return State(Status.SUCCESS, message)
        }

        fun empty(@Nullable message: String?): State {
            return State(Status.EMPTY, message)
        }

        fun network(@Nullable message: String?): State {
            return State(Status.NETWORK, message)
        }

        fun ideal(@Nullable message: String?): State {
            return State(Status.IDEAL, message)
        }
    }
}
