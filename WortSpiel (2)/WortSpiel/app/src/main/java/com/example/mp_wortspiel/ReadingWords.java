package com.example.mp_wortspiel;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.lang.Math;

 public class ReadingWords extends SQLiteOpenHelper {
    //acess words_db in device file explorer data -> data -> database
     // if you cant see uninstall the app, close the project and open and run
    private static final String db_name = "words_db";
    //name of table
    private static final String table_name = "Words";
    // name of column which is Word
    private static final String column_word = "Word";
    //version of database
    private static final int version = 1;
    //sql query to create table
    private static final String table_create = "create table " + table_name + " " + "("
            + "Word text not null);";
    //db to execute queries
    SQLiteDatabase dbw;

    public ReadingWords(Context context)

    {
        super(context,db_name,null,version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //.execSql runs the query
        db.execSQL(table_create);
        this.dbw = dbw;
    }

    public void addwordtodb(String word) {
        dbw = this.getWritableDatabase();
        String query = "Select * from Words";
        // cursor object points to a row
        Cursor cursor = dbw.rawQuery(query,null);
        // to insert we use contentvalues object
        ContentValues values = new ContentValues();
        values.put(column_word, word);
        // to insert row with contentvalues
        dbw.insert(table_name,null, values);
        //to close cursor object
        cursor.close();
        //close db
        dbw.close();
    }

    public void deletewordfromdb(String word) {
        String query = "delete from Words where "+ column_word + " = " + word;
        dbw.execSQL(query);
        //dbw.delete(table_name, column_word + " = " + word, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + table_name;
        db.execSQL(query);
        this.onCreate(db);

    }
     public class RandomNumber {
         public int  getPosition(int columncount) {
             double randomDouble = Math.random();
             randomDouble = randomDouble * columncount + 0;
             int randomInt = (int) randomDouble;
             return randomInt;
         }
     }
    public String getwordfromdb() {
         dbw = this.getReadableDatabase();
         //query
         String query = "select Word from Words";
         RandomNumber randomNumber = new RandomNumber();
         Cursor cursor = dbw.rawQuery(query,null);
         int count = cursor.getCount();
         int position = randomNumber.getPosition(count);
         int p = 0;
         String word = "";
         if (cursor.moveToFirst())
         {
             do
             {
                 if (p == position) {
                     word = cursor.getString(0);

                     break;
                 }
                 p++;

             } while (cursor.moveToNext());
         }
         return word;
     }
}
