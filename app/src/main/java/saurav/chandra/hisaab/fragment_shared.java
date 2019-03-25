package saurav.chandra.hisaab;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

import java.util.ArrayList;
import java.util.List;

import static saurav.chandra.hisaab.activity_main.database;
import static saurav.chandra.hisaab.activity_main.uid;


public class fragment_shared extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private LinearLayoutManager linearLayoutManager;
    private DatabaseReference shared_transactions_ref;
    private List<Transaction> allTransactions;
    protected static float total_shared = 0;
    private TextView tv_total_shared;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.layout_fragment_shared, container, false);
        database = ((Hisaab) getActivity().getApplication()).getDatabase();
        shared_transactions_ref = database.getReference(uid+"/shared");
        recyclerView = rootView.findViewById(R.id.transaction_list_shared);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        tv_total_shared = rootView.findViewById(R.id.expenditure_total_shared);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        total_shared = 0;
        allTransactions = new ArrayList<Transaction>();
        shared_transactions_ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getAllTask(dataSnapshot);
                tv_total_shared.setText("Total: Rs."+String.valueOf(total_shared));
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
                String tr_paid_by = dataSnapshot.child("paid_by").getValue().toString();
                ArrayList<String> tr_participants = new ArrayList<>();
                for(int x = 0; x < 3; x++){
                    if(dataSnapshot.child("participants").hasChild(String.valueOf(x))){
                        tr_participants.add(dataSnapshot.child("participants").child(String.valueOf(x)).getValue().toString());
                    }
                }

                allTransactions.add(new Transaction(tr_amount, tr_description, tr_time,"shared",tr_paid_by,tr_participants));
                total_shared = total_shared + Float.parseFloat(tr_amount);
                recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), allTransactions);
                recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}