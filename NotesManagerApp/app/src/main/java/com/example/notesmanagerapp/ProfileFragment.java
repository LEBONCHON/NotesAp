package com.example.notesmanagerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.button.MaterialButton;

public class ProfileFragment extends Fragment {

    private EditText etNoteTitle;
    private EditText etNoteContent;
    private MaterialButton btnSaveNote;
    private MaterialButton btnCancelNote;
    private NotesManager notesManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        initViews(view);
        setupClickListeners();
    }

    private void initViews(View view) {
        etNoteTitle = view.findViewById(R.id.etNoteTitle);
        etNoteContent = view.findViewById(R.id.etNoteContent);
        btnSaveNote = view.findViewById(R.id.btnSaveNote);
        btnCancelNote = view.findViewById(R.id.btnCancelNote);
        notesManager = NotesManager.getInstance(requireContext());
    }

    private void setupClickListeners() {
        btnSaveNote.setOnClickListener(v -> saveNote());
        btnCancelNote.setOnClickListener(v -> navigateBack());
    }

    private void saveNote() {
        String title = etNoteTitle.getText().toString().trim();
        String content = etNoteContent.getText().toString().trim();

        if (title.isEmpty() && content.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter some content", Toast.LENGTH_SHORT).show();
            return;
        }

        Note note = new Note(title, content);
        notesManager.saveNote(note);
        
        Toast.makeText(requireContext(), "Note saved successfully", Toast.LENGTH_SHORT).show();
        navigateBack();
    }

    private void navigateBack() {
        NavHostFragment.findNavController(ProfileFragment.this).navigateUp();
    }
}