package saurav.chandra.hisaab;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {
    private List<Transaction> transaction;
    protected Context context;
    public RecyclerViewAdapter(Context context, List<Transaction> transaction) {
        this.transaction = transaction;
        this.context = context;
    }
    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolders viewHolder = null;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_list, parent, false);
        viewHolder = new RecyclerViewHolders(layoutView, transaction);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, final int position) {
        holder.tv_amount_and_description.setText("Rs."+transaction.get(position).getAmount()+" for "+transaction.get(position).getDescription());
        holder.tv_time_and_date.setText(transaction.get(position).getTime());

        holder.tv_amount_and_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, activity_transaction_view.class);
                intent.putExtra("transaction_details","Rs."+transaction.get(position).getAmount()+" for "+transaction.get(position).getDescription());
                intent.putExtra("transaction_time",transaction.get(position).getTime() );
                intent.putExtra("transaction_type", transaction.get(position).getType());
                intent.putExtra("transaction_participants", transaction.get(position).getParticipants());
                intent.putExtra("transaction_paid_by", transaction.get(position).getPaidBy());
                context.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return this.transaction.size();
    }
}