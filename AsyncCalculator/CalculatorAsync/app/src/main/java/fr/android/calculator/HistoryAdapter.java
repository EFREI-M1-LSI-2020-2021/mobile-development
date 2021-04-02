package fr.android.calculator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.android.calculator.models.OperationResult;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<OperationResult> operations;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView operation;
        private final TextView result;

        public ViewHolder(View view) {
            super(view);
            operation = view.findViewById(R.id.operation);
            result = view.findViewById(R.id.result);
        }

        public TextView getOperation() {
            return operation;
        }

        public TextView getResult() {
            return result;
        }
    }

    public HistoryAdapter() {
        operations = CalculatorHistory.getInstance().getHistory();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.history_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        OperationResult operationResult = operations.get(position);
        viewHolder.getOperation().setText(operationResult.getOperation());
        viewHolder.getResult().setText(Double.toString(operationResult.getResult()));
    }

    @Override
    public int getItemCount() {
        return operations.size();
    }
}

