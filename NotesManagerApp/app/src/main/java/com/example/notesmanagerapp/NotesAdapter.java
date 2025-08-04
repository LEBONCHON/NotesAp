package com.example.notesmanagerapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {
    private List<Note> notes;
    private OnNoteClickListener listener;
    private OnNoteLongClickListener longClickListener;

    public interface OnNoteClickListener {
        void onNoteClick(Note note);
    }

    public interface OnNoteLongClickListener {
        void onNoteLongClick(Note note);
    }

    public NotesAdapter(List<Note> notes) {
        this.notes = notes;
    }

    public void setOnNoteClickListener(OnNoteClickListener listener) {
        this.listener = listener;
    }

    public void setOnNoteLongClickListener(OnNoteLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.bind(note);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void updateNotes(List<Note> newNotes) {
        this.notes = newNotes;
        notifyDataSetChanged();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView contentTextView;
        private TextView dateTextView;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.tvNoteTitle);
            contentTextView = itemView.findViewById(R.id.tvNoteContent);
            dateTextView = itemView.findViewById(R.id.tvNoteDate);

            itemView.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onNoteClick(notes.get(getAdapterPosition()));
                }
            });

            itemView.setOnLongClickListener(v -> {
                if (longClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    longClickListener.onNoteLongClick(notes.get(getAdapterPosition()));
                    return true;
                }
                return false;
            });
        }

        public void bind(Note note) {
            titleTextView.setText(note.getTitle() != null && !note.getTitle().isEmpty() 
                ? note.getTitle() : "Untitled Note");
            
            String content = note.getContent();
            if (content != null && content.length() > 100) {
                content = content.substring(0, 100) + "...";
            }
            contentTextView.setText(content);

            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            dateTextView.setText(dateFormat.format(note.getModifiedDate()));
        }
    }
}