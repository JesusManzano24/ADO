package com.jesusmanzano.adojm;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity7 extends AppCompatActivity {

        private EditText Id;
        private TextView detalle;
        private Button Buscar, cancelar, Regresar;
        private DatabaseHelper dbboletos;
        private int reservaId = -1;
        private String usuario;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main7);

            usuario = getIntent().getStringExtra("usuario");

            Id = findViewById(R.id.id);
            Buscar = findViewById(R.id.buscar);
            detalle = findViewById(R.id.Detalles);
            cancelar = findViewById(R.id.cancelar);
            Regresar = findViewById(R.id.regresar);
            dbboletos = new DatabaseHelper(this);

            Buscar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buscarReserva();
                }
            });

            cancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmarCancelacion();
                }
            });

            Regresar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity7.this, MainActivity3.class);
                    intent.putExtra("usuario", usuario);
                    startActivity(intent);
                    finish();
                }
            });
        }

        private void buscarReserva() {
            String idStr = Id.getText().toString().trim();
            if (idStr.isEmpty()) {
                Toast.makeText(this, "Ingrese una ID válida", Toast.LENGTH_SHORT).show();
                return;
            }

            reservaId = Integer.parseInt(idStr);
            Cursor cursor = dbboletos.getReserva(reservaId);

            if (cursor != null && cursor.moveToFirst()) {
                String origen = cursor.getString(cursor.getColumnIndex("origen"));
                String destino = cursor.getString(cursor.getColumnIndex("destino"));
                String fecha = cursor.getString(cursor.getColumnIndex("fecha"));
                String hora = cursor.getString(cursor.getColumnIndex("hora"));
                double total = cursor.getDouble(cursor.getColumnIndex("total"));

                String details = "Origen: " + origen + "\nDestino: " + destino + "\nFecha: " + fecha + "\nHora: " + hora + "\nTotal: " + total;
                detalle.setText(details);
                detalle.setVisibility(View.VISIBLE);
                cursor.close();
            } else {
                Toast.makeText(this, "Informacion del Boleto No Encontrada", Toast.LENGTH_SHORT).show();
                detalle.setText("");
                detalle.setVisibility(View.GONE);
            }
        }

        private void confirmarCancelacion() {
            if (reservaId == -1) {
                Toast.makeText(this, "Busque una reserva primero", Toast.LENGTH_SHORT).show();
                return;
            }

            new AlertDialog.Builder(this)
                    .setTitle("Cancelar Reserva")
                    .setMessage("¿Estás seguro de que deseas cancelar esta reserva?")
                    .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            cancelarReserva();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }

        private void cancelarReserva() {
            int result = dbboletos.deleteReserva(reservaId);
            if (result > 0) {
                Toast.makeText(this, "Reserva cancelada con éxito", Toast.LENGTH_SHORT).show();
                detalle.setText("");
                detalle.setVisibility(View.GONE);
                reservaId = -1;
            } else {
                Toast.makeText(this, "Error al cancelar la reserva", Toast.LENGTH_SHORT).show();
            }
        }
    }