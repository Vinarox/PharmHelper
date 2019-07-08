package by.bsu.slabko.vladislav.pharmhelper.activities.searchResult;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PointF;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.RequestPoint;
import com.yandex.mapkit.RequestPointType;
import com.yandex.mapkit.directions.DirectionsFactory;
import com.yandex.mapkit.directions.driving.DrivingOptions;
import com.yandex.mapkit.directions.driving.DrivingRoute;
import com.yandex.mapkit.directions.driving.DrivingRouter;
import com.yandex.mapkit.directions.driving.DrivingSession;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.geometry.Polyline;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.location.Location;
import com.yandex.mapkit.location.LocationListener;
import com.yandex.mapkit.location.LocationStatus;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.Error;
import com.yandex.runtime.image.ImageProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import by.bsu.slabko.vladislav.pharmhelper.R;
import by.bsu.slabko.vladislav.pharmhelper.activities.HomeActivity;
import by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.comparators.LocationComparator;
import by.bsu.slabko.vladislav.pharmhelper.activities.searchResult.comparators.OrderComparator;
import by.bsu.slabko.vladislav.pharmhelper.constants.Constants;
import by.bsu.slabko.vladislav.pharmhelper.dialogs.DialogRoute;

public class SearchResultActivity extends AppCompatActivity implements UserLocationObjectListener,
    DrivingSession.DrivingRouteListener{
    private  Point startPoint;
    private  Point endPoint;
    private  List<Point> routePoints;
    private  boolean currentLocationChecker = false;
    private  boolean roadPointsChecker = false;

    private MapView mapview;
    private RecyclerView recyclerView;
    private  RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public  SearchResultActivity instance;
    private UserLocationLayer userLocationLayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        askPermissions(instance);
                this.getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            MapKitFactory.getInstance().createLocationManager().requestSingleUpdate(new YandexLocationListener());
        }

        MapKitFactory.setApiKey(Constants.YANDEX_MAP_KEY);
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_search_result);
        final LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_search_result, null, false);

        // Укажите имя activity вместо map.
        mapview = (MapView)view.findViewById(R.id.my_mapview);

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
                if(Constants.searchRes.size() > 0) {
                    if (isChecked) {
                        if (!checkPermissions()) {
                            askPermissions(instance);
                        }
                        if (!checkPermissions()) {
                            buttonView.setChecked(false);
                        } else {

                            Collections.sort(Constants.searchRes, new LocationComparator());
                            Constants.loacotionPoint = new Point(Constants.searchRes.get(0).latitude, Constants.searchRes.get(0).longitude);
                        }
                    } else {
                        Collections.sort(Constants.searchRes, new OrderComparator());
                        Constants.loacotionPoint = new Point(Constants.searchRes.get(0).latitude, Constants.searchRes.get(0).longitude);
                        Constants.minPrice = Constants.searchRes.get(0).fullPrice;
                        Constants.maxPrice = Constants.searchRes.get(Constants.searchRes.size() - 1).fullPrice;
                    }
                    notifyAllData();
                    mapview.getMap().getMapObjects().clear();
                    mapview.getMap().getMapObjects().addPlacemark(
                            new Point(Constants.searchRes.get(0).latitude, Constants.searchRes.get(0).longitude)
                    );
                    setCameraPosition(Constants.searchRes.get(0).latitude, Constants.searchRes.get(0).longitude, 18f);
                }
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
        mapview.onStart();
        MapKitFactory.getInstance().onStart();
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

    public  SearchResultActivity getInstance(){
        return instance;
    }

    public  void notifyAllData(){
        mAdapter.notifyDataSetChanged();
        for (SimpleExpandableListAdapter adapter: Constants.adapters) {
            if(adapter != null)
                adapter.notifyDataSetChanged();
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

     Point point;
    public void myFloatingButtonClick(View view) {
        DialogFragment newFragment = new DialogRoute();
        newFragment.show(getSupportFragmentManager(), "missiles");

        //startFindingRoute("current&Байкальская 58");
       /* while(Constants.isDialogFinished == null){}

        if(!Constants.isDialogFinished.equals("nope")){
            String[] destinations = Constants.isDialogFinished.split("&");
            doTask(destinations[0], destinations[1]);
        }*/
    }

    public void startFindingRoute(String info){
        String[] destinations = info.split("&");
        doTask(destinations[0], destinations[1]);
    }


    private  void doTask(String start, String end) {
        if (start.equals("current")) {
            MapKitFactory.initialize(this);
            MapKitFactory.getInstance().createLocationManager().requestSingleUpdate(new YandexLocationListener());
        } else {
            currentLocationChecker = true;
            startPoint = getCoordinatesByAddress(start);
        }
        endPoint = getCoordinatesByAddress(end);
       // while (!currentLocationChecker){}
        currentLocationChecker = false;

        if (startPoint != null && endPoint != null) {
            getRoutePoints(startPoint, endPoint);
            while (!roadPointsChecker) {}
            roadPointsChecker = false;
            if (routePoints.size() > 0) {
                findNearestPharmacies();
            }
        }

    }



    @Override
    public void onDrivingRoutes(@NonNull List<DrivingRoute> routes) {
        if (routes.size() > 0) {
            Polyline polyline = routes.get(0).getGeometry();
            routePoints = polyline.getPoints();
            roadPointsChecker = true;
        }
    }

    @Override
    public void onDrivingRoutesError(@NonNull Error error) {

    }

    private  Point getCoordinatesByAddress(String address) {
        Point result = null;
        try {
            Geocoder geocoder = new Geocoder(HomeActivity.homeContext, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocationName(address, 1,
                    Constants.LOW_LEFT_LAT, Constants.LOW_LEFT_LON,
                    Constants.UP_RIGHT_LAT, Constants.UP_RIGHT_LON);
            if(addresses.size() > 0)
                result = new Point(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    private  void getRoutePoints(Point startPoint, Point endPoint){
        List<RequestPoint> points = new ArrayList<RequestPoint>();
        points.add(new RequestPoint(startPoint, RequestPointType.WAYPOINT, null));
        points.add(new RequestPoint(endPoint, RequestPointType.WAYPOINT, null));
        DrivingOptions drivingOptions = new DrivingOptions();
        DrivingRouter drivingRouter = DirectionsFactory.getInstance().createDrivingRouter();
        drivingRouter.requestRoutes(points, drivingOptions, getInstance());
    }

    private  void findNearestPharmacies(){
        //TODO algorithm of searching for nearest pharmacies
    }

     class YandexLocationListener implements LocationListener{
        @Override
        public void onLocationUpdated(@NonNull Location location) {
            Log.d("location", "oyy yes");
            startPoint = location.getPosition();
            currentLocationChecker = true;
        }

        @Override
        public void onLocationStatusUpdated(@NonNull LocationStatus locationStatus) {
        }
    }

}
