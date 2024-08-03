package com.jesusmanzano.adojm;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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

public class MainActivity4 extends AppCompatActivity {

    private String usuarioActual;
    private TextView campoFecha, campoHora;
    private Spinner selectorOrigen, selectorDestino;
    private Button botonFecha, botonHora, botonPagar, botonRegresar;
    private TextView textoTotal;
    private ImageView imagenDestino;
    private Calendar calendario;
    private String[] listaOrigenes = {"Mérida", "Cancun", "Campeche", "Cuidad de México", "Veracruz"};
    private String[] listaDestinos = {"Celestún", "Izamal", "Tekax", "Motul", "Peto"};
    private int[] imagenesDestinos = {R.drawable.celestun, R.drawable.izamal, R.drawable.tekax, R.drawable.motul, R.drawable.peto};
    private double[][] precios = {
            {110.00, 140.00, 120.00, 165.00, 187.50},
            {125.00, 135.00, 115.00, 150.00, 295.00},
            {230.00, 140.00, 220.00, 140.00, 350.00},
            {240.00, 120.00, 260.00, 130.00, 495.00},
            {150.00, 110.00, 140.00, 210.00, 520.00}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        usuarioActual = getIntent().getStringExtra("usuario");

        campoFecha = findViewById(R.id.fecha);
        campoHora = findViewById(R.id.horatxt);
        selectorOrigen = findViewById(R.id.origen);
        selectorDestino = findViewById(R.id.destino);
        botonFecha = findViewById(R.id.fecha2);
        botonHora = findViewById(R.id.hora);
        botonPagar = findViewById(R.id.pagar);
        botonRegresar = findViewById(R.id.regresar);
        textoTotal = findViewById(R.id.total);
        imagenDestino = findViewById(R.id.imagen_destino);
        calendario = Calendar.getInstance();

        ArrayAdapter<String> adaptadorOrigen = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaOrigenes);
        adaptadorOrigen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectorOrigen.setAdapter(adaptadorOrigen);

        ArrayAdapter<String> adaptadorDestino = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaDestinos);
        adaptadorDestino.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectorDestino.setAdapter(adaptadorDestino);

        botonFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity4.this, dateSetListener, calendario.get(Calendar.YEAR),
                        calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        botonHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(MainActivity4.this, timeSetListener, calendario.get(Calendar.HOUR_OF_DAY),
                        calendario.get(Calendar.MINUTE), true).show();
            }
        });

        botonPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posicionOrigen = selectorOrigen.getSelectedItemPosition();
                int posicionDestino = selectorDestino.getSelectedItemPosition();
                double total = precios[posicionOrigen][posicionDestino];
                String fecha = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(calendario.getTime());
                String hora = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendario.getTime());

                DatabaseHelper db = new DatabaseHelper(MainActivity4.this);
                long id = db.addReserva(listaOrigenes[posicionOrigen], listaDestinos[posicionDestino], fecha, hora, total);
                if (id > 0) {
                    Toast.makeText(MainActivity4.this, "Compra realizada con éxito", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity4.this, MainActivity3.class);
                    intent.putExtra("usuario", usuarioActual);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(MainActivity4.this, "La compra no se pudo realizar con éxito", Toast.LENGTH_SHORT).show();
                }
            }
        });

        botonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity4.this, MainActivity3.class);
                intent.putExtra("usuario", usuarioActual);
                startActivity(intent);
                finish();
            }
        });

        selectorOrigen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                calcularTotal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        selectorDestino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                calcularTotal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void calcularTotal() {
        int posicionOrigen = selectorOrigen.getSelectedItemPosition();
        int posicionDestino = selectorDestino.getSelectedItemPosition();
        double total = precios[posicionOrigen][posicionDestino];
        textoTotal.setText("Total: $" + total);
        cambiarImagenDestino(posicionDestino);
    }

    private void cambiarImagenDestino(int posicionDestino) {
        imagenDestino.setImageResource(imagenesDestinos[posicionDestino]);
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            calendario.set(Calendar.YEAR, year);
            calendario.set(Calendar.MONTH, month);
            calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            campoFecha.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(calendario.getTime()));
        }
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            calendario.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendario.set(Calendar.MINUTE, minute);
            campoHora.setText(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendario.getTime()));
        }
    };
}

