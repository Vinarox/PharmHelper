package by.bsu.slabko.vladislav.pharmhelper.fragment.pharmacySearch;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.yandex.mapkit.geometry.Point;

import java.util.Collections;

import by.bsu.slabko.vladislav.pharmhelper.R;
import by.bsu.slabko.vladislav.pharmhelper.activities.HomeActivity;
import by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.SearchResultActivity;
import by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.comparators.OrderComparator;
import by.bsu.slabko.vladislav.pharmhelper.activities.singlePharmacyResults.SinglePharmacyResults;
import by.bsu.slabko.vladislav.pharmhelper.constants.Constants;
import by.bsu.slabko.vladislav.pharmhelper.fragment.pharmacySearch.adapters.ItemSearchListAdapter;
import by.bsu.slabko.vladislav.pharmhelper.fragment.pharmacySearch.adapters.MyListViewAdapter;
import by.bsu.slabko.vladislav.pharmhelper.fragment.pharmacySearch.objects.SearchLine;


public class PharmacySearchFragment extends Fragment {
    private final String TAG = "PharmacySearchFragment";
    private static RecyclerView recyclerView;
    private static RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private static Context mContext = HomeActivity.homeContext;

    private static MyListViewAdapter myListViewAdapter;
    private static TextView curPrices = null;


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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Constants.searchRes.clear();
        View v = inflater.inflate(R.layout.fragment_pharmacy_search, container, false);
        curPrices = v.findViewById(R.id.text_sum);
        recyclerView = (RecyclerView) v.findViewById(R.id.my_view);
        registerForContextMenu(recyclerView);

        layoutManager = new LinearLayoutManager((getActivity()));
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ItemSearchListAdapter((getActivity()), inflater,
                ((HomeActivity)getActivity()).getMenuInflater());
        recyclerView.setAdapter(mAdapter);

//////////////////////////////////////////////////
        //ListView lvMain = (ListView) v.findViewById(R.id.my_view);
        // создаем адаптер
        //myListViewAdapter = new MyListViewAdapter(HomeActivity.homeContext, Constants.lines);
        // присваиваем адаптер списку
        //lvMain.setAdapter(myListViewAdapter);
//////////////////////////////////////////////////

        Button searchButton = v.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new SearchListener());
        return v;
    }

    public static void addSearchLine(boolean needNotify){
        int lineSize = Constants.lines.size() + 1;
        Constants.lines.add(new SearchLine(mContext, HomeActivity.homeInflater));
        if(needNotify)
            notifyAllData();
    }
    public static void deleteSearchLine(SearchLine object){
        int index  = Constants.lines.indexOf(object);
        if(index >= 0) {
            Constants.lines.remove(index);
            notifyDataRemove(index);
        }
    }

    public static void notifyAllData(){
        //mAdapter.notifyDataSetChanged();
        mAdapter.notifyItemInserted(Constants.lines.size() - 1);
    }

    public static void notifyDataRemove(int i){
        //mAdapter.notifyDataSetChanged();
        mAdapter.notifyItemRemoved(i);
        //mAdapter.notifyItemRangeChanged(i, Constants.lines.size());
    }




    class SearchListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            final Intent intent = new Intent();
            intent.setClass(mContext, SearchResultActivity.class);
            startActivity(intent);
        }
    }

    public static void setPrices(){
        if(Constants.searchRes.size() > 0) {
            Collections.sort(Constants.searchRes, new OrderComparator());
            Constants.loacotionPoint = new Point(Constants.searchRes.get(0).latitude, Constants.searchRes.get(0).longitude);
            Constants.minPrice = Constants.searchRes.get(0).fullPrice;
            Constants.maxPrice = Constants.searchRes.get(Constants.searchRes.size() - 1).fullPrice;
            String displayed = "Сумма: " + String.format("%.2f", Constants.minPrice)
                    + " - " + String.format("%.2f", Constants.maxPrice) + "р.";
            curPrices.setText(displayed);
        }
    }

    public static void newIntent(int index){
        final Intent intent = new Intent();
        intent.setClass(mContext, SinglePharmacyResults.class);
        intent.putExtra("index", index);
        mContext.startActivity(intent);
    }
}
