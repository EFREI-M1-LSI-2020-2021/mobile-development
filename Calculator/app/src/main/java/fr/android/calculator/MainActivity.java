package fr.android.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.android.material.button.MaterialButton;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {

    private TextView expression;
    private TextView result;
    private LinearLayout buttons;
    private Button equalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expression = findViewById(R.id.expression);
        result = findViewById(R.id.result);
        buttons = findViewById(R.id.buttons);
    }

    public void onClick(View v) {
        Button button = (Button) v;
        String operation = button.getText().toString();
        expression.setText(String.format("%s%s", expression.getText(), operation));

        if(equalButton == null) {
            equalButton = new MaterialButton(this);
            equalButton.setText("=");
            equalButton.setOnClickListener(this::onEqualClick);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.setMargins(8, 0, 0, 0);
            equalButton.setLayoutParams(params);
            buttons.addView(equalButton);
        }
    }

    public void onEqualClick(View v) {
        Expression expression = new ExpressionBuilder(this.expression.getText().toString()).build();
        String result = "Invalid expression";

        try {
            result = String.format("%s", expression.evaluate());
        }
        catch (Exception e) {}

        this.result.setText(result);
        this.expression.setText("");
        buttons.removeView(equalButton);
        equalButton = null;
    }
}