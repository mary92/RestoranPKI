package com.example.marijacivovic.restoran;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import domen.Korisnik;

public class KorisniciFragment extends Fragment {
    ArrayList<Korisnik> usersInRestaurant = new ArrayList<>();
    ListView listView;
    private MyUserAdapter listAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_korisnici, container, false);
        listView = (ListView) rootView.findViewById(R.id.users_listView);
        listAdapter = new MyUserAdapter();
        listView.setAdapter(listAdapter);
        return rootView;
    }

    public class MyUserAdapter extends BaseAdapter {

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
            Korisnik viewHolder;

            Korisnik currentUser = usersInRestaurant.get(position % usersInRestaurant.size());

            if (convertView != null) {
                view = convertView;
                viewHolder = (Korisnik) view.getTag();
            } else {
                LayoutInflater inf = LayoutInflater.from(AdministratorActivity.this);
                view = inf.inflate(R.layout.korisnik_item, null);

                viewHolder = new AnimalCardHolder();
                viewHolder.animalDescription = (TextView) view.findViewById(R.id.animalDescription);
                view.setTag(viewHolder);
            }

            viewHolder.animalDescription.setText(currentAnimal.getDescription());
            viewHolder.position = position;

            return view;
        }

    }
}