package com.mitcoe.shubhamsanglikar.avayasmartcityproject;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_spinner_dropdown_item;


public class LoginActivity extends ActionBarActivity {
    Button login,signup,login1,signup1;
    Dialog popup;
    TextView test;
    Spinner area;
    List<String> categories;
    SQLiteDatabase db;
    public LoginActivity() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        categories = new ArrayList<String>();
        categories.add("Select Area");
        categories.add("Kothrud");
        categories.add("Hadapsar");
        categories.add("Kondhwa");
        categories.add("Camp");


        db = openOrCreateDatabase("MyDB",MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS user_info(uid INT , username VARCHAR(20), email VARCHAR(20) , area VARCHAR(20))");
        db.execSQL("CREATE TABLE IF NOT EXISTS login_status(logged_in INT NOT NULL DEFAULT '0')");
        db.execSQL("CREATE TABLE IF NOT EXISTS complaints( cid INT , upvoted INT )");


        Log.d("Database", "Tables created!");
        login = (Button) findViewById(R.id.btnLogin);
        signup = (Button) findViewById(R.id.btnSignup);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popup = new Dialog(LoginActivity.this);
                popup.setContentView(R.layout.layout_login);
                login1 = (Button)popup.findViewById(R.id.btnLogin1);
                test = (TextView)popup.findViewById(R.id.test);
                String testString="";
                Cursor c = db.rawQuery("SELECT * FROM user_info",null);
                Log.d("after cursor:","fine");
                int flag=0;
                if(c.moveToFirst())
                {
                    Log.d("after cursor:","inside if");
                    do{
                        flag++;
                        Log.d("after cursor:","inside do while");
                        testString = testString+"..."+c.getInt(0);
                        Log.d("c.getString(1)", c.getString(1));
                    }while(c.moveToNext() && flag < 5);
                }
                test.setText(testString);


                login1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Check data

                        popup.dismiss();
                        db.execSQL("INSERT INTO user_info VALUES ( 5 , 'ShubhamSanglikar' , 'shubhamsanglikar@gmail.com' , 'Hadapsar' )");
                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        //Open home page
                    }
                });
                popup.setTitle("Login");
                popup.show();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup = new Dialog(LoginActivity.this);
                popup.setContentView(R.layout.layout_signup);
                area = (Spinner) popup.findViewById(R.id.spinnerArea);
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.layout_spinner,categories);
                dataAdapter.setDropDownViewResource(R.layout.layout_spinner);
                area.setAdapter(dataAdapter);
                signup1 = (Button)popup.findViewById(R.id.btnSignup1);
                signup1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Get data

                        popup.dismiss();
                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        startActivity(intent);

                        //Open home page
                    }
                });
                popup.setTitle("Register");
                popup.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
