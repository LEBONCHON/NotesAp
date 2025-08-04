package com.example.notesmanagerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android:view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class LoginFragment extends Fragment {

    private EditText etUsername;
    private Button btnEnterApp;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        etUsername = view.findViewById(R.id.etUsername);
        btnEnterApp = view.findViewById(R.id.btnEnterApp);
        
        btnEnterApp.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            if (username.isEmpty()) {
                username = "Guest";
            }
            
            // Pass username as argument to HomeFragment using NavArgs
            Bundle args = new Bundle();
            args.putString("username", username);
            
            NavHostFragment.findNavController(LoginFragment.this)
                    .navigate(R.id.action_loginFragment_to_homeFragment2, args);
        });

        return view;
    }
}
