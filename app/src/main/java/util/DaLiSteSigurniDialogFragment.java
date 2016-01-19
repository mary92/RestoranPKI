package util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.marijacivovic.restoran.MojNalogActivity;
import com.example.marijacivovic.restoran.PrijavaNaSistemActivity;

/**
 * Created by Marija on 18.01.2016..
 */
public class DaLiSteSigurniDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage("Da li ste sigurni da želite da se odjavite")
                .setTitle("Title");
        // Add the buttons
        builder.setPositiveButton("Ne", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.setNegativeButton("Da", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(getActivity(), PrijavaNaSistemActivity.class);
                startActivity(intent);
            }
        });
        // 3. Get the AlertDialog from create()
        return builder.create();
    }
}
