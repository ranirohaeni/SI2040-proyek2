package com.example.ecommerceuas;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class NoteDetails extends AppCompatActivity {

    /* global variable **/
    Intent userNote;

    /* variable Declaration **/
    TextView title, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* for back button **/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = (TextView) findViewById(R.id.noteDetailsTitle);
        content = (TextView) findViewById(R.id.noteDetailsContent);
        /* enable scrolling for large text **/
        content.setMovementMethod(new ScrollingMovementMethod());

        /* get data from previous screen sending by item click
         * as position **/
        userNote = getIntent();

        /* catch data from previous screen with it's color **/
        title.setText(userNote.getStringExtra("title"));//here "title" use as a key which was define in previous screen
        content.setText(userNote.getStringExtra("content"));//here "content" use as a key which was define in previous screen
        content.setBackgroundColor(getResources().getColor(userNote.getIntExtra("color", 0), null));//here "color" use as a key which was define in previous screen

        /* working with save button **/
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* handle edit button click
                 * for go to next screen **/
                Intent GoNext = new Intent(v.getContext(), EditNotes.class);

                /* passed the data as item clicked **/
                GoNext.putExtra("title", userNote.getStringExtra("title"));//here "title" use as a key for passing data as item click
                GoNext.putExtra("content",userNote.getStringExtra("content"));//here "content" use as a key for passing data as item click
                GoNext.putExtra("noteId",userNote.getStringExtra("noteId"));
                /* open the next screen **/
                startActivity(GoNext);
            }
        });

    }//end of the onCreate method

    /*back button as item click**/

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        /* identify back button is clicked as item **/
        if (item.getItemId() == android.R.id.home) {

            /* for sent the user back **/
            onBackPressed();

        }//end of the if condition

        return super.onOptionsItemSelected(item);

    }//end of the onOptionsItemSelected method

}//end of the class
