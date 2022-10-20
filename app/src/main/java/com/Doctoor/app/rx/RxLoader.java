package com.Doctoor.app.rx;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.Doctoor.app.rx.functions.PlainAction;
import com.Doctoor.app.rx.functions.PlainConsumer;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public abstract class RxLoader<Source, Result> {

    private final Source mSource;

    public RxLoader(Source source) {
        this.mSource = source;
    }

    @Nullable
    protected abstract Result load(Source source);

    public void load(@NonNull PlainConsumer<Result> consumer) {
        load(consumer, null);
    }

    public void load(@NonNull PlainConsumer<Result> consumer, @Nullable PlainAction onError) {
        Single<Result> single = Single.create(e -> {
            Result result = load(mSource);
            if (result != null) {
                e.onSuccess(result);
            } else {
                e.onError(new NullPointerException("Got null result"));
            }
        });
        single.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(consumer, throwable -> {
                    if (onError != null) {
                        onError.run();
                    }
                });
    }
}
