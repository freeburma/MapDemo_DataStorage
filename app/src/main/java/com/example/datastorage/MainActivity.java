package com.example.datastorage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    String TAG = "";

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //// Adding the Shared Preferences -- can store the basic types
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.datastorage", Context.MODE_PRIVATE);
        // sharedPreferences.edit().putString("username", "helloworld").apply();
//        String userName = sharedPreferences.getString("username", "");
//
//        TAG = "UserName";
//        Log.i(TAG, "onCreate: " + userName);

//        ArrayList<String> friends = new ArrayList<>();
//        friends.add("Hello");
//        friends.add("world");
//        friends.add("hp");
//
//        try
//        {
//
//            sharedPreferences.edit().putString("friendList", ObjectSerializer.serialize(friends)).apply();
//
//            TAG = "Friends";
//            Log.i(TAG, ObjectSerializer.serialize(friends));
//
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }


        ArrayList<String> newFriends = new ArrayList<>();
        try
        {
            newFriends = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("friendList", ObjectSerializer.serialize(new ArrayList<String>())));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        TAG = "NewFriends";
        Log.i(TAG, newFriends.toString());
//
//        //// Creating New Dialog
//        new AlertDialog.Builder(this)
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .setTitle("Are you sure!?")
//                .setMessage("Do you definitely want to do this?")
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
//                {
//
//                    @Override
//                    public void onClick (DialogInterface dialog, int which)
//                    {
//
//                        Toast.makeText(MainActivity.this, "It's done", Toast.LENGTH_LONG).show();
//                    }// end onClick()
//
//                }) // OK button
//                .setNegativeButton("Cancel", null) // Cancel
//                .show()
//                ;


    }// end onCreate()

    public void GoToGoogleMap (View view)
    {
        Intent intent = new Intent(this, activity_googlemap.class);
        startActivity(intent);
    }


    public void GoToMemorablePlace (View view)
    {

        Intent intent = new Intent(this, MemorablePlaceMain.class);
        startActivity(intent);
    }// end GoToMemorablePlace()

    public void GoToNotes (View view)
    {
        Intent intent = new Intent(this, Notes.class);
        startActivity(intent);

    }// end GoToNotes()

    /**
     * Creating menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);


        return super.onCreateOptionsMenu(menu);

    }// end onCreateOptionsMenu()


    /**
     * This will fire up when the menu item selected.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item)
    {
        super.onOptionsItemSelected(item);

        switch (item.getItemId())
        {
            case R.id.settings:
                TAG = "MenuItemSelected";
                Log.i(TAG, "onOptionsItemSelected: " + String.valueOf(item.getItemId()) + " Settings ");
                return true;

            case R.id.help:
                TAG = "MenuItemSelected";
                Log.i(TAG, "onOptionsItemSelected: " + String.valueOf(item.getItemId()) + " Help");
                return true;


            default:
                    return false;

        }// end switch



    }// end onOptionsItemSelected()
    
    
    public void GoToSqliteDemo (View view)
    {
        Intent intent = new Intent(this, SQLiteDemo.class);
        startActivity(intent);
        
    }// end GoToSqliteDemo()
    
    public void GoToWebviewDemo (View view)
    {
        Intent intent = new Intent(this, WebviewDemo.class);
        startActivity(intent);
        
    }// end GoToWebviewDemo()
    
    public void GoToNewsApp (View view)
    {
        Intent intent = new Intent(this, NewsApp.class);
        startActivity(intent);
    }// end GoToNewsApp()
    
}