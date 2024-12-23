package com.example.apirestful2024_it_2;

import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import WebServices.Asynchtask;
import WebServices.WebService;

public class MainActivity
        extends AppCompatActivity
        implements Asynchtask {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //LLamada al WebServices
        Map<String, String> datos = new HashMap<String, String>();
        WebService ws= new WebService(
                "https://uealecpeterson.net/turismo/lugar_turistico/json_getlistadoGridLT",
                datos, MainActivity.this, MainActivity.this);
        ws.execute("GET");

    }

    @Override
    public void processFinish(String result) throws JSONException {

        //Parsear Datos
        String lugares ="";
        JSONObject jsonObject = new JSONObject(result);
        JSONArray JSONlista = jsonObject.getJSONArray("data");
        for(int i=0; i< JSONlista.length();i++) {
            JSONObject lugar = JSONlista.getJSONObject(i);
            lugares = lugares +
                    lugar.getString("subcategoria") + ": " +
                    lugar.getString("nombre_lugar") + "\n";
        }

        EditText txtLista = findViewById(R.id.txtLista);
        txtLista.setText(lugares);
    }
}