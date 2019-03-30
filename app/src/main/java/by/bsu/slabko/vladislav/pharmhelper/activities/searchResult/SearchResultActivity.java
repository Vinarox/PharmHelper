package by.bsu.slabko.vladislav.pharmhelper.activities.searchResult;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;

import java.util.zip.Inflater;

import by.bsu.slabko.vladislav.pharmhelper.R;
import by.bsu.slabko.vladislav.pharmhelper.activities.HomeActivity;
import by.bsu.slabko.vladislav.pharmhelper.constants.Constants;
import by.bsu.slabko.vladislav.pharmhelper.fragment.pharmacySearch.adapters.ItemSearchListAdapter;

public class SearchResultActivity extends AppCompatActivity {
    private MapView mapview;
    private RecyclerView recyclerView;
    private static RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public static SearchResultActivity instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        MapKitFactory.setApiKey(Constants.YANDEX_MAP_KEY);
        setContentView(R.layout.activity_search_result);


        MapKitFactory.initialize(HomeActivity.homeContext);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_search_result, null, false);

        // Укажите имя activity вместо map.
        mapview = (MapView)view.findViewById(R.id.mapview);
        mapview.getMap().move(
                new CameraPosition(new Point(53.900706, 27.559335), 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);

        recyclerView = (RecyclerView) findViewById(R.id.my_view1);
        recyclerView.setHasFixedSize(true);
        //registerForContextMenu(recyclerView);

        layoutManager = new LinearLayoutManager(getApplication());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ResultListAdapter(getApplicationContext(), inflater);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        mapview.onStop();
        MapKitFactory.getInstance().onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapview.onStart();
        MapKitFactory.getInstance().onStart();
    }

    public static SearchResultActivity getInstance(){
        return instance;
    }

    public static void notifyAllData(){
        mAdapter.notifyDataSetChanged();
    }
}
