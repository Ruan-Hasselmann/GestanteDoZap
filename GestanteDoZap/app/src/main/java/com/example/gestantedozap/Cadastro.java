package com.example.gestantedozap;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Cadastro extends AppCompatActivity {

    RequestQueue queue = null;
    public static final String TAG = "CADASTRO";

    EditText editTextNome, editTextDataNascimento, editTextSemanaGestacao, editTextNumPartoNormal, editTextNumCesarea,
            editTextNumAborto, editTextCelular, editTextEmail, editTextSenha;
    RadioButton radioButtonPresenciouParto;
    Spinner spinnerConheceu;
    CheckBox checkBoxIsNotificar;
    Button btnCadastrar;

    LinearLayout parto;

    //Parâmetros para cadastro
    String nome, dataNascimento, celular, email, senha, comoConheceu, semanaGestacao, numPartoNormal, numCesarea, numAborto;
    boolean presenciouParto, notificacoes;

    Intent it;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        parto = findViewById(R.id.parto);

        it = getIntent();

        editTextNome = findViewById(R.id.nome);
        editTextSemanaGestacao = findViewById(R.id.semana);
        editTextDataNascimento = findViewById(R.id.nascimento);
        radioButtonPresenciouParto = findViewById(R.id.rdSim);
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