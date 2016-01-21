package com.example.marijacivovic.restoran;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.List;

import domen.*;
import util.DaLiSteSigurniDialogFragment;
import util.SingletonHolder;


public class PrijavaNaSistemActivity extends AppCompatActivity {
    EditText username_box;
    EditText password_box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prijava_na_sistem);

        android.support.v7.widget.Toolbar actionToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.signin_toolbar);
        setSupportActionBar(actionToolbar);
        actionToolbar.setLogo(R.mipmap.moj_logo);
        username_box = (EditText) findViewById(R.id.username_box);
        password_box = (EditText) findViewById(R.id.password_box);
    }

    @Override
    public void onResume() {
        super.onResume();
        android.support.v7.widget.Toolbar actionToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.signin_toolbar);
        actionToolbar.setTitle("       Kika express");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_prijava_na_sistem, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_moj_nalog:
                Intent intent = new Intent(PrijavaNaSistemActivity.this, MojNalogActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_odjava:
                new DaLiSteSigurniDialogFragment().show(getFragmentManager(), "Tag");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onSignIn(final View view) {
        boolean postoji = false;
        Korisnik ulogovani = null;
        String korisnickoIme = username_box.getText().toString().trim();
        if (!korisnickoIme.equals("")) {
            for (Korisnik k : SingletonHolder.getInstance().getKorisnici()) {
                if (k.getKorisnickoIme().equals(korisnickoIme)) {
                    postoji = true;
                    ulogovani = k;
                    break;

                }
            }
            if (postoji) {
                SingletonHolder.getInstance().setUlogovaniKorisnik(ulogovani);
                String korisnickaSifra = new String(password_box.getText().toString());
                if (!korisnickaSifra.equals("")) {

                    if (korisnickoIme.equals("admin")) {
                        if (!korisnickaSifra.equals("admin")) {
                            Context context = getApplicationContext();
                            SingletonHolder.showToast("Pogrešna lozinka!", context);
                        }
                        Intent intent = new Intent(PrijavaNaSistemActivity.this, AdministratorActivity.class);
                        startActivity(intent);
                    } else {
                        List<Korisnik> korisnici = SingletonHolder.getInstance().getKorisnici();
                        boolean sifraOk = false;
                        for (Korisnik korisnik : korisnici) {
                            if (korisnik.getKorisnickoIme().equals(korisnickoIme)) {
                                sifraOk = korisnik.getKorisnickaSifra().equals(korisnickaSifra);
                                break;
                            }
                        }
                        if (!sifraOk) {
                            Context context = getApplicationContext();
                            SingletonHolder.showToast("Pogrešna lozinka!", context);
                        }
                        Intent intent = new Intent(PrijavaNaSistemActivity.this, KonobarActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Context context = getApplicationContext();
                    SingletonHolder.showToast("Niste uneli korisničku šifru!", context);
                }
            } else {
                Context context = getApplicationContext();
                SingletonHolder.showToast("Pogrešno korisničko ime!", context);
                //TODO
                Korisnik pomocni = new Korisnik();
                pomocni.setKorisnickoIme("koko");
                pomocni.setKorisnickaSifra("koko");
                SingletonHolder.getInstance().setUlogovaniKorisnik(pomocni);
                Intent intent = new Intent(PrijavaNaSistemActivity.this, KonobarActivity.class);
                startActivity(intent);
            }
        } else {
            Context context = getApplicationContext();
            SingletonHolder.showToast("Niste uneli korisničko ime!", context);
            //TODO
            Korisnik pomocni = new Korisnik();
            pomocni.setKorisnickoIme("admin");
            pomocni.setKorisnickaSifra("admin");
            SingletonHolder.getInstance().setUlogovaniKorisnik(pomocni);
            Intent intent = new Intent(PrijavaNaSistemActivity.this, AdministratorActivity.class);
            startActivity(intent);
        }
    }


}
