package com.example.eventfinder;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SearchResults extends Fragment {

    public SearchResults() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        View view = inflater.inflate(R.layout.fragment_search_results, container, false);

        Button bBack = view.findViewById(R.id.buttonBack);

        bBack.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             FragmentManager fragmentManager = getParentFragmentManager();
//             fragmentManager.popBackStackImmediate();     // not working
//             fragmentManager.popBackStack();     // not working
                getActivity().getSupportFragmentManager().popBackStack();

                                     }
                                 }
        );

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_results, container, false);
    }
}