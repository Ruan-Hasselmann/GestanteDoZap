package com.example.gestantedozap;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.fragment.app.DialogFragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Cadastro extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText nome, sobrenome, semanaGestacao, normal, cesarea, aborto, nascimento, telefone, email, senha;
    Spinner conheceu;
    CheckBox isNotificar;
    Button btnCadastrar, button;
    RadioButton radioButton2;
    LinearLayout parto;

    public static final String TAG = "Cadastro";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        parto = findViewById(R.id.parto);

        nome = findViewById(R.id.nome);
        sobrenome = findViewById(R.id.sobrenome);
        nascimento = findViewById(R.id.nascimento);
        semanaGestacao = findViewById(R.id.semana);
        radioButton2 = findViewById(R.id.radioButton2);//é o radio button do SIM
        normal = findViewById(R.id.normal);
        cesarea = findViewById(R.id.cesarea);
        aborto = findViewById(R.id.aborto);
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
        /**/
    }

    public void nao(View view) {
        parto.setVisibility(View.GONE);
    }

    public void cadastrar(View v){

        String inemail = email.getText().toString();
        String insenha = senha.getText().toString();
        String innome = nome.getText().toString() + " " + sobrenome.getText().toString();
        String indataNascimento = nascimento.getText().toString();
        String insemanaGestacao = semanaGestacao.getText().toString();
        boolean inpresenciouParto = false;
        /*if(radioButton2.isEnabled()){
            boolean inpresenciouParto = true + "";
        }*/
        String innumPartoNormal = normal.getText() + "";
        String innumCesarea = cesarea.getText() + "";
        String innumAborto = aborto.getText() + "";
        String incelular = telefone.getText().toString();
        //String innotificar = notificar.getText().toString();
        String incomoConheceu = conheceu.getSelectedItem().toString();

        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://191.233.255.192/api/gestante";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String resposta) {
                System.out.println("Resposta do Servidor: "+ resposta.substring(0,500));
                /*if(){
                    Toast.makeText(Cadastro.this, "Cadastrada!", Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(getApplicationContext(), Login.class);
                    it.putExtra("email", inemail);
                    startActivity(it);
                }
                else (resposta.equals("0")){
                    Toast.makeText(Cadastro.this, "Login Inválido", Toast.LENGTH_SHORT).show();
                }
                */
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Cadastro.this, "Erro:" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", inemail);
                params.put("insenha", insenha);
                params.put("innome", innome);
                params.put("dataNascimento", indataNascimento);
                params.put("semanaGestacao", insemanaGestacao);
                params.put("presenciouParto", inpresenciouParto + "");
                params.put("numPartoNormal", innumPartoNormal);
                params.put("numCesarea", innumCesarea);
                params.put("numAborto", innumAborto);
                params.put("celular", incelular);
                //params.put("notificar", innotificar);
                params.put("comoConheceu", incomoConheceu);
                return params;
            }
        };
        stringRequest.setTag(TAG);
        RetryPolicy policy = new DefaultRetryPolicy(10000, 1, 2);
        stringRequest.setRetryPolicy(policy);
        queue.add(stringRequest);

    }
}