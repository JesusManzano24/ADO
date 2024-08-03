package com.jesusmanzano.adojm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {
    EditText etUsuario;
    EditText etContraseña;
    Button btnRegistrar;
    Button btnInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        etUsuario = findViewById(R.id.user);
        etContraseña = findViewById(R.id.password);
        btnRegistrar = findViewById(R.id.Registro);
        btnInicio = findViewById(R.id.Inicio);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = etUsuario.getText().toString();
                String contraseña = etContraseña.getText().toString();

                SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("usuario", usuario);
                editor.putString("contraseña", contraseña);
                editor.apply();

                Toast.makeText(MainActivity2.this, "Datos registrados", Toast.LENGTH_SHORT).show();

                // Reemplaza 'NuevaActivity' con el nombre de la clase de tu nueva pantalla
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
