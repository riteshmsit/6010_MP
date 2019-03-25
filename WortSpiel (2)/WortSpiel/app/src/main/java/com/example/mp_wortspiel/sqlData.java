package com.example.mp_wortspiel;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;
import java.util.HashMap;

public class sqlData extends SQLiteOpenHelper {



    private static final String db_name = "my_db";
    private static final String table_name = "Users";
    private static final String column_name = "Name";
    private static final String table_name2 = "Scores";

    private static final String column_date = "Date";
    private static final String column_userplayed = "Name";

    private static final String column_password = "Password";
    private static final String column_Email_id = "Email_id";
    private static final int version = 1;
    private static final String users_score = "create table Scores (Name text not null, "
            + "Date text not null);";
    private static final String table_create = "create table Users (Name text not null, Email_id text not null, Password text not null);";
    SQLiteDatabase db;


    public sqlData(Context context)

    {
        super(context,db_name,null,version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(table_create);
        db.execSQL(users_score);
        this.db = db;
    }
    public String search(String user) {
        db = this.getReadableDatabase();
        String query = "select Name, Password from Users";
        Cursor cursor = db.rawQuery(query,null);
        String cuser,cpassword = "";
        if (cursor.moveToFirst()) {
            do {
                cuser = cursor.getString(0);

                if ((cuser.equals(user))) {
                    cpassword = cursor.getString(1);
                    break;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cpassword;

    }
    public boolean userexist(String user) {
        db = this.getReadableDatabase();
        String query = "select Name, Password from Users";
        Cursor cursor = db.rawQuery(query,null);

        String cuser = "";
        if (cursor.moveToFirst()) {
            do {
                cuser = cursor.getString(0);
                if ((cuser.equals(user))) {
                    return true;
                }
            } while (cursor.moveToNext());
        } if (!cursor.moveToFirst()) {
            return false;
        }
        return false;
    }
    public void addUser(UserDetails user) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(column_name, user.getUser());
        values.put(column_Email_id, user.getEmailid());
        values.put(column_password, user.getPassword());
        db.insert(table_name,null, values);
        db.close();
    }

    public  void addScore(String user, String date) {


        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(column_userplayed , user);
        values.put(column_date, date);
        db.insert(table_name2,null, values);
        db.close();
    }

    public HashMap<String, Integer> getScores(String name) {
        db = this.getReadableDatabase();
        HashMap<String, Integer> particularname = new HashMap<String, Integer>();
        String query = "Select " + column_userplayed + "," + column_date +" from " +table_name2;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                String pdate = cursor.getString(1);
                if (cursor.getString(0).equals(name)) {
                    if (!(particularname.containsKey(pdate))) {
                        particularname.put(pdate, 1);
                    } else {
                        particularname.put(pdate, particularname.get(pdate) + 1);
                    }
                }
            } while (cursor.moveToNext());
            cursor.close();
            db.close();

        }
        return particularname;
    }
    public ArrayList<String> getdistinctnames() {
        db = this.getReadableDatabase();
        ArrayList<String> distinctnames = new ArrayList<String>();
        String query = "Select distinct " + column_userplayed + " from " + table_name2;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);
                distinctnames.add(name);

            } while (cursor.moveToNext());
            cursor.close();
            db.close();

        }
        return distinctnames;
    }
    public ArrayList<String> getdistinctdates() {
        db = this.getReadableDatabase();
        ArrayList<String> distinctdates = new ArrayList<String>();
        String query = "Select distinct " + column_date + " from " + table_name2;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(0);
                distinctdates.add(date);

            } while (cursor.moveToNext());
            cursor.close();
            db.close();

        }
        return distinctdates;
    }
    public ArrayList<String> getleadername() {
        ArrayList<String> top5 = new ArrayList<String>();
        db= this.getReadableDatabase();

        String query = "SELECT Name, Count(*) FROM 'Scores' GROUP BY Name ORDER BY COUNT(*) DESC";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                if(cursor.getString(0).length() >6) {
                    top5.add(cursor.getString(0) + "                       " + cursor.getInt(1));
                } else {
                    top5.add(cursor.getString(0) + "                                  " + cursor.getInt(1));
                }
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return top5;
    }



    public int getscoreforname(String name) {
        int score = 0;
        db= this.getReadableDatabase();
        String query = "SELECT Name from 'Scores'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(0).equals(name)) {
                    score++;
                }
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return score;
    }



//    public void addScoreandDate(String name, int score, String date) {
//        db = this.getWritableDatabase();
//        String query = "Select * from Scores";
//        Cursor cursor = db.rawQuery(query,null);
//        int count = cursor.getCount();
//        ContentValues values = new ContentValues();
//        values.put(column_userplayed , name);
//        values.put(column_date, date);
//        values.put(column_scoreofuser, score);
//        db.insert(table_name2,null, values);
//        db.close();
//    }

//    public void updateScore(String user) {
//        //get date today
//        //get date from table where username = user
//        //date exists update score
//        //not exists insert username date(today date) and score =1
//        db = this.getWritableDatabase();
//        String query = "Select * from Users";
//        System.out.println(query);
//        Cursor cursor = db.rawQuery(query,null);
//        if (cursor.moveToFirst()) {
//            do {
//                Log.d("user",cursor.getString(1));
//                Log.d("score",cursor.getString(4));
//                Log.d(user,"username");
//                if (cursor.getString(1).equals(user)) {
//                    int score = cursor.getInt(4);
//                    score += 1;
//                    Log.d("new_score",Integer.toString(score));
//
//                    db.execSQL("UPDATE Users SET Score="+ Integer.toString(score)+" WHERE Name="+user);
////                    ContentValues cv =new ContentValues();
////                    cv.put("Score", score);
////                    db.update(table_name, cv, "Name="+user, null);
//             //       Log.d("updatescore",cursor.getString(4));
//                    break;
//                }
//            } while(cursor.moveToNext());
//        } db.close();
//    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + table_name;
        db.execSQL(query);
        String query2 = "DROP TABLE IF EXISTS " + table_name2;
        db.execSQL(query2);
        this.onCreate(db);

    }
}
