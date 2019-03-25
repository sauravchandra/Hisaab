package saurav.chandra.hisaab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class activity_new_personal extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference transactionRef;
    private String uid;
    private FirebaseUser currentUser;
    private EditText editText_amount, editText_description;
    private Button button_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_new_personal);

        database = ((Hisaab) this.getApplication()).getDatabase();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = currentUser.getUid();

        transactionRef = database.getReference(uid);

        editText_amount = findViewById(R.id.add_personal_amount);
        editText_description = findViewById(R.id.add_personal_description);
        button_add = findViewById(R.id.button_add_personal);

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String amount = editText_amount.getText().toString();
                String description = editText_description.getText().toString();
                if(!TextUtils.isEmpty(amount) && !TextUtils.isEmpty(description)){

                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("timestamp",System.currentTimeMillis()*-1);
                    map.put("amount", amount);
                    map.put("description", description);
                    map.put("time",getTimeAndDate());

                    transactionRef.child("personal").push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(activity_new_personal.this, "Transaction added.",
                                    Toast.LENGTH_SHORT).show();
                            Intent Intent = new Intent(getApplicationContext(), activity_main.class);
                            startActivity(Intent);
                            finish();
                        }
                    });
                }
            }
        });

    }

    public static String getTimeAndDate(){
        Calendar c = Calendar.getInstance();
        String TimeAmPmSet;
        String TimeHour = ""+c.get(Calendar.HOUR);
        if(TimeHour.equals("0")){
            TimeHour="12";
        }

        String TimeMinute=""+c.get(Calendar.MINUTE);
        if(Integer.parseInt(TimeMinute)/10==0){
            TimeMinute="0"+TimeMinute;
        }
        int TimeAmPm=c.get(Calendar.AM_PM);
        if(TimeAmPm == Calendar.AM){
            TimeAmPmSet="AM";
        }
        else
        {
            TimeAmPmSet="PM";
        }

        String DateDay,DateMonth,DateYear;
        DateDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        DateMonth = new SimpleDateFormat("MMM").format(c.getTime());
        DateYear = String.valueOf(c.get(Calendar.YEAR));
        return TimeHour+":"+TimeMinute+" "+TimeAmPmSet+"  "+DateDay+"-"+DateMonth+"-"+DateYear;
    }
}
