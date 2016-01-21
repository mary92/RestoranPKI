package com.example.marijacivovic.restoran;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;

import domen.Korisnik;
import util.DaLiSteSigurniDialogFragment;
import util.SingletonHolder;

public class MojNalogActivity extends AppCompatActivity {

    ImageView slika;
    EditText editTextLozinka, editTextIme, editTextPrezime, editTextBrojTelefona,
            editTextEmail, editTextSlika;
    Korisnik korisnik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moj_nalog);
        android.support.v7.widget.Toolbar actionToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.signin_toolbar);
        setSupportActionBar(actionToolbar);
        actionToolbar.setLogo(R.mipmap.moj_logo);
        slika = (ImageView) findViewById(R.id.moj_nalog_image);
        editTextLozinka = (EditText) findViewById(R.id.moj_nalog_password_box);
        editTextIme = (EditText) findViewById(R.id.moj_nalog_ime_box);
        editTextPrezime = (EditText) findViewById(R.id.moj_nalog_prezime_box);
        editTextBrojTelefona = (EditText) findViewById(R.id.moj_nalog_broj_tel_box);
        editTextEmail = (EditText) findViewById(R.id.moj_nalog_email_box);
        editTextSlika = (EditText) findViewById(R.id.moj_nalog_slika_box);
        korisnik = SingletonHolder.getInstance().getUlogovaniKorisnik();
        if (korisnik != null) {
            editTextLozinka.setText(korisnik.getKorisnickaSifra());
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
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onClickPotvrdiBtn(View view) {
        SingletonHolder.getInstance().getUlogovaniKorisnik().setKorisnickaSifra(editTextLozinka.getText().toString());
        SingletonHolder.getInstance().getUlogovaniKorisnik().setIme(editTextIme.getText().toString());
        SingletonHolder.getInstance().getUlogovaniKorisnik().setPrezime(editTextPrezime.getText().toString());
        SingletonHolder.getInstance().getUlogovaniKorisnik().setTelefon(editTextBrojTelefona.getText().toString());
        SingletonHolder.getInstance().getUlogovaniKorisnik().setEmail(editTextEmail.getText().toString());
        SingletonHolder.getInstance().getUlogovaniKorisnik().setSlika(editTextSlika.getText().toString());

        Context context = getApplicationContext();
        SingletonHolder.showToast("Uspešno ste izmenili nalog!", context);
    }

}
