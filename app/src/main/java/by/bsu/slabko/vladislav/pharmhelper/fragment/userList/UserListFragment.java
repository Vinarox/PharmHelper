package by.bsu.slabko.vladislav.pharmhelper.fragment.userList;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.RequestPoint;
import com.yandex.mapkit.RequestPointType;
import com.yandex.mapkit.directions.DirectionsFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.geometry.Polyline;
import com.yandex.mapkit.geometry.PolylinePosition;
import com.yandex.mapkit.geometry.Subpolyline;
import com.yandex.mapkit.geometry.SubpolylineHelper;
import com.yandex.mapkit.location.Location;
import com.yandex.mapkit.location.LocationListener;
import com.yandex.mapkit.location.LocationManager;
import com.yandex.mapkit.location.LocationStatus;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.search.SearchFactory;
import com.yandex.mapkit.transport.TransportFactory;
import com.yandex.mapkit.transport.masstransit.MasstransitOptions;
import com.yandex.mapkit.transport.masstransit.MasstransitRouter;
import com.yandex.mapkit.transport.masstransit.Route;
import com.yandex.mapkit.transport.masstransit.Section;
import com.yandex.mapkit.transport.masstransit.SectionMetadata;
import com.yandex.mapkit.transport.masstransit.Session;
import com.yandex.mapkit.transport.masstransit.TimeOptions;
import com.yandex.mapkit.transport.masstransit.WayPoint;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.runtime.Error;
import com.yandex.runtime.image.ImageProvider;
import com.yandex.mapkit.directions.driving.*;

import java.io.IOException;
import java.security.Policy;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import by.bsu.slabko.vladislav.pharmhelper.R;
import by.bsu.slabko.vladislav.pharmhelper.activities.HomeActivity;
import by.bsu.slabko.vladislav.pharmhelper.constants.Constants;
import by.bsu.slabko.vladislav.pharmhelper.fragment.SelectFragment;
import by.bsu.slabko.vladislav.pharmhelper.services.CleverCloudDataService;

public class UserListFragment extends Fragment
        implements Session.RouteListener,
        DrivingSession.DrivingRouteListener{
    private MapView mapview;
    private UserLocationLayer userLocationLayer;

    public UserListFragment() {
        // Required empty public constructor
    }

    public static UserListFragment newInstance() {
        UserListFragment fragment = new UserListFragment();
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
        /*MapKitFactory.setApiKey(Constants.YANDEX_MAP_KEY);
        MapKitFactory.initialize(HomeActivity.homeContext);
        TransportFactory.initialize(HomeActivity.homeContext);
        DirectionsFactory.initialize(HomeActivity.homeContext);*/



        View v = inflater.inflate(R.layout.fragment_user_list, container, false);

        mapview = (MapView)v.findViewById(R.id.my_mapview2);
       /* mapview.getMap().getMapObjects().addPlacemark(
                Constants.loacotionPoint,
                ImageProvider.fromResource(HomeActivity.homeContext, R.drawable.ic_add_location)
        );*/



       List<Point> list = new ArrayList<>();
        addMyPlacemarks(new Point(53.931355, 27.508215));
        addMyPlacemarks(new Point(53.933549, 27.501425));
        addMyPlacemarks(new Point(53.926186, 27.517556));
        addMyPlacemarks(new Point(53.917010, 27.533123));
        addMyPlacemarks(new Point(53.915840, 27.534748));
        addMyPlacemarks(new Point(53.915227, 27.533398));
        addMyPlacemarks(new Point(53.911462, 27.543045));
        addMyPlacemarks(new Point(53.908511, 27.549107));
        addMyPlacemarks(new Point(53.905354, 27.552169));
        addMyPlacemarks(new Point(53.899569, 27.557719));
        addMyPlacemarks(new Point(53.898527, 27.555514));
        addMyPlacemarks(new Point(53.897078, 27.551067));


        /*list.add(new Point(53.931355, 27.508215));
        list.add(new Point(53.933549, 27.501425));
        list.add(new Point(53.926186, 27.517556));
        list.add(new Point(53.917010, 27.533123));
        list.add(new Point(53.915840, 27.534748));
        list.add(new Point(53.915227, 27.533398));
        list.add(new Point(53.911462, 27.543045));
        list.add(new Point(53.908511, 27.549107));
        list.add(new Point(53.905354, 27.552169));
        list.add(new Point(53.899569, 27.557719));
        list.add(new Point(53.898527, 27.555514));
        list.add(new Point(53.897078, 27.551067));
        addMyPlacemarks(list);*/

        mapview.getMap().move(
                new CameraPosition(new Point(53.915227, 27.533398), 3.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);

        userLocationLayer = mapview.getMap().getUserLocationLayer();
        userLocationLayer.setEnabled(true);
        userLocationLayer.setAutoZoomEnabled(true);
        userLocationLayer.setHeadingEnabled(true);
        //LocationManager locationManager = MapKitFactory.getInstance().createLocationManager();
        MapKitFactory.getInstance().createLocationManager().requestSingleUpdate(new LocationListener() {
            @Override
            public void onLocationUpdated(@NonNull Location location) {
                double lat = location.getPosition().getLatitude();
                double lon = location.getPosition().getLongitude();
            }

            @Override
            public void onLocationStatusUpdated(@NonNull LocationStatus locationStatus) {

            }
        });

        SearchFactory.initialize(HomeActivity.homeContext);
        //SearchFactory.getInstance().createSearchManager()
        Geocoder geocoder = new Geocoder(HomeActivity.homeContext, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName("Байкальская 58", 1,
                    Constants.LOW_LEFT_LAT, Constants.LOW_LEFT_LON,
                    Constants.UP_RIGHT_LAT, Constants.UP_RIGHT_LON);
            double ll = addresses.get(0).getLatitude();
            double l2 = addresses.get(0).getLongitude();
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final Point ROUTE_START_LOCATION = new Point(53.894011, 27.546973);
        final Point ROUTE_END_LOCATION = new Point(53.932455, 27.510127);


        MasstransitRouter mtRouter;
        MasstransitOptions options = new MasstransitOptions(
                new ArrayList<String>(),
                new ArrayList<String>(),
                new TimeOptions());
        List<RequestPoint> points = new ArrayList<RequestPoint>();
        points.add(new RequestPoint(ROUTE_START_LOCATION, RequestPointType.WAYPOINT, null));
        points.add(new RequestPoint(ROUTE_END_LOCATION, RequestPointType.WAYPOINT, null));
    //    mtRouter = TransportFactory.getInstance().createMasstransitRouter();
      //  Session s = mtRouter.requestRoutes(points, options, this);



        DrivingOptions drivingOptions = new DrivingOptions();

        DrivingRouter drivingRouter = DirectionsFactory.getInstance().createDrivingRouter();
        drivingRouter.requestRoutes(points, drivingOptions, this);
        //DirectionsFactory.getInstance().createDrivingRouter().requestRoutes(points, null, null);

        return v;
    }

    @Override
    public void onMasstransitRoutes(List<Route> routes) {
        // In this example we consider first alternative only
        if (routes.size() > 0) {
            List<WayPoint> wayPoints = routes.get(0).getWayPoints();
            for (Section section : routes.get(0).getSections()) {
                SectionMetadata.SectionData a = section.getMetadata().getData();
                Polyline b = SubpolylineHelper.subpolyline(routes.get(0).getGeometry(), section.getGeometry());
                System.out.println();
            }
        }
    }

    @Override
    public void onMasstransitRoutesError(@NonNull Error error) {

    }

    @Override
    public void onStop() {
        mapview.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapview.onStart();
    }

    @Override
    public void onDrivingRoutes(@NonNull List<DrivingRoute> routes) {
        if (routes.size() > 0) {
            Polyline polyline = routes.get(0).getGeometry();
            mapview.getMap().getMapObjects().addCollection().addPolyline(polyline);
            List<Point> myPoints = polyline.getPoints();
            for(Point obj: myPoints){

                double la = obj.getLatitude();
                double lo = obj.getLongitude();
            }
        }
    }

    @Override
    public void onDrivingRoutesError(@NonNull Error error) {

    }

    private void addMyPlacemarks(Point point) {
        mapview.getMap().getMapObjects().addPlacemark(
                point,
                ImageProvider.fromResource(HomeActivity.homeContext, R.drawable.ic_add_location)
        );
    }
}