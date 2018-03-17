package com.kemalettinsargin.mylib.customListeners;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CancellableCallback<T> implements Callback<T> {

    private Callback<T> callback;

    private boolean canceled;

    private CancellableCallback() {}

    public CancellableCallback(Callback<T> callback) {
        this.callback = callback;
        canceled = false;
    }

    public void cancel() {
        canceled = true;
        callback = null;
    }

    @Override
    public void onResponse(Call<T> o, Response<T> response) {
        if (!canceled) {
            callback.onResponse(o, response);
        }
    }

    @Override
    public void onFailure(Call<T> call,Throwable error) {
        if (!canceled) {
            callback.onFailure(call,error);
        }
    }
}