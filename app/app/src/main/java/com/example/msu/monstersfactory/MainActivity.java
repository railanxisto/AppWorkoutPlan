package com.example.msu.monstersfactory;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


public class MainActivity extends Activity {

    //SharedPreferences
    final String PREFS_NAME = "MyPrefsFile";
    SharedPreferences settings;

    // Imagem de inicio
    ImageView imageView;

    // SQLITE
    private SQLiteDatabase db;


    public boolean checkFirstLaunch(){
        if (settings.getBoolean("my_first_time", true)) {

            //Creates DataBase
            DBService dbService = new DBService(this);
            db = dbService.getDatabase();

            // record the fact that the app has been started at least once
            settings.edit().putBoolean("my_first_time", false).apply();
            settings.edit().commit();

            return true;
        } else {

            DBService dbService = new DBService(this);
            db = dbService.getDatabase();


            // first time task
            Toast t = Toast.makeText(getApplicationContext(), "Second time", Toast.LENGTH_LONG);
            t.show();

            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Set the contentView for the app
        setContentView(R.layout.activity_main);


        //Makes the application turn to full mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Turns background black
        getWindow().getDecorView().setBackgroundColor(Color.BLACK);

        //Get SharedPreferences
        settings = getSharedPreferences(PREFS_NAME, 0);

        //Gets ImageView object
        imageView = (ImageView) findViewById(R.id.image);


        if (checkFirstLaunch() == true){
            Toast t = Toast.makeText(getApplicationContext(), "First", Toast.LENGTH_LONG);
            t.show();
        }

        // Handler to load the image from the internet
        Handler uiHandler = new Handler(Looper.getMainLooper());
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                Picasso.with(getApplicationContext())
                        .load("https://scontent.xx.fbcdn.net/hphotos-xfp1/t31.0-8/1798924_795706623828627_1556304660425544635_o.png")
                        .into(imageView);
            }
        });


        Button new_button = (Button) findViewById(R.id.button_new);

        new_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Goest to the forms screen
                Intent i  = new Intent(getApplicationContext(), FormActivity.class);
                startActivity(i);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
