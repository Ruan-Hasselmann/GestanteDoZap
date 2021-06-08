package com.example.gestantedozap;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Cadastro extends AppCompatActivity {

    LinearLayout parto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        parto = findViewById(R.id.parto);
    }

    public void sim(View view) {
        parto.setVisibility(View.VISIBLE);
    }

    public void nao(View view) {
        parto.setVisibility(View.GONE);
    }

    public void cadastrar(View view) {
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
        finish();
    }
}