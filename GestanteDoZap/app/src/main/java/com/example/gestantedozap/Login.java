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
    Intent it;

    public static final String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        it = getIntent();
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
        /*//Implementar login no servidor - de momento abrindo tela inicial
        Intent it = new Intent(this, MainActivity.class);

        String innlogin = email.getText().toString();
        String innsenha = senha.getText().toString();

        startActivity(it);

        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://191.233.255.192/api/login";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String resposta) {
                System.out.println("Resposta do Servidor: "+ resposta.substring(0,500));
                if(resposta.equals("200")){
                    Toast.makeText(Login.this, "Logado Com Sucesso!", Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(getApplicationContext(), Postagem.class);
                    it.putExtra("Email", innlogin);
                    startActivity(it);
                }
                else if (resposta.equals("0")){
                    Toast.makeText(Login.this, "Email ou Senha Inválidos", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, "Erro:" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", innlogin);
                params.put("senha", innsenha);
                return params;
            }
        };
        stringRequest.setTag(TAG);
        RetryPolicy policy = new DefaultRetryPolicy(10000, 1, 2);
        stringRequest.setRetryPolicy(policy);
        queue.add(stringRequest);*/

        email = editTextEmail.getText().toString();
        senha = editTextSenha.getText().toString();

        if(email.length() == 0 || senha.length() == 0) {
            Toast.makeText(Login.this, "Preencha todos os campos...", Toast.LENGTH_SHORT).show();
        } else {
            /**
             * Cria uma nova request com a biblioteca volley
             * */

            queue = Volley.newRequestQueue(this);

            /**
             * Define a rota para o login
             * */
            String url = "http://191.233.255.192/api/login";
            //Cria os parâmetros da requisição
            Map<String, Object> params = new HashMap<>();
            params.put("email", email);
            params.put("senha", senha);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new JSONObject(params),
                    json -> {
                        try {
                            if (json.getInt("statusCode") == 200) {
                                //it.putExtra("mensagem", json.getString("message"));
                                //setResult(1, it);
                                finish();
                                Intent itMain = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(itMain);
                            } else if (json.getInt("statusCode") == 404) {
                                Toast.makeText(Login.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                            } else if (json.getInt("statusCode") == 403) {
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
