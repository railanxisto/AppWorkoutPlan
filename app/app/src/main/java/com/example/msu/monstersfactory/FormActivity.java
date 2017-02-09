package com.example.msu.monstersfactory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by pvsousalima on 5/4/15.
 */
public class FormActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // SetContentView to form activity
        setContentView(R.layout.activity_forms);

        // Editexts
        final EditText tvname = (EditText) findViewById(R.id.tvname);
        final EditText tvheight = (EditText) findViewById(R.id.tvheight);
        final EditText tvweight = (EditText) findViewById(R.id.tvweight);
        final EditText tvage = (EditText) findViewById(R.id.tvage);

        // Listen events to clear the content when touched
        tvheight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tvheight.getText().clear();
                return false;
            }
        });


        tvweight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tvweight.getText().clear();
                return false;
            }
        });

        tvname.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tvname.getText().clear();
                return false;
            }
        });

        tvage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                tvage.getText().clear();
                return false;
            }
        });

        Button button_next = (Button) findViewById(R.id.button_next);
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Creates new intent
                Intent i  = new Intent(getApplicationContext(), ObjectivesActivity.class);

                //Starts new activity
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


