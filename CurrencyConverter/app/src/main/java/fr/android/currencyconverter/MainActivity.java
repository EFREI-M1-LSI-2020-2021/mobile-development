package fr.android.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView text;
    private RadioButton euroButton;
    private RadioButton dollarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        text = findViewById(R.id.textView);
        euroButton = findViewById(R.id.euro);
        dollarButton = findViewById(R.id.dollar);
    }

    public void convert(View view) {
        float number = -1;
        try {
            number = checkNumber(editText.getText().toString());
            if (number <= 0) {
                Toast.makeText(this, getString(R.string.numberHigher0), Toast.LENGTH_LONG).show();
                return;
            }
        }
        catch (Exception ignored) { }

        if(number == -1) {
            return;
        }

        if(euroButton.isChecked()) {
            euroButton.setChecked(false);
            dollarButton.setChecked(true);
            double val =convertDollarToEuro(number);
            text.setText(String.format("%s %s euros", getString(R.string.conversion), val));
        }
        else {
            euroButton.setChecked(true);
            dollarButton.setChecked(false);
            double val =convertEuroDollar(number);
            text.setText(String.format("%s %s dollars", getString(R.string.conversion), val));
        }
    }

    private float checkNumber(String string) throws NumberFormatException {
        if(string.length() > 1) {
            return Float.parseFloat(string);
        }

        Toast.makeText(this, getString(R.string.enterValidNumber), Toast.LENGTH_LONG).show();
        return -1;
    }

    private double convertDollarToEuro(float dollar) {
        return dollar * 0.84;
    }

    private double convertEuroDollar(float euro) {
        return euro * 1.19;
    }
}