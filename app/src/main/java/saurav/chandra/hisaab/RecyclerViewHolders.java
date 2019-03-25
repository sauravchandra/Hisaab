package saurav.chandra.hisaab;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewHolders extends RecyclerView.ViewHolder{
    private static final String TAG = RecyclerViewHolders.class.getSimpleName();
    public TextView tv_amount_and_description,tv_time_and_date;
    private List<Transaction> transactionObject;
    public RecyclerViewHolders(final View itemView, final List<Transaction> taskObject) {
        super(itemView);
        this.transactionObject = taskObject;
        tv_amount_and_description = itemView.findViewById(R.id.transaction_amount_and_description);
        tv_time_and_date = itemView.findViewById(R.id.transaction_time_and_date);
    }
}