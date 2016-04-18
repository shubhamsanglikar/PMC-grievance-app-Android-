package com.mitcoe.shubhamsanglikar.avayasmartcityproject;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

       // FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        //        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.setDrawerListener(toggle);
        //toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if (isFirstTime()) {

            SQLiteDatabase db = openOrCreateDatabase("contacts.db", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS Data(Type VARCHAR,Name VARCHAR,Contact VARCHAR);");

            //
            //  Insert Database here
            //
            //Hospital
            db.execSQL("INSERT INTO Data VALUES('hospital','Syandri','020-2612800')");
            db.execSQL("INSERT INTO Data VALUES('hospital','YCM','020-3456892')");
            db.execSQL("INSERT INTO Data VALUES('hospital','Poona','020-7841892')");
            db.execSQL("INSERT INTO Data VALUES('hospital','Ruby','020-2616389')");
            db.execSQL("INSERT INTO Data VALUES('hospital','Sancheti','020-1224892')");
            db.execSQL("INSERT INTO Data VALUES('hospital','Dinanath Mangeshkar','020-2754892')");
            db.execSQL("INSERT INTO Data VALUES('hospital','Jahangir','020-7548892')");
            // Police Station
            db.execSQL("INSERT INTO Data VALUES('policestation','Kothrud','020-26126296')");
            db.execSQL("INSERT INTO Data VALUES('policestation','Bhosari','020-24856296')");
            db.execSQL("INSERT INTO Data VALUES('policestation','Chinchwad','020-28547678')");
            db.execSQL("INSERT INTO Data VALUES('policestation','Dattawadi','020-29126606')");
            db.execSQL("INSERT INTO Data VALUES('policestation','Deccan','020-20926777')");
            db.execSQL("INSERT INTO Data VALUES('policestation','Hinjwadi','020-26126748')");
            db.execSQL("INSERT INTO Data VALUES('policestation','Khadaki','020-25126293')");
            db.execSQL("INSERT INTO Data VALUES('policestation','Koregaon','020-27849627')");
            // Fire Brigade
            db.execSQL("INSERT INTO Data VALUES('firebrigade','Pune Cantonment','020-26450553')");
            db.execSQL("INSERT INTO Data VALUES('firebrigade','Fire','101')");
            db.execSQL("INSERT INTO Data VALUES('firebrigade','Katraj','020-22460553')");
            db.execSQL("INSERT INTO Data VALUES('firebrigade','Yerwada','020-2645123')");
            db.execSQL("INSERT INTO Data VALUES('firebrigade','Hadapser','020-28450559')");
            db.execSQL("INSERT INTO Data VALUES('firebrigade','Kondwa','020-21234553')");
            db.execSQL("INSERT INTO Data VALUES('firebrigade','Bhosari','020-2949875')");
            // Private Crane
            db.execSQL("INSERT INTO Data VALUES('privatecrane','Barkre Crane Service','9823079797')");
            db.execSQL("INSERT INTO Data VALUES('privatecrane','Inamdar Crane Serivce','9881426211')");
            //Senior Citizen
            db.execSQL("INSERT INTO Data VALUES('seniorcitizenhelpline','call','020-26111103')");
            db.execSQL("INSERT INTO Data VALUES('seniorcitizenhelpline','call','020-26126296')");
            db.execSQL("INSERT INTO Data VALUES('seniorcitizenhelpline','SMS','7350548751')");
            //Women Children Helpline
            db.execSQL("INSERT INTO Data VALUES('womenchildrenhelpline','call','020-26050191')");




            
        }

    }

        





    

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.Hospital) {

            openAndQueryDatabase("hospital");





        } else if (id == R.id.FireBrigde) {

            openAndQueryDatabase("firebrigade");


        } else if (id == R.id.SeniorCitizenHelpline) {
            openAndQueryDatabase("seniorcitizenhelpline");


        } else if (id == R.id.PunePoliceStation) {
            openAndQueryDatabase("policestation");



        } else if (id == R.id.PrivateCrane) {
            openAndQueryDatabase("privatecrane");


        } else if (id==R.id.WomanandChildren){

            openAndQueryDatabase("womenchildrenhelpline");

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void openAndQueryDatabase(String tableName) {
        ArrayList<String>  name = new ArrayList<String>();
        ArrayList<String> con = new ArrayList<String>();
        try {
            SQLiteDatabase my = openOrCreateDatabase("contacts.db", MODE_PRIVATE, null);
            Cursor c = my.rawQuery("Select * from Data Where Type=='" + tableName + "'", null);
          int x=c.getCount();

            if (c != null ) {
                if  (c.moveToFirst()) {
                    do {
                        String Name = c.getString(c.getColumnIndex("Name"));
                         String contact = c.getString(c.getColumnIndex("Contact"));
                        name.add(  Name );
                        con.add(contact);
                    }while (c.moveToNext());

                }
            }
        } catch (SQLiteException se ) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }

        String[] from = new String[] {"rowid", "col_1"};
        int[] to = new int[] { R.id.name, R.id.contact};
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
        for(int i = 0; i < name.size(); i++){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("rowid", "" + name.get(i));
            map.put("col_1", "" + con.get(i));
            fillMaps.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, fillMaps, R.layout.custom, from, to);
        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);

    }


    private boolean isFirstTime()
    {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {
            // first time
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
        }
        return !ranBefore;
    }


}
