package saurav.chandra.hisaab;

import android.app.Application;
import com.google.firebase.database.FirebaseDatabase;

public class Hisaab extends Application {

    private FirebaseDatabase database;

    @Override
    public void onCreate() {

        super.onCreate();
        setDatabase();
    }

    public void setDatabase(){
        database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
    }

    public FirebaseDatabase getDatabase() {
        return database;
    }
}


