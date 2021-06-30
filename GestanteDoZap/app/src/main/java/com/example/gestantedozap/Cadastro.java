package com.example.gestantedozap;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.util.Log;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Cadastro extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    EditText editTextNome, editTextDataNascimento, editTextSemanaGestacao, editTextNumPartoNormal, editTextNumCesarea,
            editTextNumAborto, editTextCelular, editTextEmail, editTextSenha;
    RadioButton radioButtonPresenciouParto;
    Spinner spinnerConheceu;
    CheckBox checkBoxIsNotificar;
    //Parâmetros para cadastro
    String nome, dataNascimento, celular, email, senha, comoConheceu, semanaGestacao, numPartoNormal, numCesarea, numAborto;
    boolean presenciouParto, notificacoes;

    RequestQueue queue = null;
    public static final String TAG = "CADASTRO";


    Button btnCadastrar, button;
    RadioButton radioButton2;
    LinearLayout parto;

    //public static final String TAG = "Cadastro";
    Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        it = getIntent();
        parto = findViewById(R.id.parto);

/*        nome = findViewById(R.id.nome);
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
        conheceu.setAdapter(adapter);*/

        editTextNome = findViewById(R.id.nome);
        editTextSemanaGestacao = findViewById(R.id.semana);
        editTextDataNascimento = findViewById(R.id.nascimento);
        radioButtonPresenciouParto = findViewById(R.id.radioButton2);
        editTextNumPartoNormal = findViewById(R.id.normal);
        editTextNumCesarea = findViewById(R.id.cesarea);
        editTextNumAborto = findViewById(R.id.aborto);
        editTextCelular = findViewById(R.id.telefone);
        editTextEmail = findViewById(R.id.email);
        editTextSenha = findViewById(R.id.senha);
        checkBoxIsNotificar = findViewById(R.id.isNotificar);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        //Configurando o spinner
        spinnerConheceu = (Spinner) findViewById(R.id.conheceu);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.conheceu_app, android.R.layout.simple_spinner_dropdown_item);
        spinnerConheceu.setAdapter(adapter);

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
        editTextDataNascimento.setText(currentDateString);
    }

    public void sim(View view) {
        parto.setVisibility(View.VISIBLE);
        /**/
    }

    public void nao(View view) {
        parto.setVisibility(View.GONE);
    }

    /*public void cadastrar(View v){

        *//*String inemail = email.getText().toString();
        String insenha = senha.getText().toString();
        String innome = nome.getText().toString() + " " + sobrenome.getText().toString();
        String indataNascimento = nascimento.getText().toString();
        String insemanaGestacao = semanaGestacao.getText().toString();
        boolean inpresenciouParto = false;
        *//**//*if(radioButton2.isEnabled()){
            boolean inpresenciouParto = true + "";
        }*//**//*
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
                Toast.makeText(Cadastro.this, "Testando app"+ resposta, Toast.LENGTH_SHORT).show();
                if(resposta.equals("0")){
                    Toast.makeText(Cadastro.this, "Login Inválido", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Cadastro.this, "Cadastrada!", Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(getApplicationContext(), Login.class);
                    it.putExtra("email", inemail);
                    startActivity(it);
                }
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
                params.put("senha", insenha);
                params.put("nome", innome);
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
        queue.add(stringRequest);*//*


    }*/

    public void cadastrar(View view) {
        //Metodo para pegar o conteudo do spiner de como conhecer o app, para adicionar mais opções mudar no arquivo strings.xml
        /*String conhecimento = conheceu.getSelectedItem().toString();
        Toast.makeText(getApplicationContext(), "Metodo escolhido: " + conhecimento, Toast.LENGTH_SHORT).show();*/
        nome = editTextNome.getText().toString();
        dataNascimento = editTextDataNascimento.getText().toString();
        semanaGestacao = editTextSemanaGestacao.getText().toString();
        presenciouParto = radioButtonPresenciouParto.isChecked();
        numPartoNormal = editTextNumPartoNormal.getText().toString();
        numCesarea = editTextNumCesarea.getText().toString();
        numAborto = editTextNumAborto.getText().toString();
        celular = editTextCelular.getText().toString();
        comoConheceu = spinnerConheceu.getSelectedItem().toString();
        notificacoes = checkBoxIsNotificar.isChecked();
        //Usuário
        email = editTextEmail.getText().toString();
        senha = editTextSenha.getText().toString();

        if(email.length() == 0 || senha.length() == 0 || nome.length() == 0 || dataNascimento.length() == 0) {
            Toast.makeText(Cadastro.this, "Preencha todos os campos...", Toast.LENGTH_SHORT).show();
        } else {
            /**
             * Cria uma nova request com a biblioteca volley
             * */

            queue = Volley.newRequestQueue(this);

            /**
             * Define a rota para o login
             * */
            String url = "http://192.168.0.5:3333/cadastro/gestante";
            //Cria os parâmetros da requisição
            Map<String, Object> params = new HashMap<>();
            params.put("email", email);
            params.put("senha", senha);
            params.put("nome", nome);
            params.put("dataNascimento", dataNascimento);
            params.put("semanaGestacao", semanaGestacao);
            params.put("presenciouParto", presenciouParto);
            params.put("numPartoNormal", numPartoNormal);
            params.put("numCesarea", numCesarea);
            params.put("numAborto", numAborto);
            params.put("celular", celular);
            params.put("comoConheceu", comoConheceu);
            params.put("notificacoes", notificacoes);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new JSONObject(params),
                    json -> {
                        try {
                            if(json.getInt("statusCode") == 200){
                                it.putExtra("email", email);
                                it.putExtra("mensagem", json.getString("message"));
                                setResult(1, it);
                                finish();
                            }  else if (json.getInt("statusCode") == 404){
                                Toast.makeText(Cadastro.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                            } else if(json.getInt("statusCode") == 403){
                                Toast.makeText(Cadastro.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, error -> { //erro de requisição
                Toast.makeText(Cadastro.this, "Erro:" + error.getMessage(), Toast.LENGTH_SHORT).show();
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