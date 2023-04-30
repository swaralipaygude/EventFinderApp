package com.example.eventfinder;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SearchResults extends Fragment {

    public SearchResults() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_search_results, container, false);

        // Retrieve the arguments Bundle
        Bundle args = getArguments();
        String keyword = args.getString("keyword");
        String unit = args.getString("unit");
        String segmentId = args.getString("category_segment_id");
        String geoPoint = args.getString("geopoint");
        int radius = args.getInt("radius");

        System.out.println("Search results data: \n keyword, unit, segementID, geopoint, radius :");
        System.out.println(keyword + "\t" + unit + "\t" + segmentId + "\t" + geoPoint + "\t" + radius);

        HashMap yourParams = new HashMap<>();
        yourParams.put("keyword",keyword);
        yourParams.put("segmentId",segmentId);
        yourParams.put("radius",radius);
        yourParams.put("unit",unit);
        yourParams.put("geoPoint",geoPoint);

//        callSearchBackendApi();

        Button bBack = view.findViewById(R.id.buttonBack);
        bBack.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             FragmentManager fragmentManager = getParentFragmentManager();
//             fragmentManager.popBackStackImmediate();     // not working
//             fragmentManager.popBackStack();     // not working
                getActivity().getSupportFragmentManager().popBackStack();   // not working

         }
        }
        );

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_results, container, false);
    }


    public void callSearchBackendApi(HashMap yourParams) {

        BackendAPI.callBackendAPI(getActivity().getApplicationContext(),"/search", yourParams,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle response
                        System.out.println("TM response string : " + response);

                        try {
                            JSONObject tm_json_response = new JSONObject(response);
                            JSONObject json_data = tm_json_response.getJSONObject("data");

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
                        System.out.println("TM error:" + error.getMessage());
                    }
                });

    }

}