package com.example.gestantedozap;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.util.Calendar;

public class Cadastro extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText nome, sobrenome, semanaGestacao, nascimento, telefone, email, senha;
    Spinner conheceu;
    CheckBox isNotificar;
    Button btnCadastrar, button;

    LinearLayout parto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        parto = findViewById(R.id.parto);

        nome = findViewById(R.id.nome);
        sobrenome = findViewById(R.id.sobrenome);
        semanaGestacao = findViewById(R.id.semana);
        nascimento = findViewById(R.id.nascimento);
        telefone = findViewById(R.id.telefone);
        email = findViewById(R.id.email);
        senha = findViewById(R.id.senha);
        isNotificar = findViewById(R.id.isNotificar);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        //Configurando o spinner
        conheceu = (Spinner) findViewById(R.id.conheceu);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.conheceu_app, android.R.layout.simple_spinner_dropdown_item);
        conheceu.setAdapter(adapter);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(c.getTime());
        nascimento.setText(currentDateString);
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