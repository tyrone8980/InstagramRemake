package me.jntalley.parstagram;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.parse.ParseUser;

public class LoadScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Thread myThread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(3000);
                    if(ParseUser.getCurrentUser() == null) {
                        Intent i = new Intent(LoadScreenActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }else{
                        Intent i = new Intent(LoadScreenActivity.this, HomeActivity.class);
                        startActivity(i);
                        finish();

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        myThread.start();
    }
}
