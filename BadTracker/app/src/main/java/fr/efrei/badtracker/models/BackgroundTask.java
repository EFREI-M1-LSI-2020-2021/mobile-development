package fr.efrei.badtracker.models;

import android.os.AsyncTask;

public class BackgroundTask<K> extends AsyncTask<Void, Void, K> {

    private Execute<K> execute;
    private Listener<K> listener;

    public BackgroundTask(Execute<K> execute, Listener<K> listener) {
        this.execute = execute;
        this.listener = listener;
    }

    @Override
    protected K doInBackground(Void... voids) {
        return execute.run();
    }

    @Override
    protected void onPostExecute(K k) {
        listener.onResult(k);
    }

    public interface Execute<A> {
        A run();
    }

    public interface Listener<A> {
        void onResult(A result);
    }
}
