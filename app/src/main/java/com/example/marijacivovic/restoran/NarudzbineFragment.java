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
import domen.Potkategorija;
import domen.StavkaMenija;
import domen.StavkaNarudzbine;
import util.SingletonHolder;

public class NarudzbineFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_narudzbine, container, false);
        // Kategorije
        Spinner spinnerKategorija = (Spinner) view.findViewById(R.id.narudzbine_search_kategorija);
        List<Kategorija> kategorije = SingletonHolder.getInstance().getKategorije();
        List<String> kategorijeStrings = new LinkedList<>();
        for (Kategorija order : kategorije)
            kategorijeStrings.add(order.getNaziv());
        ArrayAdapter<String> adapterArray = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, kategorijeStrings);
        adapterArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKategorija.setAdapter(adapterArray);
        spinnerKategorija.setOnItemSelectedListener(new KategorijaSpinnerListener());
        //Potkategorije
        Spinner spinnerPotkategorija = (Spinner) view.findViewById(R.id.narudzbine_search_potkategorija);
        List<Potkategorija> potkategorije = SingletonHolder.getInstance().getPotkategorije();
        kategorijeStrings = new LinkedList<>();
        for (Potkategorija potkat : potkategorije)
            kategorijeStrings.add(potkat.getNaziv());
        adapterArray = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, kategorijeStrings);
        adapterArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPotkategorija.setAdapter(adapterArray);
        spinnerPotkategorija.setOnItemSelectedListener(new PotkategorijaSpinnerListener());
        //Stavke
        Spinner spinnerStavka = (Spinner) view.findViewById(R.id.narudzbine_search_stavka);
        List<StavkaMenija> stavke = SingletonHolder.getInstance().getStavkeMenija();
        kategorijeStrings = new LinkedList<>();
        for (StavkaMenija stavka : stavke)
            kategorijeStrings.add(stavka.getNaziv());
        adapterArray = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, kategorijeStrings);
        adapterArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStavka.setAdapter(adapterArray);
        spinnerStavka.setOnItemSelectedListener(new StavkaSpinnerListener());
        return view;
    }
    public class KategorijaSpinnerListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            SingletonHolder.getInstance().izabranaKategorijaNarudzbine = parent.getItemAtPosition(position).toString();
           // Log.d("Kategorija", SingletonHolder.getInstance().izabranaKategorijaNarudzbine);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            SingletonHolder.getInstance().izabranaKategorijaNarudzbine = "";
        }
    }
    public class PotkategorijaSpinnerListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            SingletonHolder.getInstance().izabranaPotkategorijaNarudzbine = parent.getItemAtPosition(position).toString();
            //Log.d("Potkategorija", SingletonHolder.getInstance().izabranaPotkategorijaNarudzbine);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            SingletonHolder.getInstance().izabranaPotkategorijaNarudzbine = "";
        }
    }
    public class StavkaSpinnerListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            SingletonHolder.getInstance().izabranaStavkaNarudzbine = parent.getItemAtPosition(position).toString();
            //Log.d("Stavka", SingletonHolder.getInstance().izabranaStavkaNarudzbine);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            SingletonHolder.getInstance().izabranaStavkaNarudzbine = "";
        }
    }
}
