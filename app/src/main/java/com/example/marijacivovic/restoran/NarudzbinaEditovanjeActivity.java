package com.example.marijacivovic.restoran;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import domen.Korisnik;
import domen.Narudzbina;
import domen.StavkaNarudzbine;
import util.DaLiSteSigurniDialogFragment;
import util.SingletonHolder;

public class NarudzbinaEditovanjeActivity extends AppCompatActivity {

    EditText editTextSto, editTextPlaceno;
    ListView listView;
    Narudzbina narudzbina;
    MyUserAdapter listAdapter;
    List<StavkaNarudzbine> stavke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_narudzbina_editovanje);
        android.support.v7.widget.Toolbar actionToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.signin_toolbar);
        setSupportActionBar(actionToolbar);
        actionToolbar.setLogo(R.mipmap.moj_logo);


        listView = (ListView) findViewById(R.id.edit_stavke_listView);
        if (SingletonHolder.getInstance().narudzbinaKreiranje) {
            narudzbina = new Narudzbina();
            narudzbina.setSto("");
            narudzbina.setStavke(new LinkedList<StavkaNarudzbine>());
            stavke = narudzbina.getStavke();

        } else {
            narudzbina = SingletonHolder.getInstance().adminSelektovanaNarudzbina;
            stavke = SingletonHolder.getInstance().adminSelektovanaNarudzbina.getStavke();
        }

        SingletonHolder.getInstance().setKonobarStavke(stavke);
        listAdapter = new MyUserAdapter();
        listView.setAdapter(listAdapter);

        editTextSto = (EditText) findViewById(R.id.narudzbina_sto_box);
        editTextSto.setText(narudzbina.getSto());
        editTextPlaceno = (EditText) findViewById(R.id.narudzbina_placeno_box);
        editTextPlaceno.setText((narudzbina.getStavke().size() == 0) ? "Da" : "Ne");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                StavkaNarudzbine stavka = (StavkaNarudzbine) parent.getItemAtPosition(position);
                CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox);
                stavka.setPlaceno(checkBox.isChecked());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_narudzbina_editovanje, menu);
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
                Intent intent = new Intent(NarudzbinaEditovanjeActivity.this, MojNalogActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_odjava:
                new DaLiSteSigurniDialogFragment().show(getFragmentManager(), "Tag");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void saveOrder(View view) {

            narudzbina.setSto(editTextSto.getText().toString());
            //TODO
            narudzbina.setStavke(SingletonHolder.getInstance().getKonobarStavke());
        if (SingletonHolder.getInstance().narudzbinaKreiranje) {
            SingletonHolder.getInstance().getNarudzbine().add(narudzbina);
        }
        else
        {
            for (int i=0; i<SingletonHolder.getInstance().getNarudzbine().size();i++){
                if (SingletonHolder.getInstance().getNarudzbine().get(i).equals(narudzbina)){
                    SingletonHolder.getInstance().getNarudzbine().set(i,narudzbina);
                }
            }

        }

    }

    public void addStavka(View view) {
        Intent intent = new Intent(NarudzbinaEditovanjeActivity.this, StavkeNarudzbineDodajActivity.class);
        startActivity(intent);

    }

    public void removeStavka(View view) {
        int position = listView.getPositionForView(view);
        stavke.remove(position);
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

            StavkaNarudzbine trenutnaStavka = null;
            if (stavke.size() > 0)
                trenutnaStavka = stavke.get(position % stavke.size());

            if (convertView != null) {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            } else {
                LayoutInflater inf = LayoutInflater.from(NarudzbinaEditovanjeActivity.this);
                view = inf.inflate(R.layout.stavka_edit_item, null);

                viewHolder = new ViewHolder();
                viewHolder.stavkaDescription = (TextView) view.findViewById(R.id.stavkaDescription);
                view.setTag(viewHolder);
            }

            if (trenutnaStavka != null)
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
