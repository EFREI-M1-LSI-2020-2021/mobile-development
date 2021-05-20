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
            return (Response<T>) call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Response<T> response) {

    }

    public interface Listener<T> {
        void onResult(Response<T> result);
    }
}
