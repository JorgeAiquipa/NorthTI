package com.municipalidad.upc;

import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Spinner;

public class GrabarReservaDeporte extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabar_reserva_deporte);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner spDeportes = findViewById(R.id.spDeporte);
        Spinner spSedes = findViewById(R.id.spSede);
        Spinner spHijos = findViewById(R.id.spHijos);

        spDeportes.getBackground().setColorFilter(getResources().getColor(R.color.negro), PorterDuff.Mode.SRC_ATOP);
        spSedes.getBackground().setColorFilter(getResources().getColor(R.color.negro), PorterDuff.Mode.SRC_ATOP);
        spHijos.getBackground().setColorFilter(getResources().getColor(R.color.negro), PorterDuff.Mode.SRC_ATOP);

        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
