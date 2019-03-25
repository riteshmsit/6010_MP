package com.example.mp_wortspiel;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import java.util.Random;
import android.widget.TextView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Progress_Graph extends AppCompatActivity {


    HashMap<String, HashMap<String, Integer>> detailseach = new HashMap<String, HashMap<String, Integer>>();
    HashMap<String, Integer> getdata = new HashMap<String, Integer>();
    ArrayList<String> toppers = new ArrayList<String>();
    ArrayList<String> score = new ArrayList<String>();
    ArrayList<String> distinctnames = new ArrayList<String>();
    ArrayList<String> distinctdates = new ArrayList<String>();
    ArrayList<Integer> getcorrespondingscores = new ArrayList<Integer>();
    //StringBuilder s = new StringBuilder();
    String f = "";
    sqlData obj = new sqlData(this);
    String userg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress__graph);
        Intent getint = getIntent();
        TextView text = (TextView) findViewById(R.id.graph_text);
        text.setText(getint.getStringExtra("Progress"));
        distinctnames = obj.getdistinctnames();
        distinctdates = obj.getdistinctdates();
        for (int i = 0; i < distinctnames.size(); i++) {
            getcorrespondingscores.add(obj.getscoreforname(distinctnames.get(i)));
        }
//        for (int i = 0; i < getcorrespondingscores.size(); i++) {
//            System.out.println("hello" + getcorrespondingscores.get(i));
//            getcorrespondingscores.get(i);
//        }
        GraphView graph = (GraphView) findViewById(R.id.graph);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {

                new DataPoint(0, getcorrespondingscores.get(0)),
                new DataPoint(1, getcorrespondingscores.get(1)),
                new DataPoint(2, getcorrespondingscores.get(2)),
                new DataPoint(3, getcorrespondingscores.get(3)),
                new DataPoint(4, getcorrespondingscores.get(4)),
                new DataPoint(4, getcorrespondingscores.get(5)),
                new DataPoint(4, getcorrespondingscores.get(6)),
                new DataPoint(4, getcorrespondingscores.get(7)),
                new DataPoint(4, getcorrespondingscores.get(8)),
                new DataPoint(4, getcorrespondingscores.get(9))
        });
        graph.addSeries(series);

        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });
        double randomDouble = Math.random();
        randomDouble = randomDouble * distinctnames.size() + 0;
        int randomInt = (int) randomDouble;
        getdata = obj.getScores(distinctnames.get(randomInt));

        Iterator<Map.Entry<String, Integer>> entries = getdata.entrySet().iterator();
            while (entries.hasNext()) {
                Map.Entry<String, Integer> entry = entries.next();
                String key = entry.getKey();
                int value = entry.getValue();


            }
//

//

        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setVerticalLabels(new String[] {distinct});
        staticLabelsFormatter.setHorizontalLabels(new String[] {"1","2","3","4","5","6","7","8","9","10"});

        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        toppers = obj.getleadername();



//        for (int i = 0; i < distinctnames.size(); i++) {
//            HashMap<String, Integer> newdata = new HashMap<String,Integer>();
//            newdata = obj.getScores(distinctnames.get(i));
//            ArrayList<String> dates = new ArrayList<String>();
//            ArrayList<Integer> datec = new ArrayList<Integer>();
//            Iterator<Map.Entry<String, Integer>> entries = newdata.entrySet().iterator();
//            while (entries.hasNext()) {
//                Map.Entry<String, Integer> entry = entries.next();
//                String key = entry.getKey();
//                int value = entry.getValue();
//                datec.add(value);
//                dates.add(key);
//
//            }
//            creategraph(distinctnames.get(i), dates, datec);
//
//        }



//        for (int i = 0; i < distinctnames.size();i++) {
//            detailseach.put(distinctnames.get(i), obj.getScores(distinctnames.get(i)));
//        }
//        Iterator<Map.Entry<String,  Map<String, Integer>>> entries = detailseach.entrySet().iterator();
//        while (entries.hasNext()) {
//            Map.Entry<String, Integer> entry = entries.next();
//            String key = entry.getKey();
//            int value = entry.getValue();
//            f += "key " + key + "value " + value;
//            f += "\n";
//        }


//        TextView t = (TextView) findViewById(R.id.hashtext);
//        t.setText(distinctnames.toString() + "\n" + distinctdates.toString() + "\n" + "This is leaderboard\n" + toppers.toString());


//        value = getint.getIntExtra("Total", 0);
        //obj.updateScore(value,user);
    }
    public void creategraph() {

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.progress_logout, menu);
        return true;
    }
    public void onBackPressed() {
        Intent intent = new Intent(this, Wortspiel_Game.class);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.Home:
                Intent home = new Intent(this, MainActivity.class);
                this.startActivity(home);
                break;
            case R.id.leaderboard:
                Intent leaders = new Intent(this, Leaderboard.class);
                leaders.putExtra("Leaders",toppers);
                this.startActivity(leaders);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
