package saurav.chandra.hisaab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class activity_account extends AppCompatActivity {

    private EditText user_name_edittext;
    private Button save;
    private String user_name_text;
    private SharedPreferences prefs;

    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_account);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        user_name_edittext = findViewById(R.id.edittext_user_name);
        save = findViewById(R.id.button_save);

        prefs = getSharedPreferences("user_pref", MODE_PRIVATE);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_name_text = user_name_edittext.getText().toString();
                if(!TextUtils.isEmpty(user_name_text)){
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(user_name_text).build();
                    currentUser.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            prefs.edit().putString("user_name",user_name_text).apply();
                            prefs.edit().putBoolean("user_detail_set", true).apply();
                        }
                    });

                }
                Intent Intent = new Intent(getApplicationContext(), activity_main.class);
                startActivity(Intent);
                finish();
            }
        });
    }

}
