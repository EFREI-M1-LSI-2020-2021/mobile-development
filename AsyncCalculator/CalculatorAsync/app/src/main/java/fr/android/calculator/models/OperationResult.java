package fr.android.calculator.models;

public class OperationResult {
    private String operation;
    private double result;

    public OperationResult(String operation, double result) {
        this.operation = operation;
        this.result = result;
    }

    public String getOperation() {
        return operation;
    }

    public double getResult() {
        return result;
    }
}
