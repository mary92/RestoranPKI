package util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.marijacivovic.restoran.KorisniciFragment;
import com.example.marijacivovic.restoran.MeniFragment;
import com.example.marijacivovic.restoran.NarudzbineFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                KorisniciFragment tab1 = new KorisniciFragment();
                return tab1;
            case 1:
                MeniFragment tab2 = new MeniFragment();
                return tab2;
            case 2:
                NarudzbineFragment tab3 = new NarudzbineFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
