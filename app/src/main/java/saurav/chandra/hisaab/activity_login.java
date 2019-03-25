package saurav.chandra.hisaab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class activity_login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText editText_email,editText_password;
    private String email,password;
    private TextView login_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        mAuth = FirebaseAuth.getInstance();

        editText_email = findViewById(R.id.edittext_email);
        editText_password = findViewById(R.id.edittext_password);
        Button login_button = findViewById(R.id.button_login);
        login_status = findViewById(R.id.textview_login_status);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = editText_email.getText().toString();
                password = editText_password.getText().toString();

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(activity_login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        Intent Intent = new Intent(getApplicationContext(), activity_account.class);
                                        startActivity(Intent);
                                        finish();

                                    } else {
                                        Toast.makeText(activity_login.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    if (!task.isSuccessful()) {
                                        login_status.setText("Authentication failed");
                                    }
                                }
                            });
                }
            }
        });
    }
}

