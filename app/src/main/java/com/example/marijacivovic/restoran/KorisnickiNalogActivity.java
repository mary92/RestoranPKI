package com.example.marijacivovic.restoran;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import domen.Korisnik;
import util.DaLiSteSigurniDialogFragment;
import util.SingletonHolder;

public class KorisnickiNalogActivity extends AppCompatActivity {

    ImageView slika;
    EditText editTextIme, editTextPrezime, editTextBrojTelefona,
            editTextEmail, editTextSlika;
    Korisnik korisnik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_korisnicki_nalog);
        android.support.v7.widget.Toolbar actionToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.signin_toolbar);
        setSupportActionBar(actionToolbar);
        actionToolbar.setLogo(R.mipmap.moj_logo);
        slika = (ImageView) findViewById(R.id.moj_nalog_image);
        editTextIme = (EditText) findViewById(R.id.moj_nalog_ime_box);
        editTextPrezime = (EditText) findViewById(R.id.moj_nalog_prezime_box);
        editTextBrojTelefona = (EditText) findViewById(R.id.moj_nalog_broj_tel_box);
        editTextEmail = (EditText) findViewById(R.id.moj_nalog_email_box);
        editTextSlika = (EditText) findViewById(R.id.moj_nalog_slika_box);
        korisnik = SingletonHolder.getInstance().adminSelektovaniKorisnik;
        if (korisnik != null) {
            editTextIme.setText(korisnik.getKorisnickoIme());
            editTextPrezime.setText(korisnik.getPrezime());
            editTextBrojTelefona.setText(korisnik.getTelefon());
            editTextEmail.setText(korisnik.getEmail());
            editTextSlika.setText(korisnik.getSlika());
            if (korisnik.getSlika() != null) {
                File testSlika = new File(korisnik.getSlika());
                if (testSlika.exists()) {
                    slika.setImageURI(Uri.fromFile(testSlika));
                }
            }
        }
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
        getMenuInflater().inflate(R.menu.menu_moj_nalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_moj_nalog:
                return true;
            case R.id.menu_odjava:
                new DaLiSteSigurniDialogFragment().show(getFragmentManager(), "Tag");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
