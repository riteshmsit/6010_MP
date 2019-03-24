package com.example.mp_wortspiel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

public class About extends AppCompatActivity {
    StringBuilder s = new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        TextView about= (TextView) findViewById(R.id.aboutpage);
        s.append("Worstpiel means 'Word Game' in German. In this game, you will be given  jumbled letters from a word.");
        s.append("There are about 1000 words ranging from four letter to six letter word length. When you solve a word, a new word will be given.");
        s.append("Your score will increase when you solve words. You can see your position in the leaderboard consisting of");
        s.append(" all the people who have played this game. Have fun!");
        about.setText(s.toString());


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.about, menu);
        return true;
    }
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.back:
                Intent home = new Intent(this, MainActivity.class);
                this.startActivity(home);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

