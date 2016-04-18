package com.mitcoe.shubhamsanglikar.avayasmartcityproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MapsAct2 extends FragmentActivity implements View.OnClickListener {
    GoogleMap mMap;
   // private static final String hostIP="192.168.1.5";
    InitClass obj=new InitClass();
    GPSTracker g;
    String hostIP = obj.getHostIP();
    String area;
    String type;
    LatLng CampMarker = new LatLng(18.512231,73.886010);
    LatLng KothrudMarker = new LatLng(18.507399,73.807650);
    LatLng KondhwaMarker = new LatLng(18.477091,73.890687);
    LatLng HadapsarMarker = new LatLng(18.508920,73.926026);
    LatLng ZoomMarker;
    LatLng recent = new LatLng(0,0);
    Marker sel_loc;
    LatLng latLng;
   // double[] latitude = {18.5353, 18.5456, 18.6397, 18.9323};   //    Give these vaules from databases
   // double[] longitude = {73.828, 73.7849, 73.7959, 73.6869};   //    & make sure the datatype is same
    double latitude[]=new double[20];
    double longitude[]=new double[20];
    int cid[]= new int[20];
    int upvotes[]= new int[20];
    String t[]= new String[20];
    int coordinateCnt = 0;

    String[] title ={"data","data","data","data"};//
    String sub;
    int upv;
    public final static String latitudekey="latitude";
    public final static String longitudekey="longitude";


    private GoogleApiClient client;





    public void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
         g =new GPSTracker(MapsAct2.this);
        mMap = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();



        area=getIntent().getStringExtra("area");
        type = getIntent().getStringExtra("type");

        //////
        if(area.equals("Your Location"))
        {

            //String lat =getIntent().getStringExtra(Complaint1Activity.latkey);
           // String lon= getIntent().getStringExtra(Complaint1Activity.lonkey);




            //latLng = new LatLng(Double.parseDouble(lat),Double.parseDouble(lon));
            Log.d("lat lon......",""+g.latitude+"   "+g.longitude);
            latLng = new LatLng(g.latitude,g.longitude);
            //recent = latLng;
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            //mark(latitude, longitude, title);
            mMap.addMarker(new MarkerOptions().position(latLng).title("Your location"));
            //checkradius(latitude,longitude);
            area=getIntent().getStringExtra("newa");
        }
        get_latlng(area, type);
        Log.d("Got lat lon","marking");
        mark(latitude, longitude, t);
        marker();

            /////

            search(area);
            Log.d("get_latlang output", "" + area + ",Type" + type);


            Toast.makeText(getApplicationContext(), "." + area + "." + latitude[0], Toast.LENGTH_LONG).show();
            ZoomMarker = new LatLng(0, 0);
            if (latitude[0] == 0) {
                if (area.equals("Kothrud"))
                    ZoomMarker = new LatLng(KothrudMarker.latitude, KothrudMarker.longitude);
                else if (area.equals("Kondhwa"))
                    ZoomMarker = new LatLng(KondhwaMarker.latitude, KondhwaMarker.longitude);
                else if (area.equals("Hadapsar"))
                    ZoomMarker = new LatLng(HadapsarMarker.latitude, HadapsarMarker.longitude);
                else if (area.equals("Camp"))
                    ZoomMarker = new LatLng(CampMarker.latitude, CampMarker.longitude);
                else {
                    ZoomMarker = new LatLng(latLng.latitude, latLng.longitude);
                }
            } else
                ZoomMarker = new LatLng(latitude[0], longitude[0]);

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ZoomMarker, 12));

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

                public boolean onMarkerClick(Marker marker) {
                /*arg0.remove();
                Toast.makeText(getApplicationContext()
                        , "Remove Marker " + String.valueOf(arg0.getId())
                        , Toast.LENGTH_SHORT).show();*/

                    if (marker.isInfoWindowShown()) {
                        marker.hideInfoWindow();
                    } else {
                        marker.showInfoWindow();
                    }
                    if (!marker.getTitle().equals("Selected location") && !marker.getTitle().equals("Your Location")) {

                        upvote_confirmation_dialog(marker.getTitle());

                    }
                    return true;
                }
            });



        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        Button button3 = (Button) findViewById(R.id.Nextactivity);
        button3.setOnClickListener(this);


    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MapsAct Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.mitcoe.shubhamsanglikar.avayasmartcityproject/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MapsAct Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.mitcoe.shubhamsanglikar.avayasmartcityproject/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.Nextactivity:
                Intent intent = new Intent(MapsAct2.this,RegistrationActivity.class);

                // Intent intent1 = new Intent(MapsAct.this,RegistrationActivity.class);

                intent.putExtra("latitude",recent.latitude);
                intent.putExtra("longitude",recent.longitude);
                intent.putExtra("area",area);
                intent.putExtra("type",type);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                //startActivityForResult(intent);

        }
    }

    public void marker() {


        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng arg0) {

                recent = arg0;
                checkradius(latitude,longitude);


            }


        });

    }

    public void mark(double[] latitude, double[] longitude, String[] title) {
        Log.d("Marking cnt","  " +coordinateCnt);
        for (int i = 0; i < coordinateCnt; i++) {
            Log.d("Marking cnt",""+latitude[i]+ "  " +coordinateCnt);
            double x = latitude[i];
            double y = longitude[i];
            String name = ""+cid[i];
            LatLng position = new LatLng(x, y);
            mMap.addMarker(new MarkerOptions().position(position).title(name));

        }


    }
    public void checkradius(double[] latitude, double[] longitude){
        int flag=0;

        Float distance=null;
        Location location = new Location("Point A");
        location.setLatitude(recent.latitude);
        location.setLongitude(recent.longitude);
        Location location1 = new Location("Point B");
        for (int i = 0; i < 4; i++) {
            double x = latitude[i];
            double y = longitude[i];
            LatLng position = new LatLng(x, y);
            location1.setLongitude(position.longitude);
            location1.setLatitude(position.latitude);

            distance = location.distanceTo(location1);
            if (distance <= 200) {
                flag=1;
                Toast.makeText(getApplication(), "very near upvote similar complaints", Toast.LENGTH_SHORT).show();


                break;

            }

        }
        if(distance>1000) {
            // if(distance<2000)
            if(sel_loc!=null)
                sel_loc.remove();
            sel_loc = mMap.addMarker(new MarkerOptions().position(recent).title("Selected location"));

            View b = findViewById(R.id.Nextactivity);
            b.setVisibility(View.VISIBLE);

        }

    }

    public void search(String location){

        List<Address> addressList = null;

        if (location != null || !location.equals("")) {

            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(addressList!=null) {
                Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                //mMap.addMarker(new MarkerOptions().position(latLng)
                //  .title(location));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
            }

        }

    }

    void get_latlng(String area, String type){
        JSONTask task = new JSONTask();
        Log.d("calling","getlatlng");
        try {
            String res = null;
            res = task.execute("http://" + hostIP + "/smartCity/get_latlng.php?area="+area+"&type="+type).get();
            Log.d("Result", "" + res);
            JSONObject jsonObject = new JSONObject(res);
            Log.d("json 1 lat", "" + jsonObject.getJSONArray("complaints").getJSONObject(0).getString("latitude"));
            Log.d("json", "parsed successfully");
            if (res == null) {
                Toast.makeText(getApplicationContext(), "Server not found!", Toast.LENGTH_SHORT).show();
            } else {

                // list.deferNotifyDataSetChanged();
                try {
                    Log.d("Json length",""+jsonObject.getJSONArray("complaints").length());

                    for(int i = 0 ; i<jsonObject.getJSONArray("complaints").length() ; i++) {
                        latitude[i]=(jsonObject.getJSONArray("complaints").getJSONObject(i).getDouble("latitude"));
                        longitude[i]=(jsonObject.getJSONArray("complaints").getJSONObject(i).getDouble("longitude"));
                        cid[i]=(jsonObject.getJSONArray("complaints").getJSONObject(i).getInt("cid"));
                        upvotes[i]=(jsonObject.getJSONArray("complaints").getJSONObject(i).getInt("upvotes"));
                        t[i]=(jsonObject.getJSONArray("complaints").getJSONObject(i).getString("subject"));
                        Log.d("coordinates:",""+latitude[i]+","+longitude[i]+"cid:"+cid[i]);
                        coordinateCnt++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            // result.setText(res);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void upvote_confirmation_dialog(String title) {
        final AlertDialog alertDialog = new AlertDialog.Builder(MapsAct2.this).create();
        final String c = title;


        int i = Integer.parseInt(title);
        int j =0;
        for(j=0;j<cid.length;j++)
        {
            if(cid[j]==i)
            {
                sub = t[j];
                upv = upvotes[j];
                break;
            }
        }

        alertDialog.setTitle(""+sub);
        String id1="ID:"+title;
        String upvotes1="UpVotes:"+upv;
        String category1="Category:"+type;
        String area1 = "Area: "+area;

        alertDialog.setMessage(id1 + "\n" + category1 + "\n" + area1 + "\nUpvote?");

        alertDialog.setButton("Upvote", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                upvote(c);
                update_user_database(c);
                Intent i = new Intent(MapsAct2.this, HomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
        alertDialog.setButton2("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.show();  //<-- See This!


    }

    void upvote(String c)
    {
        JSONTask task = new JSONTask();
        String res = null;

        try {
            res = task.execute("http://" + hostIP + "/smartCity/upvote.php?cid="+c).get();
            Log.d("Result", "" + res);
            if (res == null)
                Toast.makeText(getApplicationContext(), "Failed to connect to server!", Toast.LENGTH_SHORT).show();
            else {
                //  votes.setText("" + a);

                Toast.makeText(getApplicationContext(), "Complaint upvoted successfully!", Toast.LENGTH_LONG).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    void update_user_database(String c)
    {
        SQLiteDatabase db;
        db= openOrCreateDatabase("MyDB",MODE_PRIVATE,null);
        upv+=1;
        db.execSQL("INSERT INTO complaints( cid , upvoted , subject ,area , upvotes ) VALUES ( "+c+" , 1 , '"+sub+"' , '"+area+"' , "+upv+" )" );
        Log.d("User DB","Inserted into phone's DB");
    }

}

