package com.example.mp_wortspiel;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class Leaderboard extends AppCompatActivity {

    ArrayList<String> leaders = new ArrayList<String>();
    ArrayList<String> scores = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_leaderboard);

        Intent in = getIntent();
        leaders = in.getStringArrayListExtra("Leaders");
//        TextView text = (TextView) findViewById(R.id.array);
//        text.setText(leaders.toString());
        TableLayout tableLayout = new TableLayout(this);
        ScrollView scrollView = new ScrollView(this);
        TableRow tableRow = new TableRow(this);
        TextView tv = new TextView(this);
        tv.setText("Name" + "                   " + "Score");
        tv.setTextSize(30);
        tv.setTextColor(0xff0000ff);
        tableRow.addView(tv);
        tableLayout.addView(tableRow);
        View v = new View(this);
        v.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT, 1));

        v.setBackgroundColor(0xff0000ff);

        tableLayout.addView(v);
        for (int i = 0; i < leaders.size(); i++) {
            TableRow tableRow2 = new TableRow(this);
            TextView tv2 = new TextView(this);
            tv2.setText(leaders.get(i));
            tv2.setTextSize(30);
            tv2.setTextColor(0xff0000ff);
            tableRow2.addView(tv2);
            tableLayout.addView(tableRow2);
            View v2 = new View(this);
            v2.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT, 1));
            v2.setBackgroundColor(0xff0000ff);

            tableLayout.addView(v2);
        }
        scrollView.addView(tableLayout);

        setContentView(scrollView);


       //setContentView(TableLayout);



    }
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.leaderboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logoutgame:
                Intent logout = new Intent(this, MainActivity.class);
                this.startActivity(logout);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
