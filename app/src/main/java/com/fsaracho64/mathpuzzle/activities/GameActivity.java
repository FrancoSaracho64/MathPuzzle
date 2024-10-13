package com.fsaracho64.mathpuzzle.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fsaracho64.mathpuzzle.R;
import com.fsaracho64.mathpuzzle.databinding.ActivityGameBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private ActivityGameBinding binding;
    private static int sumaObjetivo;
    private static int sumaActual;
    List<int[]> camino;  // Lista para registrar el camino del jugador
    int[][] tableroNumeros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Inicializaciones
        sumaObjetivo = 64;
        sumaActual = 0;
        camino = new ArrayList<>();
        tableroNumeros = new int[8][8];
        iniciarTablero();
    }


    private void iniciarTablero() {
        binding.textViewSumaObjetivo.setText("Suma Objetivo: " + sumaObjetivo);
        Random random = new Random();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Button button = new Button(this, null, R.style.SquareButton);

                int numero = random.nextInt(32) + 1; // Genera un número aleatorio entre 1 y 32
                button.setText(String.valueOf(numero));
                button.setGravity(View.TEXT_ALIGNMENT_CENTER);
                button.setWidth(100);
                button.setHeight(100);
                // Establecer un tag para identificar el botón
                button.setTag(new int[]{i, j});
                tableroNumeros[i][j] = numero;

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int[] coordenadas = (int[]) v.getTag(); // Obtener coordenadas desde el tag
                        if (!button.isEnabled()) {
                            // Si el botón ya está seleccionado, no hacer nada
                            return;
                        }
                        // Verificar que el usuario está tocando casillas adyacentes
                        if (camino.isEmpty() || esAdyacente(camino.get(camino.size() - 1), coordenadas)) {
                            camino.add(coordenadas);
                            int valorActual = tableroNumeros[coordenadas[0]][coordenadas[1]];
                            sumaActual += valorActual; // Actualizar la suma actual
                            binding.textViewSumaActual.setText("Suma Actual: " + sumaActual);
                            button.setEnabled(false); // Desactivar el botón una vez seleccionado

                            // Aquí puedes agregar lógica para verificar si la suma alcanzó el objetivo
                            if (sumaActual == sumaObjetivo) {
                                Toast.makeText(GameActivity.this, "FELICITACIONES, GANASTE.", Toast.LENGTH_LONG).show();
                                // Lógica cuando se alcanza el objetivo
                                // Ej: mostrar un mensaje o reiniciar el juego
                            } else if (sumaActual > sumaObjetivo) {
                                camino.clear();
                                sumaActual = 0;
                                binding.textViewSumaActual.setText("Suma Actual: " + sumaActual);
                                habilitarBotones();
                            }
                        } else {
                            // Si no es adyacente, el jugador pierde el camino
                            camino.clear();
                            sumaActual = 0; // Reiniciar suma actual
                            binding.textViewSumaActual.setText("Suma Actual: " + sumaActual);

                            // Habilitar todos los botones nuevamente
                            habilitarBotones();
                        }
                    }
                });
                binding.tablero.addView(button);
            }
        }
    }

    // Función para habilitar todos los botones en el tablero
    private void habilitarBotones() {
        for (int i = 0; i < binding.tablero.getChildCount(); i++) {
            View view = binding.tablero.getChildAt(i);
            if (view instanceof Button) {
                view.setEnabled(true); // Habilitar el botón
            }
        }
    }

    // Función para obtener coordenadas del botón en el tablero
    private int[] obtenerCoordenadas(View v) {
        int index = binding.tablero.indexOfChild(v);
        int row = index / 8;
        int col = index % 8;
        return new int[]{row, col};
    }

    // Verificar si dos casillas son adyacentes
    private boolean esAdyacente(int[] anterior, int[] actual) {
        int filaDiff = Math.abs(anterior[0] - actual[0]);
        int colDiff = Math.abs(anterior[1] - actual[1]);
        return filaDiff <= 1 && colDiff <= 1;
    }
}