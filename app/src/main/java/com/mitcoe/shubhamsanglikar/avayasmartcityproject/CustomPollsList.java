package com.mitcoe.shubhamsanglikar.avayasmartcityproject;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Shubham on 25-Jan-16.
 */
public class CustomPollsList extends BaseAdapter {
   // private static final String hostIP="192.168.1.4";
   InitClass obj=new InitClass();
    String hostIP = obj.getHostIP();
    private final Activity context;
    private final ArrayList<String> question;
    private final ArrayList<String> opt1;
    private final ArrayList<String> opt2;
    private final ArrayList<String> opt3;
    private final ArrayList<String> opt4;
    private final ArrayList<String> opt1cnt;
    private final ArrayList<String> opt2cnt;
    private final ArrayList<String> opt3cnt;
    private final ArrayList<String> opt4cnt;
    private final ArrayList<String> pid;
    ListView l;
    TextView votes1, votes2, votes3, votes4, votes;
    View rv;
    JSONObject jsonObject = new JSONObject();
   // private final Integer[] imageId;
    public CustomPollsList(Activity context, ArrayList<String> question,
                           ArrayList<String> opt1, ArrayList<String> opt2, ArrayList<String> opt3, ArrayList<String> opt4,
                           ArrayList<String> opt1cnt, ArrayList<String> opt2cnt, ArrayList<String> opt3cnt, ArrayList<String> opt4cnt,
                           ArrayList<String> pid) {
        this.context = context;
        this.question = question;
        this.opt1 = opt1;
        this.opt2 = opt2;
        this.opt3 = opt3;
        this.opt4 = opt4;
        this.opt1cnt = opt1cnt;
        this.opt2cnt = opt2cnt;
        this.opt3cnt = opt3cnt;
        this.opt4cnt = opt4cnt;
        this.pid = pid;

    }

    @Override
    public int getCount() {
        return this.question.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final LayoutInflater inflater = context.getLayoutInflater();
        final View rowView= inflater.inflate(R.layout.list_polls, null, false);
        TextView txtquestion = (TextView) rowView.findViewById(R.id.txtQuestion);
        final RadioGroup rg = (RadioGroup) rowView.findViewById(R.id.options);

        final RadioButton r1 = (RadioButton) rowView.findViewById(R.id.radioButton1);
        final RadioButton r2 = (RadioButton) rowView.findViewById(R.id.radioButton2);
        final RadioButton r3 = (RadioButton) rowView.findViewById(R.id.radioButton3);
        final RadioButton r4 = (RadioButton) rowView.findViewById(R.id.radioButton4);
        Button submit = (Button) rowView.findViewById(R.id.btnpollsubmit);
        TextView votes1 = (TextView) rowView.findViewById(R.id.o1cnt);
        TextView votes2 = (TextView) rowView.findViewById(R.id.o2cnt);
        TextView votes3 = (TextView) rowView.findViewById(R.id.o3cnt);
        TextView votes4 = (TextView) rowView.findViewById(R.id.o4cnt);



        // ImageView imageView = (ImageView) rowView.findViewById(R.id.iv);
        try {
            txtquestion.setText(this.question.get(position));
            r1.setText(this.opt1.get(position));
            r2.setText(this.opt2.get(position));
            r3.setText(this.opt3.get(position));
            r4.setText(this.opt4.get(position));
            votes1.setText(this.opt1cnt.get(position));
            votes2.setText(this.opt2cnt.get(position));
            votes3.setText(this.opt3cnt.get(position));
            votes4.setText(this.opt4cnt.get(position));
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(rg.getCheckedRadioButtonId()==-1)
                    {
                        Toast.makeText(context, "Please select an option", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        // get selected radio button from radioGroup
                        int selectedId = rg.getCheckedRadioButtonId();
                        String optionName="";
                        final RadioButton r = (RadioButton)rowView.findViewById(selectedId);
                        Toast.makeText(context, r.getText().toString()+" is selected\nPoll Submitted successfully!\nThank you for your opinion.", Toast.LENGTH_SHORT).show();

                        JSONTask task = new JSONTask();
                        String res = null;
                        try {
                            if(selectedId==r1.getId())
                            {
                                optionName="o1cnt";
                                res = task.execute("http://" + hostIP + "/smartCity/register_poll.php?pid=" + pid.get(position).toString() + "&option_name=o1cnt&option_value=" + opt1cnt.get(position).toString()).get();
                                question.remove(position);
                                notifyDataSetChanged();

                            }
                            else if(selectedId==r2.getId())
                            {
                                optionName="o2cnt";
                                res = task.execute("http://" + hostIP + "/smartCity/register_poll.php?pid=" + pid.get(position).toString() + "&option_name=o2cnt&option_value=" + opt2cnt.get(position).toString()).get();
                                question.remove(position);
                                notifyDataSetChanged();

                            }
                            else if(selectedId==r3.getId())
                            {
                                optionName="o3cnt";
                                res = task.execute("http://" + hostIP + "/smartCity/register_poll.php?pid=" + pid.get(position).toString() + "&option_name=o3cnt&option_value=" + opt3cnt.get(position).toString()).get();
                                question.remove(position);
                                notifyDataSetChanged();

                            }
                            else if(selectedId==r4.getId())
                            {
                                optionName="o4cnt";
                                res = task.execute("http://" + hostIP + "/smartCity/register_poll.php?pid=" + pid.get(position).toString() + "&option_name=o4cnt&option_value=" + opt4cnt.get(position).toString()).get();
                                question.remove(position);
                                notifyDataSetChanged();

                            }
                            //res = task.execute("http://" + hostIP + "/smartCity/register_poll.php?pid=" + pid.get(position).toString() + "&option_name=o1cnt&option_value=" + opt1cnt.get(position).toString()).get();
                            Log.d("Result", "" + res);
                            if (res == null)
                                Toast.makeText(context, "Failed to connect to server!", Toast.LENGTH_SHORT).show();
                            else {
                                int a = Integer.parseInt(opt1cnt.get(position)) + 1;
                                //  votes.setText("" + a);

                                Toast.makeText(context, ""+a, Toast.LENGTH_SHORT).show();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }



                    }
                }
            });
/*
            r1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JSONTask task = new JSONTask();
                    String res = null;
                    votes = (TextView) view.getRootView().findViewById(R.id.o1cnt);
                    try {
                        res = task.execute("http://" + hostIP + "/smartCity/register_poll.php?pid=" + pid.get(position).toString() + "&option_name=o1cnt&option_value=" + opt1cnt.get(position).toString()).get();
                        Log.d("Result", "" + res);
                        if (res == null)
                            Toast.makeText(context, "Failed to connect to server!", Toast.LENGTH_SHORT).show();
                        else {
                            int a = Integer.parseInt(opt1cnt.get(position)) + 1;
                          //  votes.setText("" + a);

                            Toast.makeText(context, ""+a, Toast.LENGTH_SHORT).show();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            });
            r2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JSONTask task = new JSONTask();
                    String res = null;
                    votes = (TextView) view.getRootView().findViewById(R.id.o2cnt);
                    try {
                        res = task.execute("http://" + hostIP + "/smartCity/register_poll.php?pid=" + pid.get(position).toString() + "&option_name=o2cnt&option_value=" + opt2cnt.get(position).toString()).get();
                        Log.d("Result", "" + res);
                        if (res == null)
                            Toast.makeText(context, "Failed to connect to server!", Toast.LENGTH_SHORT).show();
                        else {

                            int a = Integer.parseInt(opt2cnt.get(position)) + 1;
                            votes.setText("" + a);
                            Toast.makeText(context, "" + a, Toast.LENGTH_LONG).show();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            });
            r3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JSONTask task = new JSONTask();
                    String res = null;
                    votes = (TextView) view.getRootView().findViewById(R.id.o3cnt);
                    try {
                        res = task.execute("http://" + hostIP + "/smartCity/register_poll.php?pid=" + pid.get(position).toString() + "&option_name=o3cnt&option_value=" + opt3cnt.get(position).toString()).get();
                        Log.d("Result", "" + res);
                        if (res == null)
                            Toast.makeText(context, "Failed to connect to server!", Toast.LENGTH_SHORT).show();
                        else {
                            int a = Integer.parseInt(opt3cnt.get(position)) + 1;
                            votes.setText("" + a);
                            Toast.makeText(context, "Polls registered successfully!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            });
            r4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JSONTask task = new JSONTask();
                    String res = null;
                    votes = (TextView) view.getRootView().findViewById(R.id.o4cnt);
                    try {
                        res = task.execute("http://" + hostIP + "/smartCity/register_poll.php?pid=" + pid.get(position).toString() + "&option_name=o4cnt&option_value=" + opt4cnt.get(position).toString()).get();
                        Log.d("Result", "" + res);
                        if (res == null)
                            Toast.makeText(context, "Failed to connect to server!", Toast.LENGTH_SHORT).show();
                        else {
                            int a = Integer.parseInt(opt4cnt.get(position)) + 1;
                            votes.setText("" + a);
                            Toast.makeText(context, "Poll registered successfully!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
        });
*/
        }catch (IndexOutOfBoundsException e)
        {e.printStackTrace();}
       // imageView.setImageResource(imageId[0]);//change it
if(rv==null) {
    return rowView;
}
        else
{
    return rv;
}
    }
}
