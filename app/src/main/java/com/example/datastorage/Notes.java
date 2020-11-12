    package com.example.datastorage;
    
    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    
    import android.content.Context;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.ContextMenu;
    import android.view.Menu;
    import android.view.MenuInflater;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.ArrayAdapter;
    import android.widget.ListView;
    
    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.List;
    import java.util.zip.Inflater;
    
    public class Notes extends AppCompatActivity
    {
        static ArrayList<String> noteList = new ArrayList<String>();
        static ArrayAdapter arrayAdapter;
        private String TAG = "";
    
    
    
        @Override
        protected void onCreate (Bundle savedInstanceState)
        {
    
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_notes);
    
            /// Clearing the list
            noteList.clear();
    
    
            //// Create Shared Preferences and saving the note list
            SharedPreferences noteSharedPreferences = this.getSharedPreferences("com.example.datastorage.note", Context.MODE_PRIVATE);
            ListView listView = (ListView) findViewById(R.id.noteListViewId);
            
            try
            {
                noteList = (ArrayList<String>) ObjectSerializer.deserialize(noteSharedPreferences.getString("noteList", ObjectSerializer.serialize(new ArrayList<String>())));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            
    
            //// Create String list and check any data or display the list
            if (noteList != null && noteList.size() > 0)
            {
                //// Adding the array to ListView
                arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, noteList);
    
                listView.setAdapter(arrayAdapter);
            }
            else
            {
                // Something in the list
                noteList.add("Add New Note ...");
        
            }// end if
    
            
    
            //// ========== OnClick Event ==========
            //// Add new note will go to the "Note Detail" or Click on add new note
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    
                @Override
                public void onItemClick (AdapterView<?> parent, View view, int position, long id)
                {
                    TAG = "onItemClick";
                    Log.i(TAG, TAG + ", Selected Index " + String.valueOf(position));
    
                    GoToEditNote(position);
    
                }
            });
    
            //// ========== LongClick Event ==========
            //// Long click will go "Note Detail" and allow to edit and save
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
    
                @Override
                public boolean onItemLongClick (AdapterView<?> parent, View view, int position, long id)
                {
                    TAG = "onItemLongClick";
                    
                    Log.i(TAG, TAG);
                    GoToEditNote(position);
                    
                    return false;
                }
            });
    
    
    
        }// end onCreate()
    
        @Override
        public boolean onCreateOptionsMenu (Menu menu)
        {
            MenuInflater noteMenuInflater = new MenuInflater(this);
            noteMenuInflater.inflate(R.menu.note_menu, menu);
    
            return super.onCreateOptionsMenu(menu);
    
        }// end onCreateOptionsMenu()
    
        @Override
        public boolean onOptionsItemSelected (@NonNull MenuItem item)
        {
    
            switch (item.getItemId())
            {
                case R.id.addNoteMenuId:
                    
                    
                    GoToEditNote(-1);
    
                    return true;
    
                default:
                    return false;
    
    
            }// end switch
    
        }// onOptionsItemSelected
        
        
        private void GoToEditNote(int noteIndex)
        {
            Intent intent = new Intent(getApplicationContext(), EditNote.class);
            intent.putExtra("noteIndex", noteIndex);
            startActivity(intent);
        
        }// end GoToEditNote()
        
    
    }