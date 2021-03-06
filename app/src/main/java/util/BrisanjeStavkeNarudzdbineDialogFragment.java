package util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.marijacivovic.restoran.KonobarActivity;
import com.example.marijacivovic.restoran.NarudzbinaEditovanjeActivity;

/**
 * Created by Marija on 18.01.2016..
 */
public class BrisanjeStavkeNarudzdbineDialogFragment extends DialogFragment {
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
                SingletonHolder.getInstance().getStavkeMenija().remove(position);
                NarudzbinaEditovanjeActivity.MyUserAdapter adapter = (NarudzbinaEditovanjeActivity.MyUserAdapter) listView.getAdapter();
                adapter.notifyDataSetChanged();
                Context context = getActivity().getApplicationContext();
                SingletonHolder.showToast("Uspesno ste obrisali stavku!", context);
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

