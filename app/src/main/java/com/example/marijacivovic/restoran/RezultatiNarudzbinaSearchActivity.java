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
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import domen.Narudzbina;
import util.DaLiSteSigurniDialogFragment;
import util.SingletonHolder;

public class RezultatiNarudzbinaSearchActivity extends AppCompatActivity {
    List<Narudzbina> orders;
    ListView listView;
    private MyUserSearchAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezultati_korisnik_search);

        android.support.v7.widget.Toolbar actionToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.signin_toolbar);
        setSupportActionBar(actionToolbar);
        actionToolbar.setLogo(R.mipmap.moj_logo);

        listView = (ListView) findViewById(R.id.searched_users_listView);
        orders = SingletonHolder.getInstance().getSearchNarudzbine();
        listAdapter = new MyUserSearchAdapter();
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                Narudzbina order = (Narudzbina) adapter.getItemAtPosition(position);
                SingletonHolder.getInstance().adminSelektovanaNarudzbina = order;
                Intent intent = new Intent(RezultatiNarudzbinaSearchActivity.this, NarudzbinaPogledActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rezultati_search, menu);
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
                Intent intent = new Intent(RezultatiNarudzbinaSearchActivity.this, MojNalogActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_odjava:
                new DaLiSteSigurniDialogFragment().show(getFragmentManager(), "Tag");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class MyUserSearchAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return orders.size();
        }

        @Override
        public Object getItem(int position) {
            return orders.get(position % orders.size());
        }

        @Override
        public long getItemId(int position) {
            return position % orders.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            SearchOrderViewHolder viewHolder;

            Narudzbina currentOrder = orders.get(position % orders.size());

            if (convertView != null) {
                view = convertView;
                viewHolder = (SearchOrderViewHolder) view.getTag();
            } else {
                LayoutInflater inf = LayoutInflater.from(RezultatiNarudzbinaSearchActivity.this);
                //recikliram korisnik_search_item
                view = inf.inflate(R.layout.korisnik_search_item, null);

                viewHolder = new SearchOrderViewHolder();
                viewHolder.orderDescription = (TextView) view.findViewById(R.id.userDescription);
                view.setTag(viewHolder);
            }

            viewHolder.orderDescription.setText(currentOrder.getSto() + " " + currentOrder.getVreme());
            viewHolder.position = position;

            return view;
        }
    }

    public class SearchOrderViewHolder {
        public int position;
        public TextView orderDescription;
    }
}

