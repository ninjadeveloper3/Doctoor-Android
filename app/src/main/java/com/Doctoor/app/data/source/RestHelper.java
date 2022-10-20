package com.Doctoor.app.data.source;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.Doctoor.app.model.ErrorEntity;
import com.Doctoor.app.model.response.ApiErrorResponse;
import com.Doctoor.app.rx.functions.PlainConsumer;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Helper class to deal with operations on retrofit Rest API calling
 */

public class RestHelper {

    /**
     * Create new retrofit api request
     *
     * @param request          single api request
     * @param shouldUpdateUi   true if should update UI after request done
     * @param responseConsumer consume parsed response data (in pojo object)
     * @param errorConsumer    consume {@link ErrorEntity} object which is construct by which error is occurred
     *                         with a code and an message (will be shown to user)
     *                         the error might by a HttpException, Runtime Exception, or an error respond from back-end api...
     * @param <T>              Type of response body
     * @return a disposable
     */
    public static <T> Disposable makeRequest(
            Single<T> request, boolean shouldUpdateUi,
            @NonNull PlainConsumer<T> responseConsumer,
            @Nullable PlainConsumer<ErrorEntity> errorConsumer) {

        Single<T> single = request.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io());
        if (shouldUpdateUi) {
            single = single.observeOn(AndroidSchedulers.mainThread());
        }
        return single.subscribe(responseConsumer, throwable -> {
            // handle error
            throwable.printStackTrace();
            if (errorConsumer != null) {
                int code = ResponseHelper.getErrorCode(throwable);
                String message;

                ApiErrorResponse errorResponse = ResponseHelper.getErrorResponse(throwable);
                if (errorResponse != null && errorResponse.getResponseHeader() != null && errorResponse.getResponseHeader().getResponseMessage() != null) {
                    message = errorResponse.getResponseHeader().getResponseMessage();
                } else {
                    message = ResponseHelper.getPrettifiedErrorMessage(throwable);
                }
                errorConsumer.accept(new ErrorEntity(message, code));
            }
        });
    }

    /**
     * Create a mapper from retrofit service to {@link Resource} with rx's {@link Flowable}
     * To indicate current state while execute an rest api (loading, error, success with status and message if error)
     *
     * @param remote from retrofit service
     * @param onSave will be called after success response come, to save response data into local database
     * @param <T>    type of response
     * @return a {@link Flowable} instance to deal with progress showing and error handling
     */
    public static <T> Flowable<Resource<T>> createRemoteSourceMapper(@Nullable Single<T> remote,
                                                                     @Nullable PlainConsumer<T> onSave) {
        return Flowable.create(emitter -> {
            new SimpleRemoteSourceMapper<T>(emitter) {

                @Override
                public Single<T> getRemote() {
                    return remote;
                }

                @Override
                public void saveCallResult(T data) {
                    if (onSave != null) {
                        onSave.accept(data);
                    }
                }
            };
        }, BackpressureStrategy.BUFFER);
    }

}
