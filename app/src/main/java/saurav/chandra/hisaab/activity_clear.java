package saurav.chandra.hisaab;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import static saurav.chandra.hisaab.activity_main.uid;

public class activity_clear extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference transactionRef,clearRef;
    private static float total_expenditure=0,total_spent=0,total_personal = 0;
    private SharedPreferences prefs;
    private TextView tv_total_expenditure, tv_total_spent, tv_total_personal, tv_clear_request;
    private Button btn_clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_clear);

        total_expenditure = total_personal = total_spent = 0;
        database = ((Hisaab) this.getApplication()).getDatabase();
        transactionRef = database.getReference(uid);
        clearRef = database.getReference().child("clear_request_count");
        prefs = getSharedPreferences("user_pref", MODE_PRIVATE);

        tv_total_expenditure = findViewById(R.id.total_expenditure);
        tv_total_spent = findViewById(R.id.total_spent);
        tv_total_personal = findViewById(R.id.total_personal);
        tv_clear_request = findViewById(R.id.clear_request);

        btn_clear = findViewById(R.id.button_clear);

        //showProgressDialog();
        transactionRef.child("shared").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot sharedTransactionSnapshot) {
                if (sharedTransactionSnapshot.exists()) {//shared
                    for (DataSnapshot singleTransaction : sharedTransactionSnapshot.getChildren()) {
                        if (singleTransaction.hasChildren()) {

                            String amount = singleTransaction.child("amount").getValue().toString();
                            String paid_by = singleTransaction.child("paid_by").getValue().toString();

                            if (paid_by.equals(prefs.getString("user_name", "Undefined"))) {
                                total_spent = total_spent + Float.parseFloat(amount);
                                total_expenditure = total_expenditure + Float.parseFloat(amount);

                            } else {
                                total_expenditure = total_expenditure + Float.parseFloat(amount);
                            }
                        }
                    }

                }
                else {
                    total_expenditure = 0f;
                    total_spent = 0f;
                }

                tv_total_expenditure.setText("Rs."+String.valueOf(total_expenditure));
                tv_total_spent.setText("Rs."+String.valueOf(total_spent));
                //hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Failed to read value.", "error database");
            }
        });




        transactionRef.child("personal").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot sharedTransactionSnapshot) {
                if (sharedTransactionSnapshot.exists()) {//personal
                    for (DataSnapshot singleTransaction : sharedTransactionSnapshot.getChildren()) {

                        if (singleTransaction.hasChildren()) {

                            String amount = singleTransaction.child("amount").getValue().toString();
                            total_personal = total_personal + Float.parseFloat(amount);

                        }
                    }
                }
                else {
                    total_personal = 0f;
                }

                tv_total_personal.setText("Rs."+String.valueOf(total_personal));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Failed to read value.", "error database");
            }
        });


        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot clearSnapshot) {
                        Log.d("xxx","called");
                        String clear_request_count = clearSnapshot.getValue().toString();

                        if (Integer.valueOf(clear_request_count) >0) {
                            if(clear_request_count.equals("2")){
                                transactionRef.child("shared").setValue(null);
                                transactionRef.child("personal").setValue(null);
                            }
                            else {
                                clearRef.setValue(Integer.valueOf(clear_request_count)+1);
                                btn_clear.setVisibility(View.GONE);
                                tv_clear_request.setVisibility(View.VISIBLE);
                            }
                        }
                        else {
                            clearRef.setValue("1");
                            btn_clear.setVisibility(View.GONE);
                            tv_clear_request.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("Failed to read value.", "error database");
                    }
                });
            }
        });

    }
}
