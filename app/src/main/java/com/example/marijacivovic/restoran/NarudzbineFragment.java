package com.example.marijacivovic.restoran;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.LinkedList;
import java.util.List;

import domen.Kategorija;
import util.SingletonHolder;

public class NarudzbineFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_narudzbine, container, false);
        // Add adapter, and action listener for kategorija spinner.
        Spinner spinnerKategorija = (Spinner) view.findViewById(R.id.narudzbine_search_kategorija);
        List<Kategorija> orders = SingletonHolder.getInstance().getKategorije();
        List<String> ordersStrings = new LinkedList<>();
        for (Kategorija order : orders)
            ordersStrings.add(order.getNaziv());
        //spinner.setAdapter(new UserTypeAdapter(this, R.id.narudzbine_search_kategorija, ordersStrings));
        ArrayAdapter<String> adapterArray = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ordersStrings);
        adapterArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKategorija.setAdapter(adapterArray);
        spinnerKategorija.setOnItemSelectedListener(new KategorijaSpinnerListener());
        return view;
    }
    public class KategorijaSpinnerListener implements AdapterView.OnItemSelectedListener {
        String tip;
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            tip = parent.getItemAtPosition(position).toString();
            Log.d("Kategorija", tip);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            tip = "";
        }
    }
}
