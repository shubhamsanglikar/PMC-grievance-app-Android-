package com.mitcoe.shubhamsanglikar.avayasmartcityproject;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;


public class SuggestionsActivity extends ActionBarActivity{

   // private static final String hostIP="192.168.1.5";
   InitClass obj=new InitClass();
    String hostIP = obj.getHostIP();

    TextView title, suggestion;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);
        title = (TextView) findViewById(R.id.txttitle);
        suggestion = (TextView) findViewById(R.id.txtsuggestion);
        submit = (Button) findViewById(R.id.btnSuggest);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONTaskPOST task = new JSONTaskPOST();
                String res = null;
                try {
                    res=task.execute("http://"+hostIP+"/smartCity/new_suggestion.php",title.getText().toString(),suggestion.getText().toString()).get();
                    Log.d("Result", "" + res);
                    if(res==null)
                        Toast.makeText(getApplicationContext(), "Failed to connect to server!", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getApplicationContext(), "Suggestion regestered successfully!", Toast.LENGTH_SHORT).show();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_suggestions, menu);
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
