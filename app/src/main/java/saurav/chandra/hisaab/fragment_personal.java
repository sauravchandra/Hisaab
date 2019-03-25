package saurav.chandra.hisaab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static saurav.chandra.hisaab.activity_main.database;
import static saurav.chandra.hisaab.activity_main.uid;


public class fragment_personal extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private LinearLayoutManager linearLayoutManager;
    private DatabaseReference personal_transactions_ref;
    private List<Transaction> allTransactions;
    protected static float total_personal = 0;
    private TextView tv_total_personal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.layout_fragment_personal, container, false);
        database = ((Hisaab) getActivity().getApplication()).getDatabase();
        personal_transactions_ref = database.getReference(uid+"/personal");
        recyclerView = rootView.findViewById(R.id.transaction_list_personal);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        tv_total_personal = rootView.findViewById(R.id.expenditure_total_personal);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        total_personal = 0;
        allTransactions = new ArrayList<Transaction>();
        personal_transactions_ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getAllTask(dataSnapshot);
                tv_total_personal.setText("Total: Rs."+String.valueOf(total_personal));
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void getAllTask(DataSnapshot dataSnapshot){

        String tr_amount = dataSnapshot.child("amount").getValue().toString();
        String tr_description = dataSnapshot.child("description").getValue().toString();
        String tr_time = dataSnapshot.child("time").getValue().toString();

        allTransactions.add(new Transaction(tr_amount, tr_description, tr_time,"personal","",new ArrayList<String>()));
        total_personal = total_personal + Float.parseFloat(tr_amount);
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), allTransactions);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}