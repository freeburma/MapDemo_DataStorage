package com.example.datastorage;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class SQLiteDemo extends AppCompatActivity
{
    private String TAG = "";
    
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_q_lite_demo);
        
        try
        {
            /// Creating SQLite Db
            SQLiteDatabase myDatabase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);
    
            /// Creating table
            String creteTableQuery = "CREATE TABLE IF NOT EXISTS users (name VARCHAR, age INT(3))";
            myDatabase.execSQL(creteTableQuery);
    
            //        /// Inserting data to table
            //        String userInsert = "INSERT INTO users (name, age) VALUES ('Nick', 28)";
            //        myDatabase.execSQL(userInsert);
            //
            //        userInsert = "INSERT INTO users (name, age) VALUES ('HP', 21)";
            //        myDatabase.execSQL(userInsert);
    
    
            /// Getting data with Cursor
            String getUsers = "SELECT * FROM users";
            Cursor getDataCursor = myDatabase.rawQuery(getUsers, null);
    
            int nameIndex = getDataCursor.getColumnIndex("name"); // Name column
            int ageIndex = getDataCursor.getColumnIndex("age"); // Age column
    
            if (getDataCursor != null && getDataCursor.moveToFirst())
            {
                getDataCursor.moveToFirst();
        
                while (getDataCursor != null && getDataCursor.moveToNext())
                {
                    TAG = "Name";
                    Log.i(TAG, getDataCursor.getString(nameIndex));
            
                    TAG = "Age";
                    Log.i(TAG, Integer.toString(getDataCursor.getInt(ageIndex))); 
            
                    //                ; // Move to next
                }// end while
        
                Log.i("", "\n================================================================================\n");
        
                getDataCursor.moveToFirst();
                do
                {
                    TAG = "Name";
                    Log.i(TAG, getDataCursor.getString(nameIndex));
            
                    TAG = "Age";
                    Log.i(TAG, getDataCursor.getString(ageIndex));
            
                }
                while (getDataCursor.moveToNext());
            }// end if
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        
        
    }// end onCreate()
    
}// end class