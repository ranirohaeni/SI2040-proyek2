package com.example.ecommerceuas;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditNotes extends AppCompatActivity {

    /* global variable **/
    Intent userNote;

    /* variable declaration **/
    EditText editNoteTitle, editNoteContent;
    FirebaseFirestore fStore;
    ProgressBar progressBarUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notes);

        /* custom toolbar setup **/
        Toolbar toolbar = findViewById(R.id.toolbar);

        /* actionbar setup **/
       setSupportActionBar(toolbar);

        /* get the instance of firebaseFieStore Database **/
        fStore = fStore.getInstance();

        /* receive data as item click **/
        userNote = getIntent();

        /* initial the xml UI **/
        editNoteTitle = findViewById(R.id.editNoteTitle);
        editNoteContent = findViewById(R.id.editNoteContent);
        progressBarUpdate = findViewById(R.id.progressBar2);



        /* received data from previous screen **/
        String noteTitle = userNote.getStringExtra("title");//here "title" use as a key for receive the data as item clicked
        String noteContent = userNote.getStringExtra("content");//here "content" use as a key for receive the data as item clicked

        /* set previous screen data into edit box **/
        editNoteTitle.setText(noteTitle);
        editNoteContent.setText(noteContent);

        /* working with save Edit data button **/
        FloatingActionButton fab = findViewById(R.id.saveEditedNote);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* extract the string **/
                String nTitle = editNoteTitle.getText().toString();
                String nContent = editNoteContent.getText().toString();

                /* check the user input is empty or not **/
                if(nTitle.isEmpty() || nContent.isEmpty()){

                    /* for display message to user **/
                    Toast.makeText(EditNotes.this,"Note Can not Update With Empty Field.",Toast.LENGTH_SHORT).show();
                    return; //its men return user to the same screen
                }//end of the if condition

                /* make progressbar visible **/
                progressBarUpdate.setVisibility(View.VISIBLE);

                //update note through the id
                DocumentReference docRef = fStore.collection("notes").document(userNote.getStringExtra("noteId"));//here noteId use as key for receive specific data
                Map<String,Object> note = new HashMap<>();
                note.put("title",nTitle);
                note.put("content",nContent);

                docRef.update(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        /* display message **/
                        Toast.makeText(EditNotes.this,"Note Updated.",Toast.LENGTH_SHORT).show();

                        /* redirect user to the main screen **/
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));

                    }//end of the onSuccess method

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        /* display message **/
                        Toast.makeText(EditNotes.this,"Error Try again.",Toast.LENGTH_SHORT).show();
                        progressBarUpdate.setVisibility(View.VISIBLE);
                    }
                });//end of the addOnFailureListener

            }//end of the onClick method

        });//end of the setOnClickListener

    }//end of the onCreate method

}//end of the class
