package com.example.apirest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity2 extends AppCompatActivity {
    private EditText editTextUsuario;
    private EditText editTextPassword;
    private TextView textViewResult;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        // Obtén las referencias a los campos de texto y el TextView
        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextPassword = findViewById(R.id.editTextPassword);
        textViewResult = findViewById(R.id.textViewResult);

        ImageButton buttonEnviar = findViewById(R.id.buttonEnviar);
        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = editTextUsuario.getText().toString();
                String password = editTextPassword.getText().toString();

                // Realiza la solicitud de API con los valores de usuario y contraseña
                try {
                    hacerSolicitudAPI(usuario, password);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void hacerSolicitudAPI(String usuario, String password) throws UnsupportedEncodingException {
        // Aquí puedes incluir el código para realizar la solicitud de API y manejar la respuesta

        // Codifica el usuario y la contraseña para incluirlos en el enlace de la URL
        String encodedUsuario = URLEncoder.encode(usuario, "UTF-8");
        String encodedPassword = URLEncoder.encode(password, "UTF-8");

        // Construye la URL con los parámetros del usuario y la contraseña
        String apiUrl = "https://7f95-201-159-212-141.ngrok-free.app/api/eventos?";
        String urlWithParams = apiUrl + "?usuario=" + encodedUsuario + "&password=" + encodedPassword;

        // Realiza la solicitud de API con la URL modificada
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(urlWithParams)
                .build();
             System.out.println("Iniciando solicitud de API"); // Imprime un mensaje de depuración

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                System.out.println("Falla en la solicitud de API"); // Imprime un mensaje de depuración
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    System.out.println("Solicitud de API exitosa");
                    final String responseBody = response.body().string();
                    System.out.println("Respuesta de la API: " + responseBody);
                    Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                    intent.putExtra("response", responseBody);
                    startActivity(intent);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textViewResult.setText(responseBody);
                        }
                    });
                }
            }
        });
    }
}
