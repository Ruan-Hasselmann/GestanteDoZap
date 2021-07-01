package com.example.gestantedozap;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    EditText editTextEmail, editTextSenha;
    String email, senha;

    RequestQueue queue = null;

    public static final String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextSenha = findViewById(R.id.editTextSenha);
    }

    public void abrirCadastrar(View view) {
        Intent it = new Intent(this, Cadastro.class);
        startActivityForResult(it, 3000);
    }

    @Override
    public void onActivityResult(int codigo_da_tela, int resultado, Intent it) {
        super.onActivityResult(codigo_da_tela, resultado, it);

        if(codigo_da_tela == 3000){
            if(resultado == 1){
                editTextEmail.setText(it.getStringExtra("email"));
                Toast.makeText(Login.this, it.getStringExtra("mensagem"), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void login(View view) {

        email = editTextEmail.getText().toString();
        senha = editTextSenha.getText().toString();

        if(email.length() == 0 || senha.length() == 0) {
            Toast.makeText(Login.this, "Preencha todos os campos...", Toast.LENGTH_SHORT).show();
        } else {

            queue = Volley.newRequestQueue(this);

            String url = "http://191.233.255.192/api/login";

            Map<String, Object> params = new HashMap<>();
            params.put("email", email);
            params.put("senha", senha);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new JSONObject(params),
                    json -> {
                        try {
                            if (json.getInt("statusCode") == 200) {
                                finish();
                                Intent it = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(it);
                            } else if (json.getInt("statusCode") == 404) {
                                Toast.makeText(Login.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                            } else if (json.getInt("statusCode") == 403) {
                                Toast.makeText(Login.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, error -> {
                Toast.makeText(Login.this, "Erro:" + error.getMessage(), Toast.LENGTH_SHORT).show();
            });

            jsonObjectRequest.setTag(TAG);
            RetryPolicy policy = new DefaultRetryPolicy(10000, 1, 2);
            jsonObjectRequest.setRetryPolicy(policy);
            Log.d("Request", jsonObjectRequest.toString());
            queue.add(jsonObjectRequest);
        }
    }
}
