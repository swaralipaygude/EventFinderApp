package com.example.eventfinder;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class SearchTab extends Fragment {

    public SearchTab() {
        // empty constructor
    }

    // ALL VARIABLES

    // boolean to set when the auto-detect switch is on/off
    boolean switchIsOn = false;
    // one boolean variable to check whether all the text fields are filled by the user properly or not.
    boolean isAllFieldsChecked = false;

    String keyword = "";
    String category = "";
    String location = "";
    String category_segment_id = "";
    int distance = 0;
    String geopoint = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_tab, container, false);

        Button bSearch = view.findViewById(R.id.buttonSearch);
        Button bClear = view.findViewById(R.id.buttonClear);
        EditText etKeyword = view.findViewById(R.id.editTextKeyword);
        EditText etLocation = view.findViewById(R.id.editTextLocation);
        EditText etDistance = view.findViewById(R.id.editTextNumberDistance);
        Spinner spinnerCategory = view.findViewById(R.id.spinnerCategory);
        Switch switch_auto_detect = (Switch) view.findViewById(R.id.switchAutoDetect);


//        FOR CATEGORY SPINNER

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.categories, R.layout.spinner_list);
        adapter.setDropDownViewResource(R.layout.spinner_list);
        spinnerCategory.setAdapter(adapter);

//        FOR CATEGORY SPINNER


//      AUTO-DETECT SWITCH TOGGLE

        // initiate a Switch
        switch_auto_detect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be true if the switch is in the On position
                if(isChecked) {
                    etLocation.setVisibility(View.GONE);
                    switchIsOn = true;
                    getCurrentLocation();   //get current loc by ipinfo

                }
                else {
                    etLocation.setVisibility(View.VISIBLE);
                    switchIsOn = false;

                }
            }
        });

//      AUTO-DETECT SWITCH TOGGLE


//      FORM VALIDATION

        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etKeyword.length() == 0) {
                    System.out.println("keyword empty");
                    isAllFieldsChecked = false;
                    System.out.println("keyw bool: " + isAllFieldsChecked);
                }

                else if (etLocation.length() == 0 && !switchIsOn) {
                    isAllFieldsChecked = false;
                    System.out.println("loc bool: " + isAllFieldsChecked);
                }

                else {
                    isAllFieldsChecked = true;
                    System.out.println("else bool: " + isAllFieldsChecked);
                }

                System.out.println("bool: " + isAllFieldsChecked);
                if (isAllFieldsChecked) {

                    keyword = etKeyword.getText().toString();
                    category = spinnerCategory.getSelectedItem().toString();
                    location = etLocation.getText().toString();
                    distance = Integer.valueOf(etDistance.getText().toString());
                    switch (category) {
                        case "Music" :
                            category_segment_id = "KZFzniwnSyZfZ7v7nJ";
                            break;
                        case "Sports" :
                            category_segment_id = "KZFzniwnSyZfZ7v7nE";
                            break;
                        case "Arts & Theatre" :
                            category_segment_id = "KZFzniwnSyZfZ7v7na";
                            break;
                        case "Film" :
                            category_segment_id = "KZFzniwnSyZfZ7v7nn";
                            break;
                        case "Miscellaneous" :
                            category_segment_id = "KZFzniwnSyZfZ7v7n1";
                            break;
                        default:
                            category_segment_id = "";
                            break;
                    }

                    if(!switchIsOn) {
                    // if auto-detect is off, call backend API with address and checked as 0, returns geohash code
                    HashMap<String, String> yourParams = new HashMap<>();
                    yourParams.put("address",location);
                    yourParams.put("checked","0");
                    geopoint = callLocationBackendApi(yourParams);   // get loc with geocoding api called at backend
                    System.out.println("not checked api called");
                    }


                    // SET BUNDLE DATA
                    Bundle bundle = new Bundle();
                    bundle.putString("keyword", keyword);
                    bundle.putInt("radius", distance);
                    bundle.putString("unit", "miles");
                    bundle.putString("category_segment_id", category_segment_id);
                    bundle.putString("geopoint", geopoint);

                    System.out.println("Search tab data:\n keyword, segementID, geopoint, radius :");
                    System.out.println(keyword + "\t" + category_segment_id + "\t" + geopoint + "\t" + distance);


                    Fragment search_results_frag = new SearchResults();
                    // Set the bundle as the arguments of the fragment
                    search_results_frag.setArguments(bundle);
                    // replacing Search form fragment with Search results fragment
                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.parent_fragment, search_results_frag).addToBackStack(null).commit();

                }

                if (!isAllFieldsChecked) {
                    Snackbar snackbar = Snackbar.make(view,"Please fill all fields",Snackbar.LENGTH_SHORT);
                    snackbar.setTextColor(getResources().getColor(R.color.black));
                    snackbar.show();
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.offWhite));
                }
            }
        });

//      FORM VALIDATION


//      CLEAR BUTTON

        bClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etKeyword.setText("");
                etLocation.setText("");
                etDistance.setText("10");
                spinnerCategory.setSelection(0);
                switch_auto_detect.setChecked(false);

            }

        });

//      CLEAR BUTTON

        // Inflate the layout for this fragment
        return view;

    }

    /* Ipinfo API call to get current location  */
    public void getCurrentLocation() {

        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "https://ipinfo.io?&token=65ff4149aba108";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("ipinfo Response is: " + response);
                        try {
                            JSONObject ipinfo_json_response = new JSONObject(response);
                            System.out.println("ipinfo_json_response :" + ipinfo_json_response); // send this to backend directly, along with "checked", "1"
                            String loc = ipinfo_json_response.getString("loc"); // contains comma-separated lat, lng

                            // if auto-detect is on, call backend API with ipinfo response loc and checked as 1, returns geohash code
                            HashMap<String, String> yourParams = new HashMap<>();
                            yourParams.put("loc",loc);
                            yourParams.put("checked","1");
                            geopoint = callLocationBackendApi(yourParams);
//                            String[] loc = ipinfo_json_response.getString("loc").split(",");
//                            System.out.println("ipinfo lat: " + loc[0]);
//                            System.out.println("ipinfo lng: " + loc[1]);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Couldn't get ipinfo location");
            }
        });
        queue.add(stringRequest);
    }
    /* Ipinfo API call to get current location  */


    public String callLocationBackendApi(HashMap yourParams) {

        BackendAPI.callBackendAPI(getActivity().getApplicationContext(),"/search/location", yourParams,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle response
                        System.out.println("location geohash response string : " + response);

                        try {
                            JSONObject geohash_json_response = new JSONObject(response);
                            geopoint = geohash_json_response.getString("geohash_code");
                            System.out.println("geopoint : " + geopoint);
//                            System.out.println("event_name: " + event_name);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        System.out.println("location backend api error:" + error.getMessage());
                    }
                });
        return geopoint;
    }

}   // class SearchTab