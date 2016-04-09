package com.mitcoe.shubhamsanglikar.avayasmartcityproject;

import android.app.Activity;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.mitcoe.shubhamsanglikar.avayasmartcityproject.dummy.DummyContent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class PollsFragment extends Fragment implements AbsListView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //private static final String hostIP="192.168.1.5";
    InitClass obj=new InitClass();
    String hostIP = obj.getHostIP();



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<String> questions;
    ArrayList<String> o1;
    ArrayList<String> o2;
    ArrayList<String> o3;
    ArrayList<String> o4;
    ArrayList<String> o1cnt;
    ArrayList<String> o2cnt;
    ArrayList<String> o3cnt;
    ArrayList<String> o4cnt;
    ArrayList<String> pid;
    public CustomPollsList adapter;



    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    public CustomPollsList mAdapter;

    // TODO: Rename and change types of parameters
    public static PollsFragment newInstance(String param1, String param2) {
        PollsFragment fragment = new PollsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PollsFragment() {
    }

    public CustomPollsList get_adapter()
    {
        return adapter;
    }

    void loadPolls()
    {
        JSONTask task = new JSONTask();

        try {
            String res = null;
            res = task.execute("http://" + hostIP + "/smartCity/get_polls.php?area="+InitClass.area).get();
            Log.d("Result", "" + res);
            JSONObject jsonObject = new JSONObject(res);
            Log.d("json", "" + jsonObject.getJSONArray("polls").getJSONObject(0).getString("question"));
            Log.d("json", "parsed successfully");
            if (res == null) {
                Toast.makeText(getContext(), "Server not found!", Toast.LENGTH_SHORT).show();
            } else {

                // list.deferNotifyDataSetChanged();
                try {
                    Log.d("Json length",""+jsonObject.getJSONArray("polls").length());
                    questions = new ArrayList<String >();
                    o1 = new ArrayList<String >();
                    o2= new ArrayList<String >();
                    o3= new ArrayList<String >();
                    o4= new ArrayList<String >();
                    o1cnt= new ArrayList<String >();
                    o2cnt= new ArrayList<String >();
                    o3cnt= new ArrayList<String >();
                    o4cnt= new ArrayList<String >();
                    pid= new ArrayList<String >();


                    for(int i = 0 ; i<jsonObject.getJSONArray("polls").length() ; i++) {
                        questions.add(jsonObject.getJSONArray("polls").getJSONObject(i).getString("question"));
                        o1.add(jsonObject.getJSONArray("polls").getJSONObject(i).getString("o1"));
                        o2.add(jsonObject.getJSONArray("polls").getJSONObject(i).getString("o2"));
                        o3.add(jsonObject.getJSONArray("polls").getJSONObject(i).getString("o3"));
                        o4.add(jsonObject.getJSONArray("polls").getJSONObject(i).getString("o4"));
                        o1cnt.add(jsonObject.getJSONArray("polls").getJSONObject(i).getString("o1cnt"));
                        o2cnt.add(jsonObject.getJSONArray("polls").getJSONObject(i).getString("o2cnt"));
                        o3cnt.add(jsonObject.getJSONArray("polls").getJSONObject(i).getString("o3cnt"));
                        o4cnt.add(jsonObject.getJSONArray("polls").getJSONObject(i).getString("o4cnt"));
                        pid.add(jsonObject.getJSONArray("polls").getJSONObject(i).getString("pid"));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter = new CustomPollsList(getActivity(),questions,o1,o2,o3,o4,o1cnt,o2cnt,o3cnt,o4cnt,pid);


            }
            // result.setText(res);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // TODO: Change Adapter to display your content
       // mAdapter = new ArrayAdapter<DummyContent.DummyItem>(getActivity(),android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS);

        loadPolls();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_polls, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        mListView.setAdapter(adapter);

        //adapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

}
