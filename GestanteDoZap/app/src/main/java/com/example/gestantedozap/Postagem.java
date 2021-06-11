package com.example.gestantedozap;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Postagem extends AppCompatActivity {

    LinearLayout menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postagem);
        menu = findViewById(R.id.menu);
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
}