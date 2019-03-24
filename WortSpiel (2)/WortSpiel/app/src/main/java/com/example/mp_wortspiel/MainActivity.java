package com.example.mp_wortspiel;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDiskIOException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import java.io.BufferedReader;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity {
    TextView words;
    private Button button;
    EditText userlogin;
    EditText passwordlogin;
    String userloginstr;
    String passwordloginstr;
    int score = 0;
    BufferedReader br = null;
    sqlData database_main = new sqlData(this);
    ReadingWords database_word = new ReadingWords(this);
    private Button button2;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("words.txt"), "UTF-8"));


            String mLine;
            while ((mLine = reader.readLine()) != null) {
                String[] words = mLine.toString().split(" ");
                for (int i = 0; i < words.length; i++) {
                    database_word.addwordtodb(words[i]);
                }
            }
        } catch (IOException e) {

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {

                }
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqlData sqlobj = new sqlData(this);
        SQLiteDatabase database = sqlobj.getWritableDatabase();
        ActionBar action = getSupportActionBar();
        action.setDisplayShowHomeEnabled(true);
        button2 = (Button) findViewById(R.id.button);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userlogin = (EditText) findViewById(R.id.user_login);
                passwordlogin = (EditText) findViewById(R.id.password_login);
                userloginstr = userlogin.getText().toString();
                passwordloginstr = passwordlogin.getText().toString();
                String res_password = database_main.search(userloginstr);
                if (userloginstr.equals("") || (passwordloginstr.equals(""))) {
                    Toast message = Toast.makeText(MainActivity.this, "Username or password is not given", Toast.LENGTH_SHORT);
                    message.show();
                }
                else if (res_password.equals(passwordloginstr)) {

                    openGame(userloginstr);
                } else {
                    Toast err_message = Toast.makeText(MainActivity.this, "Username and Password don't match", Toast.LENGTH_SHORT);
                    err_message.show();
                }
            }
        });

        action.setTitle("WortSpiel");
        button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openLogin();
            }
        });

    }
    public void openLogin() {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void openGame(String user) {
        Intent intent = new Intent(this, Wortspiel_Game.class);
        intent.putExtra("Welcome", "Welcome " + user);
        intent.putExtra("Username", userloginstr);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.About:
                Intent intent = new Intent(this, About.class);
                this.startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}