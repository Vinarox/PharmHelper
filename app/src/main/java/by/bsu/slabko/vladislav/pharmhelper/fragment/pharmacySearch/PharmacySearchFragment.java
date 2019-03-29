package by.bsu.slabko.vladislav.pharmhelper.fragment.pharmacySearch;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import by.bsu.slabko.vladislav.pharmhelper.R;
import by.bsu.slabko.vladislav.pharmhelper.activities.HomeActivity;
import by.bsu.slabko.vladislav.pharmhelper.constants.Constants;
import by.bsu.slabko.vladislav.pharmhelper.database.MedicineEntity;
import by.bsu.slabko.vladislav.pharmhelper.database.MyContentProvider;
import by.bsu.slabko.vladislav.pharmhelper.fragment.pharmacySearch.adapters.ItemSearchListAdapter;
import by.bsu.slabko.vladislav.pharmhelper.fragment.pharmacySearch.objects.SearchLine;


public class PharmacySearchFragment extends Fragment {
    private final String TAG = "PharmacySearchFragment";
    private RecyclerView recyclerView;
    private static RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static Context mContext = HomeActivity.homeContext;


    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    public PharmacySearchFragment() {
        // Required empty public constructor
    }

    public static PharmacySearchFragment newInstance() {
        PharmacySearchFragment fragment = new PharmacySearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        Log.d("PharmacySearchFragment", "0000000000000000000");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FirebaseApp.initializeApp(HomeActivity.homeContext);
        //myRef = FirebaseDatabase.getInstance().getReference();
        //mAuth = FirebaseAuth.getInstance();
        //dododoanddo();
        Log.d(TAG, "11111111111111111111111111");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "2222222222222222222222222222");
        View v = inflater.inflate(R.layout.fragment_pharmacy_search, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.my_view);
        recyclerView.setHasFixedSize(true);
        //registerForContextMenu(recyclerView);

        layoutManager = new LinearLayoutManager((getActivity()));
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ItemSearchListAdapter((getActivity()), inflater,
                ((HomeActivity)getActivity()).getMenuInflater());
        recyclerView.setAdapter(mAdapter);

        Button searchButton = v.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new SearchListener());
        return v;
    }

    public static void addSearchLine(boolean needNotify){
        int lineSize = Constants.lines.size() + 1;
        Constants.lines.add(new SearchLine(mContext));
        if(needNotify)
            notifyAllData();
    }
    public static void deleteSearchLine(SearchLine object){
        int index  = Constants.lines.indexOf(object);
        if(index >= 0)
            Constants.lines.remove(index);
        notifyAllData();
    }

    public static void notifyAllData(){
        mAdapter.notifyDataSetChanged();
    }


    public void dododoanddo(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>(){};
                List<String> n = dataSnapshot.child("10118250").getValue(t);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    class SearchListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            MyContentProvider db = MyContentProvider.getInstance();
            List<MedicineEntity> result = db.getItemByName("азикар");
        }
    }
}
