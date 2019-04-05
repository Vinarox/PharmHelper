package by.bsu.slabko.vladislav.pharmhelper.activities.searchResult;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PointF;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.image.ImageProvider;

import java.util.Collections;
import java.util.zip.Inflater;

import by.bsu.slabko.vladislav.pharmhelper.R;
import by.bsu.slabko.vladislav.pharmhelper.activities.HomeActivity;
import by.bsu.slabko.vladislav.pharmhelper.activities.ReservResultActivity;
import by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.comparators.LocationComparator;
import by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.comparators.OrderComparator;
import by.bsu.slabko.vladislav.pharmhelper.constants.Constants;
import by.bsu.slabko.vladislav.pharmhelper.fragment.pharmacySearch.adapters.ItemSearchListAdapter;

public class SearchResultActivity extends AppCompatActivity implements UserLocationObjectListener {
    private MapView mapview;
    private RecyclerView recyclerView;
    private static RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public static SearchResultActivity instance;
    private UserLocationLayer userLocationLayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        askPermissions(instance);
        LocationManager locationManager = (LocationManager)
                this.getSystemService(LOCATION_SERVICE);
        LocationListener locationListener = new MyLocationListener();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 0, 1, locationListener);
        }

        MapKitFactory.setApiKey(Constants.YANDEX_MAP_KEY);
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_search_result);
        final LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_search_result, null, false);

        // Укажите имя activity вместо map.
        mapview = (MapView)view.findViewById(R.id.my_mapview);
        /*mapview.getMap().move(
                new CameraPosition(new Point(53.900706, 27.559335), 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);*/

        setCameraPosition(53.900706, 27.559335, 18f);
        Collections.sort(Constants.searchRes, new OrderComparator());
        if(Constants.searchRes.size() > 0) {
            Constants.loacotionPoint = new Point(Constants.searchRes.get(0).latitude, Constants.searchRes.get(0).longitude);
            Constants.minPrice = Constants.searchRes.get(0).fullPrice;
            Constants.maxPrice = Constants.searchRes.get(Constants.searchRes.size() - 1).fullPrice;
            if (Constants.searchRes.size() > 0) {
                mapview.getMap().getMapObjects().addPlacemark(
                        new Point(Constants.searchRes.get(0).latitude, Constants.searchRes.get(0).longitude)
                );
                setCameraPosition(Constants.searchRes.get(0).latitude, Constants.searchRes.get(0).longitude, 18f);
            }
        }

        userLocationLayer = mapview.getMap().getUserLocationLayer();
        userLocationLayer.setEnabled(true);
        userLocationLayer.setAutoZoomEnabled(true);
        userLocationLayer.setHeadingEnabled(true);
        userLocationLayer.setObjectListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.my_view1);
        recyclerView.setHasFixedSize(true);
        //registerForContextMenu(recyclerView);

        layoutManager = new CustomLinearLayoutManager(getApplication(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ResultListAdapter(getApplicationContext(), inflater);
        recyclerView.setAdapter(mAdapter);

        SwitchCompat switchCompat = findViewById(R.id.switch_compat);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    if (!checkPermissions()) {
                        askPermissions(instance);
                    }
                    if (!checkPermissions()) {
                        buttonView.setChecked(false);
                    }
                    else {
                        Collections.sort(Constants.searchRes, new LocationComparator());
                        Constants.loacotionPoint = new Point(Constants.searchRes.get(0).latitude, Constants.searchRes.get(0).longitude);
                    }
                }
                else {
                    Collections.sort(Constants.searchRes, new OrderComparator());
                    Constants.loacotionPoint = new Point(Constants.searchRes.get(0).latitude, Constants.searchRes.get(0).longitude);
                    Constants.minPrice = Constants.searchRes.get(0).fullPrice;
                    Constants.maxPrice = Constants.searchRes.get(Constants.searchRes.size()-1).fullPrice;
                }
                notifyAllData();
                mapview.getMap().getMapObjects().clear();
                mapview.getMap().getMapObjects().addPlacemark(
                        new Point(Constants.searchRes.get(0).latitude,Constants.searchRes.get(0).longitude)
                );
                setCameraPosition(Constants.searchRes.get(0).latitude,Constants.searchRes.get(0).longitude, 18f);
            }
        });
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
        MapKitFactory.getInstance().onStart();
        mapview.onStart();
    }

    private void setCameraPosition(double latitude, double longitude, float zoom){
        mapview.getMap().move(
                new CameraPosition(new Point(latitude, longitude), zoom, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 2),
                null);

    }

    private boolean checkPermissions(){
        if (ActivityCompat.checkSelfPermission(instance, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(instance, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    private void askPermissions(Activity instance){
        ActivityCompat.requestPermissions(instance, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                120);
        ActivityCompat.requestPermissions(instance, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION},
                120);
    }

    public class CustomLinearLayoutManager extends LinearLayoutManager {
        public CustomLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);

        }

        // it will always pass false to RecyclerView when calling "canScrollVertically()" method.
        @Override
        public boolean canScrollVertically() {
            return true;
        }
    }

    public static SearchResultActivity getInstance(){
        return instance;
    }

    public static void notifyAllData(){
        mAdapter.notifyDataSetChanged();
        for (SimpleExpandableListAdapter adapter: Constants.adapters) {
            if(adapter != null)
                adapter.notifyDataSetChanged();
        }
    }


    class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            Constants.myLongitude = location.getLongitude();
            Constants.myLatitude = location.getLatitude();
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }


    @Override
    public void onObjectAdded(@NonNull UserLocationView userLocationView) {
        userLocationLayer.setAnchor(
                new PointF((float) (mapview.getWidth() * 0.5), (float) (mapview.getHeight() * 0.5)),
                new PointF((float) (mapview.getWidth() * 0.5), (float) (mapview.getHeight() * 0.83)));

        // При определении направления движения устанавливается следующая иконка
        userLocationView.getArrow().setIcon(ImageProvider.fromResource(
                this, R.drawable.user_arrow));
        // При получении координат местоположения устанавливается следующая иконка
        userLocationView.getPin().setIcon(ImageProvider.fromResource(
                this, R.drawable.user_arrow));
        // Обозначается точность определения местоположения с помощью окружности
        userLocationView.getAccuracyCircle().setFillColor(Color.BLUE);

    }

    @Override
    public void onObjectRemoved(@NonNull UserLocationView userLocationView) {

    }

    @Override
    public void onObjectUpdated(@NonNull UserLocationView userLocationView, @NonNull ObjectEvent objectEvent) {

    }
}
