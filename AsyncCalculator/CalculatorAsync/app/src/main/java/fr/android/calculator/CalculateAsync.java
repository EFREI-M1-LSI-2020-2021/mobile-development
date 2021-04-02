package fr.android.calculator;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class CalculateAsync extends AsyncTask<String, String, String> {

    private Context context;
    private TextView expression;
    private TextView result;
    private LinearLayout buttonLayout;
    private Button equalButton;

    public CalculateAsync(Context context, TextView expression, TextView result,
                          LinearLayout buttonLayout, Button equalButton) {
        this.context = context;
        this.expression = expression;
        this.result = result;
        this.buttonLayout = buttonLayout;
        this.equalButton = equalButton;
    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            Socket socket = new Socket("192.168.1.14", 4242);

            OutputStream os = socket.getOutputStream();
            String operation = strings[0] + "\n";
            os.write(operation.getBytes());
            os.flush();

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            double result = dis.readDouble();

            socket.close();

            CalculatorHistory.getInstance().add(operation, result);

            return Double.toString(result);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {

        if(result == null) {
            Toast.makeText(context, "Invalid expression", Toast.LENGTH_SHORT).show();
        }
        else {
            this.result.setText(result);
        }

        this.expression.setText("");

        if(this.equalButton == null) {
            final Button equalButton = buttonLayout.findViewById(R.id.equal);
            buttonLayout.removeView(equalButton);
        }
        else {
            this.equalButton.setVisibility(View.GONE);
        }
    }
}
