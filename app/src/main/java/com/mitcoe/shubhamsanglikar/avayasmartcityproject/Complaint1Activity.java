package com.mitcoe.shubhamsanglikar.avayasmartcityproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Complaint1Activity extends AppCompatActivity implements View.OnClickListener{
    Spinner spinner,spinner1;
    ArrayAdapter<CharSequence>  adapter, adapter1;
    String area,type;
    public final static String areakey = "area";
    public final static String typekey = "type";

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
                   Intent intent = new Intent(v.getContext(), MapsAct2.class);
                   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   intent.putExtra(areakey, area);
                   intent.putExtra(typekey, type);
                   startActivityForResult(intent, 0);
               }
               else Toast.makeText(getBaseContext(), "Please select valid Input", Toast.LENGTH_SHORT).show();
       }}
}
