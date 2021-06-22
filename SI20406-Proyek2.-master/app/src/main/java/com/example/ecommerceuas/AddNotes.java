package com.example.ecommerceuas;

import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class AddNotes extends AppCompatActivity {
    FirebaseFirestore fstore;
    EditText noteTitle, noteContent;
    ProgressBar progressBarSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fstore = FirebaseFirestore.getInstance();
        noteTitle = findViewById(R.id.addNoteTitle);
        noteTitle.setTextColor(getResources().getColor(R.color.black));
        noteContent = findViewById(R.id.addNoteContent);
        progressBarSave = findViewById(R.id.progressBar);

//        Query data dari Firestore

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                save data to firestore
                String title = noteTitle.getText().toString();
                String content = noteContent.getText().toString();

                if(title.isEmpty() || content.isEmpty()){
                    Toast.makeText(AddNotes.this, "Maaf Tidak bisa menyimpan Notes", Toast.LENGTH_SHORT).show();
                    return;
                }


                DocumentReference docref = fstore.collection("notes").document();
                Map<String,Object> note = new HashMap<>();

                note.put("title", title);
                note.put("content", content);
                docref.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddNotes.this, "Note berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddNotes.this, "Note gagal ditambahkan", Toast.LENGTH_SHORT).show();
                        progressBarSave.setVisibility(view.VISIBLE);
                    }
                });
            }
        });

    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        MenuInflater inflater = getMenuInflater();
//
//        inflater.inflate(R.menu.close_menu,menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        /* checked the close button is clicked or not **/
//        if(item.getItemId() == R.id.close){
//            Toast.makeText(this,"Tidak Disimpan.",Toast.LENGTH_SHORT).show();
//            onBackPressed();
//        }
//        return super.onOptionsItemSelected(item);
//    }

}