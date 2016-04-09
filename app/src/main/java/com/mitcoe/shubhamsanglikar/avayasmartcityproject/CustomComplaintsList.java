package com.mitcoe.shubhamsanglikar.avayasmartcityproject;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Shubham on 25-Jan-16.
 */
public class CustomComplaintsList extends BaseAdapter {

   // private static final String hostIP="192.168.1.4";
   InitClass obj=new InitClass();
    String hostIP = obj.getHostIP();
    private final Activity context;

    private final ArrayList<String> atitle;
    private final ArrayList<String> atexttitle;
    private final ArrayList<String> aarea;
    private final ArrayList<String> aupvotes;

    TextView txttitle, txtarea, txtupvotes;
    JSONObject jsonObject = new JSONObject();
   // private final Integer[] imageId;
    public CustomComplaintsList(Activity context, ArrayList<String> atitle,ArrayList<String> atexttitle,
                                ArrayList<String> aarea, ArrayList<String> aupvotes) {
        this.context = context;

        this.atitle = atitle;
        this.atexttitle = atexttitle;
        this.aarea = aarea;
        this.aupvotes = aupvotes;
    }

    @Override
    public int getCount() {
        return this.atitle.size();
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
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final View rowView= inflater.inflate(R.layout.list_complaints, null, false);
        TextView txttitle = (TextView) rowView.findViewById(R.id.txttitle);
        TextView txttexttitle = (TextView) rowView.findViewById(R.id.txttexttitle);
        TextView txtarea = (TextView) rowView.findViewById(R.id.txtarea);
        TextView txtupvotes = (TextView) rowView.findViewById(R.id.txtupvotes);

        // ImageView imageView = (ImageView) rowView.findViewById(R.id.iv);
        try {

            txttitle.setText(this.atitle.get(position));
            txttexttitle.setText(this.atexttitle.get(position));
            txtarea.setText(this.aarea.get(position));
            txtupvotes.setText(this.aupvotes.get(position));

        }catch (IndexOutOfBoundsException e)
        {e.printStackTrace();}
       // imageView.setImageResource(imageId[0]);//change it

        return rowView;
    }
}
