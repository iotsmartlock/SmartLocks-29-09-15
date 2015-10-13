package com.smartlockinc.smartlocks;

/**
 * Created by SunnySingh on 7/5/2015.
 */

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;


public class SharedKeyFragment extends Fragment {

    Memberhandler dbhelper;
    Neighbourhandler dbneighbour;
    SimpleCursorAdapter dataAdapter,dataAdapter2;

    public SharedKeyFragment() {
        // Required empty public constructor
    }

    public ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle("Shared Keys");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_shared_key, container, false);


        // Inflate the layout for this fragment
        final Animation manimation = AnimationUtils.loadAnimation(getContext(),R.anim.fab);
        final Animation pullup = AnimationUtils.loadAnimation(getContext(), R.anim.pullup);
        final FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        dbhelper = new Memberhandler(getContext());
        dbneighbour = new Neighbourhandler(getContext());
        Cursor cursor = dbhelper.getmembername();
        String[] columns = new String[] {dbhelper.colName,dbhelper.colKey};
        int[] to = new int[]{R.id.Name,R.id.Key};
        dataAdapter =new SimpleCursorAdapter(getContext(),R.layout.listview,cursor,columns,to,0);
        ListView listview = (ListView) rootView.findViewById(R.id.listView);
        listview.setAdapter(dataAdapter);

        /*Cursor cursorneighbour = dbneighbour.getneighbourname();
        String[] column = new String[] {dbneighbour.colName,dbneighbour.colPhone};
        int[] too = new int[]{R.id.Neighbour,R.id.PhoneNO};
        dataAdapter2 =new SimpleCursorAdapter(getContext(),R.layout.listview,cursorneighbour,column,too,0);
        listview.setAdapter(dataAdapter2);*/

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.startAnimation(manimation);
                RelativeLayout mlayout = (RelativeLayout)getActivity().findViewById(R.id.relativelayout);
                mlayout.setAlpha(0.8F);
                RelativeLayout layout = (RelativeLayout)getActivity().findViewById(R.id.rv);
                layout.setVisibility(v.VISIBLE);
                layout.startAnimation(pullup);
                layout.setAlpha(0.8F);


            }
        });
        final FloatingActionButton fabaddneighbour = (FloatingActionButton) rootView.findViewById(R.id.fabneighbour);

        fabaddneighbour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardView cv = (CardView) getActivity().findViewById(R.id.cvaddneighbour);
                cv.startAnimation(manimation);
                cv.setVisibility(v.VISIBLE);
                RelativeLayout layout = (RelativeLayout) getActivity().findViewById(R.id.rv);
                layout.setVisibility(v.INVISIBLE);
                fab.setVisibility(v.INVISIBLE);

            }
        });
        final FloatingActionButton fabmember = (FloatingActionButton) rootView.findViewById(R.id.fabmember);
        fabmember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardView cv = (CardView) getActivity().findViewById(R.id.cvaddmember);
                cv.startAnimation(manimation);
                cv.setVisibility(v.VISIBLE);
                RelativeLayout layout = (RelativeLayout) getActivity().findViewById(R.id.rv);
                layout.setVisibility(v.INVISIBLE);
                fab.setVisibility(v.INVISIBLE);

            }
        });

        final EditText membername = (EditText)rootView.findViewById(R.id.MemberName);
        final EditText raspkey =(EditText)rootView.findViewById(R.id.Raspkey);
        com.rey.material.widget.Button register = (com.rey.material.widget.Button)rootView.findViewById(R.id.btnaddmember);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String MemberName = membername.getText().toString();
                String Raspkey = raspkey.getText().toString();
                if (MemberName.equals("") && Raspkey.equals("")) {
                    final Snackbar snackbar = Snackbar.make(rootView, "One or More Fields are Empty", Snackbar.LENGTH_SHORT);
                    snackbar.setAction("Dismiss", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackbar.dismiss();
                        }
                    });
                    snackbar.show();

            }
                else {
                    dbhelper=new Memberhandler(getContext());
                    dbhelper.insertmember(MemberName, Raspkey);
                    CardView cv = (CardView) getActivity().findViewById(R.id.cvaddmember);
                    cv.setVisibility(v.INVISIBLE);
                    dataAdapter.notifyDataSetChanged();


                }
            }
        }
        );
        final EditText neighbourname = (EditText)rootView.findViewById(R.id.Neighbourname);
        final EditText phone =(EditText)rootView.findViewById(R.id.Neighbourphoneno);
        com.rey.material.widget.Button addneighbour = (com.rey.material.widget.Button)rootView.findViewById(R.id.btnRegister);
        addneighbour.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            String NeighbourName = neighbourname.getText().toString();
                                            String Phone = phone.getText().toString();
                                            if (NeighbourName.equals("") && Phone.equals("")) {
                                                final Snackbar snackbar = Snackbar.make(rootView, "One or More Fields are Empty", Snackbar.LENGTH_SHORT);
                                                snackbar.setAction("Dismiss", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        snackbar.dismiss();
                                                    }
                                                });
                                                snackbar.show();

                                            }
                                            else {
                                                dbneighbour=new Neighbourhandler(getContext());
                                                dbhelper.insertmember(NeighbourName, Phone);
                                                CardView cv = (CardView) getActivity().findViewById(R.id.cvaddneighbour);
                                                cv.setVisibility(v.INVISIBLE);
                                                dataAdapter.notifyDataSetChanged();


                                            }
                                        }
                                    }
        );
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}