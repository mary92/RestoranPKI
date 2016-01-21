package com.example.marijacivovic.restoran;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import domen.Korisnik;
import domen.Narudzbina;
import domen.StavkaNarudzbine;
import util.DaLiSteSigurniDialogFragment;
import util.SingletonHolder;

public class NarudzbinaPogledActivity extends AppCompatActivity {

    EditText editTextSto, editTextPlaceno;
    ListView listView;
    Narudzbina narudzbina;
    MyUserAdapter listAdapter;
    List<StavkaNarudzbine> stavke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_narudzbina_pogled);
        android.support.v7.widget.Toolbar actionToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.signin_toolbar);
        setSupportActionBar(actionToolbar);
        actionToolbar.setLogo(R.mipmap.moj_logo);


        listView = (ListView) findViewById(R.id.stavke_listView);
        narudzbina = SingletonHolder.getInstance().adminSelektovanaNarudzbina;
        stavke = SingletonHolder.getInstance().adminSelektovanaNarudzbina.getStavke();
        listAdapter = new MyUserAdapter();
        listView.setAdapter(listAdapter);

        editTextSto = (EditText) findViewById(R.id.narudzbina_sto_box);
        editTextSto.setText(narudzbina.getSto());
        editTextPlaceno = (EditText) findViewById(R.id.narudzbina_placeno_box);
        editTextPlaceno.setText((narudzbina.getStavke().size() == 0) ? "Da" : "Ne");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_narudzbina_pogled, menu);
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
                Intent intent = new Intent(NarudzbinaPogledActivity.this, MojNalogActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_odjava:
                new DaLiSteSigurniDialogFragment().show(getFragmentManager(), "Tag");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class MyUserAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return stavke.size();
        }

        @Override
        public Object getItem(int position) {
            return stavke.get(position % stavke.size());
        }

        @Override
        public long getItemId(int position) {
            return position % stavke.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder viewHolder;

            StavkaNarudzbine trenutnaStavka = stavke.get(position % stavke.size());

            if (convertView != null) {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            } else {
                LayoutInflater inf = LayoutInflater.from(NarudzbinaPogledActivity.this);
                view = inf.inflate(R.layout.korisnik_search_item, null);

                viewHolder = new ViewHolder();
                viewHolder.stavkaDescription = (TextView) view.findViewById(R.id.userDescription);
                view.setTag(viewHolder);
            }

            viewHolder.stavkaDescription.setText(trenutnaStavka.getStavkaMenija().getNaziv() + " - " + trenutnaStavka.getStavkaMenija().getCena() + " din");
            viewHolder.position = position;

            return view;
        }
    }

    public class ViewHolder {
        public int position;
        public TextView stavkaDescription;
    }
}
