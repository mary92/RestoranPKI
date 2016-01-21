package com.example.marijacivovic.restoran;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.LinkedList;
import java.util.List;

import domen.Korisnik;
import domen.Narudzbina;
import util.BrisanjeKorisnikaDialogFragment;
import util.DaLiSteSigurniDialogFragment;
import util.PagerAdapter;
import util.SingletonHolder;

public class AdministratorActivity extends AppCompatActivity {

    ImageView slika;
    EditText editTextLozinka, editTextIme, editTextPrezime, editTextBrojTelefona,
            editTextEmail, editTextSlika;
    Korisnik korisnik;
    String tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);
        android.support.v7.widget.Toolbar actionToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.signin_toolbar);
        setSupportActionBar(actionToolbar);
        actionToolbar.setLogo(R.mipmap.moj_logo);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Korisnici"));
        tabLayout.addTab(tabLayout.newTab().setText("Meni"));
        tabLayout.addTab(tabLayout.newTab().setText("Narudzbine"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_moj_nalog, menu);
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
                Intent intent = new Intent(AdministratorActivity.this, MojNalogActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_odjava:
                new DaLiSteSigurniDialogFragment().show(getFragmentManager(), "Tag");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void removeUser(View view) {
        BrisanjeKorisnikaDialogFragment brisanjeKorisnikaDialogFragment = new BrisanjeKorisnikaDialogFragment();
        brisanjeKorisnikaDialogFragment.setListViewAndView((ListView) getCurrentFocus(), view);
        brisanjeKorisnikaDialogFragment.show(getFragmentManager(), "Tag");
    }

    public void addUser(View view) {
        Intent intent = new Intent(AdministratorActivity.this, KorisniciDodajActivity.class);
        startActivity(intent);
    }

    public void onClickSearchUser(View view) {
        Intent intent = new Intent(AdministratorActivity.this, KorisniciSearchActivity.class);
        startActivity(intent);
    }

    public void onClickSearchOrder(View view) {
        //TODO
        List<Narudzbina> searchNarudzbine = new LinkedList<>();
        //change this
        searchNarudzbine = SingletonHolder.getInstance().getNarudzbine();
        String dan = ((EditText) findViewById(R.id.narudzbine_search_dan_box)).getText().toString();
        String sto = ((EditText) findViewById(R.id.narudzbine_search_sto_box)).getText().toString();
        String konobar = ((EditText) findViewById(R.id.narudzbine_search_konobar_box)).getText().toString();
        //the actual search here
        if (searchNarudzbine.size() > 0) {
            SingletonHolder.getInstance().setSearchNarudzbine(searchNarudzbine);
            Intent intent = new Intent(AdministratorActivity.this, RezultatiNarudzbinaSearchActivity.class);
            startActivity(intent);
        } else {
            Context context = getApplicationContext();
            SingletonHolder.showToast("Nema korisnika koji zadovoljavaju parametre pretrage!", context);
        }
    }

}
