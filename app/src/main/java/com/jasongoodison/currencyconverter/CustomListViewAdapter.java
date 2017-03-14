package com.jasongoodison.currencyconverter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jason on 10/1/16.
 */
public class CustomListViewAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> country;

    private static LayoutInflater inflater = null;

    public CustomListViewAdapter(Context context, ArrayList<String> data) {
        mContext = context;
        country = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return country.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        int myPos = position;

        if (convertView == null) {
            view = inflater.inflate(R.layout.list_row, null);

            ImageView image = (ImageView) view.findViewById(R.id.flag);

            Log.v("test",country.get(myPos));
            Log.v("test","my pos " + myPos);
            image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_launcher));
        }
        TextView countryName = (TextView) view.findViewById(R.id.country);
        countryName.setText(country.get(myPos));

        return view;
    }
}
