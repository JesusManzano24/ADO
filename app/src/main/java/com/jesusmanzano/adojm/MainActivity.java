package com.jesusmanzano.adojm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;




public class MainActivity extends AppCompatActivity {
    Button inicio;
    Button registro;
    EditText user;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización de vistas
        ImageView logoImageView = findViewById(R.id.Logo);
        inicio = findViewById(R.id.Inicio);
        registro = findViewById(R.id.Registro);
        user = findViewById(R.id.user);
        password = findViewById(R.id.password);

        Glide.with(this)
                .load(R.drawable.ado)
                .into(logoImageView);

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuarioIngresado = user.getText().toString();
                String contraseñaIngresada = password.getText().toString();

                SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
                String usuarioGuardado = prefs.getString("usuario", "");
                String contraseñaGuardada = prefs.getString("contraseña", "");


                if (usuarioIngresado.equals(usuarioGuardado) && contraseñaIngresada.equals(contraseñaGuardada)) {
                    Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
