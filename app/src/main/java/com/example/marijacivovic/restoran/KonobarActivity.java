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
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import domen.Narudzbina;
import domen.StavkaNarudzbine;
import util.BrisanjeKorisnikaDialogFragment;
import util.BrisanjeNarudzbineDialogFragment;
import util.DaLiSteSigurniDialogFragment;
import util.SingletonHolder;

public class KonobarActivity extends AppCompatActivity {

    ListView listView;
    Narudzbina narudzbina;
    MyUserAdapter listAdapter;
    List<Narudzbina> narudzbine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konobar);
        android.support.v7.widget.Toolbar actionToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.signin_toolbar);
        setSupportActionBar(actionToolbar);
        actionToolbar.setLogo(R.mipmap.moj_logo);

        narudzbine = SingletonHolder.getInstance().getNarudzbine();
        listView = (ListView) findViewById(R.id.narudzbine_listView);
        listAdapter = new MyUserAdapter();
        listView.setAdapter(listAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_konobar, menu);
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
                Intent intent = new Intent(KonobarActivity.this, MojNalogActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_odjava:
                new DaLiSteSigurniDialogFragment().show(getFragmentManager(), "Tag");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void addOrder(View view){
        SingletonHolder.getInstance().narudzbinaKreiranje = true;
        Intent intent = new Intent(KonobarActivity.this, NarudzbinaEditovanjeActivity.class);
        startActivity(intent);
    }

    public void editItem(View view){
        int position = listView.getPositionForView(view);
        Narudzbina order = (Narudzbina) listAdapter.getItem(position);
        SingletonHolder.getInstance().narudzbinaKreiranje = false;
        SingletonHolder.getInstance().adminSelektovanaNarudzbina = order;
        Intent intent = new Intent(KonobarActivity.this, NarudzbinaEditovanjeActivity.class);
        startActivity(intent);
    }

    public void removeItem(View view){
        BrisanjeNarudzbineDialogFragment brisanjeNarudzbineDialogFragment = new BrisanjeNarudzbineDialogFragment();
        brisanjeNarudzbineDialogFragment.setListViewAndView((ListView) getCurrentFocus(), view);
        brisanjeNarudzbineDialogFragment.show(getFragmentManager(), "Tag");
    }

    public class MyUserAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return narudzbine.size();
        }

        @Override
        public Object getItem(int position) {
            return narudzbine.get(position % narudzbine.size());
        }

        @Override
        public long getItemId(int position) {
            return position % narudzbine.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder viewHolder;

            Narudzbina trenutnaNarudzbina = narudzbine.get(position % narudzbine.size());

            if (convertView != null) {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            } else {
                LayoutInflater inf = LayoutInflater.from(KonobarActivity.this);
                view = inf.inflate(R.layout.meni_item, null);

                viewHolder = new ViewHolder();
                viewHolder.narudzbinaDescription = (TextView) view.findViewById(R.id.itemDescription);
                view.setTag(viewHolder);
            }

            viewHolder.narudzbinaDescription.setText(trenutnaNarudzbina.getSto());
            viewHolder.position = position;

            return view;
        }
    }

    public class ViewHolder {
        public int position;
        public TextView narudzbinaDescription;
    }
}
