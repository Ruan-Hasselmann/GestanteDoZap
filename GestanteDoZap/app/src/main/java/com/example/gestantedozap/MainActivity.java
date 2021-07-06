package com.example.gestantedozap;

import android.net.Uri;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import com.android.volley.*;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpCookie;

public class MainActivity extends AppCompatActivity {

    LinearLayout menu, layout;
    TextView textView;
    RequestQueue queue = null;

    public static final String TAG = "GET";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menu = findViewById(R.id.menu);
        textView = findViewById(R.id.textView);
        layout = findViewById(R.id.layout);
        //exibirPostagens();
    }

    private void exibirPostagens() {

        queue = Volley.newRequestQueue(this);
        String url = "http://191.233.255.192/api/postagem";

        JsonArrayRequest JsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Toast.makeText(MainActivity.this, "Resposta", Toast.LENGTH_SHORT).show();
                        //textView.setText("" + response);

                        try {
                            for (int i = 0; i < response.length(); i++){
                                textView.setText("Response: " + response.get(i));



                                /*final TextView[] myTextViews = new TextView[response.length()];
                                final TextView rowTextView = new TextView(getApplicationContext());
                                String resposta = response.get(i).toString();
                                String teste = resposta.substring(5,25);
                                rowTextView.setText(""+response.get(i));
                                layout.addView(rowTextView);
                                myTextViews[i] = rowTextView;*/

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Toast.makeText(MainActivity.this, "Erro na solicitação" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        textView.setText("ERRO " + error.toString());
                    }
                });

        JsonArrayRequest.setTag(TAG);
        RetryPolicy policy = new DefaultRetryPolicy(10000, 1, 2);
        JsonArrayRequest.setRetryPolicy(policy);
        Log.d("Request", JsonArrayRequest.toString());
        queue.add(JsonArrayRequest);
    }

    public void menu(View view) {
        if(menu.getVisibility()==View.GONE){
            menu.setVisibility(View.VISIBLE);
        }else{
            menu.setVisibility(View.GONE);
        }
    }

    public void abrirTopicos(View view) {
        Intent it = new Intent(this, Topicos.class);
        startActivity(it);
        menu.setVisibility(View.GONE);
    }

    public void abrirPerfil(View view) {
        Intent it = new Intent(this, Perfil.class);
        startActivity(it);
        menu.setVisibility(View.GONE);
    }

    public void abrirContato(View view) {
        Intent it = new Intent(this, Contato.class);
        startActivity(it);
        menu.setVisibility(View.GONE);
    }

    public void abrirInsta(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/gestantedozap/"));
        startActivity(browserIntent);
    }

    public void teste(View view) {
        exibirPostagens();
    }
}