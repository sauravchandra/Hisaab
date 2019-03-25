package saurav.chandra.hisaab;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class activity_transaction_view extends AppCompatActivity {

    private TextView tr_details,tr_time,tr_participants,tr_participants_label,tr_paid_by,tr_paid_by_label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_transaction_view);

        tr_details = findViewById(R.id.transaction_details);
        tr_time = findViewById(R.id.transaction_time);
        tr_participants = findViewById(R.id.transaction_participants);
        tr_paid_by = findViewById(R.id.transaction_paid_by);
        tr_participants_label = findViewById(R.id.transaction_participants_label);
        tr_paid_by_label = findViewById(R.id.transaction_paid_by_label);

        Intent intent = getIntent();
        String transaction_details = intent.getStringExtra("transaction_details");
        String transaction_time = intent.getStringExtra("transaction_time");
        String transaction_type = intent.getStringExtra("transaction_type");

        if(transaction_type.equals("shared")){
            ArrayList<String> transaction_participants = intent.getStringArrayListExtra("transaction_participants");
            String transaction_paid_by = intent.getStringExtra("transaction_paid_by");

            StringBuilder builder = new StringBuilder();
            for (String details : transaction_participants) {
                builder.append(details + "\n");
            }

            tr_details.setText(transaction_details);
            tr_time.setText(transaction_time);
            tr_participants.setText(builder.toString());
            tr_paid_by.setText(transaction_paid_by);
        }
        if(transaction_type.equals("personal")){
            tr_details.setText(transaction_details);
            tr_time.setText(transaction_time);
            tr_participants.setVisibility(View.GONE);
            tr_paid_by.setVisibility(View.GONE);
            tr_participants_label.setVisibility(View.GONE);
            tr_paid_by_label.setVisibility(View.GONE);
        }

    }

}
