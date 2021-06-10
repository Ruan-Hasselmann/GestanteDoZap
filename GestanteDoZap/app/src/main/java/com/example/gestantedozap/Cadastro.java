package com.example.gestantedozap;

import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Cadastro extends AppCompatActivity {

    EditText Nome, Sobrenome, SemanaGestacao, Nascimento, Telefone, Email, Senha;
    Spinner Conheceu;
    CheckBox IsNotificar;
    Button BtnCadastrar;

    LinearLayout parto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        parto = findViewById(R.id.parto);
        conheceu = (Spinner) findViewById(R.id.conheceu);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.conheceu_app, android.R.layout.simple_spinner_dropdown_item);
        conheceu.setAdapter(adapter);
    }

    public void sim(View view) {
        parto.setVisibility(View.VISIBLE);
    }

    public void nao(View view) {
        parto.setVisibility(View.GONE);
    }

    public void cadastrar(View view) {
        //Metodo para pegar o conteudo do spiner de como conhecer o app, para adicionar mais opções mudar no arquivo strings.xml
        /*String conhecimento = conheceu.getSelectedItem().toString();
        Toast.makeText(getApplicationContext(), "Metodo escolhido: " + conhecimento, Toast.LENGTH_SHORT).show();*/

        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
        finish();
    }
}