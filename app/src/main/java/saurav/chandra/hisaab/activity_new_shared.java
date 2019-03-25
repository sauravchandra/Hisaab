package saurav.chandra.hisaab;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static saurav.chandra.hisaab.activity_new_personal.getTimeAndDate;

public class activity_new_shared extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference transactionRef;
    private EditText editText_amount, editText_description;
    private CheckBox cb_saurav, cb_robin, cb_sanyam;
    private RadioGroup paid_by_radio_g;
    private Button button_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_shared);

        database = ((Hisaab) this.getApplication()).getDatabase();
        transactionRef = database.getReference();

        editText_amount = findViewById(R.id.add_shared_amount);
        editText_description = findViewById(R.id.add_shared_description);
        cb_saurav = findViewById(R.id.cb_saurav);
        cb_robin = findViewById(R.id.cb_robin);
        cb_sanyam = findViewById(R.id.cb_sanyam);
        paid_by_radio_g = findViewById(R.id.radioPaidByGroup);
        button_add = findViewById(R.id.button_add_shared);

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount = editText_amount.getText().toString();
                String description = editText_description.getText().toString();
                if(!TextUtils.isEmpty(amount) && !TextUtils.isEmpty(description)){

                    float divided_amount = Float.parseFloat(amount)/getParticipants().size();
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("timestamp",System.currentTimeMillis()*-1);
                    map.put("amount", String.valueOf(divided_amount));
                    map.put("description", description);
                    map.put("participants",getParticipants());
                    map.put("paid_by",getPaidBy());
                    map.put("time",getTimeAndDate());

                    ArrayList<String> participants = getParticipantsUID();

                    for(String p:participants){
                        transactionRef.child(p).child("shared").push().setValue(map);
                    }

                    Toast.makeText(activity_new_shared.this, "Transaction added.",
                            Toast.LENGTH_SHORT).show();
                    Intent Intent = new Intent(getApplicationContext(), activity_main.class);
                    startActivity(Intent);
                    finish();
                }
            }
        });
    }

    ArrayList<String> getParticipants(){

        ArrayList<String> ar = new ArrayList<String>();
        if(cb_saurav.isChecked()){
            ar.add("Saurav");
        }
        if(cb_robin.isChecked()){
            ar.add("Robin");
        }
        if(cb_sanyam.isChecked()){
            ar.add("Sanyam");
        }

        return ar;
    }

    ArrayList<String> getParticipantsUID(){

        ArrayList<String> ar = new ArrayList<String>();
        if(cb_saurav.isChecked()){
            ar.add("gcNtsBRkLdMtAcIXVJFC2dljP3J3");
        }
        if(cb_robin.isChecked()){
            ar.add("2dXzEeNPelOlbCGb0BZsXrjFBGg1");
        }
        if(cb_sanyam.isChecked()){
            ar.add("jMLRqWEcDze0SMR5FsBvWoNq4rg1");
        }
        return ar;
    }

    String getPaidBy(){
        int selectedId = paid_by_radio_g.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(selectedId);
        return radioButton.getText().toString();
    }

}
