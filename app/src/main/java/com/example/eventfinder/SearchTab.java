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

import com.google.android.material.snackbar.Snackbar;


public class SearchTab extends Fragment {

    public SearchTab() {
        // empty constructor
    }

    // boolean to set when the auto-detect switch is on/off
    boolean switchIsOn = false;
    // one boolean variable to check whether all the text fields are filled by the user properly or not.
    boolean isAllFieldsChecked = false;

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
                    // display search results
                    Snackbar snackbar = Snackbar.make(view,"All validated",Snackbar.LENGTH_SHORT);
                    snackbar.setTextColor(getResources().getColor(R.color.black));
                    snackbar.show();
                    View sbView = snackbar.getView();
                    sbView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.offWhite));

                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.parent_fragment, new SearchResults()).addToBackStack(null).commit();

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




}