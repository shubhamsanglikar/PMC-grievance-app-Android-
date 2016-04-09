package com.mitcoe.shubhamsanglikar.avayasmartcityproject;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NoticesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NoticesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoticesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    ListView list;
    private String mParam1;
    private String mParam2;
   // private static final String hostIP="192.168.1.5";
   InitClass obj=new InitClass();
    String hostIP = obj.getHostIP();

    String[] notices = {
            "lkfjslfkjs fsd\n\fwsf\nasd",
            "second item \n second item second line\n third lline \n 4th line jfsklfjss fkjsds ndsjlkf nstill 4th line",
            "fjsdfj jfldfj jfsjdfkldsjf dsf..!",
            "4th item"
    };
    ArrayList<String> a;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoticesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoticesFragment newInstance(String param1, String param2) {
        NoticesFragment fragment = new NoticesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    public NoticesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }


    void get_notices()
    {
        JSONTask task = new JSONTask();
        try {
            String res = null;
            res = task.execute("http://" + hostIP + "/smartCity/get_notices.php").get();
            Log.d("Result", "" + res);
            JSONObject jsonObject = new JSONObject(res);
            Log.d("json", "" + jsonObject.getJSONArray("complaints").getJSONObject(0).getString("notice"));
            Log.d("json", "parsed successfully");
            if (res == null) {
                Toast.makeText(getContext(), "Server not found!", Toast.LENGTH_SHORT).show();
            } else {

                // list.deferNotifyDataSetChanged();
                try {
                    Log.d("Json length",""+jsonObject.getJSONArray("complaints").length());
                    a = new ArrayList<String >();
                    for(int i = 0 ; i<jsonObject.getJSONArray("complaints").length() ; i++) {
                        a.add(jsonObject.getJSONArray("complaints").getJSONObject(i).getString("notice"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                list.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, a));
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getContext(), "You Clicked at " +position, Toast.LENGTH_SHORT).show();
                        //ImageView iv = (ImageView) view.findViewById(R.id.iv);
                        //iv.setImageResource(imageId[1]);
                    }
                });

            }
            // result.setText(res);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notices, container, false);

        list = (ListView) view.findViewById(R.id.list_notices);
        get_notices();

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        public void onFragmentInteraction(Uri uri);
    }

}
