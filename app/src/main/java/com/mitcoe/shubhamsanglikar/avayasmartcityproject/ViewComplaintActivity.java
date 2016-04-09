package com.mitcoe.shubhamsanglikar.avayasmartcityproject;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class ViewComplaintActivity extends ActionBarActivity {

    TextView ttitle, tarea, tupvotes, tcategory, tcid,tstatus,tdesc,tgov_response;
    String hostIP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_complaint);
        InitClass obj=new InitClass();
        hostIP=obj.getHostIP();
        initFields();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_complaint, menu);
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

    void initFields(){
        ttitle= (TextView) findViewById(R.id.txttitle);
        tarea =  (TextView) findViewById(R.id.txtarea);
        tupvotes=(TextView) findViewById(R.id.txtupvotes);
        tcategory=(TextView) findViewById(R.id.txttype);
        tcid=(TextView) findViewById(R.id.txtcid);
        tstatus=(TextView) findViewById(R.id.txtstatus);
        tdesc=(TextView) findViewById(R.id.txtdescription);
        tgov_response=(TextView) findViewById(R.id.txtgov_response);
        String cid = getIntent().getStringExtra("cid");
        String title = "",area="",upvotes="",category="",status="",desc="",gov_response="";

        tdesc.setMovementMethod(new ScrollingMovementMethod());


        JSONTask task = new JSONTask();
        try {
            String res = null;
            res = task.execute("http://" + hostIP + "/smartCity/get_complaint_cid.php?cid="+cid).get();
            Log.d("Result", "" + res);
            JSONObject jsonObject = new JSONObject(res);
            Log.d("json cid", "" + jsonObject.getJSONArray("complaints").getJSONObject(0).getInt("cid"));
            Log.d("json", "parsed successfully");
            if (res == null) {
                Toast.makeText(getApplicationContext(), "Server not found!", Toast.LENGTH_SHORT).show();
            } else {
                title = jsonObject.getJSONArray("complaints").getJSONObject(0).getString("subject");
                area = jsonObject.getJSONArray("complaints").getJSONObject(0).getString("area");
                upvotes = ""+jsonObject.getJSONArray("complaints").getJSONObject(0).getInt("upvotes");
                category= jsonObject.getJSONArray("complaints").getJSONObject(0).getString("category");
                status = ""+jsonObject.getJSONArray("complaints").getJSONObject(0).getInt("status");
                desc = jsonObject.getJSONArray("complaints").getJSONObject(0).getString("description");
                gov_response = jsonObject.getJSONArray("complaints").getJSONObject(0).getString("gov_response");
            }
            // result.setText(res);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
String tpdesc="A material metaphor is the unifying theory of a rationalized space and a system of motion.\"\n" +
        "        \"The material is grounded in tactile reality, inspired by the study of paper and ink, yet \"\n" +
        "        \"technologically advanced and open to imagination and magic.\\n\"\n" +
        "        \"Surfaces and edges of the material provide visual cues that are grounded in reality. The \"\n" +
        "        \"use of familiar tactile attributes helps users quickly understand affordances. Yet the \"\n" +
        "        \"flexibility of the material creates new affordances that supercede those in the physical \"\n" +
        "        \"world, without breaking the rules of physics.\\n\"\n" +
        "        \"The fundamentals of light, surface, and movement are key to conveying how objects move, \"\n" +
        "        \"interact, and exist in space and in relation to each other. Realistic lighting shows \"\n" +
        "        \"seams, divides space, and indicates moving parts.\\n\\n\"\n" +
        "\n" +
        "        \"Bold, graphic, intentional.\\n\\n\"\n" +
        "\n" +
        "        \"The foundational elements of print based design typography, grids, space, scale, color, \"\n" +
        "        \"and use of imagery guide visual treatments. These elements do far more than please the \"\n" +
        "        \"eye. They create hierarchy, meaning, and focus. Deliberate color choices, edge to edge \"\n" +
        "        \"imagery, large scale typography, and intentional white space create a bold and graphic \"\n" +
        "        \"interface that immerse the user in the experience.\\n\"\n" +
        "        \"An emphasis on user actions makes core functionality immediately apparent and provides \"\n" +
        "        \"waypoints for the user.\\n\\n\"\n" +
        "\n" +
        "        \"Motion provides meaning.";


        ttitle.setText(title==""?"No title":""+title);
        tarea.setText("Area: "+area);
        tupvotes.setText("Upvotes: "+upvotes);
        tcategory.setText("Category: "+category);
        tcid.setText("ID: "+cid);
        tstatus.setText("Status: "+status);
        tdesc.setText(desc);
        tgov_response.setText("Response from PMC: \n"+gov_response);
    }
}
