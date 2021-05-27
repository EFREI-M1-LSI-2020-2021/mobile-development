package fr.efrei.badtracker.api;

import android.os.AsyncTask;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class ApiTask<T> extends AsyncTask<Call<T>, Void, Response<T>> {

    private Listener<T> listener;

    public ApiTask(Listener<T> listener) {
        this.listener = listener;
    }

    @SafeVarargs
    @Override
    protected final Response<T> doInBackground(Call<T>... params) {
        Call<T> call = params[0];

        try {
            return call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Response<T> response) {
        listener.onResponse(response);
    }

    public interface Listener<A> {
        void onResponse(Response<A> result);
    }
}
