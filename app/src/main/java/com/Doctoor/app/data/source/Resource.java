package com.Doctoor.app.data.source;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * A generic class that holds a value with its loading status.
 *
 * @param <T>
 */

public class Resource<T> {

    @NonNull
    public final State state;

    @Nullable
    public final T data;

    public Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.state = new State(status, message);
        this.data = data;
    }

    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> empty(String msg, @Nullable T data) {
        return new Resource<>(Status.EMPTY, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(Status.ERROR, data, msg);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(Status.LOADING, data, null);
    }

    public static <T> Resource<T> ideal(String msg, @Nullable T data) {
        return new Resource<>(Status.IDEAL, data, msg);
    }

    public static <T> Resource<T> network(String msg, @Nullable T data) {
        return new Resource<>(Status.NETWORK, data, msg);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Resource<?> resource = (Resource<?>) o;

        return resource.state.equals(this.state) && data != null ? data.equals(resource.data) : resource.data == null;
    }

    @Override
    public int hashCode() {
        int result = state.hashCode();
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "status=" + state.getStatus() +
                ", message='" + state.getMessage() + '\'' +
                ", data=" + data +
                '}';
    }
}
