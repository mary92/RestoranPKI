package com.example.marijacivovic.restoran;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import domen.Korisnik;
import util.SingletonHolder;

public class KorisniciFragment extends Fragment {
    List<Korisnik> usersInRestaurant;
    ListView listView;
    private MyUserAdapter listAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_korisnici, container, false);
        listView = (ListView) rootView.findViewById(R.id.users_listView);
        usersInRestaurant = SingletonHolder.getInstance().getKorisnici();
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
            ViewHolder viewHolder;

            Korisnik currentUser = usersInRestaurant.get(position % usersInRestaurant.size());

            if (convertView != null) {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            } else {
                LayoutInflater inf = LayoutInflater.from(getActivity());
                view = inf.inflate(R.layout.korisnik_item, null);

                viewHolder = new ViewHolder();
                viewHolder.userDescription = (TextView) view.findViewById(R.id.userDescription);
                view.setTag(viewHolder);
            }

            viewHolder.userDescription.setText(currentUser.getIme()+ " " + currentUser.getPrezime());
            viewHolder.position = position;

            return view;
        }
    }

    public class ViewHolder{
        public int position;
        public TextView userDescription;
    }
}