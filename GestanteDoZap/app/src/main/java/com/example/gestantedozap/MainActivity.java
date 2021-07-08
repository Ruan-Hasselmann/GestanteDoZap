package com.example.gestantedozap;

import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.android.volley.*;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    LinearLayout menu, layout;
    TextView textView;
    RequestQueue queue = null;
    LayoutInflater postModel;
    View view;
    SwipeRefreshLayout swipeLayout;

    public static final String TAG = "GET";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menu = findViewById(R.id.menu);
        textView = findViewById(R.id.textView);
        layout = findViewById(R.id.postagens);
        postModel = getLayoutInflater();
        getPostagens();
        swipeLayout = findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPostagens();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
        swipeLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)
        );
    }

    private void getPostagens() {
        layout.removeAllViews();
        queue = Volley.newRequestQueue(this);
        String url = "http://191.233.255.192/api/postagem";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            view = postModel.inflate(R.layout.postagem_layout, layout, false);
                            ImageView imageView = view.findViewById(R.id.imagem);
                            TextView textViewTitulo = view.findViewById(R.id.titulo);
                            TextView textViewDescricao = view.findViewById(R.id.descricao);
                            TextView textViewConteudo = view.findViewById(R.id.conteudo);

                            Picasso.get().load("http://191.233.255.192/api" + response.getJSONObject(i).getString("caminho")).into(imageView);
                            textViewTitulo.setText(response.getJSONObject(i).getString("titulo"));
                            textViewDescricao.setText(response.getJSONObject(i).getString("descricao"));
                            textViewConteudo.setText(response.getJSONObject(i).getString("conteudo"));
                            layout.addView(view);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, error -> {
            Toast.makeText(MainActivity.this, "Erro:" + error.getMessage(), Toast.LENGTH_SHORT).show();
        });

        jsonArrayRequest.setTag(TAG);
        RetryPolicy policy = new DefaultRetryPolicy(10000, 1, 2);
        jsonArrayRequest.setRetryPolicy(policy);
        Log.d("Request", jsonArrayRequest.toString());
        queue.add(jsonArrayRequest);
    }

    public void menu(View view) {
        if (menu.getVisibility() == View.GONE) {
            menu.setVisibility(View.VISIBLE);
        } else {
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

    public void abrirInsta(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/gestantedozap/"));
        startActivity(browserIntent);
    }

    private class PostagensAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}