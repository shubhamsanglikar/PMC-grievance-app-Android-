package com.mitcoe.shubhamsanglikar.avayasmartcityproject;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shubham on 25-Jan-16.
 */
public class CustomList extends BaseAdapter {

    private final Activity context;
    private final ArrayList<String> uid;
    private final ArrayList<String> username;
    JSONObject jsonObject = new JSONObject();
   // private final Integer[] imageId;
    public CustomList(Activity context,ArrayList<String> uid,ArrayList<String> un) {
        this.context = context;
        this.uid = uid;
        this.username = un;

    }

    @Override
    public int getCount() {
        return this.username.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_row,null,false);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.tv);
        TextView txtSubTitle = (TextView) rowView.findViewById(R.id.stv);
       // ImageView imageView = (ImageView) rowView.findViewById(R.id.iv);
        try {
            txtTitle.setText(this.username.get(position));
            txtSubTitle.setText(this.uid.get(position));
        }catch (IndexOutOfBoundsException e)
        {e.printStackTrace();}
       // imageView.setImageResource(imageId[0]);//change it

        return rowView;
    }
}
