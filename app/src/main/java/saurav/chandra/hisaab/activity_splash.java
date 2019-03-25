package saurav.chandra.hisaab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;


public class activity_splash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1000;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        prefs = getSharedPreferences("user_pref", MODE_PRIVATE);
        Boolean user_details_set = prefs.getBoolean("user_detail_set",false);
        if (FirebaseAuth.getInstance().getCurrentUser()!=null) {
            if(user_details_set){
                Intent Intent = new Intent(getApplicationContext(), activity_main.class);
                startActivity(Intent);
                finish();
            }
            else {
                Intent Intent = new Intent(getApplicationContext(), activity_account.class);
                startActivity(Intent);
                finish();
            }
        } else {
            setContentView(R.layout.layout_splash);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent Intent = new Intent(getApplicationContext(), activity_login.class);
                    startActivity(Intent);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }

    }
}
