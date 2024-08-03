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


        Button button1 = findViewById(R.id.Comprar);
        Button button2 = findViewById(R.id.Cambiar);
        Button button3 = findViewById(R.id.consultar);
        Button button4 = findViewById(R.id.Eliminar);
        Button button5 = findViewById(R.id.Inicio);
        TextView textoSaludo = findViewById(R.id.textoSaludo);


        SharedPreferences prefs = getSharedPreferences("MisPreferencias", MODE_PRIVATE);
        String usuario = prefs.getString("usuario", "");


        Calendar calendario = Calendar.getInstance();
        int hora = calendario.get(Calendar.HOUR_OF_DAY);

        // Determinar el saludo adecuado
        String saludo;
        if (hora >= 6 && hora < 12) {
            saludo = "Buenos días";
        } else if (hora >= 12 && hora < 18) {
            saludo = "Buenas Tardes";
        } else {
            saludo = "Buenas Noches";
        }


        textoSaludo.setText(saludo + ", " + usuario);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this, MainActivity4.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                Intent intent = new Intent(MainActivity3.this, MainActivity7.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this, MainActivity.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
            }
        });
    }
}


