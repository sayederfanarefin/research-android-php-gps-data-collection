package info.sayederfanarefin.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    Boolean firstRun;
    Button show_locations, show_map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, LocationService.class));

        SharedPreferences settings = getSharedPreferences("LEL", 0);
        firstRun = settings.getBoolean("isChecked", false);
        SharedPreferences.Editor editor = settings.edit();
        if(!firstRun){
            //databse dosent exists, so we need to create one
            db=openOrCreateDatabase("nemesis_local_db", Context.MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS location_table(location_id VARCHAR ,lat VARCHAR,lon VARCHAR, time_stamp VARCHAR);");

            //the database has been created, and we saved that information in the shared pref.
            editor.putBoolean("isChecked", true);
            editor.commit();
        }

        show_locations = (Button) findViewById(R.id.button_locations_list) ;
        show_map = (Button) findViewById(R.id.button_locations_map) ;

        show_locations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this,
                        show_locations.class);
                        startActivity(myIntent);
            }
        });

        show_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this,
                        MapsActivity.class);
                startActivity(myIntent);
            }
        });


    }
}
