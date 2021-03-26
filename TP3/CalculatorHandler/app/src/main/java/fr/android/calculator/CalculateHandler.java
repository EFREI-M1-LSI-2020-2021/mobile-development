package fr.android.calculator;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class CalculateHandler extends Handler {

    private Context context;
    private TextView expression;
    private TextView result;
    private LinearLayout buttonLayout;
    private Button equalButton;

    public CalculateHandler(Context context, TextView expression, TextView result,
                            LinearLayout buttonLayout, Button equalButton) {
        this.context = context;
        this.expression = expression;
        this.result = result;
        this.buttonLayout = buttonLayout;
        this.equalButton = equalButton;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {

        String str = msg.getData().getString("expression");

        Expression expression = new ExpressionBuilder(str).build();

        String result = null;

        try {
            result = String.format("%s", expression.evaluate());
        }
        catch (Exception ignored) { }

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
