package com.example.apirest;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {

    private TextView textViewResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        textViewResponse = findViewById(R.id.textViewResponse);

        // Obtén la respuesta del intent
        String response = getIntent().getStringExtra("response");

        // Configura el texto del TextView con la respuesta
        textViewResponse.setText(response);
    }
}
