package com.jesusmanzano.adojm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // Inicialización de vistas
        Button button1 = findViewById(R.id.Comprar);
        Button button2 = findViewById(R.id.Cambiar);
        Button button3 = findViewById(R.id.consultar);
        Button button4 = findViewById(R.id.Eliminar);
        Button button5 = findViewById(R.id.Inicio);
        TextView textoSaludo = findViewById(R.id.textoSaludo);

        // Recuperar el nombre del usuario de SharedPreferences
        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        String usuario = prefs.getString("usuario", "");

        // Obtener la hora actual
        Calendar calendario = Calendar.getInstance();
        int hora = calendario.get(Calendar.HOUR_OF_DAY);

        // Determinar el saludo adecuado
        String saludo;
        if (hora >= 6 && hora < 12) {
            saludo = "Buenos días";
        } else if (hora >= 12 && hora < 18) {
            saludo = "Buenas tardes";
        } else {
            saludo = "Buenas noches";
        }

        // Mostrar el saludo con el nombre del usuario
        textoSaludo.setText(saludo + ", " + usuario);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar a la primera actividad (o fragmento)
                Intent intent = new Intent(MainActivity3.this, MainActivity4.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar a la segunda actividad (o fragmento)
                Intent intent = new Intent(MainActivity3.this, MainActivity5.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this, MainActivity6.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this, MainActivity4.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this, MainActivity4.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });
    }
}


