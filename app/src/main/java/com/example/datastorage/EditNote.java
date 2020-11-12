package com.example.datastorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

public class EditNote extends AppCompatActivity
{
    private String TAG;
    private Intent getNoteInfoIntent;
    
    private EditText noteTextView;
    private int noteIndex;
    private String originalNote;
    
    
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        
        getNoteInfoIntent = getIntent();
        noteTextView = (EditText) findViewById(R.id.noteEditTextTextMultiLine);
        noteIndex = (int) getNoteInfoIntent.getIntExtra("noteIndex", 0);
    
        TAG = "noteIndex";
        Log.i(TAG, TAG + " " + String.valueOf(noteIndex));
    
        if ( noteIndex != -1 ) // Editing
        {
    
            originalNote = Notes.noteList.get(noteIndex);
    
            if (noteIndex == 0 && originalNote.equals("Add New Note ..."))
            {
                originalNote = "";
        
            }// end if
    
        }
        else // Adding new note
        {
            originalNote = "";
            noteIndex = -1;
    
//            Notes.noteList.add("");
        }// end if
    
        //// Get the value from ListIndex
    
        
        int noteLength = originalNote.length();
    
        TAG = "OriginalNote_OnCr";
        Log.i(TAG, originalNote + String.valueOf(noteLength));
        
        
        
        noteTextView.setText(originalNote);
    
        if (noteLength > 0)
        {
            noteTextView.setSelection(noteLength);
        }// end if
        
        /// Storing data
        SharedPreferences noteSharedPreferences = this.getSharedPreferences("com.example.datastorage.note", Context.MODE_PRIVATE);
        
    
    }// end onCreate()
    
    public void SaveNote_Click (View view)
    {
        //// TODO: Save
        //// Add the note to the list if it is new
        //// Edit : Get previous note and add it
    
        String newNote = noteTextView.getText().toString();
        
        if (noteIndex ==  -1)
        {
            noteIndex = Notes.noteList.size() - 1;
            
            if (noteIndex == 0 && Notes.noteList.get(noteIndex).equals("Add New Note ..."))
            {
                Notes.noteList.set(noteIndex, newNote);
            }
            else
            {
                Notes.noteList.add(newNote);
    
            }// end if
            
        }// end if
        else
        {
            Notes.noteList.set(noteIndex, newNote);
        }// end if
    
        SharedPreferences noteSharedPreferences = this.getSharedPreferences("com.example.datastorage.note", Context.MODE_PRIVATE);
        try
        {
            noteSharedPreferences.edit().putString("noteList", ObjectSerializer.serialize(Notes.noteList)).apply();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        
    
        Toast.makeText(this, "Your notes has been saved", Toast.LENGTH_LONG).show();
    
        GoBackToNoteMain();
    
    
    }// end SaveNote_Click()
    
    public void CancelNote_Click (View view)
    {
        ////  TODO: Cancel
        /// Get the current context and save it as backup
        /// Restore it back to
        
        Toast.makeText(this, "Your notes has been canceled", Toast.LENGTH_LONG).show();
        
        GoBackToNoteMain();
        
    
    }// end CancelNote_Click()
    
    private void GoBackToNoteMain()
    {
        Intent goToNote = new Intent(this, Notes.class);
        startActivity(goToNote);
        
    }// end GoBackToNoteMain()
    
   
    
    
}