package com.example.gestantedozap;

import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Importa as bibliotecas do volley
 * */
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    RequestQueue queue = null;
    EditText editTextLogin, editTextSenha;

    String email = "", senha = "";
    public static final String TAG = "LOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextLogin = findViewById(R.id.email);
        editTextSenha = findViewById(R.id.senha);
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
                editTextLogin.setText(it.getStringExtra("email"));
                Toast.makeText(Login.this, it.getStringExtra("mensagem"), Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void login(View view) {
        //Implementar login no servidor - de momento abrindo tela inicial
        /**
         * @param editTextLogin
         * @param editTextSenha
         * */
        email = editTextLogin.getText().toString();
        senha = editTextSenha.getText().toString();

        if(email.length() == 0) {
            Toast.makeText(Login.this, "Preencha o login...", Toast.LENGTH_SHORT).show();
        } else if(senha.length() == 0) {
            Toast.makeText(Login.this, "Preencha a senha...", Toast.LENGTH_SHORT).show();
        } else {
            /**
             * Cria uma nova request com a biblioteca volley
             * */
            queue = Volley.newRequestQueue(this);

            /**
             * Define a rota para o login
             * */
            String url = "http://192.168.0.5:3333/login";
            //Cria os parâmetros da requisição
            Map<String, String> params = new HashMap<>();
            params.put("email", email);
            params.put("senha", senha);

            JsonObjectRequest  jsonObjectRequest = new JsonObjectRequest(url, new JSONObject(params),
                    json -> {
                        try {
                            if(json.getInt("statusCode") == 200){
                                Intent it = new Intent(Login.this, MainActivity.class);
                                it.putExtra("token", json.getString("token"));
                                startActivity(it);
                            }  else if (json.getInt("statusCode") == 404){
                                Toast.makeText(Login.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                            } else if(json.getInt("statusCode") == 403){
                                Toast.makeText(Login.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, error -> { //erro de requisição
                        Toast.makeText(Login.this, "Erro:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    });
            //da uma tag para a requisição
            jsonObjectRequest.setTag(TAG);
            //backOffMultiplier: multiplicador do timout de resposta do servidor, para esperar mais tempo nas tentativas subsequentes
            //1a tentativa: 10seg
            //2a tentativa: 10seg (t1) + 2*10seg = 30seg
            //3a tentativa: 30seg + 2*30seg = 150seg
            //...
            RetryPolicy policy = new DefaultRetryPolicy(10000, 1, 2);
            jsonObjectRequest.setRetryPolicy(policy);
            //adiciono a requisição na fila de requisições para que ela seja dispachada
            Log.d("Request", jsonObjectRequest.toString());
            queue.add(jsonObjectRequest);
        }
    }
}