package com.mitcoe.shubhamsanglikar.avayasmartcityproject;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class ComplaintActivity extends ActionBarActivity implements View.OnClickListener{
    CustomComplaintsList adapter;
    ArrayList<String> comp_title;
    ArrayList<String> comp_area;
    ArrayList<String> comp_upvotes;
    ArrayList<String> comp_text_title;
    EditText username,email;
    //JSONObject jsonObject = new JSONObject();
    Button complaint,show;
    TextView result;
    ListView list;
    String title,upvotes,areaa;
   // private static final String hostIP="192.168.1.5";
    InitClass obj=new InitClass();
    String hostIP = obj.getHostIP();

    String[] uid;
    String[] un;
    ArrayList<String> a,b;

    //String url = "http://10.0.2.2:8080/smartCity/get_complaints.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);


        list = (ListView) findViewById(R.id.complaintsListView);
        un=new String[50];
        uid = new String[50];

        comp_area=new ArrayList<String>();
        comp_upvotes = new ArrayList<String>();
        comp_title = new ArrayList<String>();
        comp_text_title = new ArrayList<String>();
        get_user_complaints();

        adapter = new CustomComplaintsList(ComplaintActivity.this,comp_title,comp_text_title,comp_area,comp_upvotes);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ComplaintActivity.this,ViewComplaintActivity.class);
                Toast.makeText(ComplaintActivity.this,comp_title.get(i),Toast.LENGTH_SHORT);
                intent.putExtra("cid",comp_title.get(i));
                startActivity(intent);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_complaint, menu);
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

    @Override
    public void onClick(View view) {

    }


    void get_user_complaints()
    {
        SQLiteDatabase db;
        db= openOrCreateDatabase("MyDB",MODE_PRIVATE,null);

        Cursor c = db.rawQuery("SELECT * FROM complaints",null);
        Log.d("after cursor:","fine");

        if(c.moveToFirst())
        {
            Log.d("after cursor:","inside if");
            do{
                Log.d("c.getInt(0)", "" + c.getInt(0));
                get_title_upvotes("" + c.getInt(0));
                comp_title.add("" + c.getInt(0));
                comp_text_title.add(""+c.getString(10));
                //comp_area.add(c.getInt(1)==1?"Upvoted":"Complaint");
                comp_area.add("" + c.getString(4));
               // comp_upvotes.add("" + c.getInt(8));
                comp_upvotes.add(upvotes);
                Log.d("after cursor:", "after tv changed");
            }while(c.moveToNext());
        }

    }

void get_title_upvotes(String id)
{
        String t="Cannot load title";
        title=t;
    JSONTask task = new JSONTask();
    try {
        String res = null;
        res = task.execute("http://" + hostIP + "/smartCity/get_complaint_cid.php?cid="+id).get();
        Log.d("Result", "" + res);
        JSONObject jsonObject = new JSONObject(res);
        Log.d("json cid", "" + jsonObject.getJSONArray("complaints").getJSONObject(0).getInt("cid"));
        Log.d("json", "parsed successfully");
        if (res == null) {
            Toast.makeText(getApplicationContext(), "Server not found!", Toast.LENGTH_SHORT).show();
        } else {
            //title = jsonObject.getJSONArray("complaints").getJSONObject(0).getString("subject");
            upvotes = jsonObject.getJSONArray("complaints").getJSONObject(0).getString("upvotes");
            //areaa = jsonObject.getJSONArray("complaints").getJSONObject(0).getString("area");


        }
        // result.setText(res);
    } catch (InterruptedException e) {
        e.printStackTrace();
    } catch (ExecutionException e) {
        e.printStackTrace();
    } catch (JSONException e) {
        e.printStackTrace();
    }
    Log.d("title",""+title);
}


}
