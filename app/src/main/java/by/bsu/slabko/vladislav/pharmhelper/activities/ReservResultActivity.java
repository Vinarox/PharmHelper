package by.bsu.slabko.vladislav.pharmhelper.activities;

import android.content.Context;
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

import by.bsu.slabko.vladislav.pharmhelper.R;
import by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.ResultListAdapter;
import by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.SearchResultActivity;
import by.bsu.slabko.vladislav.pharmhelper.constants.Constants;

public class ReservResultActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private static RecyclerView.Adapter mAdapter;
    public RecyclerView.LayoutManager layoutManager;
    public static ReservResultActivity instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserv_result);
        instance = this;
        //MapKitFactory.setApiKey(Constants.YANDEX_MAP_KEY);


        //MapKitFactory.initialize(HomeActivity.homeContext);
        LayoutInflater inflater = getLayoutInflater();
        //View view = inflater.inflate(R.layout.activity_reserv_result, null, false);

        // Укажите имя activity вместо map.
       /* mapview = (MapView)view.findViewById(R.id.mapview);
        mapview.getMap().move(
                new CameraPosition(new Point(53.900706, 27.559335), 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);*/

        recyclerView = (RecyclerView) findViewById(R.id.my_view_0);
        recyclerView.setHasFixedSize(true);

        //registerForContextMenu(recyclerView);

        layoutManager = new CustomLinearLayoutManager(getApplication(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ReservAdapter(getApplicationContext(), inflater);
        recyclerView.setAdapter(mAdapter);
    }

    public static ReservResultActivity getInstance(){
        return instance;
    }

    public static void notifyAllData(){
        mAdapter.notifyDataSetChanged();
    }


    public class CustomLinearLayoutManager extends LinearLayoutManager {
        public CustomLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);

        }

        // it will always pass false to RecyclerView when calling "canScrollVertically()" method.
        @Override
        public boolean canScrollVertically() {
            return Constants.canScrolled;
        }
    }
}
