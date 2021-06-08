package com.example.gestantedozap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void abrirCadastrar(View view) {
        Intent it = new Intent(this, Cadastro.class);
        startActivity(it);
        finish();
    }

    public void login(View view) {
        //Implementar login no servidor - de momento abrindo tela inicial
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
        finish();
    }
}