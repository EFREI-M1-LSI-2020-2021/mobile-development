package fr.android.calculator.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.android.calculator.R;

public class DefaultFragment extends Fragment {
    private TextView expression;
    private TextView result;
    private LinearLayout buttonLayout;
    private Button equalButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_default, container, false);
        expression = view.findViewById(R.id.expression);
        result = view.findViewById(R.id.result);
        buttonLayout = view.findViewById(R.id.buttons);

        List<Integer> buttons = Arrays.asList(R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9);

        for(int buttonId : buttons) {
            Button button = view.findViewById(buttonId);
            button.setOnClickListener(this::onClick);
        }

        return view;
    }

    public void onClick(View v) {
        Button button = (Button) v;
        String operation = button.getText().toString();
        expression.setText(String.format("%s%s", expression.getText(), operation));

        if(equalButton == null) {
            equalButton = new MaterialButton(getActivity());
            equalButton.setText("=");
            equalButton.setOnClickListener(this::onEqualClick);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.setMargins(8, 0, 0, 0);
            equalButton.setLayoutParams(params);
            buttonLayout.addView(equalButton);
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
        buttonLayout.removeView(equalButton);
        equalButton = null;
    }
}
