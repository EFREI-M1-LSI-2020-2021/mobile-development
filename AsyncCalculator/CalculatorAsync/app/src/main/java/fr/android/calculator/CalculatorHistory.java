package fr.android.calculator;

import java.util.ArrayList;
import java.util.List;

import fr.android.calculator.models.OperationResult;

public class CalculatorHistory {

    private static CalculatorHistory instance;

    private List<OperationResult> history;

    private CalculatorHistory() {
        history = new ArrayList<>();
    }

    public static CalculatorHistory getInstance() {
        if(instance == null) {
            instance = new CalculatorHistory();
        }
        return instance;
    }

    public void add(String operation, double result) {
        history.add(new OperationResult(operation, result));
    }

    public List<OperationResult> getHistory() {
        return history;
    }
}
