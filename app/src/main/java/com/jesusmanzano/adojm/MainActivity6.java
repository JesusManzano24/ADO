package com.jesusmanzano.adojm;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity6 extends AppCompatActivity {

    private String usuario;
    private EditText etIdConsultar;
    private Button btnConsultar, btnConsultarTodo, btnRegresar;
    private TextView tvResultados;
    private ImageView imgDestino;

    private int[] imagenesDestinos = {R.drawable.celestun, R.drawable.izamal, R.drawable.tekax, R.drawable.motul, R.drawable.peto};
    private String[] listaDestinos = {"Celestún", "Izamal", "Tekax", "Motul", "Peto"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        usuario = getIntent().getStringExtra("usuario");

        etIdConsultar = findViewById(R.id.idboleto);
        btnConsultar = findViewById(R.id.consultar);
        btnConsultarTodo = findViewById(R.id.Todo);
        btnRegresar = findViewById(R.id.regresar);
        tvResultados = findViewById(R.id.Existentes);
        imgDestino = findViewById(R.id.img);

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idText = etIdConsultar.getText().toString();
                if (idText.isEmpty()) {
                    Toast.makeText(MainActivity6.this, "Proporcione ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                int id = Integer.parseInt(idText);
                DatabaseHelper db = new DatabaseHelper(MainActivity6.this);
                Cursor cursor = db.getReserva(id);

                if (cursor != null && cursor.moveToFirst()) {
                    String origen = cursor.getString(cursor.getColumnIndexOrThrow("origen"));
                    String destino = cursor.getString(cursor.getColumnIndexOrThrow("destino"));
                    String fecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha"));
                    String hora = cursor.getString(cursor.getColumnIndexOrThrow("hora"));
                    double total = cursor.getDouble(cursor.getColumnIndexOrThrow("total"));

                    String resultado = "ID: " + id + "\nOrigen: " + origen + "\nDestino: " + destino +
                            "\nFecha: " + fecha + "\nHora: " + hora + "\nTotal: " + total;
                    tvResultados.setText(resultado);

                    // Mostrar la imagen del destino
                    int index = getDestinoIndex(destino);
                    if (index != -1) {
                        imgDestino.setImageResource(imagenesDestinos[index]);
                    }
                } else {
                    Toast.makeText(MainActivity6.this, "No se encontró la reserva", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnConsultarTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(MainActivity6.this);
                Cursor cursor = db.getAllReservas();
                StringBuilder resultados = new StringBuilder();

                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    String origen = cursor.getString(cursor.getColumnIndexOrThrow("origen"));
                    String destino = cursor.getString(cursor.getColumnIndexOrThrow("destino"));
                    String fecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha"));
                    String hora = cursor.getString(cursor.getColumnIndexOrThrow("hora"));
                    double total = cursor.getDouble(cursor.getColumnIndexOrThrow("total"));

                    resultados.append("ID: ").append(id).append("\nOrigen: ").append(origen)
                            .append("\nDestino: ").append(destino).append("\nFecha: ").append(fecha)
                            .append("\nHora: ").append(hora).append("\nTotal: ").append(total).append("\n\n");
                }

                tvResultados.setText(resultados.toString());
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity6.this, MainActivity3.class);
                intent.putExtra("usuario", usuario);
                startActivity(intent);
                finish();
            }
        });
    }

    private int getDestinoIndex(String destino) {
        for (int i = 0; i < listaDestinos.length; i++) {
            if (listaDestinos[i].equalsIgnoreCase(destino)) {
                return i;
            }
        }
        return -1;
    }
}



