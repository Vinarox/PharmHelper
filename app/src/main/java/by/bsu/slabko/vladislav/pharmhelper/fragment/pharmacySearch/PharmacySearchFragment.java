package by.bsu.slabko.vladislav.pharmhelper.fragment.pharmacySearch;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import by.bsu.slabko.vladislav.pharmhelper.R;


public class PharmacySearchFragment extends Fragment {

    public PharmacySearchFragment() {
        // Required empty public constructor
    }

    public static PharmacySearchFragment newInstance() {
        PharmacySearchFragment fragment = new PharmacySearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pharmacy_search, container, false);
    }
}
