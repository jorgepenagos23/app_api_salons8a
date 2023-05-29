package com.example.apirest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private TextView textViewResult2;

    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.textViewResult);
        textViewResult2 = findViewById(R.id.textViewResult2);


        makeApiRequest();

        Button buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://90b6-201-159-212-141.ngrok-free.app/eventos/eventos";

                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });

    }


    private void makeApiRequest() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://90b6-201-159-212-141.ngrok-free.app/api/paquetes")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                // Manejar la falla de la solicitud aquí
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseBody = response.body().string();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textViewResult.setText(responseBody);
                        }
                    });
                }
            }
        });


        Request requestOtraSolicitud = new Request.Builder()
                .url("https://90b6-201-159-212-141.ngrok-free.app/api/eventos")
               .build();

        client.newCall(requestOtraSolicitud).enqueue(new Callback() {
           @Override
           public void onFailure(Call call, IOException e) {
              e.printStackTrace();
             // Manejar la falla de la solicitud aquí
           }

           @Override
           public void onResponse(Call call, Response response) throws IOException {
               if (response.isSuccessful()) {
                  final String responseBodyOtraSolicitud = response.body().string();

                  runOnUiThread(new Runnable() {
                       @Override
                        public void run() {
                           textViewResult2.setText(responseBodyOtraSolicitud);
                        }
                   });
                }
            }
         });
    }

}

