package com.example.marijacivovic.restoran;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import domen.StavkaMenija;
import domen.StavkaNarudzbine;
import util.DaLiSteSigurniDialogFragment;
import util.SingletonHolder;
import util.UserTypeAdapter;

public class StavkeNarudzbineDodajActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stavke_narudzbine_dodaj);

        Spinner s = (Spinner) findViewById(R.id.narudzbine_search_kategorija);
        List<StavkaMenija> stavkeMenija = SingletonHolder.getInstance().getStavkeMenija();
        MyAdapter adapter = new MyAdapter(stavkeMenija);
        // apply the Adapter:
        s.setAdapter(adapter);
        // onClickListener:
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * Called when a new item was selected (in the Spinner)
             */
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int pos, long id) {
                StavkaMenija g = (StavkaMenija) parent.getItemAtPosition(pos);
                StavkaNarudzbine sn = new StavkaNarudzbine();
                sn.setStavkaMenija(g);
                sn.setPlaceno(false);
                SingletonHolder.getInstance().getKonobarStavke().add(sn);

            }

            public void onNothingSelected(AdapterView parent) {
                // Do nothing.
            }
        });
    }

    /**
     * This is your own Adapter implementation which displays
     * the ArrayList of "StavkaMenija"-Objects.
     */
    private class MyAdapter extends BaseAdapter implements SpinnerAdapter {

        /**
         * The internal data (the ArrayList with the Objects).
         */
        private final List<StavkaMenija> data;

        public MyAdapter(List<StavkaMenija> data) {
            this.data = data;
        }

        /**
         * Returns the Size of the ArrayList
         */
        @Override
        public int getCount() {
            return data.size();
        }

        /**
         * Returns one Element of the ArrayList
         * at the specified position.
         */
        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        /**
         * Returns the View that is shown when a element was
         * selected.
         */
        @Override
        public View getView(int position, View recycle, ViewGroup parent) {
            TextView text;
            if (recycle != null) {
                // Re-use the recycled view here!
                text = (TextView) recycle;
            } else {
                // No recycled view, inflate the "original" from the platform:
                text = (TextView) getLayoutInflater().inflate(
                        android.R.layout.simple_dropdown_item_1line, parent, false
                );
            }
            text.setTextColor(Color.BLACK);
            text.setText(data.get(position).getNaziv());
            return text;
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stavke_narudzbine_dodaj, menu);
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

    }
