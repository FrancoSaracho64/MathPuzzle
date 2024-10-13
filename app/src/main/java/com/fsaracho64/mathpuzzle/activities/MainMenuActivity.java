package com.fsaracho64.mathpuzzle.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.fsaracho64.mathpuzzle.databinding.ActivityMainMenuBinding;

public class MainMenuActivity extends AppCompatActivity {
    ActivityMainMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Título del menú
        binding.titleMainMenu.setText("Menú");

        // Botón para comenzar
        binding.miBoton.setOnClickListener(v -> {
            Intent intent = new Intent(MainMenuActivity.this, GameActivity.class);
            startActivity(intent);
        });
    }
}