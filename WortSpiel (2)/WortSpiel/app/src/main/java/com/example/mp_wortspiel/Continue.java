package com.example.mp_wortspiel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
public class Continue extends AppCompatActivity {
    private Button buttonc;
    int gettotal;
    TextView textQuestion, continue_;
    Animation smalltobig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue);
        smalltobig = AnimationUtils.loadAnimation(this, R.anim.smalltobig);
        Intent getscorefromgame = getIntent();
//        gettotal = getscorefromgame.getIntExtra("");
        Typeface typeface = Typeface.createFromAsset(getAssets(), "FredokaOne-Regular.ttf");
        textQuestion = (TextView) findViewById(R.id.textQuestion);
        continue_ = (TextView) findViewById(R.id.continue_);
        textQuestion.setTypeface(typeface);
        continue_.setTypeface(typeface);
        buttonc = (Button) findViewById(R.id.continue_);
        buttonc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continuegame();
            }
        });
    }

    public void continuegame() {
        Intent intent = new Intent(this,Wortspiel_Game.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.continue_menu, menu);
        return true;
    }
    public void onBackPressed() {
        Intent intent = new Intent(this, Wortspiel_Game.class);
        startActivity(intent);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.continue_logout:
                Intent inl = new Intent(this, MainActivity.class);
                this.startActivity(inl);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

