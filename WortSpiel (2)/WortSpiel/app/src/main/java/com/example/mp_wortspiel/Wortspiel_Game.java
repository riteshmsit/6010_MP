package com.example.mp_wortspiel;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Arrays;
import java.util.Random;

public class Wortspiel_Game extends AppCompatActivity {
    private  int presCounter = 0;
    Date gamedate;
    //int totalscore;
    SimpleDateFormat gameformat;
    sqlData data = new sqlData(this);
    private  int maxPresCounter = 0;
    String[] shuffleletters;
    String word;
    Animation smallbigforth;
    ReadingWords Rw;
    String formattedDate;
    ImageView refresh;
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent getintowordgame = getIntent();
        setContentView(R.layout.activity_wortspiel__game);
        gamedate = Calendar.getInstance().getTime();
        gameformat = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = gameformat.format(gamedate);
        user  = getintowordgame.getStringExtra("Username");
        TextView text = (TextView) findViewById(R.id.userame);
        text.setText(getintowordgame.getStringExtra("Welcome"));

        //game starts here.
        refresh = (ImageView) findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        Rw = new ReadingWords(this);
        word  = Rw.getwordfromdb(); //get word function for db
        System.out.println(word+" --> word");
        String[] letters = word.split("");
        System.out.println(Arrays.toString(letters));
        maxPresCounter = letters.length-1;
        System.out.println(maxPresCounter+" count");
        shuffleletters = shuffleArray(letters);
        //adding views
        for (String key : shuffleletters) {
            if(!key.equals("")) {
                addView(((LinearLayout) findViewById(R.id.layoutParent)), key, ((EditText) findViewById(R.id.editText)));
            }
        }
        maxPresCounter = letters.length-1;
    }
    public void onClick() {
        super.onRestart();
        Intent i = new Intent(Wortspiel_Game.this, Wortspiel_Game.class);  //your class
        startActivity(i);
        finish();
    }

    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }
    //---------------------------------------------------------------
    public void openGraph() {
        Intent intent = new Intent(this, Progress_Graph.class);
        startActivity(intent);
    }
    //---------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.logout, menu);
        return true;
    }
    //--------------------------------------------------------------
    //shuffling letters
    private String[] shuffleArray(String[] ar) {
        Random rnd = new Random();
        for (int i = ar.length-1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
        return ar;
    }
    //---------------------------------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Progressgraph:
                    Intent intent = new Intent(this, Progress_Graph.class);
                    this.startActivity(intent);
                    break;
            case R.id.Logout:
                Intent progress = new Intent(this, MainActivity.class);
                this.startActivity(progress);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    //-----------------------------------------------------------------------
    private void addView(LinearLayout viewParent, final String text, final EditText editText) {
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        linearLayoutParams.gravity = Gravity.CENTER;
        linearLayoutParams.topMargin = 15;
        final TextView textView = new TextView(this);

        textView.setLayoutParams(linearLayoutParams);
        textView.setBackground(this.getResources().getDrawable(R.drawable.bgpink));
        textView.setTextColor(this.getResources().getColor(R.color.colorPurple));
        textView.setGravity(Gravity.CENTER);
        textView.setText(text);
        textView.setClickable(true);
        textView.setFocusable(true);
        textView.setTextSize(32);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"FredokaOne-Regular.ttf");
        editText.setTypeface(typeface);
        textView.setTypeface(typeface);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(presCounter < maxPresCounter) {
                    if (presCounter == 0)
                        editText.setText("");
                    editText.setText(editText.getText().toString() + text);
//                    textView.startAnimation(smallbigforth);
                    textView.animate().alpha(0).setDuration(300);
                    presCounter++;
                    if (presCounter == maxPresCounter)
                        doValidate();
                }
            }
        });
        viewParent.addView(textView);
    }
    //----------------------------------------------------------------------------
    private void doValidate() {
        presCounter = 0;
        EditText editText = findViewById(R.id.editText);
        LinearLayout linearLayout = findViewById(R.id.layoutParent);

        if(editText.getText().toString().equals(word)) {
            data.addScore(user, formattedDate);
            Toast.makeText(Wortspiel_Game.this, "Correct", Toast.LENGTH_SHORT).show();
            Intent intent = getIntent();
            finish();
            startActivity(intent);
//            Intent a = new Intent(Wortspiel_Game.this, Continue.class);
//            startActivity(a);
        } else {
            Toast.makeText(Wortspiel_Game.this, "Wrong", Toast.LENGTH_SHORT).show();
            //editText.setText("");
        }
        shuffleletters = shuffleArray(shuffleletters);
        linearLayout.removeAllViews();
        for (String key : shuffleletters) {
            if(!key.equals("")) {
                addView(linearLayout, key, editText);
            }
        }
    }
}
