package com.example.gestantedozap;

import android.util.Log;
import android.view.LayoutInflater;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.android.volley.*;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    RequestQueue queue = null;

    LinearLayout menu;

    String token = "";

    public ArrayAdapter<Post> arrayAdapter;

    public static final String TAG = "HOME";
    ListView listaPostagens;
    private Post post;
    private List<Post> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menu = findViewById(R.id.menu);
        Intent it = getIntent();
        token = it.getSerializableExtra("token").toString();

        post = new Post();
        list = new ArrayList<Post>();

        listaPostagens = (ListView) findViewById(R.id.listaDePostagens);
        LayoutInflater inflater = getLayoutInflater();
        View header = inflater.inflate(R.layout.text_header, listaPostagens, false);
        ((TextView) header.findViewById(R.id.textView)).setText("\nLista de Postagens\n");
        listaPostagens.addHeaderView(header, null, false);

        listaPostagens.setOnItemClickListener(
                (adapterView, view, position, id) -> {
                    Object item = arrayAdapter.getItem(position-1);
                    System.out.println(item);
                }
        );

        buscarPostagens();
    }

    public void menu(View view) {
        if(menu.getVisibility()==View.GONE){
            menu.setVisibility(View.VISIBLE);
        }else{
            menu.setVisibility(View.GONE);
        }
    }

    public void abrirTopicos(View view) {
        Intent it = new Intent(this, Topicos.class);
        startActivity(it);
        menu.setVisibility(View.GONE);
    }

    public void abrirPerfil(View view) {
        Intent it = new Intent(this, Perfil.class);
        startActivity(it);
        menu.setVisibility(View.GONE);
    }

    public void abrirContato(View view) {
        Intent it = new Intent(this, Contato.class);
        startActivity(it);
        menu.setVisibility(View.GONE);
    }

    public void buscarPostagens() {
        /**
         * Cria uma nova request com a biblioteca volley
         * */
        queue = Volley.newRequestQueue(this);

        /**
         * Define a rota para o login
         * */
        String url = "http://192.168.0.5:3333/postagem/all";

        List<String> postagens = new ArrayList<>();
        Map<Integer, String> postagem = new HashMap<>();

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, response -> {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = response.getJSONObject(i);
                            Post post = new Post();
                            post.setId(jsonObject.getInt("id_postagem"));
                            post.setDescricao(jsonObject.getString("titulo"));
                            System.out.println(jsonObject.getString("titulo"));
                            list.add(post);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
                    listaPostagens.setAdapter(arrayAdapter);
                }, error -> {
                    System.out.println(error);
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