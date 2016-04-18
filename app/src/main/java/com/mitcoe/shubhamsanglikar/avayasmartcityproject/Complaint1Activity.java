package com.mitcoe.shubhamsanglikar.avayasmartcityproject;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Complaint1Activity extends AppCompatActivity implements View.OnClickListener{
    Spinner spinner,spinner1;
    ArrayAdapter<CharSequence>  adapter, adapter1;
    String area,type;
    public final static String areakey = "area";
    public final static String typekey = "type";
    public final static String latkey = "lat";
    public final static String lonkey = "lon";
    GPSTracker gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);





        spinner = (Spinner) findViewById(R.id.Spinner);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        adapter = ArrayAdapter.createFromResource(this,R.array.area_arrays,android.R.layout.simple_spinner_item);
        adapter1 = ArrayAdapter.createFromResource(this,R.array.type_arrays,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner1.setAdapter(adapter1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                area = (String) parent.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                type = (String) parent.getSelectedItem();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {



            }
        });
        Button button = (Button) findViewById(R.id.bmap);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

       switch (v.getId()) {

           case  R.id.bmap:
               if (area!="Select Area" && !type.equals("Select Type") )

               {


                   if (area.equals("Your Location")) {


                       gps = new GPSTracker(Complaint1Activity.this);

                       Double lati=gps.latitude,longi=gps.longitude;
                       if (gps.canGetLocation()) {
                           Log.d("lat lon inside canget", "" + gps.latitude + "   " + gps.longitude);
                           Geocoder geocoder;
                           List<Address> addresses;
                           geocoder = new Geocoder(this, Locale.getDefault());
                           String address = null;

                           try {
                               addresses = geocoder.getFromLocation(gps.latitude, gps.longitude, 1);
                               address = addresses.get(0).getPostalCode();
                           } catch (IOException e) {
                               e.printStackTrace();
                           }

                           long p = Long.parseLong(address);
                           Intent intent = new Intent(v.getContext(), MapsAct2.class);

                           if(p==411028){
                               intent.putExtra("newa", "Hadapsar");
                           } else if(p==411021)
                           {
                               intent.putExtra("newa", "Kondhwa");
                           }
                           else if(p==411038){

                               intent.putExtra("newa", "Kothrud");
                           }
                           else if(p==411001)
                               intent.putExtra("newa", "Camp");
                           else
                               intent.putExtra("newa", "Hadapsar");




                           intent.putExtra(latkey, ""+lati);
                           Log.d("lat lon inside ...", "" + lati + "   " + longi);
                           intent.putExtra(lonkey, ""+longi);
                           //intent.putExtra("newa", address);
                           intent.putExtra(areakey, area);
                           intent.putExtra(typekey, type);
                           intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                           startActivityForResult(intent, 0);
                       } else {
                           gps.showSettingsAlert();
                       }

                   }
                   else {


                       Intent intent = new Intent(v.getContext(), MapsAct2.class);
                       intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                       intent.putExtra(areakey, area);
                       intent.putExtra(typekey, type);

                       startActivityForResult(intent, 0);
                   }
               }
               else Toast.makeText(getBaseContext(), "Please select valid Input", Toast.LENGTH_SHORT).show();
       }}
}
