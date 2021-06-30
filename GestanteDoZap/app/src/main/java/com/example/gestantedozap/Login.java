package com.example.gestantedozap;

import android.annotation.SuppressLint;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    EditText nome, senha;

    public static final String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nome = findViewById(R.id.nome);
        senha = findViewById(R.id.senha);
    }

    /*public void abrirCadastrar(View view) {
        Intent it = new Intent(this, Cadastro.class);
        startActivity(it);
        //finish();
    }*/
    public void abrirCadastrar(View view) {
        Intent it = new Intent(this, Cadastro.class);
        startActivityForResult(it, 3000);
    }

    @Override
    public void onActivityResult(int codigo_da_tela, int resultado, Intent it) {
        super.onActivityResult(codigo_da_tela, resultado, it);

        if(codigo_da_tela == 3000){
            if(resultado == 1){
                nome.setText(it.getStringExtra("email"));
                Toast.makeText(Login.this, it.getStringExtra("mensagem"), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void login(View view) {
        //Implementar login no servidor - de momento abrindo tela inicial
        Intent it = new Intent(this, MainActivity.class);

        String innlogin = nome.getText().toString();
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
        queue.add(stringRequest);

    }
    //finish();
        /*if(login==true){
            Intent it = new Intent(this, MainActivity.class);
            startActivity(it);
            finish();
        } else {

        }*/
}
