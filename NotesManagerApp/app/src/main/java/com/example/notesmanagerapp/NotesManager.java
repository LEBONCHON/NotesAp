package com.example.notesmanagerapp;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NotesManager {
    private static final String PREFS_NAME = "notes_prefs";
    private static final String NOTES_KEY = "notes_list";
    private static NotesManager instance;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    private NotesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public static synchronized NotesManager getInstance(Context context) {
        if (instance == null) {
            instance = new NotesManager(context.getApplicationContext());
        }
        return instance;
    }

    public List<Note> getAllNotes() {
        String notesJson = sharedPreferences.getString(NOTES_KEY, "[]");
        Type listType = new TypeToken<List<Note>>(){}.getType();
        return gson.fromJson(notesJson, listType);
    }

    public void saveNote(Note note) {
        List<Note> notes = getAllNotes();
        
        // Check if note already exists (update) or is new (add)
        boolean found = false;
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getId().equals(note.getId())) {
                notes.set(i, note);
                found = true;
                break;
            }
        }
        
        if (!found) {
            notes.add(0, note); // Add new note at the beginning
        }
        
        saveAllNotes(notes);
    }

    public void deleteNote(String noteId) {
        List<Note> notes = getAllNotes();
        notes.removeIf(note -> note.getId().equals(noteId));
        saveAllNotes(notes);
    }

    public Note getNoteById(String noteId) {
        List<Note> notes = getAllNotes();
        for (Note note : notes) {
            if (note.getId().equals(noteId)) {
                return note;
            }
        }
        return null;
    }

    private void saveAllNotes(List<Note> notes) {
        String notesJson = gson.toJson(notes);
        sharedPreferences.edit().putString(NOTES_KEY, notesJson).apply();
    }
}