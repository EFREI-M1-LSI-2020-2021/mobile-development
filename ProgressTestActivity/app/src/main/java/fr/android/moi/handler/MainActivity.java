package fr.android.moi.handler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private Handler handler;
    private ProgressBar progress;
    private TestTask testTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress = (ProgressBar) findViewById(R.id.progressBar1);
        handler = new Handler();
    }

    public void startProgress(View view) throws MalformedURLException {
        /*Runnable runnable = () -> {
            for (int i = 0; i <= 10; i++) {
                final int value = i;
                // simulate a slow network !
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(() -> progress.setProgress(value));
            }
        };
        new Thread(runnable).start();*/

        testTask = new TestTask();
        testTask.execute(new URL("http://localhost"));
    }

    public void setProgressPercent(int value) {
        handler.post(() -> progress.setProgress(value));
    }

    private class TestTask extends AsyncTask<URL, Integer, Integer> {
        @Override
        protected Integer doInBackground(URL... urls) {
            int i;
            for (i = 0; i <= 10; i++) {
                // simulate a slow network !
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i);
            }
            return i;
        }

        protected void onProgressUpdate(Integer... val) {
            setProgressPercent(val[0]);
        }
        protected void onPostExecute(Integer val) {
            Toast.makeText(getApplication(), "Progress done", Toast.LENGTH_SHORT);
        }
    }
}