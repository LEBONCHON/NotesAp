package com.example.notesmanagerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class LoginFragment extends Fragment {

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Button btnLogin = view.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> {
            NavHostFragment.findNavController(LoginFragment.this)
                    .navigate(R.id.action_loginFragment_to_homeFragment2); // ✅ Correct ID

        });

        return view;
    }
}

