package com.example.notesmanagerapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomeFragment extends Fragment implements NotesAdapter.OnNoteClickListener, NotesAdapter.OnNoteLongClickListener {

    private RecyclerView recyclerView;
    private NotesAdapter notesAdapter;
    private NotesManager notesManager;
    private FloatingActionButton fabAddNote;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        // Get username from arguments (NavArgs)
        Bundle args = getArguments();
        String username = "Guest";
        if (args != null && args.containsKey("username")) {
            username = args.getString("username", "Guest");
        }
        
        // Set title to include username
        if (getActivity() != null && ((AppCompatActivity)getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Notes - " + username);
        }
        
        initViews(view);
        setupRecyclerView();
        loadNotes();
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewNotes);
        fabAddNote = view.findViewById(R.id.fabAddNote);
        notesManager = NotesManager.getInstance(requireContext());

        fabAddNote.setOnClickListener(v -> {
            // Navigate to add note fragment (ProfileFragment)
            NavHostFragment.findNavController(HomeFragment.this)
                    .navigate(R.id.action_homeFragment_to_profileFragment);
        });
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        notesAdapter = new NotesAdapter(notesManager.getAllNotes());
        notesAdapter.setOnNoteClickListener(this);
        notesAdapter.setOnNoteLongClickListener(this);
        recyclerView.setAdapter(notesAdapter);
    }

    private void loadNotes() {
        List<Note> notes = notesManager.getAllNotes();
        notesAdapter.updateNotes(notes);
    }

    @Override
    public void onNoteClick(Note note) {
        // Use explicit Intent to start NoteDetailActivity
        Intent intent = new Intent(requireContext(), NoteDetailActivity.class);
        intent.putExtra("note", note);
        startActivity(intent);
    }

    @Override
    public void onNoteLongClick(Note note) {
        // Show delete confirmation dialog
        new AlertDialog.Builder(requireContext())
                .setTitle("Delete Note")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    notesManager.deleteNote(note.getId());
                    loadNotes(); // Refresh the list
                    Toast.makeText(requireContext(), "Note deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadNotes(); // Refresh notes when returning to this fragment
    }
}