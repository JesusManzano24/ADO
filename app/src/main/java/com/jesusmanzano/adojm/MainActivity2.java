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
    EditText user;
    EditText password;
    Button registrar;
    Button inicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        user = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);
        registrar = (Button) findViewById(R.id.Registro);
        inicio = findViewById(R.id.Inicio);


        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usuario = user.getText().toString();
                String contraseña = password.getText().toString();

                SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("usuario", usuario);
                editor.putString("contraseña", contraseña);
                editor.apply();

                Toast.makeText(MainActivity2.this, "Datos registrados", Toast.LENGTH_SHORT).show();
            }

        });
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reemplaza 'NuevaActivity' con el nombre de la clase de tu nueva pantalla
                Intent intent = new Intent(MainActivity2.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}