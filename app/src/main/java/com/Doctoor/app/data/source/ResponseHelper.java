package com.Doctoor.app.data.source;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.Doctoor.app.DoctoorApp;
import com.Doctoor.app.R;
import com.Doctoor.app.data.remote.GsonProvider;
import com.Doctoor.app.model.ErrorEntity;
import com.Doctoor.app.model.response.ApiErrorResponse;
import com.Doctoor.app.model.response.ResponseHeader;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

import static com.Doctoor.app.model.ErrorEntity.NO_INTERNET;

public class ResponseHelper {

    /**
     * Get http error code from {@link Throwable} if it is instance of {@link HttpException}
     *
     * @param throwable input throwable
     * @return http code or -1 if throwable isn't a instance of {@link HttpException}
     */
    public static int getErrorCode(Throwable throwable) {
        if (throwable instanceof HttpException) {
            return ((HttpException) throwable).code();

        } else if (throwable instanceof ConnectException || throwable instanceof SocketTimeoutException) {
            return NO_INTERNET;
        }
        return -1;
    }

    /**
     * Get error response from all github api's response
     *
     * @param throwable throwable instance from retrofit service's response
     * @return an instance of {@link ApiErrorResponse} contains error message and some other fields
     */
    @Nullable
    public static ApiErrorResponse getErrorResponse(@NonNull Throwable throwable) {
        ResponseBody body = null;
        if (throwable instanceof HttpException) {
            int code = ((HttpException) throwable).response().code();
            if (code == 404) {
                ApiErrorResponse response = new ApiErrorResponse(new ResponseHeader(code, ((HttpException) throwable).response().message()));
                return response;
            }
            body = ((HttpException) throwable).response().errorBody();
        }

        if (body != null) {
            try {
//                Log.d("ApiErrorResponse", body.string());
                String s = body.string();
                ApiErrorResponse response = GsonProvider.makeGson().fromJson(s, ApiErrorResponse.class);
                return response;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Get a error message from retrofit response throwable
     *
     * @param throwable retrofit rx throwable
     * @return error message
     */
    public static String getPrettifiedErrorMessage(@Nullable Throwable throwable) {
        if (throwable instanceof ConnectException || throwable instanceof SocketTimeoutException) {
            return DoctoorApp.Companion.string(R.string.error_no_network);
        }
        if (throwable instanceof HttpException) {
            return ErrorEntity.NETWORK_UNAVAILABLE;
        } else if (throwable instanceof IOException) {
            return ErrorEntity.NETWORK_UNAVAILABLE;
        }
        return ErrorEntity.OOPS;
    }
}
