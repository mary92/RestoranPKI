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

import domen.Korisnik;
import util.DaLiSteSigurniDialogFragment;
import util.SingletonHolder;

public class RezultatiSearchActivity extends AppCompatActivity {
    List<Korisnik> usersInRestaurant;
    ListView listView;
    private MyUserSearchAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezultati_search);

        android.support.v7.widget.Toolbar actionToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.signin_toolbar);
        setSupportActionBar(actionToolbar);
        actionToolbar.setLogo(R.mipmap.moj_logo);

        listView = (ListView) findViewById(R.id.searched_users_listView);
        usersInRestaurant = SingletonHolder.getInstance().getSearchKorisnici();
        listAdapter = new MyUserSearchAdapter();
        listView.setAdapter(listAdapter);
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
                Intent intent = new Intent(RezultatiSearchActivity.this, MojNalogActivity.class);
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
            return usersInRestaurant.size();
        }

        @Override
        public Object getItem(int position) {
            return usersInRestaurant.get(position % usersInRestaurant.size());
        }

        @Override
        public long getItemId(int position) {
            return position % usersInRestaurant.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            SearchViewHolder viewHolder;

            Korisnik currentUser = usersInRestaurant.get(position % usersInRestaurant.size());

            if (convertView != null) {
                view = convertView;
                viewHolder = (SearchViewHolder) view.getTag();
            } else {
                LayoutInflater inf = LayoutInflater.from(RezultatiSearchActivity.this);
                view = inf.inflate(R.layout.korisnik_search_item, null);

                viewHolder = new SearchViewHolder();
                viewHolder.userDescription = (TextView) view.findViewById(R.id.userDescription);
                view.setTag(viewHolder);
            }

            viewHolder.userDescription.setText(currentUser.getIme()+ " " + currentUser.getPrezime());
            viewHolder.position = position;

            return view;
        }
    }

    public class SearchViewHolder{
        public int position;
        public TextView userDescription;
    }
}
