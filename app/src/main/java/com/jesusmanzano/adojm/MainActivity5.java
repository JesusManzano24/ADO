package com.jesusmanzano.adojm;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity5 extends AppCompatActivity {

    private EditText editTextId, editTextFecha, editTextHora;
    private Spinner spinnerOrigen, spinnerDestino;
    private Button buttonBuscar, buttonFecha, buttonHora, buttonActualizar, buttonRegresar;
    private ImageView imagenDestino;
    private Calendar calendario;
    private String[] listaOrigenes = {"Selecciona un origen", "Mérida", "Cancun", "Campeche", "Ciudad de México", "Veracruz"};
    private String[] listaDestinos = {"Selecciona un destino", "Celestún", "Izamal", "Tekax", "Motul", "Peto"};
    private int[] imagenesDestinos = {R.drawable.celestun, R.drawable.izamal, R.drawable.tekax, R.drawable.motul, R.drawable.peto};
    private int idReserva = -1;
    private DatabaseHelper helperBaseDatos;
    private String nombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        nombreUsuario = getIntent().getStringExtra("usuario");

        editTextId = findViewById(R.id.id);
        editTextFecha = findViewById(R.id.fechatxt);
        editTextHora = findViewById(R.id.hora);
        spinnerOrigen = findViewById(R.id.origen);
        spinnerDestino = findViewById(R.id.destino);
        buttonBuscar = findViewById(R.id.buscar);
        buttonFecha = findViewById(R.id.fecha);
        buttonHora = findViewById(R.id.btn_hora);
        buttonActualizar = findViewById(R.id.actualizar);
        buttonRegresar = findViewById(R.id.regresar);
        imagenDestino = findViewById(R.id.img);
        calendario = Calendar.getInstance();
        helperBaseDatos = new DatabaseHelper(this);

        ArrayAdapter<String> adaptadorOrigen = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaOrigenes);
        adaptadorOrigen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOrigen.setAdapter(adaptadorOrigen);

        ArrayAdapter<String> adaptadorDestino = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaDestinos);
        adaptadorDestino.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDestino.setAdapter(adaptadorDestino);

        buttonFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity5.this, dateSetListener, calendario.get(Calendar.YEAR),
                        calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        buttonHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(MainActivity5.this, timeSetListener, calendario.get(Calendar.HOUR_OF_DAY),
                        calendario.get(Calendar.MINUTE), true).show();
            }
        });

        buttonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarReserva();
            }
        });

        buttonActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarReserva();
            }
        });

        buttonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity5.this, MainActivity3.class);
                intent.putExtra("usuario", nombreUsuario);
                startActivity(intent);
                finish();
            }
        });

        spinnerDestino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) { // Para evitar seleccionar la primera opción por defecto
                    imagenDestino.setImageResource(imagenesDestinos[position - 1]);
                } else {
                    imagenDestino.setImageResource(0); // Limpia la imagen si se selecciona la opción por defecto
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void buscarReserva() {
        String idStr = editTextId.getText().toString().trim();
        if (idStr.isEmpty()) {
            Toast.makeText(this, "Ingrese El Id de Su Boleto", Toast.LENGTH_SHORT).show();
            return;
        }

        idReserva = Integer.parseInt(idStr);
        Cursor cursor = helperBaseDatos.getReserva(idReserva);

        if (cursor != null && cursor.moveToFirst()) {
            String origen = cursor.getString(cursor.getColumnIndex("origen"));
            String destino = cursor.getString(cursor.getColumnIndex("destino"));
            String fecha = cursor.getString(cursor.getColumnIndex("fecha"));
            String hora = cursor.getString(cursor.getColumnIndex("hora"));

            spinnerOrigen.setSelection(getIndex(spinnerOrigen, origen));
            spinnerDestino.setSelection(getIndex(spinnerDestino, destino));
            editTextFecha.setText(fecha);
            editTextHora.setText(hora);
            cursor.close();
        } else {
            Toast.makeText(this, "Reserva no encontrada", Toast.LENGTH_SHORT).show();
        }
    }

    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }
        return 0;
    }

    private void actualizarReserva() {
        if (idReserva == -1) {
            Toast.makeText(this, "Busque una reserva primero", Toast.LENGTH_SHORT).show();
            return;
        }

        String origen = spinnerOrigen.getSelectedItem().toString();
        String destino = spinnerDestino.getSelectedItem().toString();
        String fecha = editTextFecha.getText().toString();
        String hora = editTextHora.getText().toString();

        if (fecha.isEmpty() || hora.isEmpty()) {
            Toast.makeText(this, "Ingrese fecha y hora válidas", Toast.LENGTH_SHORT).show();
            return;
        }

        int result = helperBaseDatos.updateReserva(idReserva, origen, destino, fecha, hora);
        if (result > 0) {
            Toast.makeText(this, "Reserva actualizada con éxito", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity5.this, MainActivity3.class);
            intent.putExtra("usuario", nombreUsuario);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Error al actualizar la reserva", Toast.LENGTH_SHORT).show();
        }
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendario.set(Calendar.YEAR, year);
            calendario.set(Calendar.MONTH, month);
            calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            editTextFecha.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(calendario.getTime()));
        }
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendario.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendario.set(Calendar.MINUTE, minute);
            editTextHora.setText(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendario.getTime()));
        }
    };
}
