package util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.marijacivovic.restoran.KonobarActivity;
import com.example.marijacivovic.restoran.KorisniciFragment;
import com.example.marijacivovic.restoran.PrijavaNaSistemActivity;
import com.example.marijacivovic.restoran.R;

import java.util.ConcurrentModificationException;

/**
 * Created by Marija on 18.01.2016..
 */
public class BrisanjeNarudzbineDialogFragment extends DialogFragment {
    ListView listView;
    View view;

    public void setListViewAndView(ListView listView, View view) {
        this.listView = listView;
        this.view = view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage("Da li ste sigurni da želite da obrisete narudzbinu")
                .setTitle("Title");
        // Add the buttons
        builder.setPositiveButton("Potvrdi", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                int position = listView.getPositionForView(view);
                SingletonHolder.getInstance().getNarudzbine().remove(position);
                KonobarActivity.MyUserAdapter adapter = (KonobarActivity.MyUserAdapter) listView.getAdapter();
                adapter.notifyDataSetChanged();
                Context context = getActivity().getApplicationContext();
                SingletonHolder.showToast("Uspesno ste obrisali narudzbinu!", context);
            }
        });
        builder.setNegativeButton("Odustani", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        // 3. Get the AlertDialog from create()
        return builder.create();
    }
}

