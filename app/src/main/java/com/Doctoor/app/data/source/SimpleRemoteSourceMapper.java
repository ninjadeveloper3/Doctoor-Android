package com.Doctoor.app.data.source;

import io.reactivex.FlowableEmitter;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

import static com.Doctoor.app.model.ErrorEntity.NO_INTERNET;

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 * <p>
 * You can read more about it in the <a href="https://developer.android.com/arch">Architecture
 * Guide</a>.
 */
public abstract class SimpleRemoteSourceMapper<T> {

    public static final String TAG = "source";

    public SimpleRemoteSourceMapper(FlowableEmitter<Resource<T>> emitter) {

        emitter.onNext(Resource.loading(null));
        // since realm instance was created on Main Thread, so if we need to touch on realm database after calling
        // api (such as save response data to local database, we must make request on main thread
        // by setting shouldUpdateUi params = true
        Disposable disposable = RestHelper.makeRequest(getRemote(), true, response -> {

            saveCallResult(response);
            emitter.onNext(Resource.success(response));
        }, errorEntity -> {
            if (errorEntity.getHttpCode() == NO_INTERNET) {
                emitter.onNext(Resource.network(errorEntity.getMessage(), null));
            } else {

                emitter.onNext(Resource.error(errorEntity.getMessage(), null));
            }
        });

        // set emitter disposable to ensure that when it is going to be disposed, our api request should be disposed as well
        emitter.setDisposable(disposable);
    }

    public abstract Single<T> getRemote();

    public abstract void saveCallResult(T data);
}