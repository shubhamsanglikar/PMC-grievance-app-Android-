package com.mitcoe.shubhamsanglikar.avayasmartcityproject;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RegistrationActivity extends AppCompatActivity  implements View.OnClickListener{

    ImageView viewImage;
    Button b;
    Bitmap thumbnail;
    private EditText title_edittext, description_exittext;
    private String area,type,title,description;
    private double lat,lon;
    SQLiteDatabase db;
    String uploadImage;


    InitClass obj=new InitClass();
    String hostIP = obj.getHostIP();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration1);

        b=(Button)findViewById(R.id.Upload);
        viewImage=(ImageView)findViewById(R.id.viewImage);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        Button button = (Button) findViewById(R.id.registerButton);
        button.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

       // getMenuInflater().inflate(R.menu.menu_registercomplaint, menu);
        return true;
    }

    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {


                    Intent intent = new   Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);


                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();

                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);

                    // bitmap = Bitmap.createScaledBitmap(bitmap, 70, 70, true);
                    viewImage.setImageBitmap(bitmap);

                    String path = Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 2) {


                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();

                thumbnail = (BitmapFactory.decodeFile(picturePath));
                Log.w("path of image from gall", picturePath + "");

                viewImage.setImageBitmap(thumbnail);

            }
        }
    }

    public void getdata(){

        title_edittext= (EditText) findViewById(R.id.ctitle);                     //
        description_exittext= (EditText) findViewById(R.id.cdescription);
               //
        title = title_edittext.getText().toString();                              //
        description=description_exittext.getText().toString();                   //  store these values in database
        area = getIntent().getStringExtra("area");           //
        type = getIntent().getStringExtra("type");
        lat=getIntent().getDoubleExtra("latitude", 0);
        lon=getIntent().getDoubleExtra("longitude",0);
        uploadImage = getStringImage(thumbnail);
        //lat=Double.parseDouble(getIntent().getStringExtra(MapsAct2.latitudekey));  //
        //lon=Double.parseDouble(getIntent().getStringExtra(MapsAct2.longitudekey));  //
        register_complaint();
        int cid = get_complaint();
        Log.d("complaint cid new",""+cid);
        update_area_table(cid);

        db = openOrCreateDatabase("MyDB",MODE_PRIVATE,null);
        String query="INSERT INTO complaints( cid , upvoted , subject ,area , upvotes ) VALUES ( "+cid+" , 0 , '"+title+"' , '"+area+"' , "+0+" )";
        db.execSQL(query);
        Log.d("User DB", "Inserted into phone's DB");


    }

    public String getStringImage(Bitmap bmp){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

            return encodedImage;
        }catch(NullPointerException e){}
        return "No Image";
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.registerButton:
                getdata();

                Intent intent=new Intent(RegistrationActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

        }

    }





    void register_complaint(){
        JSONTaskPOSTComplaints task = new JSONTaskPOSTComplaints();
        Log.d("title",title);
        Log.d("description",description);
        Log.d("area",area);
        Log.d("type",type);
        Log.d("lat",""+lat);
        Log.d("lon",""+lon);
        String res = null;
        try {
            res=task.execute("http://"+hostIP+"/smartCity/register_complaint.php",title,description,area,type,""+lat,""+lon,uploadImage).get();
            Log.d("Result", "" + res);
            if(res==null)
                Toast.makeText(getApplicationContext(), "Failed to connect to server!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(), "Complaint regestered successfully!", Toast.LENGTH_SHORT).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    int get_complaint(){
        int cid=345;

        JSONTask task = new JSONTask();
        try {
            String res = null;
            res = task.execute("http://" + hostIP + "/smartCity/get_complaints.php?latitude="+lat+"&longitude="+lon).get();
            Log.d("Result", "" + res);
            JSONObject jsonObject = new JSONObject(res);
            Log.d("json cid", "" + jsonObject.getJSONArray("complaints").getJSONObject(0).getInt("cid"));
            Log.d("json", "parsed successfully");
            if (res == null) {
                Toast.makeText(getApplicationContext(), "Server not found!", Toast.LENGTH_SHORT).show();
            } else {
                cid = jsonObject.getJSONArray("complaints").getJSONObject(0).getInt("cid");
            }
            // result.setText(res);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cid;
    }


    void update_area_table(int cid){
        JSONTaskPOSTUserComplaints task = new JSONTaskPOSTUserComplaints();
        String res = null;
        try {
            res=task.execute("http://"+hostIP+"/smartCity/update_area_table_post.php",area,type,""+cid,""+lat,""+lon,""+title).get();
            Log.d("Result",""+res);
            if(res==null)
                Toast.makeText(getApplicationContext(), "Failed to connect to server!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(), "Complaint regestered successfully!", Toast.LENGTH_SHORT).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
















    /**
     * Created by Shubham on 14-Mar-16.
     */
    public class JSONTaskPOSTComplaints extends AsyncTask<String,String,String> {
        HttpURLConnection connection,conn;
        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = ProgressDialog.show(RegistrationActivity.this, "Registering...", null, true, true);

        }

        @Override
        protected String doInBackground(String... strings) {
            StringBuffer buffer;
            BufferedReader reader=null;
            buffer = new StringBuffer();
            buffer.append("");

            try
            {
                URL url = new URL(strings[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("title",strings[1]));
                params.add(new BasicNameValuePair("description", strings[2]));
                params.add(new BasicNameValuePair("area",strings[3]));
                params.add(new BasicNameValuePair("type",strings[4]));
                params.add(new BasicNameValuePair("lat",strings[5]));
                params.add(new BasicNameValuePair("lon",strings[6]));
                params.add(new BasicNameValuePair("img",strings[7]));

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getQuery(params));
                writer.flush();
                writer.close();
                os.close();

                conn.connect();
                InputStream stream = conn.getInputStream();


            /*URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();
            */



                reader = new BufferedReader(new InputStreamReader(stream));
                String line = "";
                while((line=reader.readLine())!=null){
                    buffer.append(line);
                }
                Log.d("Buffer stream", ""+buffer);
                return buffer.toString();

            }catch(MalformedURLException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();

            } finally{
                if(connection!=null)
                    connection.disconnect();
                try {
                    if(reader!=null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }
        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            loading.dismiss();

        }



        private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
        {
            StringBuilder result = new StringBuilder();
            boolean first = true;

            for (NameValuePair pair : params)
            {
                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
            }
            Log.d("encoded result",""+result);
            return result.toString();
        }



    }



    /**
     * Created by Shubham on 14-Mar-16.
     */
    public class JSONTaskPOSTUserComplaints extends AsyncTask<String,String,String> {
        HttpURLConnection connection,conn;


        @Override
        protected String doInBackground(String... strings) {
            StringBuffer buffer;
            BufferedReader reader=null;
            buffer = new StringBuffer();
            buffer.append("");
            try
            {
                URL url = new URL(strings[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("title",strings[6]));
                params.add(new BasicNameValuePair("area", strings[1]));
                params.add(new BasicNameValuePair("type", strings[2]));
                params.add(new BasicNameValuePair("lat", strings[4]));
                params.add(new BasicNameValuePair("lon", strings[5]));
                params.add(new BasicNameValuePair("cid",strings[3]));
                Log.d("Areaaaa == ",".."+strings[1]+"..");

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getQuery(params));
                writer.flush();
                writer.close();
                os.close();

                conn.connect();
                InputStream stream = conn.getInputStream();


            /*URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();
            */
                Log.d("Buffer stream", ""+stream.toString());


                reader = new BufferedReader(new InputStreamReader(stream));
                String line = "";
                while((line=reader.readLine())!=null){
                    buffer.append(line);
                }
                return buffer.toString();

            }catch(MalformedURLException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();

            } finally{
                if(connection!=null)
                    connection.disconnect();
                try {
                    if(reader!=null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }
        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);

        }



        private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
        {
            StringBuilder result = new StringBuilder();
            boolean first = true;

            for (NameValuePair pair : params)
            {
                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
            }

            return result.toString();
        }



    }










}
