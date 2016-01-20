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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import domen.Korisnik;
import util.DaLiSteSigurniDialogFragment;
import util.SingletonHolder;
import util.UserTypeAdapter;

public class KorisniciDodajActivity extends AppCompatActivity {
    private String[] users = {"Administrator", "Konobar"};
    ImageView slika;
    EditText editTextKorisnickoIme, editTextLozinka, editTextIme, editTextPrezime, editTextBrojTelefona,
            editTextEmail, editTextSlika;
    String tip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_korisnici_dodaj);
        android.support.v7.widget.Toolbar actionToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.signin_toolbar);
        setSupportActionBar(actionToolbar);
        actionToolbar.setLogo(R.mipmap.moj_logo);

        slika = (ImageView) findViewById(R.id.dodaj_image);
        editTextKorisnickoIme = (EditText) findViewById(R.id.dodaj_username_box);
        editTextLozinka = (EditText) findViewById(R.id.dodaj_lozinka_box);
        editTextIme = (EditText) findViewById(R.id.dodaj_ime_box);
        editTextPrezime = (EditText) findViewById(R.id.dodaj_prezime_box);
        editTextBrojTelefona = (EditText) findViewById(R.id.dodaj_broj_tel_box);
        editTextEmail = (EditText) findViewById(R.id.dodaj_email_box);
        editTextSlika = (EditText) findViewById(R.id.dodaj_slika_box);

        // Add adapter, and action listener for type spinner.
        Spinner spinner = (Spinner) findViewById(R.id.dodaj_spinner);
        spinner.setAdapter(new UserTypeAdapter(this, users));
        spinner.setOnItemSelectedListener(new UserTypeSpinnerListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_korisnici_dodaj, menu);
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
                Intent intent = new Intent(KorisniciDodajActivity.this, MojNalogActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_odjava:
                new DaLiSteSigurniDialogFragment().show(getFragmentManager(), "Tag");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private static final int FILE_SELECT_CODE = 0;

    public void onClickDodajBtn(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    slika.setImageURI(uri);
                    String path = uri.getPath();
                    editTextSlika.setText(path);
                    Log.d("Marija", "File Path: " + path);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onClickPotvrdiBtn(View view) {
        if (editTextKorisnickoIme.getText().equals("") || editTextLozinka.getText().equals("") || editTextIme.getText().equals("")
                || editTextPrezime.getText().equals("")) {
            Context context = getApplicationContext();
            SingletonHolder.showToast("Morate popuniti obavezna polja", context);
        } else {
            Korisnik k = new Korisnik();
            List<Korisnik> korisnici = SingletonHolder.getInstance().getKorisnici();
            boolean postoji = false;

            for (int i = 0; i < korisnici.size(); i++) {
                Korisnik k1 = korisnici.get(i);
                if (k1.getKorisnickoIme().equals(editTextKorisnickoIme.getText().toString())) {
                    postoji = true;
                    break;
                }
            }
            if (!postoji) {
                k.setKorisnickoIme(editTextKorisnickoIme.getText().toString());
                k.setKorisnickaSifra(editTextLozinka.getText().toString());
                k.setIme(editTextIme.getText().toString());
                k.setPrezime(editTextPrezime.getText().toString());
                k.setTelefon(editTextBrojTelefona.getText().toString());
                k.setEmail(editTextEmail.getText().toString());
                k.setSlika(editTextSlika.getText().toString());
                k.setTipKorisnika(tip);
                korisnici.add(k);
                Context context = getApplicationContext();
                SingletonHolder.showToast("Uspešno ste izmenili nalog!", context);
            } else {
                Context context = getApplicationContext();
                SingletonHolder.showToast("Korisnicko ime vec postoji", context);
            }
        }
    }

    public class UserTypeSpinnerListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getItemAtPosition(position).equals("Administrator")) {
                tip = "Administrator";
            } else if (parent.getItemAtPosition(position).equals("Konobar")) {
                tip = "Konobar";
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            tip = "Administrator";
        }
    }
}
