package fr.android.calculator;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

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

        Expression expression = new ExpressionBuilder(strings[0]).build();

        try {
            return String.format("%s", expression.evaluate());
        }
        catch (Exception ignored) { }

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
