package fr.efrei.badtracker.models;

import android.os.AsyncTask;

public class BackgroundTask<T> extends AsyncTask<Object, Void, T> {

    private Execute<T> execute;
    private Listener<T> listener;

    public BackgroundTask(Execute<T> execute, Listener<T> listener) {
        this.execute = execute;
        this.listener = listener;
    }

    @Override
    protected T doInBackground(Object... objects) {
        return execute.run();
    }

    @Override
    protected void onPostExecute(T t) {
        listener.onResult(t);
    }

    public interface Execute<T> {
        T run();
    }

    public interface Listener<T> {
        void onResult(T result);
    }
}
