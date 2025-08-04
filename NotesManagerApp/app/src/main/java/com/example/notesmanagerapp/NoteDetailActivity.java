package com.example.notesmanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class NoteDetailActivity extends AppCompatActivity {

    private EditText etNoteTitle;
    private EditText etNoteContent;
    private MaterialButton btnUpdateNote;
    private MaterialButton btnShareNote;
    private Note currentNote;
    private NotesManager notesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        initViews();
        loadNoteData();
        setupClickListeners();
        
        // Enable back button in action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Edit Note");
        }
    }

    private void initViews() {
        etNoteTitle = findViewById(R.id.etNoteTitle);
        etNoteContent = findViewById(R.id.etNoteContent);
        btnUpdateNote = findViewById(R.id.btnUpdateNote);
        btnShareNote = findViewById(R.id.btnShareNote);
        notesManager = NotesManager.getInstance(this);
    }

    private void loadNoteData() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("note")) {
            currentNote = (Note) intent.getSerializableExtra("note");
            if (currentNote != null) {
                etNoteTitle.setText(currentNote.getTitle());
                etNoteContent.setText(currentNote.getContent());
            }
        }
    }

    private void setupClickListeners() {
        btnUpdateNote.setOnClickListener(v -> updateNote());
        btnShareNote.setOnClickListener(v -> shareNote());
    }

    private void updateNote() {
        if (currentNote == null) return;

        String title = etNoteTitle.getText().toString().trim();
        String content = etNoteContent.getText().toString().trim();

        if (title.isEmpty() && content.isEmpty()) {
            Toast.makeText(this, "Please enter some content", Toast.LENGTH_SHORT).show();
            return;
        }

        currentNote.setTitle(title);
        currentNote.setContent(content);
        notesManager.saveNote(currentNote);

        Toast.makeText(this, "Note updated successfully", Toast.LENGTH_SHORT).show();
        finish(); // Return to previous screen
    }

    private void shareNote() {
        if (currentNote == null) return;

        String shareText = currentNote.getTitle();
        if (shareText == null || shareText.isEmpty()) {
            shareText = "Shared Note";
        }
        
        if (currentNote.getContent() != null && !currentNote.getContent().isEmpty()) {
            shareText += "\n\n" + currentNote.getContent();
        }

        // Implicit Intent to share the note
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, currentNote.getTitle());

        startActivity(Intent.createChooser(shareIntent, "Share note via"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        
        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.action_delete) {
            deleteNote();
            return true;
        } else if (id == R.id.action_call_support) {
            // Implicit Intent to make a phone call
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(android.net.Uri.parse("tel:+1234567890"));
            startActivity(callIntent);
            return true;
        } else if (id == R.id.action_visit_website) {
            // Implicit Intent to open a website
            Intent webIntent = new Intent(Intent.ACTION_VIEW);
            webIntent.setData(android.net.Uri.parse("https://www.example.com"));
            startActivity(webIntent);
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }

    private void deleteNote() {
        if (currentNote == null) return;

        new AlertDialog.Builder(this)
                .setTitle("Delete Note")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    notesManager.deleteNote(currentNote.getId());
                    Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}