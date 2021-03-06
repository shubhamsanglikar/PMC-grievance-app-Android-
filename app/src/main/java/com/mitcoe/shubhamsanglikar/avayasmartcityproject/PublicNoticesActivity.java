package com.mitcoe.shubhamsanglikar.avayasmartcityproject;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;


public class PublicNoticesActivity extends ActionBarActivity {

    String[] notices = {
            "lkfjslfkjs fsd\n\fwsf\nasd",
            "second item \n second item second line\n third lline \n 4th line jfsklfjss fkjsds ndsjlkf nstill 4th line",
            "fjsdfj jfldfj jfsjdfkldsjf dsf..!",
            "4th item"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_notices);
        ListView list = (ListView) findViewById(R.id.list_notices);
        list.setAdapter(new ArrayAdapter<String>(PublicNoticesActivity.this,android.R.layout.simple_list_item_1,notices));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_public_notices, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
