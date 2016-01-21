package com.example.marijacivovic.restoran;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.LinkedList;
import java.util.List;

import domen.Korisnik;
import util.DaLiSteSigurniDialogFragment;
import util.SingletonHolder;
import util.UserTypeAdapter;

public class KorisniciSearchActivity extends AppCompatActivity {
    private String[] users = {"Administrator", "Konobar"};
    EditText editTextKorisnickoIme, editTextIme, editTextPrezime, editTextBrojTelefona,
            editTextEmail;
    String tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_korisnici_search);
        android.support.v7.widget.Toolbar actionToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.signin_toolbar);
        setSupportActionBar(actionToolbar);
        actionToolbar.setLogo(R.mipmap.moj_logo);
        editTextKorisnickoIme = (EditText) findViewById(R.id.korisnici_search_username_box);
        editTextIme = (EditText) findViewById(R.id.korisnici_search_ime_box);
        editTextPrezime = (EditText) findViewById(R.id.korisnici_search_prezime_box);
        editTextBrojTelefona = (EditText) findViewById(R.id.korisnici_search_broj_tel_box);
        editTextEmail = (EditText) findViewById(R.id.korisnici_search_email_box);

        // Add adapter, and action listener for type spinner.
        Spinner spinner = (Spinner) findViewById(R.id.korisnici_search_spinner);
        spinner.setAdapter(new UserTypeAdapter(this, users));
        spinner.setOnItemSelectedListener(new UserTypeSearchSpinnerListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_korisnici_search, menu);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        android.support.v7.widget.Toolbar actionToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.signin_toolbar);
        actionToolbar.setTitle("       Kika express");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_moj_nalog:
                Intent intent = new Intent(KorisniciSearchActivity.this, MojNalogActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_odjava:
                new DaLiSteSigurniDialogFragment().show(getFragmentManager(), "Tag");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClickSearch(View view) {
        //TODO
        List<Korisnik> searchKorisnici = new LinkedList<>();
        //change this

        searchKorisnici = SingletonHolder.getInstance().getKorisnici();
        //the actual search here
        

        if (!editTextKorisnickoIme.getText().toString().equals("")) {
            for (int i = searchKorisnici.size() - 1; i >= 0; i--) {
                Korisnik n = searchKorisnici.get(i);
                if (!n.getKorisnickoIme().contains(editTextKorisnickoIme.getText().toString())) {
                    searchKorisnici.remove(n);
                }
            }
        }

        

        if (!editTextIme.getText().toString().equals("")) {
            for (int i = searchKorisnici.size() - 1; i >= 0; i--) {
                Korisnik n = searchKorisnici.get(i);
                if (!n.getIme().contains(editTextIme.getText().toString())) {
                    searchKorisnici.remove(n);
                }
            }
        }

        if (!editTextPrezime.getText().toString().equals("")) {
            for (int i = searchKorisnici.size() - 1; i >= 0; i--) {
                Korisnik n = searchKorisnici.get(i);
                if (!n.getPrezime().contains(editTextPrezime.getText().toString())) {
                    searchKorisnici.remove(n);
                }
            }
        }


            for (int i = searchKorisnici.size() - 1; i >= 0; i--) {
                Korisnik n = searchKorisnici.get(i);
                if (!n.getTipKorisnika().equals(tip)) {
                    searchKorisnici.remove(n);
                }
            }


        if (!editTextBrojTelefona.getText().toString().equals("")) {
            for (int i = searchKorisnici.size() - 1; i >= 0; i--) {
                Korisnik n = searchKorisnici.get(i);
                if (!n.getTelefon().contains(editTextBrojTelefona.getText().toString())) {
                    searchKorisnici.remove(n);
                }
            }
        }

        if (!editTextEmail.getText().toString().equals("")) {
            for (int i = searchKorisnici.size() - 1; i >= 0; i--) {
                Korisnik n = searchKorisnici.get(i);
                if (!n.getEmail().contains(editTextEmail.getText().toString())) {
                    searchKorisnici.remove(n);
                }
            }
        }

              if (searchKorisnici.size() > 0) {
            SingletonHolder.getInstance().setSearchKorisnici(searchKorisnici);
            Intent intent = new Intent(KorisniciSearchActivity.this, RezultatiKorisnikSearchActivity.class);
            startActivity(intent);
        } else {
            Context context = getApplicationContext();
            SingletonHolder.showToast("Nema korisnika koji zadovoljavaju parametre pretrage!", context);
        }
    }

    public class UserTypeSearchSpinnerListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getItemAtPosition(position).equals("Administrator")) {
                tip = "administrator";
            } else if (parent.getItemAtPosition(position).equals("Konobar")) {
                tip = "konobar";
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            tip = "administrator";
        }
    }
}
