package by.bsu.slabko.vladislav.pharmhelper.activities.singlePharmacyResults;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;

import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;

import by.bsu.slabko.vladislav.pharmhelper.R;
import by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.ResultListAdapter;
import by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.SearchResultActivity;
import by.bsu.slabko.vladislav.pharmhelper.activities.singlePharmacyResults.adapters.RecycleViewSinglePharmacyAdapter;
import by.bsu.slabko.vladislav.pharmhelper.constants.Constants;

public class SinglePharmacyResults extends AppCompatActivity {
    private RecyclerView recyclerView;
    private static RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_pharmacy_results);
        Intent intent = getIntent();

        int index = intent.getIntExtra("index", 0);


        final LayoutInflater inflater = getLayoutInflater();

        TextView pharm = findViewById(R.id.single_pharm);
        TextView address = findViewById(R.id.single_address);
        TextView phone = findViewById(R.id.single_phone);

        pharm.setText(Constants.searchRes.get(index).pharmName);
        address.setText(Constants.searchRes.get(index).district);
        phone.setText(Constants.searchRes.get(index).phone);

        recyclerView = findViewById(R.id.single_recycleview);
        recyclerView.setHasFixedSize(true);
        //registerForContextMenu(recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new RecycleViewSinglePharmacyAdapter(getApplicationContext(), inflater, index);
        recyclerView.setAdapter(mAdapter);
    }
}
