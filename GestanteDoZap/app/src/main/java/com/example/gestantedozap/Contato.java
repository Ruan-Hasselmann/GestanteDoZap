package com.example.gestantedozap;

import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Contato extends AppCompatActivity {

    LinearLayout menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);
        menu = findViewById(R.id.menu);
    }

    public void menu(View view) {
        if (menu.getVisibility() == View.GONE) {
            menu.setVisibility(View.VISIBLE);
        } else {
            menu.setVisibility(View.GONE);
        }
    }

    /*public void abrirTopicos(View view) {
        Intent it = new Intent(this, Topicos.class);
        startActivity(it);
        finish();
    }*/

    public void abrirInicio(View view) {
        finish();
    }

    /*public void abrirPerfil(View view) {
        Intent it = new Intent(this, Perfil.class);
        startActivity(it);
        finish();
    }*/

    public void abrirInsta(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/gestantedozap/"));
        startActivity(browserIntent);
    }
}