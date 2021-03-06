package com.example.gestantedozap;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
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

    EditText editTextNome, editTextDataNascimento, editTextSemanaGestacao, editTextNumPartoNormal, editTextNumCesarea, editTextNumAborto, editTextCelular, editTextEmail, editTextSenha, repeteSenha;
    Spinner spinnerConheceu;
    CheckBox checkBoxIsNotificar;
    Button btnCadastrar, button;
    LinearLayout parto;
    RadioButton radioButtonPresenciouParto;

    String nome, dataNascimento, celular, email, senha, comoConheceu, semanaGestacao, numPartoNormal, numCesarea, numAborto, confirmaSenha;
    RequestQueue queue = null;
    Intent it;
    boolean presenciouParto, notificacoes;
    public static final String TAG = "CADASTRO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        it = getIntent();
        parto = findViewById(R.id.parto);
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
        repeteSenha = findViewById(R.id.repeteSenha);

        spinnerConheceu = (Spinner) findViewById(R.id.conheceu);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.conheceu_app, android.R.layout.simple_spinner_dropdown_item);
        spinnerConheceu.setAdapter(adapter);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        button.setText("Data de Nascimento: " + currentDateString);
    }

    public void sim(View view) {
        parto.setVisibility(View.VISIBLE);
    }

    public void nao(View view) {
        editTextNumPartoNormal.setText("");
        editTextNumAborto.setText("");
        editTextNumCesarea.setText("");
        parto.setVisibility(View.GONE);
    }

    public void cadastrar(View view) {
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

        email = editTextEmail.getText().toString();
        senha = editTextSenha.getText().toString();
        confirmaSenha = repeteSenha.getText().toString();

        if (email.length() == 0 || senha.length() == 0 || nome.length() == 0 || dataNascimento.length() == 0 || confirmaSenha.length() == 0) {
            Toast.makeText(Cadastro.this, "Preencha todos os campos...", Toast.LENGTH_SHORT).show();
        } else {
            if (!senha.equals(confirmaSenha)) {
                Toast.makeText(this, "As senhas s??o diferentes!", Toast.LENGTH_SHORT).show();
            } else {
                queue = Volley.newRequestQueue(this);

                String url = "https://gestante-do-zap-api.herokuapp.com/gestante";

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

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                        response -> {
                            try {
                                if (response.getInt("statusCode") == 200) {
                                    it.putExtra("email", email);
                                    it.putExtra("mensagem", response.getString("message"));
                                    setResult(1, it);
                                    finish();
                                } else if (response.getInt("statusCode") == 404) {
                                    Toast.makeText(Cadastro.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                } else if (response.getInt("statusCode") == 403) {
                                    Toast.makeText(Cadastro.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }, error -> {
                    Toast.makeText(Cadastro.this, "Erro:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                });
                jsonObjectRequest.setTag(TAG);
                RetryPolicy policy = new DefaultRetryPolicy(10000, 1, 2);
                jsonObjectRequest.setRetryPolicy(policy);
                Log.d("Request", jsonObjectRequest.toString());
                queue.add(jsonObjectRequest);
            }
        }
    }
}