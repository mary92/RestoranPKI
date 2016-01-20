package util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

/**
 * Created by demouser on 8/6/15.
 */
public class UserTypeAdapter extends BaseAdapter implements SpinnerAdapter {
    String[] userTypes;
    Activity which;

    public UserTypeAdapter(Activity _which, String[] userTypes) {
        which = _which;
        this.userTypes = userTypes;
    }

    @Override
    public int getCount() {
        return userTypes.length;
    }

    @Override
    public Object getItem(int position) {
        return userTypes[position % userTypes.length];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view;

        if (convertView == null) {
            view = (TextView) LayoutInflater.from(which).inflate(android.R.layout.simple_spinner_dropdown_item, null);
        } else {
            view = (TextView) convertView;
        }
        view.setPadding(80, 30, 80, 30);
        view.setText(userTypes[position]);
        return view;
    }
}
